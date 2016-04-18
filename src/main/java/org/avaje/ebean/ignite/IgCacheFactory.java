package org.avaje.ebean.ignite;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.cache.ServerCacheFactory;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheType;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.plugin.SpiServer;
import com.avaje.ebeaninternal.server.cache.DefaultServerCache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.IgniteSet;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.avaje.ebean.ignite.config.ConfigManager;
import org.avaje.ebean.ignite.config.ConfigPair;
import org.avaje.ebean.ignite.config.ConfigXmlReader;
import org.avaje.ebean.ignite.config.L2Configuration;
import org.avaje.ignite.IgniteConfigBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating L2 server caches with Apache Ignite.
 * <p>
 * The L2 Query cache is effectively an always near/no server cache and we
 * use Ignite to send/receive invalidation messages for the query caches.
 * </p>
 * <p>
 * All the 'Bean' caches will typically be partitioned with an optional 'near' cache option or replicated.
 * REPLICATED caches would be a good choice for small cardinality/stable bean types (countries, currencies etc).
 * </p>
 */
public class IgCacheFactory implements ServerCacheFactory {

  private static final Logger queryLogger = LoggerFactory.getLogger("org.avaje.ebean.cache.QUERY");

  private static final Logger logger = LoggerFactory.getLogger("org.avaje.ebean.cache.CACHE");

  private static final String QC_CREATE = "L2QueryCacheCreate";

  private static final String QC_INVALIDATE = "L2QueryCacheInvalidate";

  private final ConcurrentHashMap<String, IgQueryCache> queryCaches;

  private final ConfigManager configManager;

  private Ignite ignite;

  private IgniteMessaging messaging;

  private IgniteSet<String> queryCacheKeys;

  private SpiServer pluginServer;

  public IgCacheFactory() {
    this.queryCaches = new ConcurrentHashMap<>();
    this.configManager = new ConfigManager(readConfiguration());
  }

  /**
   * Read the L2 cache configuration.
   */
  private L2Configuration readConfiguration() {

    // check system property first
    String config = System.getProperty("ebeanIgniteConfig");
    if (config != null) {
      File file = new File(config);
      if (!file.exists()) {
        throw new IllegalStateException("ebean ignite configuration not found at " + config);
      }
      return ConfigXmlReader.read(file);
    }

    // look for local configuration external to the application
    File file = new File("ebean-ignite-config.xml");
    if (file.exists()) {
      return ConfigXmlReader.read(file);
    }

    // look for configuration inside the application
    return ConfigXmlReader.read("/ebean-ignite-config.xml");
  }

  @Override
  public void init(EbeanServer ebeanServer) {

    pluginServer = ebeanServer.getPluginApi();

    ServerConfig serverConfig = ebeanServer.getPluginApi().getServerConfig();

    // programmatically set into ServerConfig - typical DI setup
    IgniteConfiguration configuration = (IgniteConfiguration) serverConfig.getServiceObject("igniteConfiguration");
    if (configuration == null) {

      Properties properties = serverConfig.getProperties();
      if (properties != null) {
        configuration = new IgniteConfigBuilder("ignite", properties).build();
      } else {
        configuration = new IgniteConfiguration();
      }
    }

    if (configuration.getGridLogger() == null) {
      configuration.setGridLogger(new Slf4jLogger(logger));
    }

    logger.debug("Starting Ignite");
    ignite = Ignition.start(configuration);

    messaging = ignite.message(ignite.cluster().forRemotes());
    messaging.localListen(QC_INVALIDATE, new QueryCacheInvalidateListener());
    messaging.localListen(QC_CREATE, new QueryCacheCreatedListener());

    // find all the query caches that have been started and register
    // them early so that we immediately invalidate as necessary
    queryCacheKeys = ignite.set("queryCacheNames", new CollectionConfiguration());
    for (String key : queryCacheKeys) {
      try {
        logger.debug("init query cache for {}", key);
        pluginServer.initQueryCache(key);

      } catch (Exception e) {
        logger.error("Failed to initiate query cache for " + key, e);
      }
    }
  }

  private class QueryCacheCreatedListener implements IgniteBiPredicate<UUID, String> {
    @Override
    public boolean apply(UUID uuid, String key) {
      queryCacheCreated(key);
      return true;
    }
  }

  private class QueryCacheInvalidateListener implements IgniteBiPredicate<UUID, String> {
    @Override
    public boolean apply(UUID uuid, String key) {
      queryCacheInvalidate(key);
      return true;
    }
  }

  @Override
  public ServerCache createCache(ServerCacheType type, String key, ServerCacheOptions options) {

    switch (type) {
      case QUERY:
        return createQueryCache(key);

      default:
        return createNormalCache(type, key);
    }
  }

  private ServerCache createNormalCache(ServerCacheType type, String key) {

    ConfigPair pair = configManager.getConfig(type, key);

    pair.setName(fullName(type, key));

    IgniteCache cache;
    if (pair.hasNearCache()) {
      cache = ignite.getOrCreateCache(pair.getMain(), pair.getNear());

    } else {
      cache = ignite.getOrCreateCache(pair.getMain());
    }

    return new IgCache(cache);
  }

  /**
   * Return the full cache name (JMX safe name).
   */
  @NotNull
  private String fullName(ServerCacheType type, String key) {
    return type.name() + "-" + key;
  }

  /**
   * Create a local/near query cache.
   */
  private ServerCache createQueryCache(String key) {

    synchronized (this) {
      IgQueryCache cache = queryCaches.get(key);
      if (cache == null) {
        sendQueryCacheCreated(key);
        cache = new IgQueryCache(key, new ServerCacheOptions());
        queryCaches.put(key, cache);
      }
      return cache;
    }
  }

  /**
   * Local only implementation with no serialisation requirements..
   * <p>
   * Uses topic to invalidate across the cluster.
   * </p>
   */
  private class IgQueryCache extends DefaultServerCache {

    IgQueryCache(String name, ServerCacheOptions options) {
      super(name, options);
    }

    @Override
    public void clear() {
      super.clear();
      sendQueryCacheInvalidation(name);
    }

    /**
     * Process the invalidation message coming from the cluster.
     */
    private void invalidate() {
      queryLogger.debug("   CLEAR {}(*) - cluster invalidate", name);
      super.clear();
    }
  }

  /**
   * Send the invalidation message to all members of the cluster.
   */
  private void sendQueryCacheInvalidation(String key) {
    messaging.send(QC_INVALIDATE, key);
  }

  /**
   * Send the query cache created message to all members of the cluster.
   */
  private void sendQueryCacheCreated(String key) {
    queryCacheKeys.add(key);
    messaging.send(QC_CREATE, key);
  }

  /**
   * Clear the query cache if we have it.
   */
  private void queryCacheInvalidate(String key) {
    IgQueryCache queryCache = queryCaches.get(key);
    if (queryCache != null) {
      queryCache.invalidate();
    }
  }

  /**
   * A node in the cluster created this query cache so we need to as well.
   */
  private void queryCacheCreated(String key) {
    IgQueryCache queryCache = queryCaches.get(key);
    if (queryCache != null) {
      queryLogger.debug("   cluster creating cache {}", key);
      pluginServer.initQueryCache(key);
    }
  }
}

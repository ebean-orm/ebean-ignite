package io.ebean.ignite;

import io.ebean.BackgroundExecutor;
import io.ebean.cache.ServerCache;
import io.ebean.cache.ServerCacheFactory;
import io.ebean.cache.ServerCacheOptions;
import io.ebean.cache.ServerCacheType;
import io.ebean.config.CurrentTenantProvider;
import io.ebean.config.ServerConfig;
import io.ebeaninternal.server.cache.DefaultServerCache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import io.ebean.ignite.config.ConfigManager;
import io.ebean.ignite.config.ConfigPair;
import io.ebean.ignite.config.ConfigXmlReader;
import io.ebean.ignite.config.L2Configuration;
import org.avaje.ignite.IgniteConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating L2 server caches with Apache Ignite.
 * <p>
 * The L2 Query cache is effectively an always a near cache and we use Ignite to send/receive
 * invalidation messages for the query caches on all the members of the cluster.
 * </p>
 * <p>
 * All the 'Bean' caches will typically be partitioned with an optional 'near' cache option or replicated.
 * Replicated caches ought to be a good choice for small cardinality/stable bean types (like countries,
 * currencies etc).
 * </p>
 */
public class IgCacheFactory implements ServerCacheFactory {

  private static final Logger queryLogger = LoggerFactory.getLogger("org.avaje.ebean.cache.QUERY");

  private static final Logger logger = LoggerFactory.getLogger("org.avaje.ebean.cache.CACHE");

  private static final String QC_INVALIDATE = "L2QueryCacheInvalidate";

  private final ConcurrentHashMap<String, IgQueryCache> queryCaches;

  private final ConfigManager configManager;

  private final BackgroundExecutor executor;

  private Ignite ignite;

  private IgniteMessaging messaging;

  public IgCacheFactory(ServerConfig serverConfig, BackgroundExecutor executor) {
    this.executor = executor;
    this.queryCaches = new ConcurrentHashMap<>();
    this.configManager = new ConfigManager(readConfiguration());

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

  private class QueryCacheInvalidateListener implements IgniteBiPredicate<UUID, String> {
    @Override
    public boolean apply(UUID uuid, String key) {
      queryCacheInvalidate(key);
      return true;
    }
  }

  @Override
  public ServerCache createCache(ServerCacheType type, String key, CurrentTenantProvider tenantProvider, ServerCacheOptions options) {

    logger.debug("create cache - type:{} key:{}", type, key);
    switch (type) {
      case QUERY:
        return createQueryCache(key, tenantProvider, options);

      default:
        return createNormalCache(type, tenantProvider, key);
    }
  }

  private ServerCache createNormalCache(ServerCacheType type, CurrentTenantProvider tenantProvider, String key) {

    ConfigPair pair = configManager.getConfig(type, key);

    pair.setName(fullName(type, key));

    IgniteCache cache;
    if (pair.hasNearCache()) {
      cache = ignite.getOrCreateCache(pair.getMain(), pair.getNear());

    } else {
      cache = ignite.getOrCreateCache(pair.getMain());
    }

    return new IgCache(cache, tenantProvider);
  }

  /**
   * Return the full cache name (JMX safe name).
   */
  private String fullName(ServerCacheType type, String key) {
    return type.name() + "-" + key;
  }

  /**
   * Create a local/near query cache.
   */
  private ServerCache createQueryCache(String key, CurrentTenantProvider tenantProvider, ServerCacheOptions options) {
    synchronized (this) {
      IgQueryCache cache = queryCaches.get(key);
      if (cache == null) {
        cache = new IgQueryCache(key, tenantProvider, options);
        cache.periodicTrim(executor);
        queryCaches.put(key, cache);
      }
      return cache;
    }
  }

  /**
   * Local only cache implementation with no serialisation requirements.
   * <p>
   * Uses Ignite topic to invalidate across the cluster.
   * </p>
   */
  private class IgQueryCache extends DefaultServerCache {

    IgQueryCache(String name, CurrentTenantProvider tenantProvider, ServerCacheOptions options) {
      super(name, tenantProvider, options);
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
   * Clear the query cache if we have it.
   */
  private void queryCacheInvalidate(String key) {
    IgQueryCache queryCache = queryCaches.get(key);
    if (queryCache != null) {
      queryCache.invalidate();
    }
  }

}

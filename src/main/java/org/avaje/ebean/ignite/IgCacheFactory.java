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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating L2 server caches with Apache Ignite.
 */
public class IgCacheFactory implements ServerCacheFactory {

  private static final Logger queryLogger = LoggerFactory.getLogger("org.avaje.ebean.cache.QUERY");

  private static final Logger logger = LoggerFactory.getLogger("org.avaje.ebean.cache.CACHE");

  private static final String QC_CREATE = "L2QueryCacheCreate";

  private static final String QC_INVALIDATE = "L2QueryCacheInvalidate";

  private final ConcurrentHashMap<String, IgQueryCache> queryCaches;

  private final ConfigManager configManager;

  private Ignite ignite;

  private IgniteMessaging queryCacheInvalidate;

  private IgniteSet<String> queryCacheKeys;

  private SpiServer pluginServer;

  public IgCacheFactory() {
    this.queryCaches = new ConcurrentHashMap<>();
    L2Configuration l2Configuration = ConfigXmlReader.read("/ebean-ignite-config.xml");
    this.configManager = new ConfigManager(l2Configuration);
  }

  @Override
  public void init(EbeanServer ebeanServer) {

    pluginServer = ebeanServer.getPluginApi();

    ServerConfig serverConfig = ebeanServer.getPluginApi().getServerConfig();
    IgniteConfiguration configuration = (IgniteConfiguration) serverConfig.getServiceObject("igniteConfiguration");
    if (configuration == null) {
      configuration = new IgniteConfiguration();
      configuration.setClientMode(true);
    }

    if (configuration.getGridLogger() == null) {
      configuration.setGridLogger(new Slf4jLogger(logger));
    }

    logger.debug("Starting Ignite");
    ignite = Ignition.start(configuration);
    queryCacheInvalidate = ignite.message(ignite.cluster().forRemotes());
    queryCacheInvalidate.localListen(QC_INVALIDATE, new QueryCacheInvalidateListener());
    queryCacheInvalidate.localListen(QC_CREATE, new QueryCacheCreatedListener());

    queryCacheKeys = ignite.set("queryCacheNames", new CollectionConfiguration());

    for (String key : queryCacheKeys) {
      try {
        logger.info("init query cache for {}", key);
        pluginServer.initQueryCache(key);

      } catch (Exception e) {
        logger.error("Failed to initiate query cache for "+key, e);
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

  @NotNull
  private String fullName(ServerCacheType type, String key) {
    return type.name() + "-" + key;
  }

  private ServerCache createQueryCache(String key) {

    synchronized (this) {
      IgQueryCache cache = queryCaches.get(key);
      if (cache == null) {
        sendCacheCreated(key);
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
      sendInvalidation(name);
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
  private void sendInvalidation(String key) {
    //logger.trace("send query cache invalidation key[{}] ", key);
    queryCacheInvalidate.send(QC_INVALIDATE, key);
  }

  private void sendCacheCreated(String key) {
    logger.info("send query cache created key[{}] ", key);
    queryCacheKeys.add(key);
    queryCacheInvalidate.send(QC_CREATE, key);
  }

  private void queryCacheInvalidate(String key) {
    //logger.trace("received query cache invalidation key[{}] ", key);
    IgQueryCache queryCache = queryCaches.get(key);
    if (queryCache != null) {
      queryCache.invalidate();
    }
  }

  private void queryCacheCreated(String key) {
    //logger.trace("received query cache invalidation key[{}] ", key);
    IgQueryCache queryCache = queryCaches.get(key);
    if (queryCache != null) {
      queryLogger.debug("   cluster creating cache {}", key);
      pluginServer.initQueryCache(key);
    }
  }
}

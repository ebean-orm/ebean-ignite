package io.ebean.ignite;

import io.ebean.BackgroundExecutor;
import io.ebean.cache.*;
import io.ebeaninternal.server.cache.DefaultServerCacheConfig;
import io.ebeaninternal.server.cache.DefaultServerQueryCache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class IgCacheFactoryBase implements ServerCacheFactory {

  private static final Logger queryLogger = LoggerFactory.getLogger("org.avaje.ebean.cache.QUERY");

  protected static final Logger logger = LoggerFactory.getLogger("org.avaje.ebean.cache.CACHE");

  private static final Logger tableModLogger = LoggerFactory.getLogger("io.ebean.cache.TABLEMODS");

  private static final String QC_INVALIDATE = "L2QueryCacheInvalidate";

  private static final String TABLE_MOD = "L2TableMod";

  private final ConcurrentHashMap<String, IgQueryCache> queryCaches;

  private final BackgroundExecutor executor;

  protected final Ignite ignite;

  private final IgniteMessaging messaging;

  private ServerCacheNotify listener;

  protected IgCacheFactoryBase(BackgroundExecutor executor, Ignite ignite) {
    this.executor = executor;
    this.queryCaches = new ConcurrentHashMap<>();
    this.ignite = ignite;
    messaging = ignite.message(ignite.cluster().forRemotes());
    messaging.localListen(QC_INVALIDATE, new QueryCacheInvalidateListener());
    messaging.localListen(TABLE_MOD, new TableModListener());
  }

  @Override
  public ServerCacheNotify createCacheNotify(ServerCacheNotify listener) {
    this.listener = listener;
    return new IgServerCacheNotify();
  }

  private class QueryCacheInvalidateListener implements IgniteBiPredicate<UUID, String> {
    @Override
    public boolean apply(UUID uuid, String key) {
      queryCacheInvalidate(key);
      return true;
    }
  }

  private class TableModListener implements IgniteBiPredicate<UUID, String> {
    @Override
    public boolean apply(UUID uuid, String rawMessage) {
      processTableNotify(rawMessage);
      return true;
    }
  }

  @Override
  public ServerCache createCache(ServerCacheConfig config) {
    if (config.isQueryCache()) {
      return config.tenantAware(createQueryCache(config));
    }
    return config.tenantAware(new IgCache(createNormalCache(config)));
  }

  protected abstract <K, V> IgniteCache<K, V> createNormalCache(ServerCacheConfig config);

  /**
   * Return the full cache name (JMX safe name).
   */
  protected static String fullName(ServerCacheType type, String key) {
    return type.name() + '-' + key;
  }

  /**
   * Create a local/near query cache.
   */
  private ServerCache createQueryCache(ServerCacheConfig config) {
    synchronized (this) {
      IgQueryCache cache = queryCaches.get(config.getCacheKey());
      if (cache == null) {
        logger.debug("create query cache [{}]", config.getCacheKey());
        cache = new IgQueryCache(new DefaultServerCacheConfig(config));
        cache.periodicTrim(executor);
        queryCaches.put(config.getCacheKey(), cache);
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
  private class IgQueryCache extends DefaultServerQueryCache {

    IgQueryCache(DefaultServerCacheConfig config) {
      super(config);
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

  /**
   * Process a remote dependent table modify event.
   */
  private void processTableNotify(String rawMessage) {

    if (logger.isDebugEnabled()) {
      logger.debug("processTableNotify {}", rawMessage);
    }

    String[] split = rawMessage.split(",");
    Set<String> tables = new HashSet<>(Arrays.asList(split));
    listener.notify(new ServerCacheNotification(tables));
  }

  /**
   * Send the invalidation message to all members of the cluster.
   */
  private void sendTableMod(String key) {
    messaging.send(TABLE_MOD, key);
  }

  /**
   * Send the table modifications via Hazelcast topic.
   */
  class IgServerCacheNotify implements ServerCacheNotify {


    @Override
    public void notify(ServerCacheNotification tableModifications) {

      Set<String> dependentTables = tableModifications.getDependentTables();
      if (dependentTables != null && !dependentTables.isEmpty()) {

        StringBuilder msg = new StringBuilder(50);
        for (String table : dependentTables) {
          msg.append(table).append(",");
        }

        String formattedMsg = msg.toString();
        if (tableModLogger.isDebugEnabled()) {
          tableModLogger.debug("Publish TableMods - {}", formattedMsg);
        }
        sendTableMod(formattedMsg);
      }
    }
  }

}

package org.avaje.ebean.ignite;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.cache.ServerCacheFactory;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheType;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.server.cache.DefaultServerCache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating L2 server caches with Apache Ignite.
 */
public class IgCacheFactory implements ServerCacheFactory {

  private static final String TOPIC = "L2QueryCache";

  private final ConcurrentHashMap<String,IgQueryCache> queryCaches;

  private Ignite ignite;

  private IgniteMessaging queryCacheInvalidate;

  public IgCacheFactory() {
    this.queryCaches = new ConcurrentHashMap<String, IgQueryCache>();
  }

  @Override
  public void init(EbeanServer ebeanServer) {

    Ignition.setClientMode(true);
    ignite = Ignition.start();
    queryCacheInvalidate = ignite.message(ignite.cluster().forRemotes());
    queryCacheInvalidate.localListen(TOPIC, new MsgListener());
  }

  class MsgListener implements IgniteBiPredicate<UUID, String> {
    @Override
    public boolean apply(UUID uuid, String s) {
      queryCacheInvalidate(s);
      return true;
    }
  }

  @Override
  public ServerCache createCache(ServerCacheType type, String key, ServerCacheOptions options) {

    switch (type) {
      case QUERY:
        return createQueryCache(key, options);
      default:
        return createNormalCache(type, key, options);
    }
  }

  private ServerCache createNormalCache(ServerCacheType type, String key, ServerCacheOptions options) {

    String fullName = type.name() + "-" + key;

    IgniteCache cache;
    cache = ignite.getOrCreateCache(fullName);
    boolean withNearCache = false;
    if (withNearCache) {
      NearCacheConfiguration nearCfg = new NearCacheConfiguration();
      nearCfg.setNearEvictionPolicy(new LruEvictionPolicy(1000));
      cache = ignite.getOrCreateNearCache(fullName, nearCfg);
    }

    return new IgCache(cache);
  }

  private ServerCache createQueryCache(String key, ServerCacheOptions options) {

    IgQueryCache cache = new IgQueryCache(key, options);
    queryCaches.put(key, cache);
    return cache;
  }

  /**
   * Extends normal default implementation with notification of clear() to cluster.
   */
  class IgQueryCache extends DefaultServerCache {

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
      super.clear();
    }
  }

  /**
   * Send the invalidation message to all members of the cluster.
   */
  private void sendInvalidation(String name) {
    queryCacheInvalidate.send(TOPIC, name);
  }

  private void queryCacheInvalidate(String key) {
    IgQueryCache queryCache = queryCaches.get(key);
    if (queryCache != null) {
      queryCache.invalidate();
    }
  }

}

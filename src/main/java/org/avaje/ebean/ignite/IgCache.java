package org.avaje.ebean.ignite;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheStatistics;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMetrics;

/**
 * IgniteCache adaptor to ServerCache
 */
class IgCache implements ServerCache {

  private final IgniteCache cache;

  IgCache(IgniteCache cache) {
    this.cache = cache;
  }

  @Override
  public void init(EbeanServer ebeanServer) {

  }

  @Override
  public ServerCacheOptions getOptions() {
    return null;
  }

  @Override
  public void setOptions(ServerCacheOptions options) {

  }

  @Override
  @SuppressWarnings("unchecked")
  public Object get(Object id) {
    //cache.
    return cache.get(id);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object put(Object id, Object value) {
    cache.put(id, value);
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object remove(Object id) {
    cache.remove(id);
    return null;
  }

  @Override
  public void clear() {
    cache.clear();
  }

  @Override
  public int size() {
    return cache.size();
  }

  @Override
  public int getHitRatio() {
    return 0;
  }

  @Override
  public ServerCacheStatistics getStatistics(boolean reset) {
    //CacheMetrics metrics = cache.metrics();
    //metrics.
    return null;
  }
}

package org.avaje.ebean.ignite;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheStatistics;
import org.apache.ignite.IgniteCache;

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
    // don't need the executor service from ebeanServer etc
  }

  @Override
  public ServerCacheOptions getOptions() {
    // we don't care ... use the xml configuration
    return null;
  }

  @Override
  public void setOptions(ServerCacheOptions options) {
    // we don't care ... use the xml configuration
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object get(Object id) {
    return cache.get(id);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object put(Object id, Object value) {
    cache.put(id, value);
    // return null but that is fine, we don't need it
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object remove(Object id) {
    cache.remove(id);
    // return null but that is fine, we don't need it
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
    // we can get this from cache.metrics();
    return 0;
  }

  @Override
  public ServerCacheStatistics getStatistics(boolean reset) {
    // get this from cache.metrics(); ?
    return null;
  }
}

package io.ebean.ignite;

import io.ebean.cache.ServerCache;
import io.ebean.cache.ServerCacheStatistics;
import org.apache.ignite.IgniteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IgniteCache adaptor to ServerCache
 */
class IgCache implements ServerCache {

  private static final Logger logger = LoggerFactory.getLogger(IgCache.class);

  private final IgniteCache cache;

  IgCache(IgniteCache cache) {
    this.cache = cache;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object get(Object id) {
    try {
      return cache.get(id);
    } catch (Exception e) {
      logger.warn("Error calling cache GET. No ignite servers running?", e);
      // treat as miss
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object put(Object id, Object value) {
    try {
      cache.put(id, value);
    } catch (Exception e) {
      logger.warn("Error calling cache PUT. No ignite servers running?", e);
    }
    // return null but that is fine, we don't need it
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object remove(Object id) {
    try {
      cache.remove(id);
    } catch (Exception e) {
      logger.warn("Error calling cache REMOVE. No ignite servers running?", e);
    }
    // return null but that is fine, we don't need it
    return null;
  }

  @Override
  public void clear() {
    try {
      cache.clear();
    } catch (Exception e) {
      logger.warn("Error calling cache CLEAR. No ignite servers running?", e);
    }
  }

  @Override
  public int size() {
    try {
      return cache.size();
    } catch (Exception e) {
      logger.warn("Error calling cache SIZE. No ignite servers running?", e);
      return 0;
    }
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

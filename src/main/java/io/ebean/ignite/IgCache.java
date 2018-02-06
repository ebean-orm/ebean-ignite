package io.ebean.ignite;

import io.ebean.cache.ServerCache;
import io.ebean.cache.ServerCacheStatistics;
import io.ebean.cache.TenantAwareKey;
import io.ebean.config.CurrentTenantProvider;
import org.apache.ignite.IgniteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * IgniteCache adaptor to ServerCache
 */
class IgCache implements ServerCache {

  private static final Logger logger = LoggerFactory.getLogger(IgCache.class);

  private final IgniteCache cache;

  private final TenantAwareKey tenantAwareKey;

  IgCache(IgniteCache cache, CurrentTenantProvider tenantProvider) {
    this.cache = cache;
    this.tenantAwareKey = new TenantAwareKey(tenantProvider);
  }

  private Object key(Object key) {
    return tenantAwareKey.key(key);
  }

	@Override
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getAll(Set<Object> keys) {
  	try {
			return cache.getAll(keys);
	  } catch (Exception e) {
		  logger.warn("Error calling cache GET. No ignite servers running?", e);
		  // treat as miss
		  return Collections.EMPTY_MAP;
	  }
	}

	@Override
	@SuppressWarnings("unchecked")
	public void putAll(Map<Object, Object> keyValues) {
		try {
			cache.putAll(keyValues);
		} catch (Exception e) {
			logger.warn("Error calling cache GET. No ignite servers running?", e);

		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void removeAll(Set<Object> keys) {
		try {
			cache.removeAll(keys);
		} catch (Exception e) {
			logger.warn("Error calling cache GET. No ignite servers running?", e);
		}
	}

	@Override
  @SuppressWarnings("unchecked")
  public Object get(Object id) {
    try {
      return cache.get(key(id));
    } catch (Exception e) {
      logger.warn("Error calling cache GET. No ignite servers running?", e);
      // treat as miss
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void put(Object id, Object value) {
    try {
      cache.put(key(id), value);
    } catch (Exception e) {
      logger.warn("Error calling cache PUT. No ignite servers running?", e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void remove(Object id) {
    try {
      cache.remove(key(id));
    } catch (Exception e) {
      logger.warn("Error calling cache REMOVE. No ignite servers running?", e);
    }
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

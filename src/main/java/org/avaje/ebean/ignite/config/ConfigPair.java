package org.avaje.ebean.ignite.config;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;

/**
 * A configuration for both the 'main' cache and the 'near' cache.
 */
public class ConfigPair {

  private final CacheConfiguration main;

  private final NearCacheConfiguration near;

  /**
   * Construct with 'main' and 'near' cache configurations.
   */
  ConfigPair(CacheConfiguration main, NearCacheConfiguration near) {
    this.main = main;
    this.near = near;
  }

  /**
   * Return the 'main' cache configuration.
   */
  public CacheConfiguration getMain() {
    return main;
  }

  /**
   * Return the 'near' cache configuration (can be null).
   */
  public NearCacheConfiguration getNear() {
    return near;
  }

  /**
   * Return true if there is a near cache configuration.
   */
  public boolean hasNearCache() {
    return near != null;
  }

  /**
   * Set the cache name.
   */
  public void setName(String fullName) {
    main.setName(fullName);
  }
}

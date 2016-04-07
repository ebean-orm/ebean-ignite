package org.avaje.ebean.ignite.config;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;

/**
 */
public class ConfigPair {

  private final CacheConfiguration main;

  private final NearCacheConfiguration near;

  public ConfigPair(CacheConfiguration main, NearCacheConfiguration near) {
    this.main = main;
    this.near = near;
  }

  public CacheConfiguration getMain() {
    return main;
  }

  public NearCacheConfiguration getNear() {
    return near;
  }

  public boolean hasNearCache() {
    return near != null;
  }

  public void setName(String fullName) {
    main.setName(fullName);
  }
}

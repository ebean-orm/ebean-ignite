package org.avaje.ebean.ignite;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.cache.ServerCacheFactory;
import com.avaje.ebean.cache.ServerCachePlugin;
import com.avaje.ebean.config.ServerConfig;

/**
 * Cache plugin that creates the Ignite ServerCacheFactory.
 */
public class IgCachePlugin implements ServerCachePlugin {

  /**
   * Create the Ignite ServerCacheFactory implementation.
   */
  @Override
  public ServerCacheFactory create(ServerConfig config, BackgroundExecutor executor) {
    return new IgCacheFactory(config, executor);
  }
}

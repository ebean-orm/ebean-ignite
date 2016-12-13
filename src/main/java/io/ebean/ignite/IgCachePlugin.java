package io.ebean.ignite;

import io.ebean.BackgroundExecutor;
import io.ebean.cache.ServerCacheFactory;
import io.ebean.cache.ServerCachePlugin;
import io.ebean.config.ServerConfig;

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

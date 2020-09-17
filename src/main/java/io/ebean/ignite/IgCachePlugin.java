package io.ebean.ignite;

import io.ebean.BackgroundExecutor;
import io.ebean.cache.ServerCacheFactory;
import io.ebean.cache.ServerCachePlugin;
import io.ebean.config.DatabaseConfig;

/**
 * Cache plugin that creates the Ignite ServerCacheFactory.
 */
public class IgCachePlugin implements ServerCachePlugin {

  /**
   * Create the Ignite ServerCacheFactory implementation.
   */
  @Override
  public ServerCacheFactory create(DatabaseConfig config, BackgroundExecutor executor) {
    return IgCacheFactory.create(config, executor);
  }
}

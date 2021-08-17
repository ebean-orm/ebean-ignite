package io.ebean.ignite;

import io.ebean.BackgroundExecutor;
import io.ebean.cache.ServerCacheConfig;
import io.ebean.config.DatabaseConfig;
import io.ebean.ignite.config.ConfigManager;
import io.ebean.ignite.config.ConfigPair;
import io.ebean.ignite.config.ConfigXmlReader;
import io.ebean.ignite.config.L2Configuration;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import java.io.File;
import java.util.Properties;

/**
 * Factory for creating L2 server caches with Apache Ignite.
 * <p>
 * The L2 Query cache is effectively an always a near cache and we use Ignite to send/receive
 * invalidation messages for the query caches on all the members of the cluster.
 * </p>
 * <p>
 * All the 'Bean' caches will typically be partitioned with an optional 'near' cache option or replicated.
 * Replicated caches ought to be a good choice for small cardinality/stable bean types (like countries,
 * currencies etc).
 * </p>
 */
public class IgCacheFactory extends IgCacheFactoryBase {
  private final ConfigManager configManager;

  private static Ignite startIgnite(DatabaseConfig config, ConfigManager configManager) {
    // programmatically set into ServerConfig - typical DI setup
    IgniteConfiguration configuration = (IgniteConfiguration) config.getServiceObject("igniteConfiguration");
    if (configuration == null) {
      Properties properties = config.getProperties();
//      if (properties != null) {
//        configuration = new IgniteConfigBuilder("ignite", properties).build();
//      } else {
      configuration = new IgniteConfiguration();
//      }
    }

    if (configuration.getGridLogger() == null) {
      configuration.setGridLogger(new Slf4jLogger(logger));
    }

    logger.debug("Starting Ignite");
    return Ignition.start(configuration);
  }

  private IgCacheFactory(DatabaseConfig config, BackgroundExecutor executor, ConfigManager configManager) {
    super(executor, startIgnite(config, configManager));
    this.configManager = configManager;
  }

  public static IgCacheFactory create(DatabaseConfig config, BackgroundExecutor executor) {
    return new IgCacheFactory(config, executor, new ConfigManager(readConfiguration()));
  }

  /**
   * Read the L2 cache configuration.
   */
  private static L2Configuration readConfiguration() {

    // check system property first
    String config = System.getProperty("ebeanIgniteConfig");
    if (config != null) {
      File file = new File(config);
      if (!file.exists()) {
        throw new IllegalStateException("ebean ignite configuration not found at " + config);
      }
      return ConfigXmlReader.read(file);
    }

    // look for local configuration external to the application
    File file = new File("ebean-ignite-config.xml");
    if (file.exists()) {
      return ConfigXmlReader.read(file);
    }

    // look for configuration inside the application
    return ConfigXmlReader.read("/ebean-ignite-config.xml");
  }


  @Override
  protected <K, V> IgniteCache<K, V> createNormalCache(ServerCacheConfig config) {

    ConfigPair pair = configManager.getConfig(config.getType(), config.getCacheKey());

    String fullName = fullName(config.getType(), config.getCacheKey());
    logger.debug("create cache - fullName:{}", fullName);
    pair.setName(fullName);

    if (pair.hasNearCache()) {
      return ignite.getOrCreateCache(pair.getMain(), pair.getNear());

    } else {
      return ignite.getOrCreateCache(pair.getMain());
    }
  }
}

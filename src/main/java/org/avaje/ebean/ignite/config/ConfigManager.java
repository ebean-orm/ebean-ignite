package org.avaje.ebean.ignite.config;

import com.avaje.ebean.cache.ServerCacheType;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Determines the cache configuration that should be applied to any given L2 cache.
 */
public class ConfigManager {

  private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

  private final L2Configuration configuration;

  private final L2CacheConfig baseQuery;
  private final L2CacheConfig baseBean;
  private final L2CacheConfig baseKey;
  private final L2CacheConfig baseAssocManyIds;

  /**
   * Create given the configuration.
   */
  public ConfigManager(L2Configuration configuration) {

    this.configuration = configuration;

    L2CacheConfig base = defaultConfig(configuration.getBase());
    this.baseQuery = add(base, configuration.getBaseQuery());
    this.baseBean = add(base, configuration.getBaseBean());
    this.baseKey = add(base, configuration.getBaseKey());
    this.baseAssocManyIds = add(base, configuration.getBaseAssocManyIds());
  }

  /**
   * Add the configurations together apply over the top of base.
   */
  private L2CacheConfig add(L2CacheConfig base, L2CacheConfig apply) {
    return AddL2CacheConfig.add(base, apply);
  }

  /**
   * Return the config with an empty default if required.
   */
  private L2CacheConfig defaultConfig(L2CacheConfig base) {
    return (base != null) ? base : new L2CacheConfig();
  }

  /**
   * Return the config pair for main and near cache given the type and key.
   */
  public ConfigPair getConfig(ServerCacheType type, String key) {

    L2CacheConfig config = getBase(type);

    L2Apply apply = configuration.getApply();
    if (apply != null) {
      List<L2CacheMatch> match = apply.getMatch();
      for (L2CacheMatch l2CacheMatch : match) {
        if (isMatch(type, key, l2CacheMatch)) {
          logger.debug("match for type[{}] key[{}] to config [{}]", type, key, l2CacheMatch.getMatch());
          config = add(config, l2CacheMatch.getConfig());
        }
      }
    }

    return createPair(type, config);
  }

  private ConfigPair createPair(ServerCacheType type, L2CacheConfig config) {

    CacheConfiguration main = new CacheConfiguration();
    AddL2CacheConfig.apply(main, config);

    NearCacheConfiguration near = getNearCacheConfig(type, config);

    return new ConfigPair(main, near);
  }

  @Nullable
  @SuppressWarnings("unchecked")
  private NearCacheConfiguration getNearCacheConfig(ServerCacheType type, L2CacheConfig config) {

    if (type.equals(ServerCacheType.QUERY) || config.getNearSize() == null) {
      return null;

    } else {
      NearCacheConfiguration near = new NearCacheConfiguration();
      near.setNearEvictionPolicy(new LruEvictionPolicy(config.getNearSize()));
      return  near;
    }
  }

  private boolean isMatch(ServerCacheType type, String key, L2CacheMatch l2CacheMatch) {

    if (!type.equals(l2CacheMatch.getType())) {
      return false;
    }

    // just lower case contains for now
    return key.toLowerCase().contains(l2CacheMatch.getMatch().toLowerCase());
  }

  /**
   * Return the base configuration based on the cache type.
   */
  private L2CacheConfig getBase(ServerCacheType type) {
    switch (type) {
      case QUERY:
        return baseQuery;
      case BEAN:
        return baseBean;
      case NATURAL_KEY:
        return baseKey;
      case COLLECTION_IDS:
        return baseAssocManyIds;
      default:
        throw new IllegalStateException("Invalid type " + type);
    }

  }
}

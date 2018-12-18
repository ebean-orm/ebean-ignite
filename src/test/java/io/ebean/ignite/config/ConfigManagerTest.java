package io.ebean.ignite.config;

import io.ebean.cache.ServerCacheType;
import org.apache.ignite.cache.CacheMode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ConfigManagerTest {

  private final ConfigManager manager;

  public ConfigManagerTest() {
    L2Configuration config  = ConfigXmlReader.read("/test-cache-config.xml");
    this.manager = new ConfigManager(config);
  }

  @Test
  public void getConfig_country() {

    ConfigPair countryPair = manager.getConfig(ServerCacheType.BEAN, "org.example.domain.Country");

    assertEquals(countryPair.getMain().getCacheMode(), CacheMode.REPLICATED);
    assertNull(countryPair.getNear());
  }

  @Test
  public void getConfig_customer() {

    ConfigPair pair = manager.getConfig(ServerCacheType.BEAN, "org.example.domain.Customer");

    assertEquals(pair.getMain().getCacheMode(), CacheMode.PARTITIONED);
    assertNotNull(pair.getNear());
  }

}

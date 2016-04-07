package org.avaje.ebean.ignite.config;

import com.avaje.ebean.cache.ServerCacheType;
import org.apache.ignite.cache.CacheAtomicWriteOrderMode;
import org.apache.ignite.cache.CacheMode;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ConfigManagerTest {

  private final ConfigManager manager;

  public ConfigManagerTest() {
    L2Configuration config  = ConfigXmlReader.read("/test-cache-config.xml");
    this.manager = new ConfigManager(config);
  }

  @Test
  public void getConfig_country() throws Exception {

    ConfigPair countryPair = manager.getConfig(ServerCacheType.BEAN, "org.example.domain.Country");

    assertEquals(countryPair.getMain().getCacheMode(), CacheMode.REPLICATED);
    assertNull(countryPair.getNear());
  }

  @Test
  public void getConfig_customer() throws Exception {

    ConfigPair pair = manager.getConfig(ServerCacheType.BEAN, "org.example.domain.Customer");

    assertEquals(pair.getMain().getCacheMode(), CacheMode.PARTITIONED);
    assertNotNull(pair.getNear());
    assertEquals(pair.getMain().getAtomicWriteOrderMode(), CacheAtomicWriteOrderMode.CLOCK);

  }

}
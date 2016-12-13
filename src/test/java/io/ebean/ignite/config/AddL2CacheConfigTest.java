package io.ebean.ignite.config;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AddL2CacheConfigTest {

  @Test
  public void testAdd() throws Exception {

    L2CacheConfig base = new L2CacheConfig();
    base.setCacheMode(CacheMode.LOCAL);
    base.setNearSize(42);

    assertNull(base.getAtomicityMode());

    L2CacheConfig apply = new L2CacheConfig();
    apply.setNearSize(56);
    apply.setAtomicityMode(CacheAtomicityMode.ATOMIC);

    L2CacheConfig add = AddL2CacheConfig.add(base, apply);

    assertEquals(add.getNearSize(), Integer.valueOf(56));
    assertEquals(add.getAtomicityMode(), CacheAtomicityMode.ATOMIC);
    assertEquals(add.getCacheMode(), CacheMode.LOCAL);
    assertNull(add.getBackups());
  }

}
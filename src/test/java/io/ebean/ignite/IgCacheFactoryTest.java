package io.ebean.ignite;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.cache.ServerCache;
import io.ebean.cache.ServerCacheManager;
import io.ebean.config.DatabaseConfig;
import main.Server;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.example.domain.EFoo;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class IgCacheFactoryTest {

  private final static Database server;

  private final static Server igniteServer;

  static {
    igniteServer = new Server("testServer");

    IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
    igniteConfiguration.setGridLogger(new Slf4jLogger());

    DatabaseConfig serverConfig = new DatabaseConfig();
    serverConfig.loadFromProperties();

    serverConfig.putServiceObject("igniteConfiguration", igniteConfiguration);

    server = DatabaseFactory.create(serverConfig);
  }


  @Test
  public void integration() {

    ServerCacheManager cacheManager = server.cacheManager();
    ServerCache beanCache = cacheManager.beanCache(EFoo.class);

    assertThat(beanCache).isInstanceOf(IgCache.class);

    EFoo foo = new EFoo("bar");
    foo.save();

    EFoo fetch1 = DB.find(EFoo.class, foo.getId());
    System.out.println("f" + fetch1);

    EFoo fetch2 = DB.find(EFoo.class, foo.getId());
    System.out.println("f" + fetch2);
  }

  @Test
  public void putGet() throws InterruptedException {

    EFoo foo = new EFoo("hello");
    foo.save();

    EFoo fetch1 = DB.find(EFoo.class)
        .setId(foo.getId())
        .select("name, status, notes, version")
        .findOne();

    EFoo fetch2 = DB.find(EFoo.class)
        .setId(foo.getId())
        .select("name, status, notes, version")
        .findOne();

    assertNotNull(fetch1);
    assertNotNull(fetch2);

    EFoo update = new EFoo();
    update.setId(foo.getId());
    update.setNotes("ModNotes");
    update.update();

    Thread.sleep(20);

    EFoo fetch3 = DB.find(EFoo.class)
        .setId(foo.getId())
        .select("name, status, notes, version")
        .findOne();

    assertNotNull(fetch3);
    assertEquals(fetch3.getNotes(), "ModNotes");
    assertEquals(fetch3.getName(), "hello");


    EFoo update2 = new EFoo();
    update2.setId(foo.getId());
    update2.setNotes("ModNotes");
    update2.setVersion(fetch3.getVersion());
    update2.update();

    Thread.sleep(10);
  }

  @Test
  public void queryCache() {

    new EFoo("one1").save();
    new EFoo("one2").save();
    new EFoo("one3").save();

    DB.find(EFoo.class)
        .setUseQueryCache(true)
        .where().startsWith("name", "one")
        .findList();

    DB.find(EFoo.class)
        .setUseQueryCache(true)
        .where().startsWith("name", "one")
        .findList();

    new EFoo("one4").save();

    DB.find(EFoo.class)
        .setUseQueryCache(true)
        .where().startsWith("name", "one")
        .findList();

    DB.find(EFoo.class)
        .setUseQueryCache(true)
        .where().startsWith("name", "one")
        .findList();

  }
}

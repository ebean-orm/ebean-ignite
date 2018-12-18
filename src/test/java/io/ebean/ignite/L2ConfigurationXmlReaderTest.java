package io.ebean.ignite;

import io.ebean.ignite.config.ConfigXmlReader;
import io.ebean.ignite.config.L2Configuration;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class L2ConfigurationXmlReaderTest {

  @Test
  public void testRead() {

    L2Configuration config  = ConfigXmlReader.read("/test-cache-config.xml");
    assertNotNull(config);
  }

}

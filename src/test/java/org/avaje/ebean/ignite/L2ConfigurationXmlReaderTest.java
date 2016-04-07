package org.avaje.ebean.ignite;

import org.avaje.ebean.ignite.config.L2Configuration;
import org.avaje.ebean.ignite.config.ConfigXmlReader;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class L2ConfigurationXmlReaderTest {

  @Test
  public void testRead() throws Exception {

    L2Configuration config  = ConfigXmlReader.read("/test-cache-config.xml");
    assertNotNull(config);
  }

}
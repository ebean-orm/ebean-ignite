package org.avaje.ebean.ignite.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads L2Configuration from Xml resources.
 */
public class ConfigXmlReader {

  private static final Logger logger = LoggerFactory.getLogger(ConfigXmlReader.class);

  /**
   * Read and return a Migration from an xml document at the given resource path.
   */
  public static L2Configuration read(String resourcePath) {

    InputStream is = ConfigXmlReader.class.getResourceAsStream(resourcePath);
    if (is == null) {
      return new L2Configuration();
    }
    return read(is);
  }

  public static L2Configuration read(File file) {
    try {
      return read(new FileInputStream(file));
    } catch (IOException e) {
      throw new RuntimeException("Error reading ignite config file", e);
    }
  }

  /**
   * Read and return a L2Configuration from an xml document.
   */
  public static L2Configuration read(InputStream is) {

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(L2Configuration.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      return (L2Configuration) unmarshaller.unmarshal(is);

    } catch (JAXBException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        logger.error("Error while closing configuration", e);
      }
    }
  }
}

package org.avaje.ebean.ignite.config;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Reads L2Configuration from Xml resources.
 */
public class ConfigXmlReader {

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


  /**
   * Read and return a L2Configuration from an xml document.
   */
  private static L2Configuration read(InputStream is) {

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(L2Configuration.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      return (L2Configuration) unmarshaller.unmarshal(is);

    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}

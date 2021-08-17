
package io.ebean.ignite.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for l2Apply complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="l2Apply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="match" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheMatch" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "l2Apply", propOrder = {
  "match"
})
public class L2Apply {

  @XmlElement(nillable = true)
  protected List<L2CacheMatch> match;

  /**
   * Gets the value of the match property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the match property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getMatch().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link L2CacheMatch }
   */
  public List<L2CacheMatch> getMatch() {
    if (match == null) {
      match = new ArrayList<>();
    }
    return this.match;
  }

}

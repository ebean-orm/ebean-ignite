
package io.ebean.ignite.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="base" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheConfig" minOccurs="0"/>
 *         &lt;element name="baseBean" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheConfig" minOccurs="0"/>
 *         &lt;element name="baseKey" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheConfig" minOccurs="0"/>
 *         &lt;element name="baseManyIds" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheConfig" minOccurs="0"/>
 *         &lt;element name="baseQuery" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheConfig" minOccurs="0"/>
 *         &lt;element name="apply" type="{http://ebean-orm.github.io/xml/ns/ignite}l2Apply" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "l2Configuration")
public class L2Configuration {

  protected L2CacheConfig base;
  protected L2CacheConfig baseBean;
  protected L2CacheConfig baseKey;
  protected L2CacheConfig baseManyIds;
  protected L2CacheConfig baseQuery;
  protected L2Apply apply;

  /**
   * Gets the value of the base property.
   *
   * @return possible object is
   * {@link L2CacheConfig }
   */
  public L2CacheConfig getBase() {
    return base;
  }

  /**
   * Sets the value of the base property.
   *
   * @param value allowed object is
   *              {@link L2CacheConfig }
   */
  public void setBase(L2CacheConfig value) {
    this.base = value;
  }

  /**
   * Gets the value of the baseBean property.
   *
   * @return possible object is
   * {@link L2CacheConfig }
   */
  public L2CacheConfig getBaseBean() {
    return baseBean;
  }

  /**
   * Sets the value of the baseBean property.
   *
   * @param value allowed object is
   *              {@link L2CacheConfig }
   */
  public void setBaseBean(L2CacheConfig value) {
    this.baseBean = value;
  }

  /**
   * Gets the value of the baseKey property.
   *
   * @return possible object is
   * {@link L2CacheConfig }
   */
  public L2CacheConfig getBaseKey() {
    return baseKey;
  }

  /**
   * Sets the value of the baseKey property.
   *
   * @param value allowed object is
   *              {@link L2CacheConfig }
   */
  public void setBaseKey(L2CacheConfig value) {
    this.baseKey = value;
  }

  /**
   * Gets the value of the baseManyIds property.
   *
   * @return possible object is
   * {@link L2CacheConfig }
   */
  public L2CacheConfig getBaseManyIds() {
    return baseManyIds;
  }

  /**
   * Sets the value of the baseManyIds property.
   *
   * @param value allowed object is
   *              {@link L2CacheConfig }
   */
  public void setBaseManyIds(L2CacheConfig value) {
    this.baseManyIds = value;
  }

  /**
   * Gets the value of the baseQuery property.
   *
   * @return possible object is
   * {@link L2CacheConfig }
   */
  public L2CacheConfig getBaseQuery() {
    return baseQuery;
  }

  /**
   * Sets the value of the baseQuery property.
   *
   * @param value allowed object is
   *              {@link L2CacheConfig }
   */
  public void setBaseQuery(L2CacheConfig value) {
    this.baseQuery = value;
  }

  /**
   * Gets the value of the apply property.
   *
   * @return possible object is
   * {@link L2Apply }
   */
  public L2Apply getApply() {
    return apply;
  }

  /**
   * Sets the value of the apply property.
   *
   * @param value allowed object is
   *              {@link L2Apply }
   */
  public void setApply(L2Apply value) {
    this.apply = value;
  }

}

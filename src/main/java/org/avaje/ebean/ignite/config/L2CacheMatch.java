
package org.avaje.ebean.ignite.config;

import com.avaje.ebean.cache.ServerCacheType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for l2CacheMatch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="l2CacheMatch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="config" type="{http://ebean-orm.github.io/xml/ns/ignite}l2CacheConfig" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="match" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://ebean-orm.github.io/xml/ns/ignite}serverCacheType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "l2CacheMatch", propOrder = {

})
public class L2CacheMatch {

    protected L2CacheConfig config;
    @XmlAttribute(name = "match", required = true)
    protected String match;
    @XmlAttribute(name = "type")
    protected ServerCacheType type;

    /**
     * Gets the value of the config property.
     * 
     * @return
     *     possible object is
     *     {@link L2CacheConfig }
     *     
     */
    public L2CacheConfig getConfig() {
        return config;
    }

    /**
     * Sets the value of the config property.
     * 
     * @param value
     *     allowed object is
     *     {@link L2CacheConfig }
     *     
     */
    public void setConfig(L2CacheConfig value) {
        this.config = value;
    }

    /**
     * Gets the value of the match property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatch() {
        return match;
    }

    /**
     * Sets the value of the match property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatch(String value) {
        this.match = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ServerCacheType }
     *     
     */
    public ServerCacheType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerCacheType }
     *     
     */
    public void setType(ServerCacheType value) {
        this.type = value;
    }

}

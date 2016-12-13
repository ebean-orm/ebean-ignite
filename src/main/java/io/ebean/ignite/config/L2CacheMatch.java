
package io.ebean.ignite.config;

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
 *       &lt;attribute name="matchClasses" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="typeQuery" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="typeBean" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="typeKey" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="typeManyId" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
    @XmlAttribute(name = "matchClasses", required = true)
    protected String matchClasses;
    @XmlAttribute(name = "typeQuery")
    protected Boolean typeQuery;
    @XmlAttribute(name = "typeBean")
    protected Boolean typeBean;
    @XmlAttribute(name = "typeKey")
    protected Boolean typeKey;
    @XmlAttribute(name = "typeManyId")
    protected Boolean typeManyId;

    public String toString() {
        String val = "classes:"+matchClasses;
        if (Boolean.TRUE.equals(typeQuery)) {
            val += " typeQuery:true";
        }
        if (Boolean.TRUE.equals(typeBean)) {
            val += " typeBean:true";
        }
        if (Boolean.TRUE.equals(typeKey)) {
            val += " typeKey:true";
        }
        if (Boolean.TRUE.equals(typeManyId)) {
            val += " typeManyId:true";
        }
        return val;
    }

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
     * Gets the value of the matchClasses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchClasses() {
        return matchClasses;
    }

    /**
     * Sets the value of the matchClasses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchClasses(String value) {
        this.matchClasses = value;
    }

    /**
     * Gets the value of the typeQuery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTypeQuery() {
        return typeQuery;
    }

    /**
     * Sets the value of the typeQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTypeQuery(Boolean value) {
        this.typeQuery = value;
    }

    /**
     * Gets the value of the typeBean property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTypeBean() {
        return typeBean;
    }

    /**
     * Sets the value of the typeBean property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTypeBean(Boolean value) {
        this.typeBean = value;
    }

    /**
     * Gets the value of the typeKey property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTypeKey() {
        return typeKey;
    }

    /**
     * Sets the value of the typeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTypeKey(Boolean value) {
        this.typeKey = value;
    }

    /**
     * Gets the value of the typeManyId property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTypeManyId() {
        return typeManyId;
    }

    /**
     * Sets the value of the typeManyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTypeManyId(Boolean value) {
        this.typeManyId = value;
    }

}

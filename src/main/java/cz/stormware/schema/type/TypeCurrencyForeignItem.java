//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCurrencyForeignItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeCurrencyForeignItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unitPrice" type="{http://www.stormware.cz/schema/type.xsd}currencyType" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.stormware.cz/schema/type.xsd}currencyType" minOccurs="0"/>
 *         &lt;element name="priceVAT" type="{http://www.stormware.cz/schema/type.xsd}currencyType" minOccurs="0"/>
 *         &lt;element name="priceSum" type="{http://www.stormware.cz/schema/type.xsd}currencyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeCurrencyForeignItem", propOrder = {
    "unitPrice",
    "price",
    "priceVAT",
    "priceSum"
})
public class TypeCurrencyForeignItem {

    protected Double unitPrice;
    protected Double price;
    protected Double priceVAT;
    protected Double priceSum;

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUnitPrice(Double value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPrice(Double value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceVAT property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPriceVAT() {
        return priceVAT;
    }

    /**
     * Sets the value of the priceVAT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPriceVAT(Double value) {
        this.priceVAT = value;
    }

    /**
     * Gets the value of the priceSum property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPriceSum() {
        return priceSum;
    }

    /**
     * Sets the value of the priceSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPriceSum(Double value) {
        this.priceSum = value;
    }

}

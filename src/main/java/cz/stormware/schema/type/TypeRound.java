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
 * <p>Java class for typeRound complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeRound">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="priceRound" type="{http://www.stormware.cz/schema/type.xsd}currencyType"/>
 *         &lt;group ref="{http://www.stormware.cz/schema/type.xsd}myGroupOfRound"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeRound", propOrder = {
    "priceRound",
    "rateVATround",
    "priceRoundSum",
    "priceRoundSumVAT"
})
public class TypeRound {

    protected Double priceRound;
    protected Double rateVATround;
    protected Double priceRoundSum;
    protected Double priceRoundSumVAT;

    /**
     * Gets the value of the priceRound property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPriceRound() {
        return priceRound;
    }

    /**
     * Sets the value of the priceRound property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPriceRound(Double value) {
        this.priceRound = value;
    }

    /**
     * Gets the value of the rateVATround property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRateVATround() {
        return rateVATround;
    }

    /**
     * Sets the value of the rateVATround property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRateVATround(Double value) {
        this.rateVATround = value;
    }

    /**
     * Gets the value of the priceRoundSum property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPriceRoundSum() {
        return priceRoundSum;
    }

    /**
     * Sets the value of the priceRoundSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPriceRoundSum(Double value) {
        this.priceRoundSum = value;
    }

    /**
     * Gets the value of the priceRoundSumVAT property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPriceRoundSumVAT() {
        return priceRoundSumVAT;
    }

    /**
     * Sets the value of the priceRoundSumVAT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPriceRoundSumVAT(Double value) {
        this.priceRoundSumVAT = value;
    }

}
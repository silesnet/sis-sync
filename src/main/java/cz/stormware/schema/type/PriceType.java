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
 * Rozepsání ceny a DPH
 * <p/>
 * <p>Java class for priceType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="priceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="price" type="{http://www.stormware.cz/schema/type.xsd}currencyType"/>
 *         &lt;element name="priceVAT" type="{http://www.stormware.cz/schema/type.xsd}currencyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceType", propOrder = {
    "price",
    "priceVAT"
})
public class PriceType {

  protected double price;
  protected double priceVAT;

  /**
   * Gets the value of the price property.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the value of the price property.
   */
  public void setPrice(double value) {
    this.price = value;
  }

  /**
   * Gets the value of the priceVAT property.
   */
  public double getPriceVAT() {
    return priceVAT;
  }

  /**
   * Sets the value of the priceVAT property.
   */
  public void setPriceVAT(double value) {
    this.priceVAT = value;
  }

}

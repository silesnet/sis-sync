//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.type;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Odkaz na entitu. Vy��� prioritu m� elemet "id", d�le "ids".
 * <p/>
 * <p>Java class for paymentType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="paymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.stormware.cz/schema/type.xsd}idType" minOccurs="0"/>
 *         &lt;element name="ids" type="{http://www.stormware.cz/schema/type.xsd}idsType" minOccurs="0"/>
 *         &lt;element name="paymentType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="draft"/>
 *               &lt;enumeration value="cash"/>
 *               &lt;enumeration value="postal"/>
 *               &lt;enumeration value="delivery"/>
 *               &lt;enumeration value="creditcard"/>
 *               &lt;enumeration value="advance"/>
 *               &lt;enumeration value="encashment"/>
 *               &lt;enumeration value="cheque"/>
 *               &lt;enumeration value="compensation"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentType", propOrder = {
    "id",
    "ids",
    "paymentType"
})
public class PaymentType {

  protected BigInteger id;
  protected String ids;
  protected String paymentType;

  /**
   * Gets the value of the id property.
   *
   * @return possible object is
   *         {@link BigInteger }
   */
  public BigInteger getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   *
   * @param value allowed object is
   *              {@link BigInteger }
   */
  public void setId(BigInteger value) {
    this.id = value;
  }

  /**
   * Gets the value of the ids property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getIds() {
    return ids;
  }

  /**
   * Sets the value of the ids property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setIds(String value) {
    this.ids = value;
  }

  /**
   * Gets the value of the paymentType property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getPaymentType() {
    return paymentType;
  }

  /**
   * Sets the value of the paymentType property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setPaymentType(String value) {
    this.paymentType = value;
  }

}

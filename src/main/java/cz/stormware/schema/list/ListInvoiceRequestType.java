//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.list;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import cz.stormware.schema.invoice.InvoiceTypeType;


/**
 * <p>Java class for listInvoiceRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="listInvoiceRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="requestInvoice" type="{http://www.stormware.cz/schema/list.xsd}requestInvoiceType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.stormware.cz/schema/list.xsd}listInvoiceRequestVerType" />
 *       &lt;attribute name="invoiceType" use="required" type="{http://www.stormware.cz/schema/invoice.xsd}invoiceTypeType" />
 *       &lt;attribute name="invoiceVersion" use="required" type="{http://www.stormware.cz/schema/invoice.xsd}invVersionType" />
 *       &lt;attribute name="extSystem" type="{http://www.stormware.cz/schema/type.xsd}string64" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listInvoiceRequestType", propOrder = {
    "requestInvoice"
})
public class ListInvoiceRequestType {

  @XmlElement(required = true)
  protected List<RequestInvoiceType> requestInvoice;
  @XmlAttribute(required = true)
  protected String version;
  @XmlAttribute(required = true)
  protected InvoiceTypeType invoiceType;
  @XmlAttribute(required = true)
  protected String invoiceVersion;
  @XmlAttribute
  protected String extSystem;

  /**
   * Gets the value of the requestInvoice property.
   * <p/>
   * <p/>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the requestInvoice property.
   * <p/>
   * <p/>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getRequestInvoice().add(newItem);
   * </pre>
   * <p/>
   * <p/>
   * <p/>
   * Objects of the following type(s) are allowed in the list
   * {@link RequestInvoiceType }
   */
  public List<RequestInvoiceType> getRequestInvoice() {
    if (requestInvoice == null) {
      requestInvoice = new ArrayList<RequestInvoiceType>();
    }
    return this.requestInvoice;
  }

  /**
   * Gets the value of the version property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getVersion() {
    return version;
  }

  /**
   * Sets the value of the version property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setVersion(String value) {
    this.version = value;
  }

  /**
   * Gets the value of the invoiceType property.
   *
   * @return possible object is
   *         {@link InvoiceTypeType }
   */
  public InvoiceTypeType getInvoiceType() {
    return invoiceType;
  }

  /**
   * Sets the value of the invoiceType property.
   *
   * @param value allowed object is
   *              {@link InvoiceTypeType }
   */
  public void setInvoiceType(InvoiceTypeType value) {
    this.invoiceType = value;
  }

  /**
   * Gets the value of the invoiceVersion property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getInvoiceVersion() {
    return invoiceVersion;
  }

  /**
   * Sets the value of the invoiceVersion property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setInvoiceVersion(String value) {
    this.invoiceVersion = value;
  }

  /**
   * Gets the value of the extSystem property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getExtSystem() {
    return extSystem;
  }

  /**
   * Sets the value of the extSystem property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setExtSystem(String value) {
    this.extSystem = value;
  }

}

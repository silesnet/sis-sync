//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import cz.stormware.schema.addressbook.AddressbookResponseType;
import cz.stormware.schema.invoice.InvoiceResponseType;
import cz.stormware.schema.list.ListAddressBookType;
import cz.stormware.schema.list.ListInvoiceType;
import cz.stormware.schema.type.StavType2;


/**
 * <p>Java class for responsePackItemType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="responsePackItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://www.stormware.cz/schema/invoice.xsd}invoiceResponse"/>
 *         &lt;element ref="{http://www.stormware.cz/schema/addressbook.xsd}addressbookResponse"/>
 *         &lt;element ref="{http://www.stormware.cz/schema/list.xsd}listInvoice"/>
 *         &lt;element ref="{http://www.stormware.cz/schema/list.xsd}listAddressBook"/>
 *       &lt;/choice>
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="state" use="required" type="{http://www.stormware.cz/schema/type.xsd}stavType2" />
 *       &lt;attribute name="note" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responsePackItemType", propOrder = {
    "invoiceResponse",
    "addressbookResponse",
    "listInvoice",
    "listAddressBook"
})
public class ResponsePackItemType {

  @XmlElement(namespace = "http://www.stormware.cz/schema/invoice.xsd")
  protected InvoiceResponseType invoiceResponse;
  @XmlElement(namespace = "http://www.stormware.cz/schema/addressbook.xsd")
  protected AddressbookResponseType addressbookResponse;
  @XmlElement(namespace = "http://www.stormware.cz/schema/list.xsd")
  protected ListInvoiceType listInvoice;
  @XmlElement(namespace = "http://www.stormware.cz/schema/list.xsd")
  protected ListAddressBookType listAddressBook;
  @XmlAttribute(required = true)
  protected String version;
  @XmlAttribute(required = true)
  protected String id;
  @XmlAttribute(required = true)
  protected StavType2 state;
  @XmlAttribute
  protected String note;

  /**
   * Gets the value of the invoiceResponse property.
   *
   * @return possible object is
   *         {@link InvoiceResponseType }
   */
  public InvoiceResponseType getInvoiceResponse() {
    return invoiceResponse;
  }

  /**
   * Sets the value of the invoiceResponse property.
   *
   * @param value allowed object is
   *              {@link InvoiceResponseType }
   */
  public void setInvoiceResponse(InvoiceResponseType value) {
    this.invoiceResponse = value;
  }

  /**
   * Gets the value of the addressbookResponse property.
   *
   * @return possible object is
   *         {@link AddressbookResponseType }
   */
  public AddressbookResponseType getAddressbookResponse() {
    return addressbookResponse;
  }

  /**
   * Sets the value of the addressbookResponse property.
   *
   * @param value allowed object is
   *              {@link AddressbookResponseType }
   */
  public void setAddressbookResponse(AddressbookResponseType value) {
    this.addressbookResponse = value;
  }

  /**
   * Gets the value of the listInvoice property.
   *
   * @return possible object is
   *         {@link ListInvoiceType }
   */
  public ListInvoiceType getListInvoice() {
    return listInvoice;
  }

  /**
   * Sets the value of the listInvoice property.
   *
   * @param value allowed object is
   *              {@link ListInvoiceType }
   */
  public void setListInvoice(ListInvoiceType value) {
    this.listInvoice = value;
  }

  /**
   * Gets the value of the listAddressBook property.
   *
   * @return possible object is
   *         {@link ListAddressBookType }
   */
  public ListAddressBookType getListAddressBook() {
    return listAddressBook;
  }

  /**
   * Sets the value of the listAddressBook property.
   *
   * @param value allowed object is
   *              {@link ListAddressBookType }
   */
  public void setListAddressBook(ListAddressBookType value) {
    this.listAddressBook = value;
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
   * Gets the value of the id property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setId(String value) {
    this.id = value;
  }

  /**
   * Gets the value of the state property.
   *
   * @return possible object is
   *         {@link StavType2 }
   */
  public StavType2 getState() {
    return state;
  }

  /**
   * Sets the value of the state property.
   *
   * @param value allowed object is
   *              {@link StavType2 }
   */
  public void setState(StavType2 value) {
    this.state = value;
  }

  /**
   * Gets the value of the note property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getNote() {
    return note;
  }

  /**
   * Sets the value of the note property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setNote(String value) {
    this.note = value;
  }

}

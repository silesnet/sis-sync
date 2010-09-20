//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import cz.stormware.schema.type.StavType2;


/**
 * <p>Java class for responsePackType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="responsePackType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responsePackItem" type="{http://www.stormware.cz/schema/response.xsd}responsePackItemType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="state" use="required" type="{http://www.stormware.cz/schema/type.xsd}stavType2" />
 *       &lt;attribute name="note" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="programVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responsePackType", propOrder = {
    "responsePackItem"
})
public class ResponsePackType {

  protected List<ResponsePackItemType> responsePackItem;
  @XmlAttribute(required = true)
  protected String version;
  @XmlAttribute(required = true)
  protected String id;
  @XmlAttribute(required = true)
  protected StavType2 state;
  @XmlAttribute
  protected String note;
  @XmlAttribute
  protected String programVersion;

  /**
   * Gets the value of the responsePackItem property.
   * <p/>
   * <p/>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the responsePackItem property.
   * <p/>
   * <p/>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getResponsePackItem().add(newItem);
   * </pre>
   * <p/>
   * <p/>
   * <p/>
   * Objects of the following type(s) are allowed in the list
   * {@link ResponsePackItemType }
   */
  public List<ResponsePackItemType> getResponsePackItem() {
    if (responsePackItem == null) {
      responsePackItem = new ArrayList<ResponsePackItemType>();
    }
    return this.responsePackItem;
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

  /**
   * Gets the value of the programVersion property.
   *
   * @return possible object is
   *         {@link String }
   */
  public String getProgramVersion() {
    return programVersion;
  }

  /**
   * Sets the value of the programVersion property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setProgramVersion(String value) {
    this.programVersion = value;
  }

}

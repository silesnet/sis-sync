//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.list;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for requestInvoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="requestInvoiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateFrom" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dateTill" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="userFilterName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selectedNumbers" type="{http://www.stormware.cz/schema/list.xsd}selectedNumbersType" minOccurs="0"/>
 *         &lt;element name="selectedCompanys" type="{http://www.stormware.cz/schema/list.xsd}selectedCompanysType" minOccurs="0"/>
 *         &lt;element name="selectedIco" type="{http://www.stormware.cz/schema/list.xsd}selectedIcoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requestInvoiceType", propOrder = {
    "dateFrom",
    "dateTill",
    "userFilterName",
    "selectedNumbers",
    "selectedCompanys",
    "selectedIco"
})
public class RequestInvoiceType {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateFrom;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateTill;
    protected String userFilterName;
    protected SelectedNumbersType selectedNumbers;
    protected SelectedCompanysType selectedCompanys;
    protected SelectedIcoType selectedIco;

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFrom(XMLGregorianCalendar value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTill property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTill() {
        return dateTill;
    }

    /**
     * Sets the value of the dateTill property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTill(XMLGregorianCalendar value) {
        this.dateTill = value;
    }

    /**
     * Gets the value of the userFilterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserFilterName() {
        return userFilterName;
    }

    /**
     * Sets the value of the userFilterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserFilterName(String value) {
        this.userFilterName = value;
    }

    /**
     * Gets the value of the selectedNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedNumbersType }
     *     
     */
    public SelectedNumbersType getSelectedNumbers() {
        return selectedNumbers;
    }

    /**
     * Sets the value of the selectedNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedNumbersType }
     *     
     */
    public void setSelectedNumbers(SelectedNumbersType value) {
        this.selectedNumbers = value;
    }

    /**
     * Gets the value of the selectedCompanys property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedCompanysType }
     *     
     */
    public SelectedCompanysType getSelectedCompanys() {
        return selectedCompanys;
    }

    /**
     * Sets the value of the selectedCompanys property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedCompanysType }
     *     
     */
    public void setSelectedCompanys(SelectedCompanysType value) {
        this.selectedCompanys = value;
    }

    /**
     * Gets the value of the selectedIco property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedIcoType }
     *     
     */
    public SelectedIcoType getSelectedIco() {
        return selectedIco;
    }

    /**
     * Sets the value of the selectedIco property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedIcoType }
     *     
     */
    public void setSelectedIco(SelectedIcoType value) {
        this.selectedIco = value;
    }

}

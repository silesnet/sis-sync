//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parameterAgendaFormType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="parameterAgendaFormType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="issuedInvoice"/>
 *     &lt;enumeration value="receivedInvoice"/>
 *     &lt;enumeration value="receivable"/>
 *     &lt;enumeration value="commitment"/>
 *     &lt;enumeration value="issuedAdvanceInvoice"/>
 *     &lt;enumeration value="receivedAdvanceInvoice"/>
 *     &lt;enumeration value="offer"/>
 *     &lt;enumeration value="enquiry"/>
 *     &lt;enumeration value="receivedOrder"/>
 *     &lt;enumeration value="issuedOrder"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "parameterAgendaFormType")
@XmlEnum
public enum ParameterAgendaFormType {


  /**
   * Formul�\u0159 agendy Vydan� faktury.
   */
  @XmlEnumValue("issuedInvoice")
  ISSUED_INVOICE("issuedInvoice"),

  /**
   * Formul�\u0159 agendy P\u0159ijat� faktury.
   */
  @XmlEnumValue("receivedInvoice")
  RECEIVED_INVOICE("receivedInvoice"),

  /**
   * Formul�\u0159 agendy Ostatn� pohled�vky.
   */
  @XmlEnumValue("receivable")
  RECEIVABLE("receivable"),

  /**
   * Formul�\u0159 agendy Ostatn� z�vazky.
   */
  @XmlEnumValue("commitment")
  COMMITMENT("commitment"),

  /**
   * Formul�\u0159 agendy Vydan� z�lohov� faktury.
   */
  @XmlEnumValue("issuedAdvanceInvoice")
  ISSUED_ADVANCE_INVOICE("issuedAdvanceInvoice"),

  /**
   * Formul�\u0159 agendy P\u0159ijat� z�lohov� faktury.
   */
  @XmlEnumValue("receivedAdvanceInvoice")
  RECEIVED_ADVANCE_INVOICE("receivedAdvanceInvoice"),

  /**
   * Formul�\u0159 agendy Nab�dky.
   */
  @XmlEnumValue("offer")
  OFFER("offer"),

  /**
   * Formul�\u0159 agendy Popt�vky.
   */
  @XmlEnumValue("enquiry")
  ENQUIRY("enquiry"),

  /**
   * Formul�\u0159 agendy P\u0159ijat� objedn�vky.
   */
  @XmlEnumValue("receivedOrder")
  RECEIVED_ORDER("receivedOrder"),

  /**
   * Formul�\u0159 agendy Vydan� objedn�vky.
   */
  @XmlEnumValue("issuedOrder")
  ISSUED_ORDER("issuedOrder");
  private final String value;

  ParameterAgendaFormType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ParameterAgendaFormType fromValue(String v) {
    for (ParameterAgendaFormType c : ParameterAgendaFormType.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}

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
 * <p>Java class for vatRateType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="vatRateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="high"/>
 *     &lt;enumeration value="low"/>
 *     &lt;enumeration value="none"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "vatRateType")
@XmlEnum
public enum VatRateType {


  /**
   * Z�kladn� sazba.
   */
  @XmlEnumValue("high")
  HIGH("high"),

  /**
   * Sn�en� sazba.
   */
  @XmlEnumValue("low")
  LOW("low"),

  /**
   * Bez DPH.
   */
  @XmlEnumValue("none")
  NONE("none");
  private final String value;

  VatRateType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static VatRateType fromValue(String v) {
    for (VatRateType c : VatRateType.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.14 at 09:43:22 PM CET 
//


package cz.stormware.schema.data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cz.stormware.schema.data package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataPack_QNAME = new QName("http://www.stormware.cz/schema/data.xsd", "dataPack");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cz.stormware.schema.data
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DataPackItemType }
     * 
     */
    public DataPackItemType createDataPackItemType() {
        return new DataPackItemType();
    }

    /**
     * Create an instance of {@link TransformationType }
     * 
     */
    public TransformationType createTransformationType() {
        return new TransformationType();
    }

    /**
     * Create an instance of {@link DataPackType }
     * 
     */
    public DataPackType createDataPackType() {
        return new DataPackType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataPackType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.stormware.cz/schema/data.xsd", name = "dataPack")
    public JAXBElement<DataPackType> createDataPack(DataPackType value) {
        return new JAXBElement<DataPackType>(_DataPack_QNAME, DataPackType.class, null, value);
    }

}
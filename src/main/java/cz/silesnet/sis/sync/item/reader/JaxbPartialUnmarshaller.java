package cz.silesnet.sis.sync.item.reader;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.transform.Source;

import org.springframework.oxm.XmlMappingException;
import org.springframework.xml.transform.StaxSource;

/**
 * JAXB partial unmarshaller from StAX {@link XMLEventReader}. It is able to
 * unmarshal {@link JAXBElement} that is not declared as root in XSD. Suitable
 * for processing long batches of repeating elements.
 */
public class JaxbPartialUnmarshaller implements org.springframework.oxm.Unmarshaller {

  private Unmarshaller jaxbUnmarshaller;
  private Class<?> fragmentClass;

  public JaxbPartialUnmarshaller() {
  }

  /**
   * Sets the {@link JAXBContext} and for internal {@link Unmarshaller}.
   * 
   * @param context
   *          the new context
   */
  public void setContext(JAXBContext context) {
    try {
      jaxbUnmarshaller = context.createUnmarshaller();
    } catch (JAXBException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Sets the fragment class, {@link #unmarshal(XMLEventReader)} will return
   * instances of this class.
   * 
   * @param fragmentClass
   *          the new fragment class
   */
  public void setFragmentClass(Class<?> fragmentClass) {
    this.fragmentClass = fragmentClass;
  }

  @Override
  public Object unmarshal(Source source) throws IOException, XmlMappingException {
    if (!(source instanceof StaxSource))
      throw new IllegalArgumentException("StAXSource expected.");
    XMLEventReader reader = ((StaxSource) source).getXMLEventReader();
    JAXBElement<?> element;
    try {
      element = jaxbUnmarshaller.unmarshal(reader, fragmentClass);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    return element.getValue();
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean supports(Class clazz) {
    return fragmentClass.equals(clazz);
  }

}

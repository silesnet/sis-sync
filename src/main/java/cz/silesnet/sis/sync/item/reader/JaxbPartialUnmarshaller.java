package cz.silesnet.sis.sync.item.reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;

/**
 * JAXB partial unmarshaller from StAX {@link XMLEventReader}. It is able to
 * unmarshal {@link JAXBElement} that is not declared as root in XSD. Suitable
 * for processing long batches of repeating elements.
 */
public class JaxbPartialUnmarshaller<F> {

  private Unmarshaller jaxbUnmarshaller;
  private Class<F> fragmentClass;

  public JaxbPartialUnmarshaller() {
  }

  /**
   * Unmarshal.
   * 
   * @param eventReader
   *          {@link XMLEventReader} to read from, should be positioned on
   *          START_ELEMENT of the required element.
   * 
   * @return unmarshaled element's value {@link JAXBElement#getValue()} of
   *         fragmentClass
   */
  public F unmarshal(XMLEventReader eventReader) {
    JAXBElement<F> element;
    try {
      element = jaxbUnmarshaller.unmarshal(eventReader, fragmentClass);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    return element.getValue();
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
  public void setFragmentClass(Class<F> fragmentClass) {
    this.fragmentClass = fragmentClass;
  }

}

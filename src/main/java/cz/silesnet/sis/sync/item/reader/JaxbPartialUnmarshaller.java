package cz.silesnet.sis.sync.item.reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;

public class JaxbPartialUnmarshaller<F> {

  private Unmarshaller jaxbUnmarshaller;
  private Class<F> fragmentClass;

  public JaxbPartialUnmarshaller() {
  }

  public F unmarshal(XMLEventReader eventReader) {
    JAXBElement<F> element;
    try {
      element = jaxbUnmarshaller.unmarshal(eventReader, fragmentClass);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    return element.getValue();
  }

  public void setContext(JAXBContext context) {
    try {
      jaxbUnmarshaller = context.createUnmarshaller();
    } catch (JAXBException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public void setFragmentClass(Class<F> fragmentClass) {
    this.fragmentClass = fragmentClass;
  }

}

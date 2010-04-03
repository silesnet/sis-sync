package cz.silesnet.sis.sync.item.reader;

import javax.xml.stream.XMLEventReader;

import org.springframework.batch.item.xml.EventReaderDeserializer;

/**
 * The Class JaxbEventReaderDeserializer.
 */
public class JaxbEventReaderDeserializer<F> implements EventReaderDeserializer {

  private JaxbPartialUnmarshaller<F> unmarshaller;

  /**
   * Sets the partial unmarshaller for given type.
   * 
   * @param unamrshaller
   *          the new partial unmarshaller
   */
  public void setPartialUnmarshaller(JaxbPartialUnmarshaller<F> unamrshaller) {
    this.unmarshaller = unamrshaller;
  }

  /**
   * @see org.springframework.batch.item.xml.EventReaderDeserializer#deserializeFragment
   *      (javax.xml.stream.XMLEventReader)
   */
  @Override
  public Object deserializeFragment(XMLEventReader eventReader) {
    return unmarshaller.unmarshal(eventReader);
  }

}

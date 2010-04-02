package cz.silesnet.sis.sync.item.reader;

import javax.xml.stream.XMLEventReader;

import org.springframework.batch.item.xml.EventReaderDeserializer;

public class JaxbEventReaderDeserializer implements EventReaderDeserializer {

  private JaxbPartialUnmarshaller unmarshaller;

  public void setPartialUnmarshaller(JaxbPartialUnmarshaller unamrshaller) {
    this.unmarshaller = unamrshaller;
  }

  @Override
  public Object deserializeFragment(XMLEventReader eventReader) {
    return unmarshaller.unmarshal(eventReader);
  }

}

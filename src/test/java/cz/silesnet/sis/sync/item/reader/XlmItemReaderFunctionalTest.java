package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.xml.EventReaderDeserializer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import cz.stormware.schema.response.ResponsePackItemType;
import cz.stormware.schema.type.StavType2;

public class XlmItemReaderFunctionalTest {

  @Test
  public void testReadResponses() throws Exception {
    StaxEventItemReader reader = new StaxEventItemReader();
    reader.setFragmentRootElementName("responsePackItem");
    Resource resource = new ClassPathResource("xml/invoices-response-20100313.xml");
    reader.setResource(resource);

    final JAXBContext context = JAXBContext.newInstance("cz.stormware.schema.response");
    final Unmarshaller unmarshaller = context.createUnmarshaller();

    reader.setFragmentDeserializer(new EventReaderDeserializer() {

      @Override
      public Object deserializeFragment(XMLEventReader eventReader) {
        try {
          return unmarshaller.unmarshal(eventReader, ResponsePackItemType.class).getValue();
        } catch (JAXBException e) {
          throw new RuntimeException(e);
        }
      }
    });

    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("cz.stormware.schema.response");
    marshaller.afterPropertiesSet();
    // reader.setFragmentDeserializer(new
    // UnmarshallingEventReaderDeserializer(marshaller));

    reader.afterPropertiesSet();

    reader.open(new ExecutionContext());

    Object object = reader.read();
    ResponsePackItemType item = (ResponsePackItemType) object;
    assertThat(item.getState(), is(StavType2.OK));
    assertThat(item.getId(), is("2010-03-10T17:02:48.593_0000000000_134409"));
  }
}

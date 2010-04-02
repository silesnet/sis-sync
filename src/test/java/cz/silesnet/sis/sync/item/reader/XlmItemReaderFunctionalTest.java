package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cz.stormware.schema.response.ResponsePackItemType;
import cz.stormware.schema.type.StavType2;

public class XlmItemReaderFunctionalTest {

  @Test
  public void testReadResponses() throws Exception {
    StaxEventItemReader reader = new StaxEventItemReader();
    reader.setFragmentRootElementName("responsePackItem");
    Resource resource = new ClassPathResource("xml/invoices-response-20100313.xml");
    reader.setResource(resource);

    JAXBContext context = JAXBContext.newInstance("cz.stormware.schema.response");
    JaxbPartialUnmarshaller<ResponsePackItemType> partialUnmarshaller = new JaxbPartialUnmarshaller<ResponsePackItemType>();
    partialUnmarshaller.setContext(context);
    partialUnmarshaller.setFragmentClass(ResponsePackItemType.class);

    JaxbEventReaderDeserializer readerDeserializer = new JaxbEventReaderDeserializer();
    readerDeserializer.setPartialUnmarshaller(partialUnmarshaller);

    reader.setFragmentDeserializer(readerDeserializer);
    reader.afterPropertiesSet();

    reader.open(new ExecutionContext());
    Object object = reader.read();
    ResponsePackItemType item = (ResponsePackItemType) object;
    assertThat(item.getState(), is(StavType2.OK));
    assertThat(item.getId(), is("2010-03-10T17:02:48.593_0000000000_134409"));
  }
}

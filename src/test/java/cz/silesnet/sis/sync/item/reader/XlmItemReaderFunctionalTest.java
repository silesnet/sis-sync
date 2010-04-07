package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.xml.EventReaderDeserializer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.xml.stream.StaxEventXmlReader;
import org.xml.sax.InputSource;

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
    JaxbEventReaderDeserializer deserializer = new JaxbEventReaderDeserializer();
    deserializer.setContext(context);
    deserializer.setFragmentClass(ResponsePackItemType.class);

    reader.setFragmentDeserializer(deserializer);
    reader.afterPropertiesSet();

    reader.open(new ExecutionContext());
    Object object = reader.read();
    ResponsePackItemType item = (ResponsePackItemType) object;
    assertThat(item.getState(), is(StavType2.OK));
    assertThat(item.getId(), is("2010-03-10T17:02:48.593_0000000000_134409"));
  }

  @Test
  public void testStaxFragmentToSaxIntegration() throws Exception {
    StaxEventItemReader reader = new StaxEventItemReader();
    reader.setFragmentRootElementName("responsePackItem");
    Resource resource = new ClassPathResource("xml/invoices-response-20100313.xml");
    reader.setResource(resource);

    OutputFormat format = new OutputFormat();
    format.setIndent(2);
    format.setLineWidth(0);
    final XMLSerializer serializer = new XMLSerializer();
    serializer.setOutputByteStream(System.out);
    serializer.setOutputFormat(format);

    final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    reader.setFragmentDeserializer(new EventReaderDeserializer() {

      @Override
      public Object deserializeFragment(XMLEventReader eventReader) {
        // the filtering is needed for proper output formatting (indentation)
        // only
        try {
          XMLEventReader filteredReader = xmlInputFactory.createFilteredReader(eventReader,
              new EventFilter() {
                @Override
                public boolean accept(XMLEvent event) {
                  if (event.isCharacters() && event.asCharacters().isWhiteSpace())
                    return false;
                  return true;
                }
              });

          StaxEventXmlReader saxReader = new StaxEventXmlReader(filteredReader);
          saxReader.setContentHandler(serializer);
          saxReader.parse((InputSource) null);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        return new Object();
      }
    });

    reader.afterPropertiesSet();
    reader.open(new ExecutionContext());
    reader.read();
  }
}

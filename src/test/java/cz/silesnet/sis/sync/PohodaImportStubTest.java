package cz.silesnet.sis.sync;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.xml.transform.StaxSource;
import org.xml.sax.SAXException;

public class PohodaImportStubTest {

  @Test
  public void testSpringFragmentReader() throws UnexpectedInputException, ParseException, Exception {
    Resource input = new ClassPathResource("data/20081206_sis_customers.xml");
    StaxEventItemReader reader = new StaxEventItemReader();
    reader.setResource(input);
    reader.setFragmentRootElementName("dataPackItem");
    reader.setUnmarshaller(new Unmarshaller() {

      @Override
      public Object unmarshal(Source source) throws XmlMappingException, IOException {
        XMLEventReader eventReader = ((StaxSource) source).getXMLEventReader();
        while (eventReader.hasNext()) {
          try {
            XMLEvent tag = eventReader.nextEvent();
            if (tag.isStartElement()) {
              StartElement element = tag.asStartElement();
              System.out.println(element.getName());
              if ("dataPackItem".equals(element.getName().getLocalPart())) {
                System.out.println(element.getAttributeByName(new QName("id")).getValue());
              }
            }
          } catch (XMLStreamException e) {
            e.printStackTrace();
          }
        }
        return "OK";
      }

      @Override
      public boolean supports(Class clazz) {
        return true;
      }
    });
    Object item = null;
    ExecutionContext context = new ExecutionContext();
    reader.open(context);
    while ((item = reader.read()) != null) {
      System.out.println(item);
    }
    reader.close();
  }

  // FIXME
  // @Test
  public void testDoImport() throws FileNotFoundException, IOException, SAXException {
    Resource input = new ClassPathResource("data/20081206_sis_customers.xml");
    Resource output = new FileSystemResource("target/test-classes/data/20081206_sps_customers.xml");
    Resource referenceOutput = new ClassPathResource("data/20090608_sps_customers.xml");
    PohodaImportStub pohodaImport = new PohodaImportStub(null);
    pohodaImport.doImport(input, output);
    Reader producedXml = new FileReader(output.getFile());
    FileReader expectedXml = new FileReader(referenceOutput.getFile());
    XMLUnit.setIgnoreWhitespace(true);
    Diff diff = XMLUnit.compareXML(expectedXml, producedXml);
    assertThat(diff.toString(), diff.similar(), is(true));
  }

  // FIXME
  // @Test
  // public void testParseIniFile() {
  // fail("Not yet implemented");
  // }

}

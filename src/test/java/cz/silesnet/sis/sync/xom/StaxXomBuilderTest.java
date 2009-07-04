package cz.silesnet.sis.sync.xom;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.xml.EventReaderDeserializer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import cz.silesnet.sis.sync.xom.StaxXomBuilder;

public class StaxXomBuilderTest extends XMLTestCase {
    private Resource input;

    @Before
    public void setUp() throws Exception {
        input = new ClassPathResource("data/20081206_sis_customers.xml");
    }

    @After
    public void tearDown() throws Exception {
        input = null;
    }

    @Test
    public void testBuild() throws XMLStreamException, FactoryConfigurationError, IOException, SAXException {
        XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(input.getInputStream());
        StaxXomBuilder builder = new StaxXomBuilder(eventReader);
        Document document = builder.build();

        String xmlDocument = toXml(document);

        // compare to original
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(new FileReader(input.getFile()), new StringReader(xmlDocument));
    }

    @Test
    public final void testBuildFromFragments() throws UnexpectedInputException, NoWorkFoundException, ParseException,
            Exception {
        // prepare fragments parser
        StaxEventItemReader reader = new StaxEventItemReader();
        reader.setResource(input);
        reader.setFragmentRootElementName("dataPackItem");
        reader.setFragmentDeserializer(new EventReaderDeserializer() {

            public Object deserializeFragment(XMLEventReader eventReader) {
                StaxXomBuilder builder = new StaxXomBuilder(eventReader);
                return builder.build();
            }
        });

        // prepare output root element
        Element dataPack = new Element("dat:dataPack", "http://www.stormware.cz/schema/data.xsd");
        dataPack.addNamespaceDeclaration("adb", "http://www.stormware.cz/schema/addressbook.xsd");
        dataPack.addNamespaceDeclaration("typ", "http://www.stormware.cz/schema/type.xsd");
        dataPack.addAttribute(new Attribute("id", "11E6F9FA0F6"));
        dataPack.addAttribute(new Attribute("ico", "12345678"));
        dataPack.addAttribute(new Attribute("application", "SIS"));
        dataPack.addAttribute(new Attribute("version", "1.1"));
        dataPack.addAttribute(new Attribute("note", "Customers import."));

        // read fragments sequentially and add them to root
        ExecutionContext context = new ExecutionContext();
        reader.open(context);
        Object item = null;
        while ((item = reader.read()) != null) {
            Document doc = (Document) item;
            Element dataPackItem = doc.getRootElement();
            // has to do it in order to detach fragment's root element
            doc.setRootElement(new Element("empty"));
            dataPack.appendChild(dataPackItem);
        }
        reader.close(context);

        // create result document
        Document document = new Document(dataPack);
        String xmlDocument = toXml(document);

        // compare to original
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(new FileReader(input.getFile()), new StringReader(xmlDocument));
    }

    private String toXml(Document document) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Serializer serializer = new Serializer(bos);
        serializer.setIndent(2);
        serializer.write(document);
        serializer.flush();
        return bos.toString();
    }

}

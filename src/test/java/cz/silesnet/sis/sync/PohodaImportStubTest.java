package cz.silesnet.sis.sync;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.NodeFactory;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;
import nu.xom.XPathContext;

import org.codehaus.staxmate.SMInputFactory;
import org.codehaus.staxmate.dom.DOMConverter;
import org.codehaus.staxmate.in.SMInputCursor;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.xml.stream.XmlEventStreamReader;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PohodaImportStubTest extends XMLTestCase {

  @Override
  @Before
  public void setUp() throws Exception {
  }

  @Override
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testSpringFragmentReader() throws UnexpectedInputException, NoWorkFoundException,
      ParseException, Exception {
    Resource input = new ClassPathResource("data/20081206_sis_customers.xml");
    StaxEventItemReader reader = new StaxEventItemReader();
    reader.setResource(input);
    reader.setFragmentRootElementName("dataPackItem");
    reader.setFragmentDeserializer(new EventReaderDeserializer() {

      public Object deserializeFragment(XMLEventReader eventReader) {
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
    });
    Object item = null;
    ExecutionContext context = new ExecutionContext();
    reader.open(context);
    while ((item = reader.read()) != null) {
      System.out.println(item);
    }
    reader.close(context);
  }

  @Test
  public void testXStreamMapping() throws UnexpectedInputException, NoWorkFoundException,
      ParseException, Exception {
    Resource input = new ClassPathResource("data/20081206_sis_customers.xml");
    final XStream xStream = new XStream();
    final DOMConverter converter = new DOMConverter();
    xStream.registerConverter(new Converter() {

      public void marshal(Object arg0, HierarchicalStreamWriter arg1, MarshallingContext arg2) {
        throw new UnsupportedOperationException();
      }

      public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext arg1) {

        String name = reader.getNodeName();
        reader.close();
        return "#" + name;
      }

      public boolean canConvert(Class arg0) {
        return arg0.equals(PohodaImportStubTest.class);
      }
    });
    xStream.alias("dataPackItem", PohodaImportStubTest.class);
    StaxEventItemReader reader = new StaxEventItemReader();
    reader.setResource(input);
    reader.setFragmentRootElementName("dataPackItem");
    reader.setFragmentDeserializer(new EventReaderDeserializer() {
      public Object deserializeFragment(XMLEventReader eventReader) {
        Object item = null;
        org.w3c.dom.Document document = null;
        try {
          XmlEventStreamReader streamReader = new XmlEventStreamReader(eventReader);
          document = converter.buildDocument(streamReader);
          // StaxReader staxReader = new StaxReader(new QNameMap(),
          // streamReader);
          // PathTrackingReader trackingReader = new
          // PathTrackingReader(staxReader, new PathTracker());
          // item = xStream.unmarshal(trackingReader);
          // System.out.println(item);
        } catch (XMLStreamException e) {
          throw new RuntimeException(e);
        }
        return document.toString() + "$$";
      }
    });
    Object item = null;
    ExecutionContext context = new ExecutionContext();
    reader.open(context);
    while ((item = reader.read()) != null) {
      System.out.println(item);
    }
    reader.close(context);

  }

  // @Test
  public void fixtestStaxMate() throws XMLStreamException, IOException, SAXException {
    // FIXME
    Resource input = new ClassPathResource("data/20081206_sis_customers.xml");
    SMInputFactory factory = new SMInputFactory(XMLInputFactory.newInstance());
    SMInputCursor root = factory.rootElementCursor(input.getFile()).advance();
    QName itemQName = new QName("http://www.stormware.cz/schema/data.xsd", "dataPackItem", "dat");
    SMInputCursor items = root.childElementCursor(itemQName);
    for (items.advance(); items.asEvent() != null; items.advance()) {
      System.out.println(items.getLocalName());
      // addressbook/addressbookHeader
      SMInputCursor addressbookHeader = items.childElementCursor().advance().childElementCursor()
          .advance();
      // identity/address
      SMInputCursor address = addressbookHeader.childElementCursor().advance().childElementCursor()
          .advance();
      SMInputCursor addressChildren = address.childElementCursor();
      for (addressChildren.advance(); addressChildren.asEvent() != null; addressChildren.advance()) {
        System.out.println(addressChildren.getLocalName() + ": "
            + addressChildren.collectDescendantText());
      }
    }
    fail("Not yet implemented");
  }

  private String cursorLocation(SMInputCursor cursor) {
    String location = null;
    try {
      location = cursor.getCursorLocation().getLineNumber() + ", "
          + cursor.getCursorLocation().getColumnNumber();
    } catch (XMLStreamException e) {
      throw new RuntimeException(e);
    }
    return location;
  }

  @Test
  public void testDoImport() throws FileNotFoundException, IOException, SAXException {
    Resource input = new ClassPathResource("data/20081206_sis_customers.xml");
    Resource output = new FileSystemResource("target/test-files/20081206_sps_customers.xml");
    Resource referenceOutput = new ClassPathResource("data/20090608_sps_customers.xml");
    PohodaImportStub pohodaImport = new PohodaImportStub(null);
    pohodaImport.doImport(input, output);
    Reader producedXml = new FileReader(output.getFile());
    FileReader expectedXml = new FileReader(referenceOutput.getFile());
    XMLUnit.setIgnoreWhitespace(true);
    assertXMLEqual(expectedXml, producedXml);
  }

  // FIXME
  // @Test
  // public void testParseIniFile() {
  // fail("Not yet implemented");
  // }

  public final void doImportXom() throws IOException, ValidityException, ParsingException {
    ClassPathResource inRes = new ClassPathResource("data/20081206_sis_customers.xml");
    assertTrue(inRes.getFile().exists());
    DataPackItemReader reader = new DataPackItemReader();
    Builder parser = new Builder(reader);
    parser.build(inRes.getFile());
    reader.dumpResult();

    fail("Not yet implemented");
  }

  public class DataPackItemReader extends NodeFactory {
    private final Nodes empty = new Nodes();
    private final Element responsePack;
    private final Document doc;

    public DataPackItemReader() {
      super();
      responsePack = new Element("rsp:responsePack", "http://www.stormware.cz/schema/response.xsd");
      responsePack.addNamespaceDeclaration("rdc",
          "http://www.stormware.cz/schema/documentresponse.xsd");
      responsePack.addNamespaceDeclaration("adb", "http://www.stormware.cz/schema/addressbook.xsd");
      responsePack.addNamespaceDeclaration("inv", "http://www.stormware.cz/schema/invoice.xsd");
      responsePack.addNamespaceDeclaration("typ", "http://www.stormware.cz/schema/type.xsd");
      responsePack.addAttribute(new Attribute("version", "1.1"));
      responsePack.addAttribute(new Attribute("state", "ok"));
      responsePack.addAttribute(new Attribute("note", ""));

      doc = new Document(responsePack);

    }

    @Override
    public Nodes finishMakingElement(Element element) {
      if (!"dataPackItem".equals(element.getLocalName())) {
        return super.finishMakingElement(element);
      }
      XPathContext context = new XPathContext();
      context.addNamespace("adb", "http://www.stormware.cz/schema/addressbook.xsd");
      context.addNamespace("typ", "http://www.stormware.cz/schema/type.xsd");
      String id = element.getAttributeValue("id");
      String customerName = element.query(
          "adb:addressbook/adb:addressbookHeader/adb:identity/typ:address/typ:company", context)
          .get(0).getValue();
      // create response item
      Element details = new Element("rdc:producedDetails",
          "http://www.stormware.cz/schema/documentresponse.xsd");

      Element resId = new Element("rdc:id", "http://www.stormware.cz/schema/documentresponse.xsd");
      // FIXME put correct id here
      resId.appendChild(id);

      Element resCode = new Element("rdc:code",
          "http://www.stormware.cz/schema/documentresponse.xsd");
      resCode.appendChild(customerName);

      details.appendChild(resId);
      details.appendChild(resCode);

      Element resAddress = new Element("adb:addressbookResponse",
          "http://www.stormware.cz/schema/addressbook.xsd");
      resAddress.appendChild(details);
      resAddress.addAttribute(new Attribute("version", "1.4"));
      resAddress.addAttribute(new Attribute("state", "ok"));

      Element resItem = new Element("rsp:responsePackItem",
          "http://www.stormware.cz/schema/response.xsd");
      resItem.addAttribute(new Attribute("version", "1.0"));
      resItem.addAttribute(new Attribute("id", id));
      resItem.addAttribute(new Attribute("state", "ok"));
      resItem.appendChild(resAddress);

      responsePack.appendChild(resItem);
      return empty;
    }

    public void dumpResult() {
      // TODO have correct id
      responsePack.addAttribute(new Attribute("id", "12345"));
      Serializer serializer;
      try {
        serializer = new Serializer(System.out, "Windows-1250");
        serializer.setIndent(2);
        serializer.write(doc);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

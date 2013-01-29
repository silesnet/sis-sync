package cz.silesnet.sis.sync.item.writer;

import static cz.silesnet.sis.sync.item.writer.AbstractDataPackItemWriter.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import cz.silesnet.sis.sync.domain.Customer;

public class CustomerItemWriterTest extends XMLTestCase {

  private static Log log = LogFactory.getLog(CustomerItemWriterTest.class);

  private SisCustomerItemWriter writer;

  @Override
  @Before
  public void setUp() throws Exception {
    writer = new SisCustomerItemWriter();
  }

  @Override
  @After
  public void tearDown() throws Exception {
    writer = null;
  }

  @Test
  public void testItemLines() throws IOException, SAXException, XpathException {
    Customer customer = new Customer();
    customer.setSymbol("12345");
    customer.setName("Customer Name");
    customer.setSupplementaryName("Supplementary name");
    customer.setContactName("Contact Name");
    customer.setCity("Test City");
    customer.setStreet("Street 1234");
    customer.setZip("12345");
    customer.setIco("12345679");
    customer.setDic("DIC12345679");
    customer.setPhone("+420123456789");
    customer.setEmail("contact@customer.cz");
    customer.setContract("1245");
    customer.setAccountNo("1234-567890");
    customer.setBankCode("2400");
    String[] lines = writer.dataPackItemLines(customer);
    String linesString = StringUtils.collectionToDelimitedString(Arrays.asList(lines), "\n");

    String xmlLines = "<test xmlns:adb=\"addressbook\" xmlns:typ=\"types\" xmlns:ftr=\"filter\">" + linesString
        + "</test>";
    Reader customerXml = new StringReader(xmlLines);
    Reader expectedXml = new FileReader((new ClassPathResource("data/20090208_customers.xml"))
        .getFile());
    Map<String, String> ns = new HashMap<String, String>();
    ns.put("adb", "addressbook");
    ns.put("typ", "types");
    ns.put("ftr", "filter");
    XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(ns));
    XMLUnit.setIgnoreWhitespace(true);
    Diff diff = new Diff(expectedXml, customerXml);
    DetailedDiff detailedDiff = new DetailedDiff(diff);
    assertTrue(detailedDiff.toString(), diff.identical());
  }

  @Test
  public void testNoBankAccount() throws Exception {
    Customer customer = new Customer();
    String[] lines = writer.dataPackItemLines(customer);
    int length = lines.length;
    assertEquals(elEnd("adb:addressbookHeader"), lines[length - 2]);
    assertEquals(elEnd("adb:addressbook"), lines[length - 1]);
    if (log.isDebugEnabled()) {
      for (int i = 0; i < lines.length; i++) {
        log.debug(lines[i]);
      }
    }
  }

  @Test
  public void testNameSpaceLines() throws Exception {
    String[] lines = writer.nameSpaceLines();
    assertEquals("xmlns:adb=\"http://www.stormware.cz/schema/version_2/addressbook.xsd\"", lines[0]);
    assertEquals("xmlns:ftr=\"http://www.stormware.cz/schema/version_2/filter.xsd\"", lines[1]);
    assertEquals(2, lines.length);
  }
}

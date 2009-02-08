package cz.silesnet.sis.sync.item.writer;

import static cz.silesnet.sis.sync.item.writer.AbstractDataPackItemWriter.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
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
    public void testItemLines() throws IOException, SAXException {
        Customer customer = new Customer();
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
        customer.setContract("1245/2008");
        customer.setAccountNo("1234-567890");
        customer.setBankCode("2400");
        String[] lines = writer.dataPackItemLines(customer);
        Reader customerXml = new StringReader(StringUtils.join(lines, "\n"));
        Reader expectedXml = new FileReader((new ClassPathResource("data/20090208_customers.xml")).getFile());
        new Diff(expectedXml, customerXml);
        assertEquals(expectedXml, customerXml);
        log.debug("\n" + customerXml);
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
        assertEquals("xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\"", lines[0]);
        assertEquals(1, lines.length);
    }
}

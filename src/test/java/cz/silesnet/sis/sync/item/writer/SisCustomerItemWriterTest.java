package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Customer;

public class SisCustomerItemWriterTest {

    private static Log log = LogFactory.getLog(SisCustomerItemWriterTest.class);

    private SisCustomerItemWriter writer;

    @Before
    public void setUp() throws Exception {
        writer = new SisCustomerItemWriter();
    }

    @After
    public void tearDown() throws Exception {
        writer = null;
    }

    @Test
    public void testItemLines() {
        Customer customer = new Customer();
        customer.setId(1234L);
        customer.setName("Customer Name");
        customer.setCity("Test City");
        String[] lines = writer.dataPackItemLines(customer);
        int index = 0;
        assertEquals("<adb:addressbook version=\"" + SisCustomerItemWriter.ADDRESSBOOK_ELEMENT_VERSION + "\">",
                lines[index++]);
        assertEquals("<adb:addressbookHeader>", lines[index++]);
        assertEquals("<adb:identity>", lines[index++]);
        assertEquals("<typ:address>", lines[index++]);
        // customer data
        assertEquals("<typ:company>" + customer.getName() + "</typ:company>", lines[index++]);
        assertEquals("<typ:city>" + customer.getCity() + "</typ:city>", lines[index++]);
        // element trailer
        assertEquals("</typ:address>", lines[index++]);
        assertEquals("</adb:identity>", lines[index++]);
        assertEquals("</adb:addressbookHeader>", lines[index++]);
        assertEquals("</adb:addressbook>", lines[index++]);
        assertEquals(index, lines.length);

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

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
        customer.setContract("2008/007");
        String[] lines = writer.dataPackItemLines(customer);
        int index = 0;
        // header
        assertEquals("<adb:addressbook version=\"" + SisCustomerItemWriter.ADDRESSBOOK_ELEMENT_VERSION + "\">",
                lines[index++]);
        assertEquals("<adb:addressbookHeader>", lines[index++]);
        // identity
        assertEquals("<adb:identity>", lines[index++]);
        assertEquals("<typ:address>", lines[index++]);
        assertEquals("<typ:company>" + customer.getName() + "</typ:company>", lines[index++]);
        assertEquals("<typ:division>" + customer.getSupplementaryName() + "</typ:division>", lines[index++]);
        assertEquals("<typ:name>" + customer.getContactName() + "</typ:name>", lines[index++]);
        assertEquals("<typ:city>" + customer.getCity() + "</typ:city>", lines[index++]);
        assertEquals("<typ:street>" + customer.getStreet() + "</typ:street>", lines[index++]);
        assertEquals("<typ:zip>" + customer.getZip() + "</typ:zip>", lines[index++]);
        assertEquals("<typ:ico>" + customer.getIco() + "</typ:ico>", lines[index++]);
        assertEquals("<typ:dic>" + customer.getDic() + "</typ:dic>", lines[index++]);
        assertEquals("</typ:address>", lines[index++]);
        assertEquals("</adb:identity>", lines[index++]);
        // other
        assertEquals("<adb:phone>" + customer.getPhone() + "</adb:phone>", lines[index++]);
        assertEquals("<adb:email>" + customer.getEmail() + "</adb:email>", lines[index++]);
        assertEquals("<adb:adGroup>" + Customer.AD_GROUP_KEY + "</adb:adGroup>", lines[index++]);
        assertEquals("<adb:contract>" + customer.getContract() + "</adb:contract>", lines[index++]);
        // duplicity check
        assertEquals("<adb:duplicityFields actualize=\"true\">", lines[index++]);
        assertEquals("<adb:fieldICO>true</adb:fieldICO>", lines[index++]);
        assertEquals("<adb:fieldFirma>true</adb:fieldFirma>", lines[index++]);
        assertEquals("</adb:duplicityFields>", lines[index++]);
        // trailer
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

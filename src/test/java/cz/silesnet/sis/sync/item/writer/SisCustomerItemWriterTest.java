package cz.silesnet.sis.sync.item.writer;

import static cz.silesnet.sis.sync.item.writer.AbstractDataPackItemWriter.*;
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
        assertEquals(elBeg("adb:addressbook version=\"" + SisCustomerItemWriter.ADDRESSBOOK_ELEMENT_VERSION + "\""),
                lines[index++]);
        assertEquals(elBeg("adb:addressbookHeader"), lines[index++]);
        // identity
        assertEquals(elBeg("adb:identity"), lines[index++]);
        assertEquals(elBeg("typ:address"), lines[index++]);
        assertEquals(elValue("typ:company", customer.getName()), lines[index++]);
        assertEquals(elValue("typ:division", customer.getSupplementaryName()), lines[index++]);
        assertEquals(elValue("typ:name", customer.getContactName()), lines[index++]);
        assertEquals(elValue("typ:city", customer.getCity()), lines[index++]);
        assertEquals(elValue("typ:street", customer.getStreet()), lines[index++]);
        assertEquals(elValue("typ:zip", customer.getZip()), lines[index++]);
        assertEquals(elValue("typ:ico", customer.getIco()), lines[index++]);
        assertEquals(elValue("typ:dic", customer.getDic()), lines[index++]);
        assertEquals(elEnd("typ:address"), lines[index++]);
        assertEquals(elEnd("adb:identity"), lines[index++]);
        // other
        assertEquals(elValue("adb:phone", customer.getPhone()), lines[index++]);
        assertEquals(elValue("adb:email", customer.getEmail()), lines[index++]);
        assertEquals(elValue("adb:adGroup", Customer.AD_GROUP_KEY), lines[index++]);
        assertEquals(elValue("adb:p2", "true"), lines[index++]);
        assertEquals(elValue("adb:note", SisCustomerItemWriter.CONTRACT_NUMBER_PREFIX + customer.getContract()),
                lines[index++]);
        // duplicity check
        assertEquals(elBeg("adb:duplicityFields actualize=\"true\""), lines[index++]);
        assertEquals(elValue("adb:fieldICO", "true"), lines[index++]);
        assertEquals(elValue("adb:fieldFirma", "true"), lines[index++]);
        assertEquals(elEnd("adb:duplicityFields"), lines[index++]);
        // trailer
        assertEquals(elEnd("adb:addressbookHeader"), lines[index++]);
        assertEquals(elEnd("adb:addressbook"), lines[index++]);

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

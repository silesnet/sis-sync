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
        customer.setSymbol("1234");
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
        int index = 0;
        // header
        assertEquals(elBeg("adb:addressbook version=\"" + SisCustomerItemWriter.ADDRESSBOOK_ELEMENT_VERSION + "\""),
                lines[index++]);
        // address
        assertEquals(elBeg("adb:addressbookHeader"), lines[index++]);
        // identity
        assertEquals(elBeg("adb:identity"), lines[index++]);
        assertEquals(elBeg("typ:address"), lines[index++]);
        assertEquals(elValue("typ:company", customer.getName()), lines[index++]);
        assertEquals(elValue("typ:division", customer.getSupplementaryName()), lines[index++]);
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
        assertEquals(elValue("adb:adGroup", SisCustomerItemWriter.AD_GROUP_KEY), lines[index++]);
        assertEquals(elValue("adb:maturity", SisCustomerItemWriter.DUE_DAYS), lines[index++]);
        assertEquals(elValue("adb:agreement", customer.getSpsContract()), lines[index++]);
        assertEquals(elValue("adb:p2", "true"), lines[index++]);
        assertEquals(elValue("adb:note", SisCustomerItemWriter.CONTACT_NAME_PREFIX + customer.getContactName()),
                lines[index++]);
        // duplicity check
        assertEquals(elBeg("adb:duplicityFields actualize=\"true\""), lines[index++]);
        assertEquals(elValue("adb:id", customer.getSymbol()), lines[index++]);
        assertEquals(elEnd("adb:duplicityFields"), lines[index++]);
        assertEquals(elEnd("adb:addressbookHeader"), lines[index++]);
        // bank account
        assertEquals(elBeg("adb:addressbookAccount"), lines[index++]);
        assertEquals(elBeg("adb:accountItem"), lines[index++]);
        assertEquals(elValue("adb:accountNumber", customer.getAccountNo()), lines[index++]);
        assertEquals(elValue("adb:bankCode", customer.getBankCode()), lines[index++]);
        assertEquals(elValue("adb:defaultAccount", "true"), lines[index++]);
        assertEquals(elEnd("adb:accountItem"), lines[index++]);
        assertEquals(elEnd("adb:addressbookAccount"), lines[index++]);
        // trailer
        assertEquals(elEnd("adb:addressbook"), lines[index++]);

        assertEquals(index, lines.length);

        if (log.isDebugEnabled()) {
            for (int i = 0; i < lines.length; i++) {
                log.debug(lines[i]);
            }
        }
    }

    @Test
    public void testNoBankAccountNoSymbol() throws Exception {
        Customer customer = new Customer();
        String[] lines = writer.dataPackItemLines(customer);
        int length = lines.length;
        assertEquals(elValue("adb:note", SisCustomerItemWriter.CONTACT_NAME_PREFIX + customer.getContactName()),
                lines[length - 3]);
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

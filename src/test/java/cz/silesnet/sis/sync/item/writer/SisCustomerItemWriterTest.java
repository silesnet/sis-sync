package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Customer;

public class SisCustomerItemWriterTest {

    private static final String ICO = "12345678";
    private SisCustomerItemWriter writer;

    @Before
    public void setUp() throws Exception {
        writer = new SisCustomerItemWriter();
        writer.setIco(ICO);
    }

    @After
    public void tearDown() throws Exception {
        writer = null;
    }

    @Test
    public void testHeaderToString() {
        String header = writer.headerToString();
        String[] lines = header.split("\n");
        String[] dataPackLines = lines[1].split(" ");
        assertEquals("<?xml version=\"1.0\" encoding=\"Windows-1250\"?>", lines[0]);
        assertEquals("<dat:dataPack", dataPackLines[0]);
        assertEquals("id=\"" + writer.getBatchImportId() + "\"", dataPackLines[1]);
        assertEquals("ico=\"" + ICO + "\"", dataPackLines[2]);
        assertEquals("application=\"SIS\"", dataPackLines[3]);
        assertEquals("version=\"1.0\"", dataPackLines[4]);
        assertEquals("note=\"SIS customers import.\"", dataPackLines[5] + " " + dataPackLines[6] + " "
                + dataPackLines[7]);
        assertEquals("xmlns:dat=\"http://www.stormware.cz/schema/data.xsd\"", lines[2]);
        assertEquals("xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\"", lines[3]);
        assertEquals("xmlns:typ=\"http://www.stormware.cz/schema/type.xsd\">", lines[4]);
        assertEquals(5, lines.length);
    }

    @Test
    public void testItemToString() {
        Customer customer = new Customer();
        customer.setName("Customer Name");
        String customerXml = writer.itemToString(customer);
        String[] customerLines = customerXml.split("\n");
        int dataIndex = 5;
        int trailerIndex = 6;
        // element header
        assertEquals("<dat:dataPackItem id=\"" + writer.getItemImportId(customer) + "\" version=\"1.0\">",
                customerLines[0]);
        assertEquals("<adb:addressbook version=\"1.3\">", customerLines[1]);
        assertEquals("<adb:addressbookHeader>", customerLines[2]);
        assertEquals("<adb:identity>", customerLines[3]);
        assertEquals("<typ:address>", customerLines[4]);
        // customer data
        assertEquals("<typ:company>" + customer.getName() + "</typ:company>", customerLines[dataIndex]);
        // element trailer
        assertEquals("</typ:address>", customerLines[trailerIndex]);
        assertEquals("</adb:identity>", customerLines[trailerIndex + 1]);
        assertEquals("</adb:addressbookHeader>", customerLines[trailerIndex + 2]);
        assertEquals("</adb:addressbook>", customerLines[trailerIndex + 3]);
        assertEquals("</dat:dataPackItem>", customerLines[trailerIndex + 4]);
        assertEquals(customerLines.length, 11);
    }

    @Test
    public void testTrailerToString() {
        String trailer = writer.trailerToString();
        assertEquals("</dat:dataPack>", trailer);
    }

    @Test
    public void testGetBatchImportId() {
        long deadline = (new Date()).getTime() + 1000;
        assertTrue(writer.getBatchImportId().compareTo(String.format("%X", deadline)) < 0);
    }

    @Test
    public void testGetItemImportId() throws Exception {
        String batchId = writer.getBatchImportId();
        String firstItemId = writer.getItemImportId(null);
        writer.itemWritten(null);
        String secondItemId = writer.getItemImportId(null);
        assertEquals(batchId + "_00000000", firstItemId);
        assertEquals(batchId + "_00000001", secondItemId);
    }

}

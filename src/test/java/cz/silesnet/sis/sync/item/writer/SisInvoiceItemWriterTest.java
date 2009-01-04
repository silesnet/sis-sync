package cz.silesnet.sis.sync.item.writer;

import static cz.silesnet.sis.sync.item.writer.AbstractDataPackItemWriter.*;
import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.domain.Invoice;
import cz.silesnet.sis.sync.domain.Invoice.Item;

public class SisInvoiceItemWriterTest {
    private static Log log = LogFactory.getLog(SisInvoiceItemWriterTest.class);

    private SisInvoiceItemWriter writer;

    @Before
    public void setUp() throws Exception {
        writer = new SisInvoiceItemWriter();
    }

    @After
    public void tearDown() throws Exception {
        writer = null;
    }

    @Test
    public void testInvoiceLines() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setNumber("1234567890");
        invoice.setDate(new DateTime("2009-01-01"));
        invoice.setText("Invoice");
        invoice.new Item("Item text", 1.0F, 10);
        Item item = invoice.getItems().get(0);
        Customer customer = new Customer();
        customer.setId(1234);
        customer.setSymbol("1235");
        invoice.setCustomer(customer);
        int index = 0;
        String[] lines = writer.dataPackItemLines(invoice);
        assertEquals(elBeg("inv:invoice version=\"" + SisInvoiceItemWriter.INVOICE_ELEMENT_VERSION + "\""),
                lines[index++]);
        // Header
        assertEquals(elBeg("inv:invoiceHeader"), lines[index++]);
        assertEquals(elValue("inv:invoiceType", "issuedInvoice"), lines[index++]);
        assertEquals(elBeg("inv:number"), lines[index++]);
        assertEquals(elValue("typ:numberRequested", invoice.getNumber()), lines[index++]);
        assertEquals(elEnd("inv:number"), lines[index++]);
        assertEquals(elValue("inv:date", invoice.getDate().toString("yyyy-MM-dd")), lines[index++]);
        assertEquals(elValue("inv:text", invoice.getText()), lines[index++]);
        assertEquals(elBeg("inv:partnerIdentity"), lines[index++]);
        // NOTE: SPS customer Id == SIS customer Symbol
        assertEquals(elValue("typ:id", invoice.getCustomer().getSymbol()), lines[index++]);
        assertEquals(elEnd("inv:partnerIdentity"), lines[index++]);
        assertEquals(elEnd("inv:invoiceHeader"), lines[index++]);
        // Detail
        assertEquals(elBeg("inv:invoiceDetail"), lines[index++]);
        assertEquals(elBeg("inv:invoiceItem"), lines[index++]);
        assertEquals(elValue("inv:text", item.getText()), lines[index++]);
        assertEquals(elValue("inv:quantity", "1"), lines[index++]);
        assertEquals(elValue("inv:unit", SisInvoiceItemWriter.ITEM_UNIT), lines[index++]);
        assertEquals(elValue("inv:coefficient", "1.0"), lines[index++]);
        assertEquals(elBeg("inv:homeCurrency"), lines[index++]);
        assertEquals(elValue("typ:unitPrice", item.getNet()), lines[index++]);
        assertEquals(elValue("typ:price", item.getNet()), lines[index++]);
        assertEquals(elValue("typ:priceVAT", item.getVat()), lines[index++]);
        assertEquals(elValue("typ:priceSum", item.getBrt()), lines[index++]);
        assertEquals(elEnd("inv:homeCurrency"), lines[index++]);
        assertEquals(elEnd("inv:invoiceItem"), lines[index++]);
        assertEquals(elEnd("inv:invoiceDetail"), lines[index++]);
        // Summary
        assertEquals(elBeg("inv:invoiceSummary"), lines[index++]);
        assertEquals(elValue("inv:roundingDocument", "math2one"), lines[index++]);
        assertEquals(elValue("inv:roundingVAT", "none"), lines[index++]);
        assertEquals(elBeg("inv:homeCurrency"), lines[index++]);
        assertEquals(elValue("typ:priceHigh", invoice.getNet()), lines[index++]);
        assertEquals(elValue("typ:priceHighVAT", invoice.getVat()), lines[index++]);
        assertEquals(elValue("typ:priceHighSum", invoice.getBrt()), lines[index++]);
        assertEquals(elBeg("typ:round"), lines[index++]);
        assertEquals(elValue("typ:priceRound", Float.valueOf(invoice.getRounding()).toString()), lines[index++]);
        assertEquals(elEnd("typ:round"), lines[index++]);
        assertEquals(elEnd("inv:homeCurrency"), lines[index++]);
        assertEquals(elEnd("inv:invoiceSummary"), lines[index++]);
        assertEquals(elEnd("inv:invoice"), lines[index++]);
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
        assertEquals("xmlns:inv=\"http://www.stormware.cz/schema/invoice.xsd\"", lines[0]);
        assertEquals(1, lines.length);
    }

}

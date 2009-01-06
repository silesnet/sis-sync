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
    private Invoice invoice;
    @Before
    public void setUp() throws Exception {
        writer = new SisInvoiceItemWriter();
        invoice = new Invoice();
    }

    @After
    public void tearDown() throws Exception {
        writer = null;
        invoice = null;
    }

    @Test
    public void testInvoiceLines() throws Exception {
        invoice.setNumber("1234567890");
        invoice.setDate(new DateTime("2009-01-01"));
        invoice.setDueDate(new DateTime("2009-01-15"));
        invoice.setPeriodFrom(new DateTime("2009-01-01"));
        invoice.setPeriodTo(new DateTime("2009-01-31"));
        invoice.new Item("WIRELESSdirect", 1.0F, 10, true);
        invoice.new Item("Aktivace", 1.0F, 20, false);
        Item item1 = invoice.getItems().get(0);
        Item item2 = invoice.getItems().get(1);
        Customer customer = new Customer();
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
        assertEquals(elValue("inv:symVar", invoice.getNumber()), lines[index++]);
        assertEquals(elValue("inv:symPar", invoice.getCustomer().getSymbol()), lines[index++]);
        assertEquals(elValue("inv:date", invoice.getDate().toString("yyyy-MM-dd")), lines[index++]);
        assertEquals(elValue("inv:dateDue", invoice.getDueDate().toString("yyyy-MM-dd")), lines[index++]);
        assertEquals(elBeg("inv:classificationVAT"), lines[index++]);
        assertEquals(elValue("typ:classificationVATType", SisInvoiceItemWriter.CLASSIFICATION_VAT_TYPE), lines[index++]);
        assertEquals(elEnd("inv:classificationVAT"), lines[index++]);
        assertEquals(elValue("inv:text", SisInvoiceItemWriter.getInvoiceText(invoice)), lines[index++]);
        assertEquals(elBeg("inv:partnerIdentity"), lines[index++]);
        // NOTE: SPS customer Id == SIS customer Symbol
        assertEquals(elValue("typ:id", invoice.getCustomer().getSymbol()), lines[index++]);
        assertEquals(elEnd("inv:partnerIdentity"), lines[index++]);
        assertEquals(elValue("inv:symConst", SisInvoiceItemWriter.SYM_CONST), lines[index++]);
        assertEquals(elValue("inv:note", SisInvoiceItemWriter.DEFAULT_NOTE), lines[index++]);
        assertEquals(elValue("inv:intNote", SisInvoiceItemWriter.DEFAULT_INTERNAL_NOTE), lines[index++]);
        assertEquals(elEnd("inv:invoiceHeader"), lines[index++]);
        // Detail
        assertEquals(elBeg("inv:invoiceDetail"), lines[index++]);
        // item #1
        assertEquals(elBeg("inv:invoiceItem"), lines[index++]);
        assertEquals(elValue("inv:text", SisInvoiceItemWriter.SERVICE_ITEM_TEXT_PREFIX + item1.getText()),
                lines[index++]);
        assertEquals(elValue("inv:quantity", item1.getAmount()), lines[index++]);
        assertEquals(elValue("inv:unit", SisInvoiceItemWriter.ITEM_UNIT), lines[index++]);
        assertEquals(elValue("inv:rateVAT", SisInvoiceItemWriter.RATE_VAT), lines[index++]);
        assertEquals(elBeg("inv:homeCurrency"), lines[index++]);
        assertEquals(elValue("typ:unitPrice", item1.getPrice()), lines[index++]);
        assertEquals(elValue("typ:price", item1.getNet()), lines[index++]);
        assertEquals(elValue("typ:priceVAT", item1.getVat()), lines[index++]);
        assertEquals(elValue("typ:priceSum", item1.getBrt()), lines[index++]);
        assertEquals(elEnd("inv:homeCurrency"), lines[index++]);
        assertEquals(elBeg("inv:accounting"), lines[index++]);
        assertEquals(elValue("typ:ids", SisInvoiceItemWriter.getItemAccounting(item1)), lines[index++]);
        assertEquals(elEnd("inv:accounting"), lines[index++]);
        assertEquals(elEnd("inv:invoiceItem"), lines[index++]);
        // item #2
        assertEquals(elBeg("inv:invoiceItem"), lines[index++]);
        assertEquals(elValue("inv:text", item2.getText()), lines[index++]);
        assertEquals(elValue("inv:quantity", item2.getAmount()), lines[index++]);
        assertEquals(elValue("inv:rateVAT", SisInvoiceItemWriter.RATE_VAT), lines[index++]);
        assertEquals(elBeg("inv:homeCurrency"), lines[index++]);
        assertEquals(elValue("typ:unitPrice", item2.getPrice()), lines[index++]);
        assertEquals(elValue("typ:price", item2.getNet()), lines[index++]);
        assertEquals(elValue("typ:priceVAT", item2.getVat()), lines[index++]);
        assertEquals(elValue("typ:priceSum", item2.getBrt()), lines[index++]);
        assertEquals(elEnd("inv:homeCurrency"), lines[index++]);
        assertEquals(elBeg("inv:accounting"), lines[index++]);
        assertEquals(elValue("typ:ids", SisInvoiceItemWriter.getItemAccounting(item2)), lines[index++]);
        assertEquals(elEnd("inv:accounting"), lines[index++]);
        assertEquals(elEnd("inv:invoiceItem"), lines[index++]);
        assertEquals(elEnd("inv:invoiceDetail"), lines[index++]);
        // Summary
        assertEquals(elBeg("inv:invoiceSummary"), lines[index++]);
        assertEquals(elValue("inv:roundingDocument", SisInvoiceItemWriter.ROUNDING_DOCUMENT), lines[index++]);
        assertEquals(elValue("inv:roundingVAT", SisInvoiceItemWriter.ROUNDING_VAT), lines[index++]);
        assertEquals(elBeg("inv:homeCurrency"), lines[index++]);
        assertEquals(elValue("typ:priceHigh", invoice.getNet()), lines[index++]);
        assertEquals(elValue("typ:priceHighVAT", invoice.getVat()), lines[index++]);
        assertEquals(elValue("typ:priceHighSum", invoice.getBrt()), lines[index++]);
        assertEquals(elBeg("typ:round"), lines[index++]);
        assertEquals(elValue("typ:priceRound", invoice.getRounding()), lines[index++]);
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

package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        invoice.setCustomerId(1234);
        invoice.new Item("Item text", 10.00F);
        Item item = invoice.getItems().get(0);
        int index = 0;
        String[] lines = writer.dataPackItemLines(invoice);
        assertEquals("<inv:invoice version=\"" + SisInvoiceItemWriter.ELEMENT_INVOICE_VERSION + "\">", lines[index++]);
        // Header
        assertEquals("<inv:invoiceHeader>", lines[index++]);
        assertEquals("<inv:invoiceType>issuedInvoice</inv:invoiceType>", lines[index++]);
        assertEquals("<inv:number>", lines[index++]);
        assertEquals("<typ:numberRequested>" + invoice.getNumber() + "</typ:numberRequested>", lines[index++]);
        assertEquals("</inv:number>", lines[index++]);
        assertEquals("<inv:partnerIdentity>", lines[index++]);
        assertEquals("<typ:id>" + invoice.getCustomerId() + "</typ:id>", lines[index++]);
        assertEquals("</inv:partnerIdentity>", lines[index++]);
        assertEquals("</inv:invoiceHeader>", lines[index++]);
        // Detail
        assertEquals("<inv:invoiceDetail>", lines[index++]);
        assertEquals("<inv:invoiceItem>", lines[index++]);
        assertEquals("<inv:text>" + item.getName() + "</inv:text>", lines[index++]);
        assertEquals("<inv:quantity>" + "1" + "</inv:quantity>", lines[index++]);
        assertEquals("<inv:unit>" + "m&#236;s." + "</inv:unit>", lines[index++]);
        assertEquals("<inv:coefficient>" + "1.0" + "</inv:coefficient>", lines[index++]);
        assertEquals("<inv:homeCurrency>", lines[index++]);
        assertEquals("<typ:unitPrice>" + item.getNet() + "</typ:unitPrice>", lines[index++]);
        assertEquals("<typ:price>" + item.getNet() + "</typ:price>", lines[index++]);
        assertEquals("<typ:priceVAT>" + item.getVat() + "</typ:priceVAT>", lines[index++]);
        assertEquals("<typ:priceSum>" + item.getBrt() + "</typ:priceSum>", lines[index++]);
        assertEquals("</inv:homeCurrency>", lines[index++]);
        assertEquals("</inv:invoiceItem>", lines[index++]);
        assertEquals("</inv:invoiceDetail>", lines[index++]);
        // Summary
        assertEquals("<inv:invoiceSummary>", lines[index++]);
        assertEquals("<inv:roundingDocument>math2one</inv:roundingDocument>", lines[index++]);
        assertEquals("<inv:roundingVAT>none</inv:roundingVAT>", lines[index++]);
        assertEquals("<inv:homeCurrency>", lines[index++]);
        assertEquals("<typ:priceHigh>" + invoice.getNet() + "</typ:priceHigh>", lines[index++]);
        assertEquals("<typ:priceHighVAT>" + invoice.getVat() + "</typ:priceHighVAT>", lines[index++]);
        assertEquals("<typ:priceHighSum>" + invoice.getBrt() + "</typ:priceHighSum>", lines[index++]);
        assertEquals("<typ:round>", lines[index++]);
        assertEquals("<typ:priceRound>" + invoice.getRounding() + "</typ:priceRound>", lines[index++]);
        assertEquals("</typ:round>", lines[index++]);
        assertEquals("</inv:homeCurrency>", lines[index++]);
        assertEquals("</inv:invoiceSummary>", lines[index++]);
        assertEquals("</inv:invoice>", lines[index++]);
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

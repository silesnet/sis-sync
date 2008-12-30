package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Invoice;

public class SisInvoiceItemWriterTest {
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
        String[] invoiceLines = writer.dataPackItemLines(invoice);
        assertEquals("<inv:invoice version=\"" + SisInvoiceItemWriter.ELEMENT_INVOICE_VERSION + "\">", invoiceLines[0]);
        assertEquals("<inv:invoiceHeader>", invoiceLines[1]);
        assertEquals("<inv:invoiceType>issuedInvoice</inv:invoiceType>", invoiceLines[2]);
        assertEquals("<inv:number>", invoiceLines[3]);
        assertEquals("<typ:numberRequested>" + invoice.getNumber() + "</typ:numberRequested>", invoiceLines[4]);
        assertEquals("</inv:number>", invoiceLines[5]);
        assertEquals("<inv:partnerIdentity>", invoiceLines[6]);
        assertEquals("<typ:id>" + invoice.getCustomerId() + "</typ:id>", invoiceLines[7]);
        assertEquals("</inv:partnerIdentity>", invoiceLines[8]);
        assertEquals("<inv:myIdentity>", invoiceLines[6]);
        assertEquals("<typ:address>", invoiceLines[6]);
        assertEquals("<typ:company>" + SisInvoiceItemWriter.MY_COMPANY_NAME + "</typ:company>", invoiceLines[6]);
        assertEquals("</typ:address>", invoiceLines[6]);
        assertEquals("</inv:myIdentity>", invoiceLines[6]);
        assertEquals("</inv:invoiceHeader>", invoiceLines[6]);
        assertEquals("<inv:invoiceSummary>", invoiceLines[6]);
        assertEquals("<inv:roundingDocument>math2one</inv:roundingDocument>", invoiceLines[6]);
        assertEquals("<inv:roundingVAT>none</inv:roundingVAT>", invoiceLines[6]);
        assertEquals("<inv:homeCurrency>", invoiceLines[6]);
        assertEquals("</inv:homeCurrency>", invoiceLines[6]);
        assertEquals("</inv:invoiceSummary>", invoiceLines[6]);
        assertEquals("</inv:invoice>", invoiceLines[0]);
        // assertEquals("", invoiceLines[6]);
        // assertEquals("", invoiceLines[6]);
        fail("Not yet implemented");
    }
    @Test
    public void testNameSpaceLines() throws Exception {
        String[] lines = writer.nameSpaceLines();
        assertEquals("xmlns:inv=\"http://www.stormware.cz/schema/invoice.xsd\"", lines[0]);
        assertEquals(1, lines.length);
    }

}

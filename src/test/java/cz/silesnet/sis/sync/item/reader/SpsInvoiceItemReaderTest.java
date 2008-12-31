package cz.silesnet.sis.sync.item.reader;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.silesnet.sis.sync.domain.Invoice;

public class SpsInvoiceItemReaderTest {

    private static final int INVOICE_ID = 1234;

    @Test
    public final void testMapLines() {
        SpsInvoiceItemReader reader = new SpsInvoiceItemReader();
        Invoice invoice = (Invoice) reader.mapLines(INVOICE_ID, null);
        assertEquals(INVOICE_ID, invoice.getId());
    }

}

package cz.silesnet.sis.sync.item.reader;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Test;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;

public class SpsInvoiceBankAccountItemReaderTest {

    private final static long INVOICE_ID = 1234;

    @Test
    public void testMapLines() {
        SpsInvoiceBankAccountItemReader reader = new SpsInvoiceBankAccountItemReader();
        // mock InvoiceDao
        InvoiceDao dao = createMock(InvoiceDao.class);
        Invoice daoInvoice = new Invoice();
        daoInvoice.setSpsId(1L);
        expect(dao.find(INVOICE_ID)).andReturn(daoInvoice).once();
        replay(dao);
        reader.setDao(dao);
        Invoice invoice = (Invoice) reader.mapLines(INVOICE_ID, null);
        assertSame(daoInvoice, invoice);
        // test is SPS id gets populated correctly (overwritten to 0 because we
        // pass null as lines)
        assertEquals(0L, invoice.getSpsId());
        verify(dao);
    }
}

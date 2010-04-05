package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;
import cz.stormware.schema.documentresponse.ProducedDetailsType;
import cz.stormware.schema.invoice.InvoiceResponseType;
import cz.stormware.schema.response.ResponsePackItemType;

public class SpsInvoiceBankAccountItemReaderTest {

  private final static String RESPONSE_ID = "TIME_SEQ_01";
  private final static String SPS_INVOICE_ID = "02";

  @Test
  public void testMapLines() throws Exception {

    InvoiceDao dao = mock(InvoiceDao.class);
    Invoice daoInvoice = new Invoice();
    when(dao.find(1L)).thenReturn(daoInvoice);

    SpsInvoiceBankAccountItemReader reader = new SpsInvoiceBankAccountItemReaderSpy();
    reader.setDao(dao);

    Invoice invoice = (Invoice) reader.read();

    assertThat(invoice, is(sameInstance(daoInvoice)));
    assertThat(invoice.getSpsId(), is(2L));
  }

  private class SpsInvoiceBankAccountItemReaderSpy extends SpsInvoiceBankAccountItemReader {

    @Override
    protected Object readResponseItemInternal() throws Exception {

      ProducedDetailsType details = new ProducedDetailsType();
      details.setId(SPS_INVOICE_ID);

      InvoiceResponseType invoiceResponse = new InvoiceResponseType();
      invoiceResponse.setProducedDetails(details);

      ResponsePackItemType responseItem = new ResponsePackItemType();
      responseItem.setId(RESPONSE_ID);
      responseItem.setInvoiceResponse(invoiceResponse);

      return responseItem;
    }

  }
}

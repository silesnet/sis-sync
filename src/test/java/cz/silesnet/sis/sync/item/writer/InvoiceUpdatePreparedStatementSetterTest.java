package cz.silesnet.sis.sync.item.writer;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import cz.stormware.schema.invoice.InvoiceResponseType;
import cz.stormware.schema.type.StavType2;
import org.junit.Test;

import cz.stormware.schema.response.ResponsePackItemType;

public class InvoiceUpdatePreparedStatementSetterTest {

  @Test
  public void testId() throws Exception {
    ResponsePackItemType responseItem = new ResponsePackItemType();
    responseItem.setId("TIME_SEQ_01");
    responseItem.setState(StavType2.OK);
    final InvoiceResponseType invoiceResponse = new InvoiceResponseType();
    invoiceResponse.setState(StavType2.OK);
    responseItem.setInvoiceResponse(invoiceResponse);

    PreparedStatement statement = mock(PreparedStatement.class);

    InvoiceUpdatePreparedStatementSetter statementSetter;
    statementSetter = new InvoiceUpdatePreparedStatementSetter();
    Timestamp now = new Timestamp(new Date().getTime());
    statementSetter.setValues(responseItem, statement);

    verify(statement).setLong(2, 1);
    verify(statement).setTimestamp(eq(1), (Timestamp) geq(now));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResponseItemError() throws Exception {
    ResponsePackItemType responseItem = new ResponsePackItemType();
    responseItem.setState(StavType2.ERROR);
    responseItem.setId("TIME_SEQ_01");
    InvoiceUpdatePreparedStatementSetter statementSetter;
    statementSetter = new InvoiceUpdatePreparedStatementSetter();
    statementSetter.setValues(responseItem, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvoiceResponseError() throws Exception {
    ResponsePackItemType responseItem = new ResponsePackItemType();
    responseItem.setState(StavType2.OK);
    responseItem.setId("TIME_SEQ_01");
    final InvoiceResponseType invoiceResponse = new InvoiceResponseType();
    invoiceResponse.setState(StavType2.ERROR);
    responseItem.setInvoiceResponse(invoiceResponse);
    InvoiceUpdatePreparedStatementSetter statementSetter;
    statementSetter = new InvoiceUpdatePreparedStatementSetter();
    statementSetter.setValues(responseItem, null);
  }
}

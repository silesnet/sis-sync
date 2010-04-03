package cz.silesnet.sis.sync.item.writer;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import cz.stormware.schema.response.ResponsePackItemType;

public class InvoiceUpdatePreparedStatementSetterTest {

  @Test
  public void testId() throws Exception {
    ResponsePackItemType responseItem = new ResponsePackItemType();
    responseItem.setId("TIME_SEQ_01");
    PreparedStatement statement = mock(PreparedStatement.class);

    InvoiceUpdatePreparedStatementSetter statementSetter;
    statementSetter = new InvoiceUpdatePreparedStatementSetter();
    Timestamp now = new Timestamp(new Date().getTime());
    statementSetter.setValues(responseItem, statement);

    verify(statement).setLong(2, 1);
    verify(statement).setTimestamp(eq(1), (Timestamp) geq(now));
  }
}

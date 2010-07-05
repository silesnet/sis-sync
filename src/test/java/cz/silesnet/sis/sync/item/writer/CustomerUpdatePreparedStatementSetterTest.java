package cz.silesnet.sis.sync.item.writer;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import cz.stormware.schema.addressbook.AddressbookResponseType;
import cz.stormware.schema.documentresponse.ProducedDetailsType;
import cz.stormware.schema.response.ResponsePackItemType;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.CustomerResult;

public class CustomerUpdatePreparedStatementSetterTest {

  private static final long ID = 1L;
  private static final long SYMBOL = 123456;

  @Test
  public void testSetValues() throws SQLException {
    ResponsePackItemType responseItem = new ResponsePackItemType();
    responseItem.setId("2010-07-05T20:30:29.496_0000000000_1");

    AddressbookResponseType addressbookResponse = new AddressbookResponseType();
    ProducedDetailsType details = new ProducedDetailsType();
    details.setId("" + SYMBOL);
    addressbookResponse.setProducedDetails(details);
    responseItem.setAddressbookResponse(addressbookResponse);
    // mock prepared statement
    // SQL: UPDATE customers SET symbol = ?, synchronized = ? WHERE id = ?
    PreparedStatement ps = mock(PreparedStatement.class);
    CustomerUpdatePreparedStatementSetter psSetter = new CustomerUpdatePreparedStatementSetter();
    psSetter.setValues(responseItem, ps);
    verify(ps).setString(1, "" + SYMBOL);
    verify(ps)
        .setTimestamp(eq(2), (Timestamp) leq(new Timestamp((new java.util.Date()).getTime())));
    verify(ps).setLong(3, ID);
  }
}

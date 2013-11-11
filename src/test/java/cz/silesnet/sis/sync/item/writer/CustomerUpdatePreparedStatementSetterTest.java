package cz.silesnet.sis.sync.item.writer;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import cz.stormware.schema.addressbook.AddressbookResponseType;
import cz.stormware.schema.documentresponse.ProducedDetailsType;
import cz.stormware.schema.response.ResponsePackItemType;
import cz.stormware.schema.type.StavType2;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.CustomerResult;

public class CustomerUpdatePreparedStatementSetterTest {

  private static final long ID = 1L;
  private static final long SYMBOL = 123456;
  private static final String SIS_ITEM_ID = "2010-07-05T20:30:29.496_0000000000_1";

  @Test
  public void testSetValues() throws SQLException {
    ResponsePackItemType responseItem = new ResponsePackItemType();
    responseItem.setId(SIS_ITEM_ID);
    responseItem.setState(StavType2.OK);
    AddressbookResponseType addressbookResponse = new AddressbookResponseType();
    addressbookResponse.setState(StavType2.OK);
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

  @Test(expected = IllegalArgumentException.class)
  public void testResponseError() throws Exception {
    final ResponsePackItemType item = new ResponsePackItemType();
    item.setId(SIS_ITEM_ID);
    item.setState(StavType2.ERROR);
    final CustomerUpdatePreparedStatementSetter setter = new CustomerUpdatePreparedStatementSetter();
    setter.setValues(item, null);
  }

  @Test(expected = CustomerSpsImportException.class)
  public void testAddressBookError() throws Exception {
    final ResponsePackItemType item = new ResponsePackItemType();
    item.setId(SIS_ITEM_ID);
    item.setState(StavType2.OK);
    AddressbookResponseType addressbookResponse = new AddressbookResponseType();
    addressbookResponse.setState(StavType2.ERROR);
    item.setAddressbookResponse(addressbookResponse);
    final CustomerUpdatePreparedStatementSetter setter = new CustomerUpdatePreparedStatementSetter();
    setter.setValues(item, null);
  }
}

package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import cz.silesnet.sis.sync.dao.CustomerDao;
import cz.silesnet.sis.sync.domain.Customer;
import cz.stormware.schema.response.ResponsePackItemType;

public class CustomerItemReaderTest {

  private final static String RESPONSE_ID = "TIME_SEQ_01";

  @Test
  public void testMapLines() {
    CustomerItemReader reader = new CustomerItemReader();
    CustomerDao dao = mock(CustomerDao.class);
    Customer daoCustomer = new Customer();
    when(dao.find(1)).thenReturn(daoCustomer);

    ResponsePackItemType item = new ResponsePackItemType();
    item.setId(RESPONSE_ID);

    Customer customer = reader.doReadWithDao(item, dao);
    assertThat(daoCustomer, is(sameInstance(customer)));
  }

}

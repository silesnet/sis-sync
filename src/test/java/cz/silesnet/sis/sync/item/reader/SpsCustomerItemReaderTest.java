package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import cz.silesnet.sis.sync.dao.CustomerDao;
import cz.silesnet.sis.sync.domain.Customer;

public class SpsCustomerItemReaderTest {

  private final static long CUSTOMER_ID = 1234;

  @Test
  public void testMapLines() {
    SpsCustomerItemReader reader = new SpsCustomerItemReader();
    CustomerDao dao = mock(CustomerDao.class);
    Customer daoCustomer = new Customer();
    when(dao.find(CUSTOMER_ID)).thenReturn(daoCustomer);
    reader.setDao(dao);
    Customer customer = (Customer) reader.mapLines(CUSTOMER_ID, null);
    assertThat(daoCustomer, is(sameInstance(customer)));
  }

}

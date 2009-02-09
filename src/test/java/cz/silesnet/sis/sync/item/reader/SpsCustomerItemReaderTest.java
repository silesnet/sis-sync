package cz.silesnet.sis.sync.item.reader;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Test;

import cz.silesnet.sis.sync.dao.CustomerDao;
import cz.silesnet.sis.sync.domain.Customer;

public class SpsCustomerItemReaderTest {

    private final static long CUSTOMER_ID = 1234;

    @Test
    public void testMapLines() {
        SpsCustomerItemReader reader = new SpsCustomerItemReader();
        // mock CustomerDao
        CustomerDao dao = createMock(CustomerDao.class);
        Customer daoCustomer = new Customer();
        expect(dao.find(CUSTOMER_ID)).andReturn(daoCustomer).once();
        replay(dao);
        reader.setDao(dao);
        Customer customer = (Customer) reader.mapLines(CUSTOMER_ID, null);
        assertSame(daoCustomer, customer);
        verify(dao);
    }
}

package cz.silesnet.sis.sync.item.writer;

import static org.easymock.EasyMock.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;

import cz.silesnet.sis.sync.domain.Customer;

public class CustomerUpdatePreparedStatementSetterTest {

    private static final long ID = 1L;
    private static final String SYMBOL = "123456";

    @Test
    public void testSetValues() throws SQLException {
        Customer customer = new Customer();
        customer.setSymbol(SYMBOL);
        customer.setId(ID);
        // mock prepared statement
        PreparedStatement ps = createStrictMock(PreparedStatement.class);
        ps.setString(1, SYMBOL);
        ps.setTimestamp(eq(2), (Timestamp) geq(new Timestamp((new java.util.Date()).getTime())));
        ps.setLong(3, 1L);
        replay(ps);
        CustomerUpdatePreparedStatementSetter psSetter = new CustomerUpdatePreparedStatementSetter();
        psSetter.setValues(customer, ps);
        verify(ps);
    }
}

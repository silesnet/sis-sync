package cz.silesnet.sis.sync.item.writer;

import static org.easymock.EasyMock.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import cz.silesnet.sis.sync.domain.Customer;

public class CustomerSpsInvoicesUpdatePreparedStatementSetterTest {

    private static final String BANK_CODE = "0100";
    private static final String ACCOUNT_NUMBER = "1234-567890";
    private static final long ID = 1234L;

    @Test
    public void testSetValues() throws SQLException {
        Customer customer = new Customer();
        customer.setSymbol("" + ID);
        customer.setAccountNo(ACCOUNT_NUMBER);
        customer.setBankCode(BANK_CODE);
        // mock PreparedStatement
        /*
         * SQL: UPDATE FA SET Ucet = ?, KodBanky = ? WHERE RefAD = ? AND DatLikv
         * IS NULL
         */
        PreparedStatement ps = createStrictMock(PreparedStatement.class);
        ps.setString(1, ACCOUNT_NUMBER);
        ps.setString(2, BANK_CODE);
        ps.setInt(3, (int) ID);
        replay(ps);
        CustomerSpsInvoicesUpdatePreparedStatementSetter psSetter = new CustomerSpsInvoicesUpdatePreparedStatementSetter();
        psSetter.setValues(customer, ps);
        verify(ps);
    }

}

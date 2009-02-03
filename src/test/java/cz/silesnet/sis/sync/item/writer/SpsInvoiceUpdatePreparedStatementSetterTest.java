package cz.silesnet.sis.sync.item.writer;

import static org.easymock.EasyMock.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.domain.Invoice;

public class SpsInvoiceUpdatePreparedStatementSetterTest {

    private static final String BANK_CODE = "0100";
    private static final String ACCOUNT_NUMBER = "1234-567890";
    private static final long ID = 1234L;

    @Test
    public void testSetValues() throws SQLException {
        // invoice to update
        Invoice invoice = new Invoice();
        invoice.setSpsId(ID);
        Customer customer = new Customer();
        customer.setAccountNo(ACCOUNT_NUMBER);
        customer.setBankCode(BANK_CODE);
        invoice.setCustomer(customer);
        // mock PreparedStatement
        // SQL: UPDATE invoices SET synchronized = ? WHERE id = ?
        PreparedStatement ps = createStrictMock(PreparedStatement.class);
        ps.setString(1, ACCOUNT_NUMBER);
        ps.setString(2, BANK_CODE);
        ps.setInt(3, (int) ID);
        replay(ps);
        SpsInvoiceUpdatePreparedStatementSetter psSetter = new SpsInvoiceUpdatePreparedStatementSetter();
        psSetter.setValues(invoice, ps);
        verify(ps);
    }

}

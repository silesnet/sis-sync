package cz.silesnet.sis.sync.item.writer;

import static org.easymock.EasyMock.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;

import cz.silesnet.sis.sync.domain.InvoiceResult;

public class InvoiceUpdatePreparedStatementSetterTest {

    private static final long ID = 1234L;

    @Test
    public void testSetValues() throws SQLException {
        // invoice to update
        InvoiceResult result = new InvoiceResult();
        result.setSisId(ID);
        // mock PreparedStatement
        // SQL: UPDATE invoices SET synchronized = ? WHERE id = ?
        PreparedStatement ps = createStrictMock(PreparedStatement.class);
        ps.setTimestamp(eq(1), (Timestamp) geq(new Timestamp((new java.util.Date()).getTime())));
        ps.setLong(2, ID);
        replay(ps);
        InvoiceUpdatePreparedStatementSetter psSetter = new InvoiceUpdatePreparedStatementSetter();
        psSetter.setValues(result, ps);
        verify(ps);
    }
}

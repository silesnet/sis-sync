package cz.silesnet.sis.sync.item.writer;

import static org.easymock.EasyMock.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;

import cz.silesnet.sis.sync.domain.CustomerResult;

public class CustomerUpdatePreparedStatementSetterTest {

    private static final long ID = 1L;
    private static final long SYMBOL = 123456;

    @Test
    public void testSetValues() throws SQLException {
        CustomerResult result = new CustomerResult();
        result.setSpsId(SYMBOL);
        result.setSisId(ID);
        // mock prepared statement
        // SQL: UPDATE customers SET symbol = ?, synchronized = ? WHERE id = ?
        PreparedStatement ps = createStrictMock(PreparedStatement.class);
        ps.setString(1, "" + SYMBOL);
        ps.setTimestamp(eq(2), (Timestamp) geq(new Timestamp((new java.util.Date()).getTime())));
        ps.setLong(3, ID);
        replay(ps);
        CustomerUpdatePreparedStatementSetter psSetter = new CustomerUpdatePreparedStatementSetter();
        psSetter.setValues(result, ps);
        verify(ps);
    }
}

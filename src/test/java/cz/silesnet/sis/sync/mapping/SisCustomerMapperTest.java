package cz.silesnet.sis.sync.mapping;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.domain.Customer;

public class SisCustomerMapperTest {

    private static final String CUSTOMER_NAME = "Test Customer";
    private static final int IGNORED_ROW_NUM = 0;
    private static final String CITY = "Test City";
    private static final String SYMBOL = "12345678";
    private static final long ID = 1234L;
    private RowMapper mapper;
    private ResultSet rs;

    @Before
    public void setUp() throws Exception {
        mapper = new SisCustomerMapper();
        rs = createMock(ResultSet.class);
    }

    @After
    public void tearDown() throws Exception {
        rs = null;
        mapper = null;
    }

    @Test
    public void testMapRow() throws SQLException {
        expect(rs.getLong(SisCustomerMapper.ID_COLUMN)).andReturn(ID);
        expect(rs.getString(SisCustomerMapper.NAME_COLUMN)).andReturn(CUSTOMER_NAME);
        expect(rs.getString(SisCustomerMapper.SYMBOL_COLUMN)).andReturn(SYMBOL);
        expect(rs.getString(SisCustomerMapper.CITY_COLUMN)).andReturn(CITY);
        replay(rs);
        Customer customer = (Customer) mapper.mapRow(rs, IGNORED_ROW_NUM);
        assertEquals(ID, customer.getId());
        assertEquals(CUSTOMER_NAME, customer.getName());
        assertEquals(SYMBOL, customer.getSymbol());
        assertEquals(CITY, customer.getCity());
        verify(rs);
    }
}

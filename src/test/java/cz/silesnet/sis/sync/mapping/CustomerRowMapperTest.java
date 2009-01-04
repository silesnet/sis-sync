package cz.silesnet.sis.sync.mapping;

import static cz.silesnet.sis.sync.mapping.CustomerRowMapper.*;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.domain.Customer;

public class CustomerRowMapperTest {

    private static final int IGNORED_ROW_NUM = 0;
    private static final long ID = 1234L;
    private static final String SYMBOL = "12345678";
    private static final String NAME = "Test Customer";
    private static final String SUPPLEMENTARY_NAME = "Supplementary Name";
    private static final String CONTACT_NAME = "Contact Name";
    private static final String CITY = "Test City";
    private static final String STREET = "Street 777";
    private static final String ZIP = "12345";
    private static final String ICO = "12345678";
    private static final String DIC = "CZ12345678";
    private static final String PHONE = "+420123456789";
    private static final String EMAIL = "contact@customer.cz";
    private static final String CONTRACT = "2008/0007";

    private RowMapper mapper;
    private ResultSet rs;

    @Before
    public void setUp() throws Exception {
        mapper = new CustomerRowMapper();
        rs = createMock(ResultSet.class);
    }

    @After
    public void tearDown() throws Exception {
        rs = null;
        mapper = null;
    }

    @Test
    public void testMapRow() throws SQLException {
        expect(rs.getLong(ID_COLUMN)).andReturn(ID);
        expect(rs.getString(SYMBOL_COLUMN)).andReturn(SYMBOL);
        expect(rs.getString(NAME_COLUMN)).andReturn(NAME);
        expect(rs.getString(SUPPLEMENTARY_NAME_COLUMN)).andReturn(SUPPLEMENTARY_NAME);
        expect(rs.getString(CONTACT_NAME_COLUMN)).andReturn(CONTACT_NAME);
        expect(rs.getString(CITY_COLUMN)).andReturn(CITY);
        expect(rs.getString(STREET_COLUMN)).andReturn(STREET);
        expect(rs.getString(ZIP_COLUMN)).andReturn(ZIP);
        expect(rs.getString(ICO_COLUMN)).andReturn(ICO);
        expect(rs.getString(DIC_COLUMN)).andReturn(DIC);
        expect(rs.getString(PHONE_COLUMN)).andReturn(PHONE);
        expect(rs.getString(EMAIL_COLUMN)).andReturn(EMAIL);
        expect(rs.getString(CONTRACT_COLUMN)).andReturn(CONTRACT);
        replay(rs);
        Customer customer = (Customer) mapper.mapRow(rs, IGNORED_ROW_NUM);
        assertEquals(ID, customer.getId());
        assertEquals(SYMBOL, customer.getSymbol());
        assertEquals(NAME, customer.getName());
        assertEquals(SUPPLEMENTARY_NAME, customer.getSupplementaryName());
        assertEquals(CONTACT_NAME, customer.getContactName());
        assertEquals(CITY, customer.getCity());
        assertEquals(STREET, customer.getStreet());
        assertEquals(ZIP, customer.getZip());
        assertEquals(ICO, customer.getIco());
        assertEquals(DIC, customer.getDic());
        assertEquals(PHONE, customer.getPhone());
        assertEquals(EMAIL, customer.getEmail());
        assertEquals(CONTRACT, customer.getContract());
        verify(rs);
    }
}

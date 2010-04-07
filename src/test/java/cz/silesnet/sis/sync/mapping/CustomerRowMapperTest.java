package cz.silesnet.sis.sync.mapping;

import static cz.silesnet.sis.sync.mapping.CustomerRowMapper.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
  private static final String ACCOUNT_NO = "1234-567890";
  private static final String BANK_CODE = "2400";

  private RowMapper mapper;
  private ResultSet rs;

  @Before
  public void setUp() throws Exception {
    mapper = new CustomerRowMapper();
    rs = mock(ResultSet.class);
  }

  @After
  public void tearDown() throws Exception {
    rs = null;
    mapper = null;
  }

  @Test
  public void testMapRow() throws SQLException {
    when(rs.getLong(ID_COLUMN)).thenReturn(ID);
    when(rs.getString(SYMBOL_COLUMN)).thenReturn(SYMBOL);
    when(rs.getString(NAME_COLUMN)).thenReturn(NAME);
    when(rs.getString(SUPPLEMENTARY_NAME_COLUMN)).thenReturn(SUPPLEMENTARY_NAME);
    when(rs.getString(CONTACT_NAME_COLUMN)).thenReturn(CONTACT_NAME);
    when(rs.getString(CITY_COLUMN)).thenReturn(CITY);
    when(rs.getString(STREET_COLUMN)).thenReturn(STREET);
    when(rs.getString(ZIP_COLUMN)).thenReturn(ZIP);
    when(rs.getString(ICO_COLUMN)).thenReturn(ICO);
    when(rs.getString(DIC_COLUMN)).thenReturn(DIC);
    when(rs.getString(PHONE_COLUMN)).thenReturn(PHONE);
    when(rs.getString(EMAIL_COLUMN)).thenReturn(EMAIL);
    when(rs.getString(CONTRACT_COLUMN)).thenReturn(CONTRACT);
    when(rs.getString(ACCOUNT_NO_COLUMN)).thenReturn(ACCOUNT_NO);
    when(rs.getString(BANK_CODE_COLUMN)).thenReturn(BANK_CODE);

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
    assertEquals(ACCOUNT_NO, customer.getAccountNo());
    assertEquals(BANK_CODE, customer.getBankCode());
  }
}

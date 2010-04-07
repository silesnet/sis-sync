package cz.silesnet.sis.sync.mapping;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;

public class InvoiceRowMapperTest {

  private static final int IGNORED_ROW_NUM = 0;
  private static final long ID = 1L;

  @Test
  public void testMapRow() throws SQLException {
    InvoiceRowMapper mapper = new InvoiceRowMapper();
    // mock InvoiceDao
    InvoiceDao dao = mock(InvoiceDao.class);
    Invoice invoice = new Invoice();
    invoice.setId(ID);
    when(dao.find(ID)).thenReturn(invoice);
    mapper.setDao(dao);
    // mock ResultSet
    ResultSet rs = mock(ResultSet.class);
    when(rs.getLong(InvoiceRowMapper.ID_COLUMN)).thenReturn(ID);
    // call the mapper
    Invoice mappedInvoice = (Invoice) mapper.mapRow(rs, IGNORED_ROW_NUM);
    assertThat(invoice, is(sameInstance(mappedInvoice)));
  }

}

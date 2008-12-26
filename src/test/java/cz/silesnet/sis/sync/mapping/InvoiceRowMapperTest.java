package cz.silesnet.sis.sync.mapping;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

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
        InvoiceDao dao = createMock(InvoiceDao.class);
        Invoice invoice = new Invoice();
        invoice.setId(ID);
        expect(dao.find(ID)).andReturn(invoice);
        replay(dao);
        mapper.setDao(dao);
        // mock ResultSet
        ResultSet rs = createMock(ResultSet.class);
        expect(rs.getLong(InvoiceRowMapper.ID_COLUMN)).andReturn(ID);
        replay(rs);
        // call the mapper
        Invoice mappedInvoice = (Invoice) mapper.mapRow(rs, IGNORED_ROW_NUM);
        assertSame(invoice, mappedInvoice);
        verify(rs);
        verify(dao);
    }

}

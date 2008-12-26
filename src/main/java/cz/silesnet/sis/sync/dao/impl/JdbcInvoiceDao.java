/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;

/**
 * Implements InvoiceDao using Spring's JdbcTemplate.
 * 
 * @author sikorric
 * 
 */
public class JdbcInvoiceDao implements InvoiceDao {

    private static final String INVOICE_SQL = "SELECT * FROM invoices WHERE id = ?";
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Invoice find(long id) {
        Invoice invoice = null;
        try {
            invoice = (Invoice) template.queryForObject(INVOICE_SQL, new Object[] { id }, new InvoiceRowMapper());
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(e);
        }
        return invoice;
    }

    /**
     * Maps result set to Invoice object. Uses JdbcTemplate to extract invoice items.
     * 
     * @author sikorric
     * 
     */
    private class InvoiceRowMapper implements RowMapper {

        private static final String ITEMS_SQL = "SELECT * FROM items WHERE invoice_id = ?";
        private static final String ID_COLUMN = "id";
        private static final String NUMBER_COLUMN = "number";
        private static final String NAME_COLUMN = "name";
        private static final String NET_COLUMN = "net";

        /**
         * Maps result set to Invoice object. Retrieves invoice items and associates them with the invoice.
         */
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Invoice invoice = new Invoice();
            invoice.setId(rs.getLong(ID_COLUMN));
            invoice.setNumber(rs.getString(NUMBER_COLUMN));
            // retrieve invoice items from database by invoice_id
            template.query(ITEMS_SQL, new Object[] { rs.getLong(ID_COLUMN) }, new RowMapper() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // result set contains row from invoice items table
                    invoice.new Item(rs.getString(NAME_COLUMN), rs.getFloat(NET_COLUMN));
                    // just creating new Item automatically associates it with the invoice
                    // result is not read, null can be returned
                    return null;
                }
            });
            return invoice;
        }
    }

}

/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.domain.Invoice;

/**
 * Sets Invoice parameters to SQL PreparedStatement.
 * 
 * @author Richard Sikora
 */
public class InvoiceUpdatePreparedStatementSetter implements ItemPreparedStatementSetter {

    /**
     * Maps Invoice members to SQL update command.
     * <p>
     * UPDATE invoices SET synchronized = ? WHERE id = ?
     * 
     * @param item
     *            Invoice
     * @param ps
     *            SQL update command wrapped in PreparedStatement
     */
    public void setValues(Object item, PreparedStatement ps) throws SQLException {
        Invoice invoice = (Invoice) item;
        ps.setTimestamp(1, new Timestamp((new Date()).getTime()));
        ps.setLong(2, invoice.getId());
    }

}

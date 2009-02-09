/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.domain.CustomerResult;

/**
 * Sets parameters for Customer update SQL.
 * 
 * @author sikorric
 * 
 */
public class CustomerUpdatePreparedStatementSetter implements ItemPreparedStatementSetter {
    /**
     * Maps Customer members to SQL update command.
     * <p>
     * UPDATE customers SET symbol = ?, synchronized = ? WHERE id = ?
     * 
     * @param item
     *            Customer object
     * @param ps
     *            SQL wrapped in PreparedStatement
     */
    public void setValues(Object item, PreparedStatement ps) throws SQLException {
        CustomerResult customer = (CustomerResult) item;
        ps.setString(1, "" + customer.getSpsId());
        ps.setTimestamp(2, new Timestamp((new Date()).getTime()));
        ps.setLong(3, customer.getSisId());
    }
}

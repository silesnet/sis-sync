/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * @author sikorric
 * 
 */
public class CustomerUpdatePreparedStatementSetter implements ItemPreparedStatementSetter {

    public void setValues(Object item, PreparedStatement ps) throws SQLException {
        Customer customer = (Customer) item;
        ps.setString(1, customer.getSymbol());
        ps.setTimestamp(2, new Timestamp((new Date()).getTime()));
        ps.setLong(3, customer.getId());
    }
}

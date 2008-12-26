/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Maps JDBC SIS customer result set to Customer entity.
 * 
 * @author sikorric
 * 
 */
public class SisCustomerMapper implements RowMapper {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String SYMBOL_COLUMN = "symbol";
    public static final String CITY_COLUMN = "city";

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong(ID_COLUMN));
        customer.setName(rs.getString(NAME_COLUMN));
        customer.setSymbol(rs.getString(SYMBOL_COLUMN));
        customer.setCity(rs.getString(CITY_COLUMN));
        return customer;
    }

}

/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.dao.InvoiceDao;

/**
 * @author sikorric
 * 
 */
public class InvoiceRowMapper implements RowMapper {

    public static final String ID_COLUMN = "id";
    private InvoiceDao dao;

    public void setDao(InvoiceDao dao) {
        this.dao = dao;
    }

    /**
     * RowMapper that expect only one column in ResultSet containing invoice id. Uses {@link #InvoiceDao.find()} to
     * retrieve the invoice from database.
     */
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return dao.find(rs.getLong(ID_COLUMN));
    }

}

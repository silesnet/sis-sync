/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.dao.ReminderDao;

/**
 * @author sikorric
 * 
 */
public class ReminderRowMapper implements RowMapper {

    public static final String ID_COLUMN = "RefAD";
    private ReminderDao dao;

    public void setDao(ReminderDao dao) {
        this.dao = dao;
    }

    /**
     * RowMapper that expect only one column in ResultSet containing SPS
     * customer id. Uses {@link #ReminderDao.find()} to retrieve the reminder
     * from SPS database.
     */
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return dao.find(rs.getLong(ID_COLUMN));
    }

}

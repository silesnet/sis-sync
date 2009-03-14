/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import cz.silesnet.sis.sync.dao.ReminderDao;
import cz.silesnet.sis.sync.domain.Reminder;

/**
 * JDBC implementation of the {@link ReminderDao}.
 * 
 * @author rsi
 * 
 */
public class JdbcReminderDao implements ReminderDao {

    private JdbcTemplate template;

    public JdbcReminderDao() {
    }

    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Reminder get(long customerId) {
        return null;
    }

}

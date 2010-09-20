/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.dao;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * DAO for retrieving reminders from SPS database.
 *
 * @author rsi
 */
public interface ReminderDao {

  Reminder find(long customerId);

}

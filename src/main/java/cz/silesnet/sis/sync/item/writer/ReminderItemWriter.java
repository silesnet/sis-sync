/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.item.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.AbstractItemWriter;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * Reminder ItemWriter implementation that sends reminders to the customer by
 * email.
 * 
 * @author rsi
 * 
 */
public class ReminderItemWriter extends AbstractItemWriter implements ItemWriter {

    private static Log log = LogFactory.getLog(ReminderItemWriter.class);

    public ReminderItemWriter() {
    }

    public void write(Object item) throws Exception {
        Reminder reminder = (Reminder) item;
        log.debug(reminder);
    }

}

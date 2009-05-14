/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.item.writer;

import javax.mail.MessagingException;

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
    private ReminderSender sender;
    private int delay = 0;

    public ReminderItemWriter() {
    }

    public void setSender(ReminderSender sender) {
        this.sender = sender;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void write(Object item) throws Exception {
        Reminder reminder = (Reminder) item;
        log.debug("Sendig reminder: " + reminder);
        try {
            sender.send(reminder);
        } catch (MessagingException e) {
            log.error("Can not send reminder! [" + reminder + "]: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("Can not send reminder! [" + reminder + "]: " + e.getMessage());
        }
        Thread.sleep(delay * 1000);
    }
}

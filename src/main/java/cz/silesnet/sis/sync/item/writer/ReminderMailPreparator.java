/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * Interface for a {@link Reminder} email format.
 * 
 * @author sikorric
 * 
 */
public interface ReminderMailPreparator {
    /**
     * Prepares email message before sending. This method modifies given {@link MimeMessage} using data from the
     * Reminder.
     * 
     * @param reminder
     *            reminder to be sent
     * @param message
     *            message that would be sent
     * @throws MessagingException
     */
    void prepare(Reminder reminder, MimeMessage message) throws MessagingException;
}

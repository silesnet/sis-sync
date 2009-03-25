/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import javax.mail.MessagingException;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * Interface for various {@link Reminder} senders.
 * 
 * @author sikorric
 * 
 */
public interface ReminderSender {

    /**
     * Sends reminder to the customer.
     * 
     * @param reminder
     *            delayed invoices reminder to be send
     * @throws MessagingException
     */
    void send(Reminder reminder) throws MessagingException;
}

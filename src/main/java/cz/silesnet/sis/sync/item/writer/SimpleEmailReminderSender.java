/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * Simple email implementation of {@link ReminderSender}. It uses {@link JavaMailSender} to send an email.
 * 
 * @author sikorric
 * 
 */
public class SimpleEmailReminderSender implements ReminderSender {
    private static Log log = LogFactory.getLog(SimpleEmailReminderSender.class);

    JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Reminder reminder) {
        log.debug("Sending reminder email to: " + reminder.getCustomer().getEmail());
    }

}

/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * {@link ReminderMailPreparator} that prepares plain email message. It utilizes {@link MimeMessageHelper}.
 * 
 * @author sikorric
 * 
 */
public class SimpleReminderMailPreparator implements ReminderMailPreparator {

    public void prepare(Reminder reminder, MimeMessage message) throws MessagingException {
        MimeMessageHelper email = new MimeMessageHelper(message);
        email.setTo(reminder.getCustomer().getEmail());
        email.setText(reminder.getCustomer().getName());
    }

}

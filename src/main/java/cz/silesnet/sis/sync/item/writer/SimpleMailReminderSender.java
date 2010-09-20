/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * Simple email implementation of {@link ReminderSender}. It uses {@link JavaMailSender} to send an email.
 *
 * @author sikorric
 */
public class SimpleMailReminderSender implements ReminderSender {
  private static Log log = LogFactory.getLog(SimpleMailReminderSender.class);

  private JavaMailSender mailSender;
  private ReminderMailPreparator preparator;

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setPreparator(ReminderMailPreparator preparator) {
    this.preparator = preparator;
  }

  public void send(Reminder reminder) throws MessagingException {
    log.debug("Sending reminder email to: " + reminder.getCustomer().getEmail());
    MimeMessage msg = mailSender.createMimeMessage();
    preparator.prepare(reminder, msg);
    mailSender.send(msg);
  }
}

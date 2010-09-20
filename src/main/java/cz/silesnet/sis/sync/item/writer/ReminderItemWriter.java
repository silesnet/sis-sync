/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.item.writer;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

import cz.silesnet.sis.sync.domain.Reminder;

/**
 * Reminder ItemWriter implementation that sends reminders to the customer by
 * email.
 *
 * @author rsi
 */
public class ReminderItemWriter implements ItemWriter<Reminder> {

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

  @Override
  public void write(List<? extends Reminder> items) throws Exception {
    for (Reminder reminder : items) {
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
}

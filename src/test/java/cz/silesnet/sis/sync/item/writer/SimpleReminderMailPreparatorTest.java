package cz.silesnet.sis.sync.item.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Reminder;

public class SimpleReminderMailPreparatorTest {

  private static Log log = LogFactory.getLog(SimpleReminderMailPreparatorTest.class);

  private SimpleReminderMailPreparator preparator;
  private Reminder reminder;

  @Before
  public void setUp() throws Exception {
    // configure preparator
    preparator = new SimpleReminderMailPreparator();
    preparator.setTemplate("/flt/html-notice.flt");
    preparator.setHtml(true);
    preparator.setFrom("from@address");
    preparator.setSubject("Test Reminder");
    preparator.afterPropertiesSet();
    // prepare sample reminder
    reminder = new Reminder(1, "Reminded Customer", "fido@dido", "Street 123");
    reminder.addInvoice(reminder.new Invoice(1, 1, "001", "001", new LocalDate("2009-02-15"), new BigDecimal(
        "120.00"), new BigDecimal("100.00")));
    reminder.addInvoice(reminder.new Invoice(2, 1, "002", "002", new LocalDate("2009-03-15"), new BigDecimal(
        "120.00"), new BigDecimal("50.00")));
  }

  @After
  public void tearDown() throws Exception {
    preparator = null;
  }

  @Test
  public void testPrepare() throws MessagingException, IOException {
    MimeMessage message = new MimeMessage((Session) null);
    preparator.prepare(reminder, message);
    ByteArrayOutputStream emailStream = new ByteArrayOutputStream();
    message.writeTo(emailStream);
    log.debug(emailStream.toString());
  }

  @Test
  public void testPrepareSmall() throws MessagingException, IOException {
      // prepare small reminder
      reminder = new Reminder(1, "Reminded Customer", "fido@dido", "Street 123");
      reminder.addInvoice(reminder.new Invoice(2, 1, "002", "002", new LocalDate("2009-03-15"), new BigDecimal(
              "120.00"), new BigDecimal("49.99")));
      MimeMessage message = new MimeMessage((Session) null);
      preparator.prepare(reminder, message);
      ByteArrayOutputStream emailStream = new ByteArrayOutputStream();
      message.writeTo(emailStream);
      log.debug(emailStream.toString());
  }
}

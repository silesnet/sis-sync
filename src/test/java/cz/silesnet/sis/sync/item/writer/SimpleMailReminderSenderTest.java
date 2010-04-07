package cz.silesnet.sis.sync.item.writer;

import static org.mockito.Mockito.*;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

import cz.silesnet.sis.sync.domain.Reminder;

public class SimpleMailReminderSenderTest {

  private static final String EMAIL_TO = "reminder@host";
  SimpleMailReminderSender sender;
  JavaMailSender javaSender;

  @Before
  public void setUp() throws Exception {
    sender = new SimpleMailReminderSender();
  }

  @After
  public void tearDown() throws Exception {
    sender = null;
  }

  @Test
  public final void testSend() throws MessagingException {
    // reminder to be sent
    Reminder reminder = new Reminder(1, null, EMAIL_TO, null);
    reminder.addInvoice(reminder.new Invoice(1, 1, null, null, null, null, null));

    javaSender = mock(JavaMailSender.class);
    MimeMessage msg = new MimeMessage((Session) null);
    when(javaSender.createMimeMessage()).thenReturn(msg);
    sender.setMailSender(javaSender);

    ReminderMailPreparator preparator = mock(ReminderMailPreparator.class);
    sender.setPreparator(preparator);

    sender.send(reminder);
    verify(javaSender).send(msg);
    verify(preparator).prepare(reminder, msg);
  }
}

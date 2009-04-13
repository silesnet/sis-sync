package cz.silesnet.sis.sync.item.writer;

import static org.easymock.EasyMock.*;

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
        Reminder reminder = new Reminder(1, null, EMAIL_TO, null, 10);
        reminder.addInvoice(reminder.new Invoice(1, 1, null, null, null, null, null));
        // java sender mock
        javaSender = createMock(JavaMailSender.class);
        MimeMessage msg = new MimeMessage((Session) null);
        expect(javaSender.createMimeMessage()).andReturn(msg);
        javaSender.send(msg);
        replay(javaSender);
        // email preparator mock
        ReminderMailPreparator preparator = createMock(ReminderMailPreparator.class);
        preparator.prepare(reminder, msg);
        replay(preparator);
        // test the sender
        sender.setMailSender(javaSender);
        sender.setPreparator(preparator);
        sender.send(reminder);
        verify(javaSender);
        verify(preparator);
    }
}

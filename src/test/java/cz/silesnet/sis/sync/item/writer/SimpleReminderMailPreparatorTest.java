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
import org.springframework.core.io.ClassPathResource;

import cz.silesnet.sis.sync.domain.Reminder;

public class SimpleReminderMailPreparatorTest {

    private static Log log = LogFactory.getLog(SimpleReminderMailPreparatorTest.class);

    private SimpleReminderMailPreparator preparator;
    private Reminder reminder;

    @Before
    public void setUp() throws Exception {
        // configure preparator
        preparator = new SimpleReminderMailPreparator();
        preparator.setTemplateResource(new ClassPathResource("/flt/simple-notice.flt"));
        preparator.setFrom("from@address");
        preparator.setSubject("Test Reminder");
        // prepare sample reminder
        reminder = new Reminder(1, "Reminded Customer", "fido@dido", 10);
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

}

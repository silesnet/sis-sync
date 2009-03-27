/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;

import cz.silesnet.sis.sync.domain.Reminder;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * {@link ReminderMailPreparator} that prepares plain email message. It utilizes {@link MimeMessageHelper}.
 * 
 * @author sikorric
 * 
 */
public class SimpleReminderMailPreparator implements ReminderMailPreparator {

    private static Log log = LogFactory.getLog(SimpleReminderMailPreparator.class);
    private static final String REMINDER_KEY = "reminder";

    private Configuration cfg;
    private Resource templateResource;
    private Template template;
    private String from;
    private String subject;

    public SimpleReminderMailPreparator() throws IOException {
        super();
        cfg = new Configuration();
        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }

    public void setTemplateResource(Resource templateResource) throws IOException {
        this.templateResource = templateResource;
        log.debug(this.templateResource.getFilename());
        cfg.setTemplateLoader(new FileTemplateLoader(this.templateResource.getFile().getParentFile()));
        template = cfg.getTemplate(this.templateResource.getFilename());
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void prepare(Reminder reminder, MimeMessage message) throws MessagingException {
        MimeMessageHelper email = new MimeMessageHelper(message);
        email.setFrom(from);
        email.setTo(reminder.getCustomer().getEmail());
        email.setSubject(subject);
        // render body text
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(REMINDER_KEY, reminder);
        StringWriter body = new StringWriter();
        try {
            template.process(model, body);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        email.setText(body.toString());
    }

}

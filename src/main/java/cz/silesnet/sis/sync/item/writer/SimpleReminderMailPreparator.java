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

import org.springframework.beans.factory.InitializingBean;
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
public class SimpleReminderMailPreparator implements ReminderMailPreparator, InitializingBean {

    private static final String REMINDER_KEY = "reminder";

    private Configuration cfg;
    private Resource textTemplateResource;
    private Resource htmlTemplateResource;
    private Template textTemplate;
    private Template htmlTemplate;
    private String from;
    private String subject;

    public SimpleReminderMailPreparator() throws IOException {
    }

    public void setTextTemplateResource(Resource textTemplateResource) {
        this.textTemplateResource = textTemplateResource;
    }

    public void setHtmlTemplateResource(Resource htmlTemplateResource) {
        this.htmlTemplateResource = htmlTemplateResource;
    }

    public void afterPropertiesSet() throws Exception {
        cfg = new Configuration();
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setTemplateLoader(new FileTemplateLoader(this.textTemplateResource.getFile().getParentFile()));
        textTemplate = cfg.getTemplate(this.textTemplateResource.getFilename());
        htmlTemplate = cfg.getTemplate(this.htmlTemplateResource.getFilename());
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void prepare(Reminder reminder, MimeMessage message) throws MessagingException {
        MimeMessageHelper email = new MimeMessageHelper(message, true);
        email.setFrom(from);
        email.setTo(reminder.getCustomer().getEmail());
        email.setSubject(subject);
        // render body text
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(REMINDER_KEY, reminder);
        StringWriter text = new StringWriter();
        StringWriter html = new StringWriter();
        try {
            textTemplate.process(model, text);
            htmlTemplate.process(model, html);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        email.setText(text.toString(), html.toString());
    }

}

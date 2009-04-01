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
 * {@link ReminderMailPreparator} that prepares plain email message based on Freemarker template. It utilizes
 * {@link MimeMessageHelper} for accessing message fields.
 * 
 * @author sikorric
 * 
 */
public class SimpleReminderMailPreparator implements ReminderMailPreparator, InitializingBean {

    private static final String REMINDER_KEY = "reminder";
    private static final String DEFAULT_TEMPLATE_ENCODING = "UTF-8";

    private Configuration cfg;
    private Resource templateResource;
    private String encodig = DEFAULT_TEMPLATE_ENCODING;
    private Template template;
    private boolean isHtml = false;
    private String from;
    private String subject;

    public SimpleReminderMailPreparator() throws IOException {
    }

    public void afterPropertiesSet() throws Exception {
        cfg = new Configuration();
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setTemplateLoader(new FileTemplateLoader(this.templateResource.getFile().getParentFile()));
        cfg.setDefaultEncoding(encodig);
        template = cfg.getTemplate(this.templateResource.getFilename());
    }

    public void setTemplate(Resource templateResource) {
        this.templateResource = templateResource;
    }

    public void setEncodig(String encodig) {
        this.encodig = encodig;
    }

    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
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
        StringWriter body = new StringWriter();
        try {
            template.process(model, body);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        email.setText(body.toString(), isHtml);
    }

}

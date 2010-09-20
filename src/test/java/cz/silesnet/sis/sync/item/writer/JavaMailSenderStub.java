/*
 * Copyright 2009 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @author sikorric
 */
public class JavaMailSenderStub implements JavaMailSender {

  /* (non-Javadoc)
  * @see org.springframework.mail.javamail.JavaMailSender#createMimeMessage()
  */

  public MimeMessage createMimeMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
  * @see org.springframework.mail.javamail.JavaMailSender#createMimeMessage(java.io.InputStream)
  */

  public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
  * @see org.springframework.mail.javamail.JavaMailSender#send(javax.mail.internet.MimeMessage)
  */

  public void send(MimeMessage mimeMessage) throws MailException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
  * @see org.springframework.mail.javamail.JavaMailSender#send(javax.mail.internet.MimeMessage[])
  */

  public void send(MimeMessage[] mimeMessages) throws MailException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
  * @see org.springframework.mail.javamail.JavaMailSender#send(org.springframework.mail.javamail.MimeMessagePreparator)
  */

  public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
  * @see org.springframework.mail.javamail.JavaMailSender#send(org.springframework.mail.javamail.MimeMessagePreparator[])
  */

  public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
  * @see org.springframework.mail.MailSender#send(org.springframework.mail.SimpleMailMessage)
  */

  public void send(SimpleMailMessage simpleMessage) throws MailException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
  * @see org.springframework.mail.MailSender#send(org.springframework.mail.SimpleMailMessage[])
  */

  public void send(SimpleMailMessage[] simpleMessages) throws MailException {
    // TODO Auto-generated method stub

  }

}

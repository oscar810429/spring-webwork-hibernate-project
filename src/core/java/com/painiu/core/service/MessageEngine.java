/**
 * @(#)MessageEngine.java Dec 03, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.painiu.Painiu;
import com.painiu.core.model.Message;
import com.painiu.core.model.SystemMessage;
import com.painiu.core.model.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <p>
 * <a href="MessageEngine.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageEngine.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class MessageEngine implements InitializingBean {
	//~ Static fields/initializers =============================================

	private static final Logger log = LoggerFactory.getLogger(MessageEngine.class);
	
	//~ Instance fields ========================================================

	private MailSender mailSender;
    private MessageManager messageManager;
	private Configuration templateConfiguration;
	
	private String mailFrom;
	private String mailFromAddress;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/**
	 * @param mailSender The mailSender to set.
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	/**
	 * @param messageManager The messageManager to set.
	 */
	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}
	
	/**
	 * @param templateConfiguration The templateConfiguration to set.
	 */
	public void setTemplateConfiguration(Configuration templateConfiguration) {
		this.templateConfiguration = templateConfiguration;
	}
	
	/**
	 * @param mailFrom The mailFrom to set.
	 */
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
		if (mailFrom.indexOf('<') != -1 && mailFrom.indexOf('>') != -1) {
			this.mailFromAddress = mailFrom.substring(mailFrom.indexOf('<') + 1, mailFrom.indexOf('>'));
		} else {
			this.mailFromAddress = mailFrom;
		}
	}

	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		this.templateConfiguration.setEncoding(Locale.CHINA, "UTF-8");
		this.templateConfiguration.setEncoding(Locale.CHINESE, "UTF-8");
	}
	
	/**
	 * Send a message based on a FreeMarker template.
	 * 
	 * @param from
	 * @param to
	 * @param model
	 * @param templateName
	 * @param locale
	 */
	public void sendMessage(User from, User to, Map model, String templateName, Locale locale) {
		if (log.isDebugEnabled()) {
			log.debug("user[" + (from == null? "system" : from.getUsername()) + "] sending message to user[" + to.getUsername() + "]...");
		}
		model.put("app_domain", Painiu.getAppConfig().getDomain());
		String[] texts = mergeTempateIntoString(model, templateName, locale);
		
		String subject = texts[0];
		String content = texts[1];
		
		Message msg = new Message();
		msg.setSender(from);
		msg.setRecipient(to);
		msg.setSubject(subject);
		msg.setContent(content);
		msg.setSentDate(new Date());
		msg.setState(Message.State.UNREAD);
		
		msg.setOwner(to);
		
		messageManager.saveMessage(msg);
		
		if (from != null) {
			Message copy = new Message();
			try {
				BeanUtils.copyProperties(copy, msg);
			} catch (Exception e) {}
			copy.setId(null);
			copy.setState(Message.State.READ);
			copy.setOwner(from);
			
			messageManager.saveMessage(copy);
		}
	}
	
	/**
	 * Send system message to the specified user
	 * 
	 * @param to
	 * @param systemMsg
	 */
	public void sendMessage(User to, SystemMessage systemMsg) {
		if (log.isDebugEnabled()) {
			log.debug("sending system message to user[" + to.getUsername() + "]...");
		}
		Message msg = new Message();
		msg.setSender(null);
		msg.setRecipient(to);
		msg.setSubject(systemMsg.getSubject());
		msg.setContent(systemMsg.getContent());
		msg.setSentDate(systemMsg.getCreatedDate());
		msg.setState(Message.State.UNREAD);
		msg.setOwner(to);
		
		messageManager.saveMessage(msg);
	}
	
	/**
     * Send a simple message based on a FreeMarker template.
     * @param msg
     * @param templateName
     * @param model
     */
    public void sendMail(User to, Map model, String templateName, Locale locale) {
    	if (log.isDebugEnabled()) {
            log.debug("sending e-mail to user [" + to.getEmail() + "]...");
        }
    	model.put("app_domain", Painiu.getAppConfig().getDomain());
    	model.put("partner_name", Painiu.getAppConfig().getDomain());
    	model.put("media_root", Painiu.getAppConfig().getMediaRoot());
    	String[] texts = mergeTempateIntoString(model, templateName, locale);
    	
		SimpleMailMessage msg = new SimpleMailMessage();
		if (Painiu.getAppConfig().getDomain() == "painiu.com") {
			msg.setFrom(mailFrom);
		} else {
			msg.setFrom(Painiu.getAppConfig().getDomain()+"<service@painiu.com>");
		}
		msg.setTo(to.getNickname() + "<" + to.getEmail() + ">");
		msg.setSubject(texts[0]);
		
        msg.setText(texts[1]);
        
        sendMail(msg);
    }

    public void sendMail(String to, User from, Map model, String templateName, Locale locale) {
    	if (log.isDebugEnabled()) {
            log.debug("sending e-mail to user[" + to + "]...");
        }
    	model.put("app_domain", Painiu.getAppConfig().getDomain());
    	model.put("partner_name", Painiu.getAppConfig().getDomain());
    	model.put("media_root", Painiu.getAppConfig().getMediaRoot());

    	SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from.getNickname() + "<" + mailFromAddress + ">");
		msg.setTo(to);
		String[] texts = mergeTempateIntoString(model, templateName, locale);
		msg.setSubject(texts[0]);
        msg.setText(texts[1]);
        
        sendMail(msg);
    }
    
    /**
     * Send a simple message with pre-populated values.
     * @param msg
     */
    public void sendMail(SimpleMailMessage msg) {
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            //log it and go on
            log.error(ex.getMessage());
        }
    }

    /**
     * Convenience method for sending messages with attachments.
     * 
     * @param emailAddresses
     * @param resource
     * @param bodyText
     * @param subject
     * @param attachmentName
     * @throws MessagingException
     * @author Ben Gill
     */
    public void sendMail(String[] emailAddresses,
                            ClassPathResource resource, String bodyText,
                            String subject, String attachmentName)
    						throws MessagingException {
        MimeMessage message =
            ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailAddresses);
        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) mailSender).send(message);
    }
    
    public void sendHTMLMail(String[] emailAddresses, Map model, String templateName, Locale locale)
    						throws MessagingException {
    	model.put("app_domain", Painiu.getAppConfig().getDomain());
    	model.put("partner_name", Painiu.getAppConfig().getDomain());
    	model.put("media_root", Painiu.getAppConfig().getMediaRoot());
    	MimeMessage message =
            ((JavaMailSenderImpl) mailSender).createMimeMessage();
    	String[] texts = mergeTempateIntoString(model, templateName, locale);
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setSentDate(new Date());
        helper.setTo(emailAddresses);
        helper.setSubject(texts[0]);
        helper.setText(texts[1], true);
        helper.setFrom(Painiu.getAppConfig().getDomain()+"<service@painiu.com>");
        ((JavaMailSenderImpl) mailSender).send(message);
    }
    
    private String[] mergeTempateIntoString(Map model, String templateName, Locale locale) {
    	String[] result = new String[2];

		try {
			Template template = templateConfiguration.getTemplate(templateName, locale);
			//text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			StringWriter buff = new StringWriter();
			template.process(model, buff);
			
			BufferedReader reader = new BufferedReader(new StringReader(buff.toString()));
			
			String subject = null;
			StringBuffer content = new StringBuffer();
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("%")) {
					subject = line.substring(1);
				} else if (!line.startsWith("#")) {
					content.append(line).append('\n');
				}
			}
			
			result[0] = subject;
			result[1] = content.toString();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (TemplateException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
    }

    public class StringDataSource implements javax.activation.DataSource{
    	private java.lang.String data;
    	private java.lang.String type;
    	
    	public StringDataSource(String data, String type){
    		this.data = data;
    		this.type = type;
    	}

		/*
		 * @see javax.activation.DataSource#getContentType()
		 */
		public String getContentType() {
			return type;
		}

		/*
		 * @see javax.activation.DataSource#getInputStream()
		 */
		public InputStream getInputStream() throws IOException {
			return new java.io.StringBufferInputStream(data);
		}

		/*
		 * @see javax.activation.DataSource#getName()
		 */
		public String getName() {
			return "html mail";
		}

		/*
		 * @see javax.activation.DataSource#getOutputStream()
		 */
		public OutputStream getOutputStream() throws IOException {
			throw new java.io.IOException("it does not support this method now!");
		}
    	
    }
	
}

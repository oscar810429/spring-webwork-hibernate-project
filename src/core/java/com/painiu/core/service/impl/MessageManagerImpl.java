/*
 * @(#)MessageManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.MessageDAO;
import com.painiu.core.service.MessageManager;
import com.painiu.core.model.Message;
import com.painiu.core.model.User;
import com.painiu.core.model.Comment.Situation;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="MessageManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class MessageManagerImpl implements MessageManager {
	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(MessageManagerImpl.class);
	
	//~ Instance fields ========================================================

	private MessageDAO messageDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @param messageDAO The messageDAO to set.
	 */
	@NonTransactional
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}
	
	/*
	 * @see com.painiu.core.logic.MessageManager#getMessage(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Message getMessage(String id) {
		return messageDAO.getMessage(id);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#saveMessage(com.painiu.core.model.Message)
	 */
	public void saveMessage(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + message);
		}
		
		messageDAO.saveMessage(message);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#removeMessage(com.painiu.core.model.Message)
	 */
	public void removeMessage(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + message);
		}
		
		messageDAO.removeMessage(message);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#getSentMessages(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSentMessages(User user, int start, int limit) {
		return messageDAO.getSentMessages(user, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#getReceivedMessages(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getReceivedMessages(User user, int start, int limit) {
		return messageDAO.getReceivedMessages(user, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#getUnreadMessagesCount(com.painiu.core.model.User)
	 */
	@Transactional(readOnly=true)
	public Integer getUnreadMessagesCount(User user) {
		return messageDAO.getUnreadMessagesCount(user);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#getReceivedMessages(com.painiu.core.model.User, java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getReceivedMessages(User user, String keyword, int start, int limit) {
		return messageDAO.getReceivedMessages(user, keyword, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.MessageManager#getSentMessages(com.painiu.core.model.User, java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSentMessages(User user, String keyword, int start, int limit) {
		return messageDAO.getSentMessages(user, keyword, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.MessageManager#getMessages(com.painiu.core.model.User, java.lang.String, com.painiu.core.model.Comment.Situation, java.util.Date, java.util.Date, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getMessages(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit) {
		return messageDAO.getMessages(user, key, situation, startDate, endDate, start, limit);
	}

}

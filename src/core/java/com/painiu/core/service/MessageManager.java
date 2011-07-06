/*
 * @(#)MessageManager.java  2006-2-22
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;

import com.painiu.core.dao.MessageDAO;
import com.painiu.core.model.Comment;
import com.painiu.core.model.Message;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="MessageManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageManager.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public interface MessageManager {
	
	public void setMessageDAO(MessageDAO messageDAO);
	
	public Message getMessage(String id);
	
	public void saveMessage(Message message);
	
	/**
	 * todo security check.
	 * 
	 * @param message
	 */
	public void removeMessage(Message message);
	
	public Result getSentMessages(User user, String keyword, int start, int limit);
	public Result getSentMessages(User user, int start, int limit);
	
	public Result getReceivedMessages(User user, String keyword, int start, int limit);
	public Result getReceivedMessages(User user, int start, int limit);
	
	public Integer getUnreadMessagesCount(User user);
	
	/*For manage coments in console.
	 * */
	public Result getMessages(User user, String key, Comment.Situation situation, Date startDate, Date endDate, int start, int limit);
	
}

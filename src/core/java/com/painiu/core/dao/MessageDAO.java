/*
 * @(#)MessageDAO.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;

import com.painiu.core.model.Message;
import com.painiu.core.model.Comment;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="MessageDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface MessageDAO extends DAO {
	
	public Message getMessage(String id);
	
	public void saveMessage(Message message);
	
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

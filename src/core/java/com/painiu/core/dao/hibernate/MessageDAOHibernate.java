/*
 * @(#)MessageDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.painiu.core.dao.MessageDAO;
import com.painiu.core.model.Comment;
import com.painiu.core.model.Message;
import com.painiu.core.model.User;
import com.painiu.core.model.Comment.Situation;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="MessageDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class MessageDAOHibernate extends BaseDAOHibernate implements MessageDAO {
	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(MessageDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.dao.MessageDAO#getMessage(java.lang.String)
	 */
	public Message getMessage(String id) {
		Message message = (Message) getHibernateTemplate().get(Message.class, id);
		
		if (message == null) {
			log.warn("uh oh, message '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(Message.class, id);	
		}
		return message;
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#saveMessage(com.painiu.core.model.Message)
	 */
	public void saveMessage(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + message);
		}
		
		getHibernateTemplate().saveOrUpdate(message);
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#removeMessage(com.painiu.core.model.Message)
	 */
	public void removeMessage(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + message);
		}
		
		getHibernateTemplate().delete(message);
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#getSentMessages(com.painiu.core.model.User, int, int)
	 */
	public Result getSentMessages(User user, int start, int limit) {
		return find("select count(*) from Message m where m.owner = ? and m.sender = ? and m.situation <> ? ",
				"from Message m inner join fetch m.recipient" +
				" where m.owner = ? and m.sender = ? and m.situation <> ? order by m.sentDate desc",
				new Object[] {user, user, Comment.Situation.deleted}, 
				new Type[] {Hibernate.entity(User.class), Hibernate.entity(User.class),UserTypes.commentSituationType()},
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#getReceivedMessages(com.painiu.core.model.User, int, int)
	 */
	public Result getReceivedMessages(User user, int start, int limit) {
		return find("select count(*) from Message m where m.owner = ? and m.recipient = ? and m.situation <> ? ",
				"from Message m left join fetch m.sender" +
				" where m.owner = ? and m.recipient = ? and m.situation <> ? order by m.sentDate desc",
				new Object[] {user, user, Comment.Situation.deleted},
				new Type[] {Hibernate.entity(User.class), Hibernate.entity(User.class), UserTypes.commentSituationType()},
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#getUnreadMessagesCount(com.painiu.core.model.User)
	 */
	public Integer getUnreadMessagesCount(final User user) {
		return (Integer) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query queryObject = session.createQuery(
						"select count(*) from Message msg " +
						"where msg.owner = :owner and msg.recipient = :recipient and msg.state = :state and msg.situation <> " + Comment.Situation.deleted );
				queryObject.setEntity("owner", user);
				queryObject.setEntity("recipient", user);
				queryObject.setParameter("state", Message.State.UNREAD, UserTypes.messageState());
				
				Object result = queryObject.uniqueResult();
				
				if (result == null) {
					result = new Integer(0);
				}
				return result;
			}
		});
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#getReceivedMessages(com.painiu.core.model.User, java.lang.String, int, int)
	 */
	public Result getReceivedMessages(User user, String keyword, int start, int limit) {
		return find("select count(*) from Message m where m.owner = ? and m.recipient = ? and (m.subject like ? or m.content like ? ) and m.situation <> ? ",
				"from Message m left join fetch m.sender" +
				" where m.owner = ? and m.recipient = ? and (m.subject like ? or m.content like ? ) and m.situation <> ? order by m.sentDate desc",
				new Object[] {user, user,  "%" +keyword +"%",  "%" +keyword +"%", Comment.Situation.deleted},
				new Type[] {Hibernate.entity(User.class), Hibernate.entity(User.class), Hibernate.STRING, Hibernate.STRING, UserTypes.commentSituationType()}, 
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.MessageDAO#getSentMessages(com.painiu.core.model.User, java.lang.String, int, int)
	 */
	public Result getSentMessages(User user, String keyword, int start, int limit) {
		return find("select count(*) from Message m where m.owner = ? and m.sender = ? and (m.subject like ? or m.content like ? ) and m.situation <> ? ",
				"from Message m left join fetch m.sender" +
				" where m.owner = ? and m.sender = ? and (m.subject like ? or m.content like ? ) and m.situation <> ? order by m.sentDate desc",
				new Object[] {user, user,  "%" +keyword +"%",  "%" +keyword +"%", Comment.Situation.deleted},
				new Type[] {Hibernate.entity(User.class), Hibernate.entity(User.class), Hibernate.STRING, Hibernate.STRING, UserTypes.commentSituationType()}, 
				start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.MessageDAO#getMessages(com.painiu.core.model.User, java.lang.String, com.painiu.core.model.Comment.Situation, java.util.Date, java.util.Date, int, int)
	 */
	public Result getMessages(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit) {
		if(user != null) {
			if(StringUtils.isNotEmpty(key)){
				key = "%" + key + "%";
				return find("select count(c.id) from Message c where c.sender = ? and c.situation = ? and c.sentDate > ? and c.sentDate < ? and c.content like ?",
						"from Message c where c.sender = ? and c.situation = ? and c.sentDate > ? and c.sentDate < ? and c.content like ? order by c.sentDate", 
						new Object[] { user , situation, startDate, endDate, key},
						new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, Hibernate.STRING },
						start, limit);
			}
			return find("select count(c.id) from Message c where c.sender = ? and c.situation = ? and c.sentDate > ? and c.sentDate < ? ",
					"from Message c where c.sender = ? and c.situation = ? and c.sentDate > ? and c.sentDate < ?  order by c.sentDate", 
					new Object[] { user , situation, startDate, endDate},
					new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
					start, limit);
			
		}
		if(StringUtils.isNotEmpty(key)){
			key = "%" + key + "%";
			return find("select count(c.id) from Message c where c.situation = ? and c.sentDate > ? and c.sentDate < ? and c.content like ?",
					"from Message c where c.situation = ? and c.sentDate > ? and c.sentDate < ? and c.content like ? order by c.sentDate", 
					new Object[] { situation, startDate, endDate, key},
					new Type[] { UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, Hibernate.STRING },
					start, limit);
		}
		return find("select count(c.id) from Message c where c.situation = ? and c.sentDate > ? and c.sentDate < ? ",
				"from Message c where c.situation = ? and c.sentDate > ? and c.sentDate < ?  order by c.sentDate", 
				new Object[] { situation, startDate, endDate},
				new Type[] { UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
	}
}

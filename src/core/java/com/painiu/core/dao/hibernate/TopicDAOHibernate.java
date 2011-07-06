/*
 * @(#)TopicDAOHibernate.java  2006-2-14
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.painiu.Painiu;
import com.painiu.core.dao.TopicDAO;
import com.painiu.core.model.Category;
import com.painiu.core.model.Comment;
import com.painiu.core.model.Forum;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Topic;
import com.painiu.core.model.User;
import com.painiu.core.model.Comment.Situation;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="TopicDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zola Zhou
 * @version $Id: TopicDAOHibernate.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class TopicDAOHibernate extends BaseDAOHibernate implements TopicDAO {
	// ~ Static fields/initializers
	// =============================================

	private static final Log log = LogFactory.getLog(TopicDAOHibernate.class);

	// ~ Instance fields
	// ========================================================

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================

	/*
	 * @see com.painiu.core.dao.TopicDAO#getThread(java.lang.String)
	 */
	public List getTopics(User user, int limit) {
			return find(
					"from Topic topic join fetch topic.group join fetch topic.author where topic.author = ? and topic.situation <> ? order by topic.modifiedDate desc",
					new Object[] { user, Comment.Situation.deleted },
					new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType() }, -1, limit)
					.getData();
	}

	public Result getTopics(Category cateogry, int start, int limit) {
		return find(
				"select count(t.id) from Topic t where t.group.viewPrivacy = ? and (t.group.category = ? or t.group.category.parentCategory = ?) and t.situation <> ?",
				"from Topic topic where topic.group.viewPrivacy = ? and (topic.group.category = ? or topic.group.category.parentCategory = ?) and topic.situation <> ? order by topic.modifiedDate desc",
				new Object[] {Privacy.EVERYONE, cateogry, cateogry , Comment.Situation.deleted},
				new Type[] { UserTypes.privacy(), Hibernate.entity(Category.class), Hibernate.entity(Category.class), UserTypes.commentSituationType() }, start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.TopicDAO#getTopics(int, int)
	 */
	public Result getTopics(int start, int limit) {
		return find(
				"select count(t.id) from Topic t where t.group.viewPrivacy = ? and t.situation <> ? ",
				"from Topic topic where topic.group.viewPrivacy = ? and topic.situation <> ? order by topic.modifiedDate desc",
				new Object[] {Privacy.EVERYONE, Comment.Situation.deleted},
				new Type[] {UserTypes.privacy(), UserTypes.commentSituationType()}, start, limit);
	}
	
	public Topic getTopic(String id) {
		Topic topic = (Topic) getHibernateTemplate().get(Topic.class, id);

		if (topic == null) {
			log.warn("uh, oh, Topic[" + id + "] not found...");
			throw new ObjectRetrievalFailureException(Topic.class, id);
		}

		return topic;
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#saveTopic(com.painiu.core.model.Topic)
	 */
	public void saveTopic(Topic topic) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + topic);
		}

		getHibernateTemplate().saveOrUpdate(topic);
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#removeTopic(com.painiu.core.model.Topic)
	 */
	public void removeTopic(Topic topic) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + topic);
		}

		getHibernateTemplate().delete(topic);
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getForumTopics(java.lang.String, int, int)
	 */
	public Result getForumTopics(Forum forum, int start, int limit) {
		return find("select count(t.id) from Topic t where t.forum = ? and t.situation <> ? ",
				"from Topic t join fetch t.author where t.forum = ? and t.situation <> ? "
						+ "order by t.position desc, t.modifiedDate desc",
						new Object[] {forum, Comment.Situation.deleted},
						new Type[] {Hibernate.entity(Forum.class), UserTypes.commentSituationType()}, start, limit);
	}
	
	public Result getUserForumTopics(Forum forum, User user, int start, int limit) {
		return find("select count(t.id) from Topic t where t.author = ? and t.forum = ? and t.situation <> ? ",
				"from Topic t join fetch t.author where t.author = ? and t.forum = ? and t.situation <> ? "
						+ "order by t.position desc, t.modifiedDate desc",
						new Object[] { user, forum, Comment.Situation.deleted}, 
						new Type[] {Hibernate.entity(User.class), Hibernate.entity(Forum.class), UserTypes.commentSituationType()}, 
						start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.TopicDAO#getForumTopics(com.painiu.core.model.Forum,
	 *      java.lang.String, int, int)
	 */
	public Result getForumTopics(Forum forum, String keyword, int start,
			int limit) {
		String key = "%" + keyword + "%";

		return find("select count(t.id) from Topic t where t.forum = ? and t.situation <> ? "
				+ "and (t.subject like ? or t.content like ?)",
				"from Topic t join fetch t.author where t.forum = ? and t.situation <> ? "
						+ "and (t.subject like ? or t.content like ?) "
						+ "order by t.modifiedDate desc", new Object[] { forum, Comment.Situation.deleted, 
						key, key, }, new Type[] {
						Hibernate.entity(Forum.class),UserTypes.commentSituationType(), Hibernate.STRING,
						Hibernate.STRING }, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getSystemForumTopics(com.painiu.core.model.Forum,
	 *      java.lang.String, int, int)
	 */
	public Result getSystemForumTopics(final Forum forum, final User user, final String keyword,
			int start, int limit) {
		return findByCriteria(new CriteriaBuilder() {
			public Criteria buildCountCriteria(Session session) {
				return buildCriteria(session).setProjection(
						Projections.rowCount());
			}

			public Criteria buildCriteria(Session session) {
				Criteria criteria = session.createCriteria(Topic.class);
				criteria.setFetchMode("author", FetchMode.JOIN);
				criteria.add(Restrictions.isNull("group"));
				if (forum != null) {
					criteria.add(Restrictions.eq("forum", forum));
				}
				if (user == null) {
					criteria.add(Restrictions.le("position", 1));
				} else {
					if (!(user.isInRole(Painiu.ADMIN_ROLE) || user.isInRole(Painiu.MANAGER_ROLE) || user.isInRole(Painiu.CS_ROLE)) ) {
						Disjunction disj = Restrictions.disjunction();
						disj.add(Restrictions.ge("position", 1));
						disj.add(Restrictions.eq("author", user));
						criteria.add(disj);
					}
				}
				if (keyword != null) {
					Disjunction disj = Restrictions.disjunction();
					disj.add(Restrictions.like("subject", keyword,
							MatchMode.ANYWHERE));
					disj.add(Restrictions.like("content", keyword,
							MatchMode.ANYWHERE));
					criteria.add(disj);
				}
				criteria.addOrder(Order.desc("modifiedDate"));
				return criteria;
			}
		}, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getGroupForumTopics(int, int)
	 */
	public List getGroupForumTopics(final int start, final int limit) {
		return (List) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List list = session
						.createSQLQuery(
								"select {topic.*}, {user.*} "
										+ "from yp_topic topic, yp_user user, yp_group g "
										+ "where topic.author = user.user_id "
										+ " and topic.situation <> " + Comment.Situation.deleted.value()
										+ " and topic.group_id = g.group_id "
										+ " and g.privacy_view = 63 "
										+ " order by topic.modified_date desc limit :offset, :limit")
						.addEntity("topic", Topic.class).addJoin("user",
								"topic.author").setInteger("offset", start)
						.setInteger("limit", limit).list();

				List topics = new ArrayList(list.size());

				for (Iterator i = list.iterator(); i.hasNext();) {
					Object[] arr = (Object[]) i.next();
					topics.add(arr[0]);
				}

				return topics;
			}
		});
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getUserGroupForumTopics(com.painiu.core.model.User,
	 *      java.util.Date, java.net.Date, int, int)
	 */
	public List getUserGroupForumTopics(final User user, final Date fromDate,
			final Date toDate, final int start, final int limit) {
		return (List) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List list = session
						.createSQLQuery(
								"select {topic.*}, {user.*} "
										+ "from yp_topic topic, yp_user user, yp_group_user gu "
										+ "where topic.author = user.user_id "
										+ " and topic.situation <> " + Comment.Situation.deleted.value()
										+ " and topic.group_id = gu.group_id "
										+ "and gu.status = 1 "
										+ "and gu.user_id = :user "
										+ "and topic.modified_date > :fromDate and topic.modified_date < :toDate "
										+ "order by topic.modified_date desc limit :offset, :limit")
						.addEntity("topic", Topic.class).addJoin("user",
								"topic.author").setEntity("user", user)
						.setTimestamp("fromDate", fromDate).setTimestamp(
								"toDate", toDate).setInteger("offset", start)
						.setInteger("limit", limit).list();

				List topics = new ArrayList(list.size());

				for (Iterator i = list.iterator(); i.hasNext();) {
					Object[] arr = (Object[]) i.next();
					topics.add(arr[0]);
				}

				return topics;
			}
		});
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getUserGroupForumTopics(com.painiu.core.model.User, int, int)
	 */
	public Result getUserGroupForumTopics(User user, int start, int limit) {
		return find(
				"select count(t.id) from Topic t, GroupUser gu where  gu.user = ? and t.group = gu.group and t.group != null and t.situation <> ? ",
				"select topic from Topic topic,GroupUser gu inner join fetch topic.group where gu.user = ? and topic.group = gu.group and topic.group != null and topic.situation <> ? order by topic.modifiedDate desc",
				new Object[] { user, Comment.Situation.deleted },
				new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType()}, start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.TopicDAO#getTopicsByUser(com.painiu.core.model.User, int, int)
	 */
	public Result getTopicsByUser(User user, int start, int limit) {

		return find(
				"select count(t.id) from Topic t where t.author = ? and t.group != null and t.situation <> ? ",
				"from Topic topic inner join fetch topic.group where topic.author = ? and topic.situation <> ? order by topic.modifiedDate desc",
				new Object[] { user, Comment.Situation.deleted },
				new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType()}, start, limit);

	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getTopicsRepliedByMe(com.painiu.core.model.User, int, int)
	 */
	public Result getTopicsRepliedByMe(User user, int start, int limit) {
		return find(
				"select count(distinct t.id) from Topic t, Post p where p.author = ? and p.topic = t and t.group != null and t.situation <> ? ",
				"select distinct topic from Topic topic,Post post inner join fetch topic.group where post.author = ? and post.topic = topic and topic.situation <> ? order by topic.modifiedDate desc",
				new Object[] { user, Comment.Situation.deleted },
				new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType()}, start, limit);
	}

	public Integer getForumTopicsSize(final Forum forum, final Date fromDate) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query queryObject = session.createQuery(
						"select count( t) from Topic t " +
						" where t.forum = :forum " +
						" and t.postedDate > :fromDate  " +
						" and t.situation <> " + Comment.Situation.deleted.value()
						); 
				queryObject.setEntity("forum", forum);
				queryObject.setDate("fromDate", fromDate);
				
				Object result = queryObject.uniqueResult();
				
				if (result == null) {
					result = new Integer(0);
				}
				return result;
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.TopicDAO#getPopularGroupTopics(java.lang.String, int, int)
	 */
	public Result getPopularGroupTopicsByAccessTimes(int start, int limit) {
		return find(
				"from Topic topic where topic.group != null and topic.situation <> ? order by topic.accessedTimes desc",
				new Object[] {Privacy.EVERYONE, Comment.Situation.deleted},
				new Type[] {UserTypes.privacy(), UserTypes.commentSituationType()},
					start, limit);
	}


	/* (non-Javadoc)
	 * @see com.painiu.core.dao.GroupDAO#getCommendatoryGroupTopic(com.painiu.core.model.Group, int, int)
	 */
	/*public Result getCommendatoryGroupTopic(Group group, int start, int limit) {
		return find("select count(topic.id) from Topic topic where topic.group = ? and topic.state = ? and topic.situation <> ? ",
				"from Topic topic where topic.group = ? and topic.state = ? and topic.situation <> ? " +
				"order by topic.modifiedDate desc",
				new Object[] { group,Topic.State.USER_COMMENDATORY, Comment.Situation.deleted },
				new Type[] { Hibernate.entity(Group.class), UserTypes.topicState(), UserTypes.commentSituationType()},
				start, limit);
	}*/

	/*
	 * @see com.painiu.core.dao.TopicDAO#getHotTopics(com.painiu.core.model.Forum, int)
	 */
	public List getHotTopics(Forum forum, int limit) {
		return find("select count(t.id) from Topic t where t.forum = ? and t.situation <> ?",
				"from Topic t where t.forum = ? and t.situation <> ?"
						+ "order by t.accessedTimes desc, t.replys desc",
						new Object[] {  forum, Comment.Situation.deleted }, 
						new Type[] { Hibernate.entity(Forum.class), UserTypes.commentSituationType()}, 
						-1, limit).getData();
	}

	/*
	 * @see com.painiu.core.dao.TopicDAO#getSystemForumTopics(com.painiu.core.model.Forum, com.painiu.core.model.User, int, int)
	 */
	public Result getSystemForumTopics(Forum forum, User user, int start, int limit) {
		if (user == null) {
			return find("select count(t.id) from Topic t where t.forum = ? and t.position > 0 and t.situation <> ?",
					"from Topic t join fetch t.author where t.forum = ? and t.position > 0 and t.situation <> ?"
							+ "order by t.position desc, t.modifiedDate desc",
						new Object[] {  forum, Comment.Situation.deleted }, 
						new Type[] { Hibernate.entity(Forum.class), UserTypes.commentSituationType()},  start, limit);
		} 
		if (user.isInRole(Painiu.ADMIN_ROLE) || user.isInRole(Painiu.MANAGER_ROLE) || user.isInRole(Painiu.CS_ROLE)) {
			return find("select count(t.id) from Topic t where t.forum = ? and t.situation <> ?",
					"from Topic t join fetch t.author where t.forum = ? and t.situation <> ?"
							+ "order by t.position desc, t.modifiedDate desc",
							new Object[] {  forum, Comment.Situation.deleted }, 
							new Type[] { Hibernate.entity(Forum.class), UserTypes.commentSituationType()}, start, limit);
		} 
		return find("select count(t.id) from Topic t where t.forum = ? and (t.position > 0 or t.author = ?) and t.situation <> ? ",
				"from Topic t join fetch t.author where t.forum = ?  and (t.position > 0 or t.author = ?) and t.situation <> ?"
						+ "order by t.position desc, t.modifiedDate desc",
				new Object[] {forum, user, Comment.Situation.deleted}, new Type[] {Hibernate.entity(Forum.class), Hibernate.entity(User.class), UserTypes.commentSituationType()}, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.TopicDAO#getTopics(com.painiu.core.model.User, java.lang.String, com.painiu.core.model.Comment.Situation, java.util.Date, java.util.Date, int, int)
	 */
	public Result getTopics(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit) {
		if(user != null) {
			if(StringUtils.isNotEmpty(key)){
				key = "%" + key + "%";
				return find("select count(c.id) from Topic c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ? and (c.content like ? or c.subject like ? )",
						"from Topic c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ? and (c.content like ? or c.subject like ? ) order by c.postedDate", 
						new Object[] { user , situation, startDate, endDate, key, key},
						new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, Hibernate.STRING, Hibernate.STRING },
						start, limit);
			}
			return find("select count(c.id) from Topic c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ? ",
					"from Topic c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ?  order by c.postedDate", 
					new Object[] { user , situation, startDate, endDate},
					new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
					start, limit);
			
		}
		if(StringUtils.isNotEmpty(key)){
			key = "%" + key + "%";
			return find("select count(c.id) from Topic c where c.situation = ? and c.postedDate > ? and c.postedDate < ? and (c.content like ? or c.subject like ? )",
					"from Topic c where c.situation = ? and c.postedDate > ? and c.postedDate < ? and (c.content like ? or c.subject like ? ) order by c.postedDate", 
					new Object[] { situation, startDate, endDate, key, key},
					new Type[] { UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, Hibernate.STRING, Hibernate.STRING },
					start, limit);
		}
		return find("select count(c.id) from Topic c where c.situation = ? and c.postedDate > ? and c.postedDate < ? ",
				"from Topic c where c.situation = ? and c.postedDate > ? and c.postedDate < ?  order by c.postedDate", 
				new Object[] { situation, startDate, endDate},
				new Type[] { UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
	}




}

/**
 * @(#)SoftwareDAOHibernate.java 2010-5-17
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import com.painiu.core.dao.SoftwareDAO;
import com.painiu.core.model.Category;
import com.painiu.core.model.Icon;
import com.painiu.core.model.Photo;
import com.painiu.core.model.PhotoAddress;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.Software;
import com.painiu.core.model.User;
import com.painiu.core.model.Software.State;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SoftwareDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareDAOHibernate.java 133 2010-11-23 08:14:43Z zhangsf $
 */

public class SoftwareDAOHibernate extends BaseDAOHibernate implements SoftwareDAO {

	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(SoftwareDAOHibernate.class);
	private static final long ONE_DAYS = 1000 * 60 * 60 * 24 * 1;

	//~ Instance fields ===================================================

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	
	/**
	 * @see com.painiu.core.dao.SoftwareDAO#getSoftware(java.lang.String)
	 */
	public Software getSoftware(String softwareId) {
		Software software = (Software)getHibernateTemplate().get(Software.class, softwareId);
		if(software==null){
			 log.warn("uh oh, Software '" + softwareId + "' not found...");
			 throw new ObjectRetrievalFailureException(Software.class, softwareId);
		}
			
		return software;
	}
	
	/**
	 * @see com.painiu.core.dao.SoftwareDAO#getSoftware(com.painiu.core.model.Software)
	 */
	
	public Software getSoftware(Software software) {
		return getSoftware(software.getId());
	}
	

	/**
	 * @see com.painiu.core.dao.SoftwareDAO#getSoftwares(com.painiu.core.model.Category, int, int)
	 */
	/*public Result getSoftwares(Category category, int start, int limit) {
		return null;
	}*/

	/**
	 * @see com.painiu.core.dao.SoftwareDAO#getSoftwares(com.painiu.core.model.Category, java.lang.String, int, int)
	 */
	/*public Result getSoftwares(Category category, String name, int start,int limit) {
		return null;
	}*/

	/**
	 * @see com.painiu.core.dao.SoftwareDAO#removeSoftware(com.painiu.core.model.Software)
	 */
	public void removeSoftware(Software software) {
	       getHibernateTemplate().delete(software);	
     }

	/**
	 * @see com.painiu.core.dao.SoftwareDAO#saveSoftware(com.painiu.core.model.Software)
	 */
	public void saveSoftware(Software software) {
		  getHibernateTemplate().saveOrUpdate(software);
     }
	
	
	/**
	 * @see com.painiu.core.dao.PhotoDAO#getRecentPublicPhotos(int, int)
	 */
	public Result getRecentPublicSoftwares(int start, int limit) {
		if (log.isDebugEnabled()) {
			log.debug("Retriving recent softwares: start = " + start + ", limit=" + limit);
		}
		
		Long timestamp = new Long(System.currentTimeMillis() - ONE_DAYS);
		
		return find("from Software s inner join fetch s.user where s.privacy = ?" +
				" and s.state = ? and s.timestamp > ? " +
				" order by s.timestamp desc", 
				new Object[] {
					Privacy.EVERYONE,
					Software.State.PUBLISHED,
					timestamp
				},
				new Type[] {
					UserTypes.privacy(),
					UserTypes.softwareState(),
					Hibernate.LONG
				},
				start, limit);
	}

    /**
     * @see com.painiu.core.dao.SoftwareDAO#getTaggedSoftwares(java.lang.String, int, int)
     */
	
	public Result getTaggedSoftwares(String tagName, int start, int limit) {
		return find(
				"select count(s.id) from SoftwareTag t join t.software as s inner join s.user u " +
				"where t.tagName = ? and s.privacy = ? and s.state = ? " ,
				"select s from SoftwareTag t join t.sofware as s inner join s.user u" +
				" where t.tagName = ? and s.privacy = ? and s.state = ? " +
				" order by t.taggedDate desc", 
				new Object[] { tagName, Privacy.EVERYONE, 
						Software.State.PUBLISHED },
				new Type[] { Hibernate.STRING, UserTypes.privacy(), 
						UserTypes.softwareState() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getUserPhotos(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserSoftwares(User user, boolean orderByTaken, int start, int limit, Relation relation) {

		String countSql = "select count(s.id) from Software s where s.user = ? " +
		" and bitwise_and(s.privacy, ?) > 0";

        String sql = "from Software s join fetch s.stat where s.user = ? " +
	    " and bitwise_and(s.privacy, ?) > 0 order by " + (orderByTaken?"s.takenDate":"s.timestamp") +
	    " desc";
		return find(countSql, sql, 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
			
		
	}

	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getUserTaggedSoftwares(com.painiu.core.model.User, java.lang.String, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserTaggedSoftwares(User user, String tagName, int start, int limit, Relation relation) {
		return find(
				"select count(s.id) from SoftwareTag t inner join t.software s " +
				"where s.user = ? and t.tagName = ? and bitwise_and(s.privacy, ?) > 0",
				"select s from SoftwareTag t inner join t.software s " +
				"where s.user = ? and t.tagName = ? and bitwise_and(s.privacy, ?) > 0 order by t.taggedDate desc", 
				new Object[] { user, tagName, relation },
				new Type[] { Hibernate.entity(User.class), Hibernate.STRING, UserTypes.relation() },
				start, limit);
	}
    
	/*
     * @see com.painiu.core.dao.SoftwareDAO#getCommentedSofwares(com.painiu.core.model.User, int, int)
     */
    public Result getCommentedSoftwares(User user, int start, int limit) {
        return find(
                "select count(distinct s.id) from SoftwareComment c inner join c.software s " +
                "where s.user != ? and c.author = ?",
                "select distinct s from SoftwareComment c inner join c.sofware s inner join fetch s.user " +
                "where s.user != ? and c.author = ? order by c.postedDate desc",
                new Object[] { user, user },
                new Type[] { Hibernate.entity(User.class), Hibernate.entity(User.class) },
                start, limit);
    }
    
    /*
	 * @see com.painiu.core.dao.SoftwareDAO#getRecentActiveSoftwares(com.painiu.core.model.User, java.util.Date, int, int)
	 */
	/*public Result getRecentActiveSoftwares(User user, Date afterDate, int start, int limit) {
        return find(
                "select count(distinct p.id) from SoftwareEvent e where e.owner = ? and e.createdDate > ?",
                "select distinct p from SoftwareEvent e inner join e.software p " +
                "where e.owner = ? and e.createdDate > ? order by e.createdDate desc",
                new Object[] { user, afterDate },
                new Type[] { Hibernate.entity(User.class), Hibernate.TIMESTAMP },
                start, limit);
	}*/

	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getMostComments(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostComments(User user, int start, int limit, Relation relation) {
		return find(
				"select count(s.id) from Software s join s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.comments > 0",
				"from Software s join fetch s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.comments > 0 " +
				"order by stat.comments desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getMostFavorites(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostFavorites(User user, int start, int limit, Relation relation) {
		return find(
				"select count(s.id) from Software s join s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.favorites > 0",
				"from Software s join fetch s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.favorites > 0 " +
				"order by stat.favorites desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getMostInteresting(java.util.Date, java.util.Date, int, int, int)
	 */
	public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit) {
		return find(
				"select count(s.id) from Software s join s.stat stat where s.timestamp >= ? and s.timestamp < ?" +
				"and bitwise_and(s.privacy, ?) > 0 and stat.interests >= ?",
				"from Software s join fetch s.stat stat where s.timestamp >= ? and s.timestamp < ?" +
				"and bitwise_and(s.privacy, ?) > 0 and stat.interests >= ? " +
				"order by stat.interests desc", 
				new Object[] { fromDate.getTime(), toDate.getTime(), Relation.NONE, new Integer(interests)},
				new Type[] { Hibernate.LONG, Hibernate.LONG, UserTypes.relation(), Hibernate.INTEGER },
				start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getMostInteresting(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostInteresting(User user, int start, int limit, Relation relation) {
		return find(
				"select count(s.id) from Software s join s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.interests > 0",
				"from Software s join fetch s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.interests > 0 " +
				"order by stat.interests desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getMostViews(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostViews(User user, int start, int limit, Relation relation) {
		return find(
				"select count(s.id) from Software s join s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.views > 0",
				"from Software s join fetch s.stat stat where s.user = ? " +
				"and bitwise_and(s.privacy, ?) > 0 and stat.views > 0 " +
				"order by stat.views desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}
	
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotosPostedAt(java.util.Date, java.util.Date, int, int)
	 */
	public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit) {
		return getSoftwaresPostedAt(from, to, start, limit, true, false);
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotosPostedAt(java.util.Date, java.util.Date, int, int, boolean)
	 */
	public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit, boolean descOrder, boolean update) {
		String countSql = "select count(s.id) from Software s where s.timestamp >= ? and s.timestamp < ?";

		String sql = "from Software s join fetch s.user where s.timestamp >= ? and s.timestamp < ? order by s.timestamp" 
					+ (descOrder ? " desc" : "");

		return find(countSql, sql,
				new Object[] {from.getTime(), to.getTime()},
				new Type[] {Hibernate.LONG, Hibernate.LONG},
				start, limit, !update);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotosPostedAt(java.util.Date, java.util.Date, int, int, java.util.List)
	 */
	public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit, List softwateStates) {
		String userFilter = "";
		List objects = new ArrayList(5);
		objects.add(from.getTime());
		objects.add(to.getTime());
		List types = new ArrayList(5);
		types.add(Hibernate.LONG);
		types.add(Hibernate.LONG);
		if (softwateStates != null && softwateStates.size() > 0) {
			Properties userRank = new Properties();
			userRank.put("enumClassname", Software.State.class);
			for (int i = 0; i < softwateStates.size(); i++) {
				if (StringUtils.isEmpty(userFilter)) {
					userFilter = " and (s.state = ? ";
				} else {
					userFilter += " or s.state = ? ";
				}
				objects.add(softwateStates.get(i));
				types.add(UserTypes.softwareState());
			}
			userFilter += ") ";
		}
		Object[] srcTypes = types.toArray();
		Type[] desTypes = new Type[srcTypes.length];
		for (int i = 0; i < srcTypes.length; i++) {
			desTypes[i] = (Type)srcTypes[i];
		}
		String countSql = "select count(s.id) from Software s " +
				"where s.timestamp >= ? and s.timestamp < ?" + userFilter;

		String sql = "from Software s join fetch s.user user " +
				"where s.timestamp >= ? and s.timestamp < ? " + userFilter +
				"order by s.timestamp" ;

		return find(countSql, sql, objects.toArray(), desTypes, start, limit, false);
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotos(com.painiu.core.model.User, java.util.Date, java.util.Date, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserSoftwaresPostedAt(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation) {
		String countSql = "select count(s.id) from Software s where s.user = ? and " +
						"s.postedDate >= ? and s.postedDate < ? and bitwise_and(s.privacy, ?) > 0"; 
		
		String sql = "from Software s" + (detail ? " join fetch s.stat " : " ") +
					"where s.user = ? and s.postedDate >= ? and " +
					"s.postedDate < ? and bitwise_and(s.privacy, ?) > 0 order by s.postedDate desc";
		
		return find(countSql, sql,
				new Object[] { user, from, to, relation },
				new Type[] { Hibernate.entity(User.class), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, UserTypes.relation() },
				start, limit);
	}
	
	
	/*
	 * @see com.painiu.core.dao.SoftwareDAO#getSoftwares(com.painiu.core.model.User, java.lang.String[], boolean, java.lang.String, int, int, com.painiu.core.model.Relation)
	 */
	public Result getSoftwares(final User user,final Category category, final String[] tags, final boolean taggedAll, final String text, final int start, final int limit, final Relation relation) {
		
		return (Result) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = buildCountSoftwareCriteria(session, user,category, tags, taggedAll, text, relation);
				
				int count = ((Integer) criteria.list().iterator().next()).intValue();
				
				criteria = buildSoftwareCriteria(session, user,category, tags, taggedAll, text, relation, false);
				
				List data = criteria.setFirstResult(start).setMaxResults(limit).list();
				
				Result result = new Result(start, limit);
				result.setTotal(count);
				result.setData(data);
				
				if (user != null) {
					for (Iterator i = data.iterator(); i.hasNext();) {
						Software software = (Software) i.next();
						software.setUser(user);
					}
				}
				
				return result;
			}
		});
	}
	

	static Criteria buildCountSoftwareCriteria(final Session session, User user,Category category, String[] tags, boolean taggedAll, String text, Relation relation) {
		Criteria criteria = buildSoftwareCriteria(session, user,category, tags, taggedAll, text, relation, true);
		if ( (tags != null && tags.length > 1) || text != null ) {
			return criteria.setProjection( Projections.countDistinct("id") );
		}
		return criteria.setProjection( Projections.count("id") );
	}
	

	static Criteria buildSoftwareCriteria(final Session session, User user,Category category, String[] tags, boolean taggedAll, String text, Relation relation, boolean count) {
		Criteria criteria = session.createCriteria(Software.class);
		
		if (user != null) {
			criteria.add( Restrictions.eq("user", user) );
			
			if (relation != null) {
				criteria.add( Restrictions.sqlRestriction(" {alias}.privacy & ? > 0", relation, UserTypes.relation()) );
			}
		} else {
			criteria.add( Restrictions.sqlRestriction(" {alias}.privacy & ? > 0", Relation.NONE, UserTypes.relation()) );
			
			criteria.setFetchMode("user", FetchMode.JOIN);
		}
		
		if (user == null){
			Disjunction disjState = Restrictions.disjunction();
			
			disjState.add( Restrictions.eq("state", Software.State.PUBLISHED) );
			
			criteria.add(disjState);
		}
		
		if (category != null) {
		   criteria.add( Restrictions.eq("category",category) );
		 }
		
		if ((tags != null && tags.length > 0) || text != null) {
			Criteria subCriteria = criteria.createCriteria("softwareTags", "tags");
            
			if (tags != null && tags.length > 0) {
				if (taggedAll) {
					Conjunction conj = Restrictions.conjunction();
					for (int i = 0; i < tags.length; i++) {
						conj.add( Restrictions.eq("tagName", tags[i]) );
					}
					subCriteria.add(conj);
				} else {
					Disjunction disj = Restrictions.disjunction();
					for (int i = 0; i < tags.length; i++) {
						disj.add( Restrictions.eq("tagName", tags[i]) );
					}
					subCriteria.add(disj);
				}
			}
			
			if (text != null) {
				Disjunction disj = Restrictions.disjunction();
				
				disj.add( Restrictions.like("title", text, MatchMode.ANYWHERE) );
				disj.add( Restrictions.like("description", text, MatchMode.ANYWHERE) );
				disj.add( Restrictions.eq("tags.tagName", text) );
				
				criteria.add(disj);
			}
		}

		if (!count) {
				criteria.addOrder(Order.desc("timestamp"));
		}
		
		// distinct ?
		if ( (tags != null && tags.length > 1) || text != null ) {
			ProjectionList proj = Projections.projectionList();
			
			proj.add(Projections.property("id"))
				.add(Projections.property("name"));
			
			if (user == null) {
				criteria.createAlias("user", "user");
				proj.add(Projections.property("user.id"))
					.add(Projections.property("user.username"))
					.add(Projections.property("user.nickname"))
					.add(Projections.property("user.buddyIcon.host"))
					.add(Projections.property("user.buddyIcon.dir"))
					.add(Projections.property("user.buddyIcon.filename"))
					.add(Projections.property("user.buddyIcon.username"))
					.add(Projections.property("user.buddyIcon.fileKey"));
			}
			
			criteria.setProjection( Projections.distinct(proj) );
			
			criteria.setResultTransformer(new SoftwareBeanResultTransformer());
		}
		
		return criteria;
	}
	
	static class SoftwareBeanResultTransformer implements ResultTransformer {
		
		private static final long serialVersionUID = 1L;

		public SoftwareBeanResultTransformer() {
		}

		public Object transformTuple(Object[] tuple, String[] aliases) {
			Software software = new Software();
			software.setId((String) tuple[0]);
			software.setName((String) tuple[1]);
			
			if (tuple.length > 2) {
				User user = new User();
				user.setId((String) tuple[2]);
				user.setUsername((String) tuple[3]);
				user.setNickname((String) tuple[4]);
				Icon icon = new Icon();
				icon.setHost((Integer) tuple[4]);
				icon.setDir((String) tuple[5]);
				icon.setFilename((String) tuple[6]);
				icon.setUsername((String) tuple[7]);
				icon.setFileKey((String) tuple[8]);
				user.setBuddyIcon(icon);
				
				software.setUser(user);
			}
			
			return software;
		}

		public List transformList(List collection) {
			return collection;
		}
	}
	
	
	
	public void setSoftwaresState(User user, Software.State state) {
		if (log.isDebugEnabled()) {
			log.debug("update softwares status of " + user);
		}
		
		bulkUpdate(
				"update Software p set p.state = ? where p.user = ? and (p.state != ? or p.state is null)", 
				new Object[] {state, user, state},
				new Type[] {UserTypes.softwareState(), Hibernate.entity(User.class), UserTypes.softwareState()});
	}

	//~ Accessors ======================================================

}

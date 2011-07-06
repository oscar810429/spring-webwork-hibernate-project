/**
 * @(#)PhotoDAOHibernate.java 2010-3-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.sql.SQLException;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.painiu.core.dao.PhotoDAO;
//import com.painiu.core.model.Album;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Icon;
//import com.painiu.core.model.InterestingPhoto;
//import com.painiu.core.model.LatestPhoto;
import com.painiu.core.model.License;
import com.painiu.core.model.Photo;
import com.painiu.core.model.PhotoAddress;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
//import com.painiu.core.model.UserRecentPhoto;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;


/**
 * <p>
 * <a href="PhotoDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoDAOHibernate.java 41 2010-06-10 17:30:08Z zhangsf $
 */

public class PhotoDAOHibernate  extends BaseDAOHibernate implements PhotoDAO{
	
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(PhotoDAOHibernate.class);
	
	private static final long ONE_DAYS = 1000 * 60 * 60 * 24 * 1;
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PhotoDAO#getPhoto(java.lang.String)
	 */
	public Photo getPhoto(String id) {
        Photo photo = (Photo) getHibernateTemplate().get(Photo.class, id);
        if (photo == null) {
        	if (log.isWarnEnabled()) {
        		log.warn("uh, oh, photo[" + id + "] not found...");
        	}
        	throw new ObjectRetrievalFailureException(Photo.class, id);
        }
        return photo;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PhotoDAO#savePhoto(com.painiu.core.model.Photo)
	 */
	public void savePhoto(Photo photo) {
        if (log.isDebugEnabled()) {
            log.debug("Saving Photo[" + photo.getId() + "]");
        }

        getHibernateTemplate().saveOrUpdate(photo);
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#removePhoto(com.painiu.core.model.Photo)
	 */
	public void removePhoto(Photo photo) {
		if (log.isDebugEnabled()) {
			log.debug("Removing Photo[" + photo.getId() + "]");
		}
		getHibernateTemplate().delete(photo);
		getHibernateTemplate().flush();
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getRecentPublicPhotos(int, int)
	 */
	public Result getRecentPublicPhotos(int start, int limit) {
		if (log.isDebugEnabled()) {
			log.debug("Retriving recent photos: start = " + start + ", limit=" + limit);
		}
		
		Long timestamp = new Long(System.currentTimeMillis() - ONE_DAYS);
		
		return find("from Photo p inner join fetch p.user where p.privacy = ?" +
				" and (p.state = ? or p.state = ? or p.state = ?) and p.timestamp > ? " +
				" order by p.timestamp desc", 
				new Object[] {
					Privacy.EVERYONE,
					Photo.State.USER_POPULAR,
					Photo.State.USER_COMMENDATORY,
					Photo.State.USER_SENIOR,
					timestamp
				},
				new Type[] {
					UserTypes.privacy(),
					UserTypes.photoState(),
					UserTypes.photoState(),
					UserTypes.photoState(),
					Hibernate.LONG
				},
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getTaggedPhotos(java.lang.String, int, int)
	 */
	public Result getTaggedPhotos(String tagName, int start, int limit) {
		return find(
				"select count(p.id) from PhotoTag t join t.photo as p inner join p.user u " +
				"where t.tagName = ? and p.privacy = ? and (p.state = ? or p.state = ? or p.state = ? )" ,
				"select p from PhotoTag t join t.photo as p inner join p.user u" +
				" where t.tagName = ? and p.privacy = ? and (p.state = ? or p.state = ? or p.state = ?)" +
				" order by t.taggedDate desc", 
				new Object[] { tagName, Privacy.EVERYONE, 
						Photo.State.USER_POPULAR,
						Photo.State.USER_COMMENDATORY,
						Photo.State.USER_SENIOR},
				new Type[] { Hibernate.STRING, UserTypes.privacy(), 
						UserTypes.photoState(), UserTypes.photoState(), 
						UserTypes.photoState() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getUserPhotos(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserPhotos(User user, boolean orderByTaken, int start, int limit, Relation relation) {

		String countSql = "select count(p.id) from Photo p where p.user = ? " +
		" and bitwise_and(p.privacy, ?) > 0";

        String sql = "from Photo p join fetch p.stat where p.user = ? " +
	    " and bitwise_and(p.privacy, ?) > 0 order by " + (orderByTaken?"p.takenDate":"p.timestamp") +
	    " desc";
		return find(countSql, sql, 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
			
		
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getUserTaggedPhotos(com.painiu.core.model.User, java.lang.String, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserTaggedPhotos(User user, String tagName, int start, int limit, Relation relation) {
		return find(
				"select count(p.id) from PhotoTag t inner join t.photo p " +
				"where p.user = ? and t.tagName = ? and bitwise_and(p.privacy, ?) > 0",
				"select p from PhotoTag t inner join t.photo p " +
				"where p.user = ? and t.tagName = ? and bitwise_and(p.privacy, ?) > 0 order by t.taggedDate desc", 
				new Object[] { user, tagName, relation },
				new Type[] { Hibernate.entity(User.class), Hibernate.STRING, UserTypes.relation() },
				start, limit);
	}
    
	/*
     * @see com.painiu.core.dao.PhotoDAO#getCommentedPhotos(com.painiu.core.model.User, int, int)
     */
    public Result getCommentedPhotos(User user, int start, int limit) {
        return find(
                "select count(distinct p.id) from PhotoComment c inner join c.photo p " +
                "where p.user != ? and c.author = ?",
                "select distinct p from PhotoComment c inner join c.photo p inner join fetch p.user " +
                "where p.user != ? and c.author = ? order by c.postedDate desc",
                new Object[] { user, user },
                new Type[] { Hibernate.entity(User.class), Hibernate.entity(User.class) },
                start, limit);
    }
    
    /*
	 * @see com.painiu.core.dao.PhotoDAO#getRecentActivePhotos(com.painiu.core.model.User, java.util.Date, int, int)
	 */
	public Result getRecentActivePhotos(User user, Date afterDate, int start, int limit) {
        return find(
                "select count(distinct p.id) from PhotoEvent e where e.owner = ? and e.createdDate > ?",
                "select distinct p from PhotoEvent e inner join e.photo p " +
                "where e.owner = ? and e.createdDate > ? order by e.createdDate desc",
                new Object[] { user, afterDate },
                new Type[] { Hibernate.entity(User.class), Hibernate.TIMESTAMP },
                start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getMostComments(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostComments(User user, int start, int limit, Relation relation) {
		return find(
				"select count(p.id) from Photo p join p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.comments > 0",
				"from Photo p join fetch p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.comments > 0 " +
				"order by stat.comments desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getMostFavorites(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostFavorites(User user, int start, int limit, Relation relation) {
		return find(
				"select count(p.id) from Photo p join p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.favorites > 0",
				"from Photo p join fetch p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.favorites > 0 " +
				"order by stat.favorites desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getMostInteresting(java.util.Date, java.util.Date, int, int, int)
	 */
	public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit) {
		return find(
				"select count(p.id) from Photo p join p.stat stat where p.timestamp >= ? and p.timestamp < ?" +
				"and bitwise_and(p.privacy, ?) > 0 and stat.interests >= ?",
				"from Photo p join fetch p.stat stat where p.timestamp >= ? and p.timestamp < ?" +
				"and bitwise_and(p.privacy, ?) > 0 and stat.interests >= ? " +
				"order by stat.interests desc", 
				new Object[] { fromDate.getTime(), toDate.getTime(), Relation.NONE, new Integer(interests)},
				new Type[] { Hibernate.LONG, Hibernate.LONG, UserTypes.relation(), Hibernate.INTEGER },
				start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getMostInteresting(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostInteresting(User user, int start, int limit, Relation relation) {
		return find(
				"select count(p.id) from Photo p join p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.interests > 0",
				"from Photo p join fetch p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.interests > 0 " +
				"order by stat.interests desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getMostViews(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	 */
	public Result getMostViews(User user, int start, int limit, Relation relation) {
		return find(
				"select count(p.id) from Photo p join p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.views > 0",
				"from Photo p join fetch p.stat stat where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 and stat.views > 0 " +
				"order by stat.views desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}
	
	public Result getMostPopular(User user, int start, int limit, Relation relation) {
		return find(
				"select count(pi.id) from InterestingPhoto pi join pi.photo p where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0",
				"select p from InterestingPhoto as pi join pi.photo as p where p.user = ? " +
				"and bitwise_and(p.privacy, ?) > 0 " +
				"order by pi.createdDate desc", 
				new Object[] { user, relation },
				new Type[] { Hibernate.entity(User.class), UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotos(com.painiu.core.model.User, java.lang.String[], boolean, java.lang.String, int, int, com.painiu.core.model.Relation)
	 */
	//public Result getPhotos(final User user, final Group group, final String[] tags, final boolean taggedAll, final String text, final int start, final int limit, final Relation relation) {
	public Result getPhotos(final User user, final String[] tags, final boolean taggedAll, final String text, final int start, final int limit, final Relation relation) {
		
		return (Result) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException {
				//Criteria criteria = buildCountPhotoCriteria(session, user, group, tags, taggedAll, text, relation);
				Criteria criteria = buildCountPhotoCriteria(session, user, tags, taggedAll, text, relation);
				
				int count = ((Integer) criteria.list().iterator().next()).intValue();
				
				//criteria = buildPhotoCriteria(session, user, group, tags, taggedAll, text, relation, false);
				criteria = buildPhotoCriteria(session, user, tags, taggedAll, text, relation, false);
				
				List data = criteria.setFirstResult(start)
							.setMaxResults(limit).list();
				
				Result result = new Result(start, limit);
				result.setTotal(count);
				result.setData(data);
				
				if (user != null) {
					for (Iterator i = data.iterator(); i.hasNext();) {
						Photo photo = (Photo) i.next();
						photo.setUser(user);
					}
				}
				
				return result;
			}
		});
	}
	
	//static Criteria buildCountPhotoCriteria(final Session session, User user, Group group, String[] tags, boolean taggedAll, String text, Relation relation) {
	static Criteria buildCountPhotoCriteria(final Session session, User user, String[] tags, boolean taggedAll, String text, Relation relation) {
		//Criteria criteria = buildPhotoCriteria(session, user, group, tags, taggedAll, text, relation, true);
		Criteria criteria = buildPhotoCriteria(session, user, tags, taggedAll, text, relation, true);
		if ( (tags != null && tags.length > 1) || text != null ) {
			return criteria.setProjection( Projections.countDistinct("id") );
		}
		return criteria.setProjection( Projections.count("id") );
	}
	
	//static Criteria buildPhotoCriteria(final Session session, User user, Group group, String[] tags, boolean taggedAll, String text, Relation relation, boolean count) {
	static Criteria buildPhotoCriteria(final Session session, User user, String[] tags, boolean taggedAll, String text, Relation relation, boolean count) {
		Criteria criteria = session.createCriteria(Photo.class);
		
		if (user != null) {
			criteria.add( Restrictions.eq("user", user) );
			
			if (relation != null) {
				criteria.add( Restrictions.sqlRestriction(" {alias}.privacy & ? > 0", relation, UserTypes.relation()) );
			}
		} else {
			criteria.add( Restrictions.sqlRestriction(" {alias}.privacy & ? > 0", Relation.NONE, UserTypes.relation()) );
			
			criteria.setFetchMode("user", FetchMode.JOIN);
		}
		
		//if (user == null && group == null) {
		if (user == null){
			Disjunction disjState = Restrictions.disjunction();
			
			disjState.add( Restrictions.eq("state", Photo.State.USER_POPULAR) );
			disjState.add( Restrictions.eq("state", Photo.State.USER_COMMENDATORY) );
			disjState.add( Restrictions.eq("state", Photo.State.USER_SENIOR) );
			
			criteria.add(disjState);
		}
		
		//if (album != null) {
		//	criteria.createAlias("albumPhotos", "ap");
		//	criteria.add( Restrictions.eq("ap.album", album) );
		//}
		
		//if (group != null) {
		//	criteria.createAlias("groupPhotos", "gp");
		//	criteria.add( Restrictions.eq("gp.group", group) );
		//}
		
		if ((tags != null && tags.length > 0) || text != null) {
			Criteria subCriteria = criteria.createCriteria("photoTags", "tags");
            
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
		
		// TODO order parameters
		if (!count) {
			/*if (album != null) {
				criteria.addOrder(Order.asc("ap.position"));
			} else*/
			/*if (group != null) {
				criteria.addOrder(Order.asc("gp.position"));
			} else {*/
				criteria.addOrder(Order.desc("timestamp"));
			//}
		}
		// distinct ?
		if ( (tags != null && tags.length > 1) || text != null ) {
			ProjectionList proj = Projections.projectionList();
			
			proj.add(Projections.property("id"))
				.add(Projections.property("title"))
				.add(Projections.property("width"))
				.add(Projections.property("height"))	
				.add(Projections.property("address.host"))
				.add(Projections.property("address.dir"))
				.add(Projections.property("address.filename"))
				.add(Projections.property("address.secret"))
				.add(Projections.property("address.username"))
				.add(Projections.property("address.fileKey"));
			
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
			
			criteria.setResultTransformer(new PhotoBeanResultTransformer());
		}
		
		return criteria;
	}
	
	static class PhotoBeanResultTransformer implements ResultTransformer {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PhotoBeanResultTransformer() {
		}

		public Object transformTuple(Object[] tuple, String[] aliases) {
			Photo photo = new Photo();
			photo.setAddress(new PhotoAddress());
			photo.setId((String) tuple[0]);
			photo.setTitle((String) tuple[1]);
			photo.setWidth((Integer) tuple[2]);
			photo.setHeight((Integer) tuple[3]);
			photo.getAddress().setHost((Integer) tuple[4]);
			photo.getAddress().setDir((String) tuple[5]);
			photo.getAddress().setFilename((String) tuple[6]);
			photo.getAddress().setSecret((String) tuple[7]);
			photo.getAddress().setUsername((String) tuple[8]);
			photo.getAddress().setFileKey((String) tuple[9]);
			
			if (tuple.length > 10) {
				User user = new User();
				user.setId((String) tuple[10]);
				user.setUsername((String) tuple[11]);
				user.setNickname((String) tuple[12]);
				Icon icon = new Icon();
				icon.setHost((Integer) tuple[13]);
				icon.setDir((String) tuple[14]);
				icon.setFilename((String) tuple[15]);
				icon.setUsername((String) tuple[16]);
				icon.setFileKey((String) tuple[17]);
				user.setBuddyIcon(icon);
				
				photo.setUser(user);
			}
			
			return photo;
		}

		public List transformList(List collection) {
			return collection;
		}
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotosPostedAt(java.util.Date, java.util.Date, int, int)
	 */
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit) {
		return getPhotosPostedAt(from, to, start, limit, true, false);
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotosPostedAt(java.util.Date, java.util.Date, int, int, boolean)
	 */
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, boolean descOrder, boolean update) {
		String countSql = "select count(p.id) from Photo p where p.timestamp >= ? and p.timestamp < ?";

		String sql = "from Photo p join fetch p.user where p.timestamp >= ? and p.timestamp < ? order by p.timestamp" 
					+ (descOrder ? " desc" : "");

		return find(countSql, sql,
				new Object[] {from.getTime(), to.getTime()},
				new Type[] {Hibernate.LONG, Hibernate.LONG},
				start, limit, !update);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotosPostedAt(java.util.Date, java.util.Date, int, int, java.util.List)
	 */
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, List photoStates) {
		String userFilter = "";
		List objects = new ArrayList(5);
		objects.add(from.getTime());
		objects.add(to.getTime());
		List types = new ArrayList(5);
		types.add(Hibernate.LONG);
		types.add(Hibernate.LONG);
		if (photoStates != null && photoStates.size() > 0) {
			Properties userRank = new Properties();
			userRank.put("enumClassname", Photo.State.class);
			for (int i = 0; i < photoStates.size(); i++) {
				if (StringUtils.isEmpty(userFilter)) {
					userFilter = " and (p.state = ? ";
				} else {
					userFilter += " or p.state = ? ";
				}
				objects.add(photoStates.get(i));
				types.add(UserTypes.photoState());
			}
			userFilter += ") ";
		}
		Object[] srcTypes = types.toArray();
		Type[] desTypes = new Type[srcTypes.length];
		for (int i = 0; i < srcTypes.length; i++) {
			desTypes[i] = (Type)srcTypes[i];
		}
		String countSql = "select count(p.id) from Photo p " +
				"where p.timestamp >= ? and p.timestamp < ?" + userFilter;

		String sql = "from Photo p join fetch p.user user " +
				"where p.timestamp >= ? and p.timestamp < ? " + userFilter +
				"order by p.timestamp" ;

		return find(countSql, sql, objects.toArray(), desTypes, start, limit, false);
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getPhotos(com.painiu.core.model.User, java.util.Date, java.util.Date, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserPhotosPostedAt(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation) {
		String countSql = "select count(p.id) from Photo p where p.user = ? and " +
						"p.postedDate >= ? and p.postedDate < ? and bitwise_and(p.privacy, ?) > 0"; 
		
		String sql = "from Photo p" + (detail ? " join fetch p.stat " : " ") +
					"where p.user = ? and p.postedDate >= ? and " +
					"p.postedDate < ? and bitwise_and(p.privacy, ?) > 0 order by p.postedDate desc";
		
		return find(countSql, sql,
				new Object[] { user, from, to, relation },
				new Type[] { Hibernate.entity(User.class), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getUserPhotosTakenOn(com.painiu.core.model.User, java.util.Date, java.util.Date, int, int, com.painiu.core.model.Relation)
	 */
	public Result getUserPhotosTakenOn(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation) {
		String countSql = "select count(p.id) from Photo p where p.user = ? and " +
						"p.takenDate >= ? and p.takenDate < ? and bitwise_and(p.privacy, ?) > 0";
		
		String sql = "from Photo p" + (detail ? " join fetch p.stat " : " ") +
					"where p.user = ? and p.takenDate >= ? and " +
					"p.takenDate < ? and bitwise_and(p.privacy, ?) > 0 order by p.takenDate desc";
		
		return find(countSql, sql,
				new Object[] { user, from, to, relation},
				new Type[] { Hibernate.entity(User.class), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, UserTypes.relation() },
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getContactsPhotos(com.painiu.core.model.User, java.util.Date, java.util.Date, int, int)
	 */
	public List getContactsPhotos(final User user, final Date fromDate, final Date toDate, final int start, final int limit) {
		return (List) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(
						"select {photo.*}, {user.*} " +
						"from yp_photo photo, yp_user user, yp_contact uc " +
						"where photo.user_id = uc.user_id " +
						"and uc.user_id = user.user_id " +
						"and uc.reversed_type & photo.privacy > 0 " +
						"and uc.owner_id = :user " +
						"and photo.posted_date > :fromDate and photo.posted_date < :toDate " +
						"order by photo.posted_date desc limit :offset, :limit")
				.addEntity("photo", Photo.class)
				.addJoin("user", "photo.user")
				.setEntity("user", user)
				.setTimestamp("fromDate", fromDate)
				.setTimestamp("toDate", toDate)
				.setInteger("offset", start)
				.setInteger("limit", limit)
				.list();
				
				List photos = new ArrayList(list.size());
				
				for (Iterator i = list.iterator(); i.hasNext();) {
					Object[] arr = (Object[]) i.next();
					photos.add(arr[0]);
				}
				
				return photos;
			}
		});
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getContactsLatestPhotos(com.painiu.core.model.User, int)
	 */
	/*public List getContactsLatestPhotos(User user, int limit) {
		return getContactsLatestPhoto(user, limit, true);
	}*/
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getContactsLatestPhoto(com.painiu.core.model.User, int)
	 */
	/*public List getContactsLatestPhoto(User user, int limit) {
		return getContactsLatestPhoto(user, limit, false);
	}*/
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getContactsLatestPhotos(com.painiu.core.model.User, int, int)
	 */
	/*public Result getContactsLatestPhotos(final User user, final int start, final int limit) {
		return getContactsLatestPhoto(user, start, limit, true);
	}*/
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getContactsLatestPhoto(com.painiu.core.model.User, int, int)
	 */
	/*public Result getContactsLatestPhoto(final User user, final int start, final int limit) {
		return getContactsLatestPhoto(user, start, limit, false);
	}
	
	private List getContactsLatestPhoto(final User user, final int limit, final boolean fivePerUser) {
		return (List) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String privacyColumn = fivePerUser ? "five_privacy" : "one_privacy";
				
				List list = session.createSQLQuery(
						"select p.* from yp_photo_latest p inner join yp_user u on p.user_id = u.user_id " +
						"left outer join yp_contact uc on u.user_id = uc.user_id " +
						"where uc.reversed_type & p." + privacyColumn +  " > 0 and uc.owner_id = :userId " +
						"order by p.posted_timestamp desc")
				.addEntity("p", LatestPhoto.class)
				.setString("userId", user.getId())
				.setMaxResults(limit)
				.list();
				
				List photos = new ArrayList(list.size());
				
				for (Iterator i = list.iterator(); i.hasNext();) {
					LatestPhoto latestPhoto = (LatestPhoto) i.next();
					try {
						photos.add(getPhoto(latestPhoto.getId()));
					} catch (Exception e) {
						// may be deleted
					}
				}
				
				return photos;
			}
		});
	}

	private Result getContactsLatestPhoto(final User user, final int start, final int limit, final boolean fivePerUser) {
		return (Result) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Result result = new Result(start, limit);
				
				String privacyColumn = fivePerUser ? "five_privacy" : "one_privacy";
				
				Number count = (Number) session.createSQLQuery(
						"select count(photo_id) from yp_photo_latest p inner join yp_user u on p.user_id = u.user_id " +
						"left outer join yp_contact uc on u.user_id = uc.user_id " +
						"where uc.reversed_type & p." + privacyColumn +  " > 0 and uc.owner_id = :userId")
	    				.setString("userId", user.getId())
	    				.uniqueResult();
	    		
	    		result.setTotal(count.intValue());
				
				List list = session.createSQLQuery(
						"select p.* from yp_photo_latest p inner join yp_user u on p.user_id = u.user_id " +
						"left outer join yp_contact uc on u.user_id = uc.user_id " +
						"where uc.reversed_type & p." + privacyColumn +  " > 0 and uc.owner_id = :userId " +
						"order by p.posted_timestamp desc")
				.addEntity("p", LatestPhoto.class)
				.setString("userId", user.getId())
				.setFirstResult(start)
				.setMaxResults(limit + 1)
				.list();
				
				List photos = new ArrayList(list.size());
				
				for (Iterator i = list.iterator(); i.hasNext();) {
					LatestPhoto latestPhoto = (LatestPhoto) i.next();
					try {
						photos.add(getPhoto(latestPhoto.getId()));
					} catch (Exception e) {
						// may be deleted
					}
				}
				
				result.setData(photos);
				
				return result;
			}
		});
	}*/
	
	
	public List getCameraList() {
		return (List) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(
						"select camera_model, cnt " +
						"from (select camera_model, count(photo_id) as cnt from yp_photo " +
						" where camera_model is not null group by camera_model) as tt order by cnt desc").list();
				
				List photos = new ArrayList(list.size());
				
				for (int i = 0; i < list.size(); i++) {
					Object[] arr = (Object[]) list.get(i);
					photos.add(arr);
				}
				
				return photos;
			}
		});
	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getNotInAlbum(com.painiu.core.model.User, java.util.Date, java.util.Date, java.util.Date, java.util.Date, int, int, com.painiu.core.model.Relation)
	 */
	public Result getNotInAlbum(User user, Date minPostedDate, Date maxPostedDate, 
				Date minTakenDate, Date maxTakenDate, int start, int limit, Relation relation) {
		List args = new ArrayList(6);
		List types = new ArrayList(6);
		
		StringBuffer sb = new StringBuffer();
		sb.append("from yp_photo photo left join yp_album_photo ap on photo.photo_id = ap.photo_id")
			.append(" where photo.user_id = ?");
		
		args.add(user);
		types.add(Hibernate.entity(User.class));
		
		if (minPostedDate != null) {
			sb.append(" and photo.posted_date >= ?");
			args.add(minPostedDate);
			types.add(Hibernate.TIMESTAMP);
		}
		if (maxPostedDate != null) {
			sb.append(" and photo.posted_date < ?");
			args.add(maxPostedDate);
			types.add(Hibernate.TIMESTAMP);
		}
		if (minTakenDate != null) {
			sb.append(" and photo.taken_date >= ?");
			args.add(minTakenDate);
			types.add(Hibernate.TIMESTAMP);
		}
		if (maxTakenDate != null) {
			sb.append(" and photo.taken_date < ?");
			args.add(maxTakenDate);
			types.add(Hibernate.TIMESTAMP);
		}
		
		sb.append(" and photo.privacy & ? > 0 and ap.album_id is null");
		
		args.add(new Integer(relation.value()));
		types.add(Hibernate.INTEGER);
		
		final String baseSql = sb.toString();
		final Object[] paramValues = args.toArray();
		final Type[] paramTypes = (Type[]) types.toArray(new Type[types.size()]);
		
		return findBySQL("select count(photo.photo_id) " + baseSql,
				"select {photo.*} " + baseSql + " order by photo.posted_timestamp desc",
				"photo", Photo.class,
				paramValues, paramTypes, start, limit);
	}

	/* 
	 * @see com.painiu.core.dao.PhotoDAO#blockUserPhotos(com.painiu.core.model.User)
	 */
	public void blockUserPhotos(User user) {
		if (log.isDebugEnabled()) {
			log.debug("blocking photos of " + user);
		}
		
		bulkUpdate(
				"update Photo p set p.state = ? where p.user = ? and p.state != ?", 
				new Object[] {Photo.State.BLOCKED, user, Photo.State.BLOCKED},
				new Type[] {UserTypes.photoState(), Hibernate.entity(User.class), UserTypes.photoState()});
	}
	
	/*
	 *  get license photos
	 */
	public Result getLicensePhotos(License license, int start, int limit, Date from, Date to) {
		return find(
				" from Photo p  where p.creativeType = ? " +
				" and p.license = ?  and p.privacy = ? " +
				" and (p.state = ? or p.state = ? or p.state = ?) " +
				" and p.postedDate >= ? and p.postedDate < ? " +
				" order by p.postedDate desc", 
				new Object[] { Integer.valueOf(1), license, Privacy.EVERYONE, 
						Photo.State.USER_POPULAR, Photo.State.USER_COMMENDATORY, 
						Photo.State.USER_SENIOR, from, to},
				new Type[] { Hibernate.INTEGER, UserTypes.license(), 
						UserTypes.privacy(), UserTypes.photoState(), 
						UserTypes.photoState(), UserTypes.photoState(), 
						Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
	}
	
	/*
	 *  get license photos by tag
	 */
	public Result getTaggedLicensePhotos(String tagName, License license, int start, int limit, Date from, Date to) {
		return find(
				"select count(p.id) from PhotoTag t join t.photo as p " +
				"where t.tagName = ? and p.privacy = ? and p.state = ? and p.creativeType = ? and p.license = ?  and p.postedDate >= ? and p.postedDate < ? ",
				"select p from PhotoTag t join t.photo as p inner join p.user " +
				"where t.tagName = ? and p.privacy = ? and p.state = ? and p.creativeType = ? and p.license = ? order by t.taggedDate desc", 
				new Object[] { tagName, Privacy.EVERYONE, Photo.State.PUBLISHED, Integer.valueOf(1), license, from, to},
				new Type[] { Hibernate.STRING, UserTypes.privacy(), UserTypes.photoState(), Hibernate.INTEGER, UserTypes.license(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
	}
	
	/*public void saveInterestingPhoto(InterestingPhoto interestingPhoto) {
		if (log.isDebugEnabled()) {
			log.debug("Saving Photo Interesting[" + interestingPhoto + "]");
		}
		
		getHibernateTemplate().saveOrUpdate(interestingPhoto);
	}
	
	public InterestingPhoto getInterestingPhoto(Photo photo) {
        return (InterestingPhoto) getHibernateTemplate().get(InterestingPhoto.class, photo.getId());
	}
	
	public void removeInterestingPhoto(InterestingPhoto interestingPhoto) {
		if (log.isDebugEnabled()) {
			log.debug("Removing Photo Interesting[" + interestingPhoto + "]");
		}
		getHibernateTemplate().delete(interestingPhoto);
	}*/
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getInterestingnessPhotoIdsGroupByDate(java.util.Date, java.util.Date, int)
	 */
	public List getInterestingnessPhotoIdsGroupByDate(final Date fromDate, final Date toDate, final int limit) {
		return (List) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.getNamedQuery("getPhotoInterestingGroupByDate");
				
				queryObject.setInteger("limit", limit);
				queryObject.setTimestamp("fromDate", fromDate);
				queryObject.setTimestamp("toDate", toDate);
				
				return queryObject.list();
			}
		});
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getInterestingnessPhotos(java.util.Date, java.util.Date, int, int)
	 */
	/*public Result getInterestingnessPhotos(Date fromDate, Date toDate, int start, int limit) {
		return getInterestingnessPhotos(fromDate, toDate, "stat.interests desc", start, limit);
	}*/
	
	/*
	public Result getInterestingnessPhotos(int start, int limit) {
		return find(
				"select count(p.id) from Photo p join p.stat stat where " +
				"bitwise_and(p.privacy, ?) > 0 and stat.score > 0 ",
				"select p from Photo p join p.stat as stat join fetch p.user " +
				"where bitwise_and(p.privacy, ?) > 0 and stat.score > 0 " +
				"order by p.postedDate desc ", 
				new Object[] { Relation.NONE  },
				new Type[] { UserTypes.relation() },
				start, limit);
	}
	*/
	
//	public List getInterestingnessPhotosNoMore(Date fromDate, Date toDate, String orderBy, int limit) {
//		return find(
//				"select pi from InterestingPhoto pi  where pi.createdDate >=? and pi.createdDate <= ? order by " + (orderBy == null ? "rand()" : orderBy),
//				new Object[] {fromDate, toDate},
//				new Type[] {Hibernate.TIMESTAMP, Hibernate.TIMESTAMP},
//				0, limit).getData();
//	}
	
	/*
	 * @see com.painiu.core.dao.PhotoDAO#getInterestingnessPhotos(java.util.Date, java.util.Date, int)
	 */
	/*public List getInterestingnessPhotos(Date fromDate, Date toDate, int limit) {
		return getInterestingnessPhotos(fromDate, toDate, null, -1, limit).getData();
	}*/
	
	/*private Result getInterestingnessPhotos(Date fromDate, Date toDate, String orderBy, int start, int limit) {
		Result result = find(
				"select count(pi.id) from InterestingPhoto pi where pi.createdDate >= ? and pi.createdDate < ?",
				"select pi from InterestingPhoto pi " + 
				((orderBy != null && orderBy.indexOf("stat") != -1) ? "join pi.stat as stat " : "") + 
				"where pi.createdDate >= ? and pi.createdDate < ? " +
				"order by " + (orderBy == null ? "rand()" : orderBy), 
				new Object[] { fromDate, toDate },
				new Type[] { Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
		return convertPhotoResult(result);
	}*/
	
	public int getInterestingnessPhotos(Date fromDate, Date toDate) {
		return count(
				"select count(pi.id) from InterestingPhoto pi where pi.createdDate >= ? and pi.createdDate < ?", 
				new Object[] { fromDate, toDate },
				new Type[] { Hibernate.TIMESTAMP, Hibernate.TIMESTAMP });
	}
	

	
	/*private static Result convertPhotoResult(Result result) {
		List list = result.getOriginalData();
		Iterator it = list.iterator();
		List photos = new ArrayList(list.size());
		while (it.hasNext()) {
			InterestingPhoto interestingPhoto = (InterestingPhoto) it.next();
			photos.add(interestingPhoto.getPhoto());
		}
		result.setData(photos);
		return result;
	}*/
	
	/*
	 *   ä»¥ä¸‹æ˜¯ç”¨æˆ·æœ€è¿‘ç…§ç‰‡è®°å½•çš„ 
	 */
	
	/**
	 *  Save User Recent Photo
	 */
	/*public void saveUserRecentPhoto(UserRecentPhoto userRecentPhoto) {
		 if (log.isDebugEnabled()) {
	            log.debug("Saving UserRecentPhoto [" + userRecentPhoto.getId() + "]");
	      }
		 getHibernateTemplate().saveOrUpdate(userRecentPhoto);
	}*/
	
	/**
	 * remove recent photo from user pool...
	 * @param userRecentPhoto
	 */
	/*public void removeUserRecentPhoto (UserRecentPhoto userRecentPhoto) {
		if (log.isDebugEnabled()) {
			log.debug("Removing UserRecentPhoto [" + userRecentPhoto.getId() + "]");
		}
		getHibernateTemplate().delete(userRecentPhoto);
	}
	
	public UserRecentPhoto getUserRecentPhoto(Photo photo) {
		List list =  find(" from UserRecentPhoto urp where urp.photo = ? ",
				new Object[] {photo},
				new Type[] {Hibernate.entity(Photo.class)},
				0, 1
			).getData();
		
		if (list.size() > 0) {
			return (UserRecentPhoto) list.get(0);
		}
		return null;
	}
	public UserRecentPhoto getUserRecentPhoto(String id) {
		UserRecentPhoto photo = (UserRecentPhoto) getHibernateTemplate().get(UserRecentPhoto.class, id);
        if (photo == null) {
        	log.warn("uh, oh, UserRecentPhoto[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(UserRecentPhoto.class, id);
        }
        return photo;
	}*/
	
	/**
	 * get recent photos from user pool...
	 * @param user
	 * @param privacy
	 * @param limit
	 * @return
	 */
	public List getUserRecentPhotos(User user, Privacy privacy, int limit) {
		return find(" from UserRecentPhoto urp where urp.user = ? " +
						" and urp.privacy = ?  ",
						new Object[] {user, privacy},
						new Type[] {Hibernate.entity(User.class), UserTypes.privacy()},
						0, limit
 				).getData();
	}
	
	/**
	 * get top photos from photo pool...
	 * @param user
	 * @param privacy
	 * @param limit
	 * @return
	 */
	public List getTopUserPhotos (User user, Privacy privacy, int limit) {
		return find (" from Photo p where p.user = ? and p.privacy = ? and p.state = ? order by p.postedDate desc",
				new Object[] {user, privacy, Photo.State.PUBLISHED}, 
				new Type[] {Hibernate.entity(User.class), UserTypes.privacy(), UserTypes.photoState()},
				0, limit
				).getData();
		// return result.getData();
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PhotoDAO#getUserPhotoTotal(com.painiu.core.model.User)
	 */
	public Integer getUserPhotoTotal(User user) {
		return count("select count(p.id) from Photo p where p.user = ? ",
				new Object[] {user},
				new Type[] {Hibernate.entity(User.class)}
				);
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#updatePhotosState(com.painiu.core.model.User)
	 */
	public void setPhotosState(User user, Photo.State state) {
		if (log.isDebugEnabled()) {
			log.debug("update photos status of " + user);
		}
		
		bulkUpdate(
				"update Photo p set p.state = ? where p.user = ? and (p.state != ? or p.state is null)", 
				new Object[] {state, user, state},
				new Type[] {UserTypes.photoState(), Hibernate.entity(User.class), UserTypes.photoState()});
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#getInterestingnessPhotosByTags(java.lang.String, java.util.Date, java.util.Date, int, int)
	 */
	public Result getInterestingnessPhotosByTags(String tags, Date fromDate, Date toDate, int start, int limit) {
//		Result result = find(
//				"select count(pi.id) from InterestingPhoto pi where pi.createdDate >= ? and pi.createdDate < ?",
//				"select pi from InterestingPhoto pi stat.interests desc " + 
//				"where pi.createdDate >= ? and pi.createdDate < ? " +
//				" stat.interests desc ", 
//				new Object[] { fromDate, toDate },
//				new Type[] { Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
//				start, limit);
//		return convertPhotoResult(result);
		return null;
	}

	/*
	 * @see com.painiu.core.dao.PhotoDAO#mergeUserPhoto(com.painiu.core.model.User, com.painiu.core.model.User)
	 */
	public void mergeUserPhoto(User user, User merged) {
		if (log.isDebugEnabled()) {
			log.debug("update photos owner from " + merged + " to " + user);
		}
		bulkUpdate(
				"update Photo p set p.user = ? where p.user = ?",
				new Object[] {user, merged},
				new Type[] {Hibernate.entity(User.class), Hibernate.entity(User.class)}
		);
	}


}

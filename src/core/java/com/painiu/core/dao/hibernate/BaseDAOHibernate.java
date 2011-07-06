package com.painiu.core.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.painiu.core.dao.DAO;
import com.painiu.core.search.Result;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 *
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseDAOHibernate extends ExtendedHibernateDaoSupport implements DAO {
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * @see com.painiu.core.dao.DAO#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * @see com.painiu.core.dao.DAO#getObject(java.lang.Class, java.io.Serializable)
     */
    public Object getObject(Class clazz, Serializable id) {
        Object o = getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * @see com.painiu.core.dao.DAO#getObjects(java.lang.Class)
     */
    public List getObjects(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see com.painiu.core.dao.DAO#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject(Class clazz, Serializable id) {
        getHibernateTemplate().delete(getObject(clazz, id));
    }
    
    /* (non-Javadoc)
     * @see com.painiu.core.dao.DAO#removeObject(java.lang.Object)
     */
    public void removeObject(Object o) {
    	getHibernateTemplate().delete(o);
    }
    
    
    //~ Convenience find methods  ============================================

	protected List findByCriteria(final CriteriaBuilder criteriaBuilder) {
    	return findByCriteria(criteriaBuilder, -1, -1).getData();
    }
    
    protected Result findByCriteria(final CriteriaBuilder criteriaBuilder, final int start, final int limit) {
    	return (Result) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = null;
				
				int count = -1;
				if (start != -1 && limit != -1) {
					criteria = criteriaBuilder.buildCountCriteria(session);
					if (criteria != null) {
						count = ((Integer) criteria.list().iterator().next()).intValue();
					}
				}
				
				criteria = criteriaBuilder.buildCriteria(session);
				
				if (start >= 0) {
					criteria.setFirstResult(start);
				}
				
				if (limit >= 0) {
					criteria.setMaxResults(limit + 1);
				}
				
				List data = criteria.list();
				
				Result result = new Result(start, limit);
				result.setTotal(count == -1 ? data.size() : count);
				result.setData(data);
				
				return result;
			}
		});
    }
    
    
    protected List find(String queryString, int limit) {
    	return find(queryString, null, 0, limit).getData();
    }
    
    protected List find(String queryString, Object[] values) {
    	return find(queryString, values, -1, -1).getData();
    }
    
    protected List find(String queryString, Object[] values, Type[] types) {
    	return find(queryString, values, types, -1, -1).getData();
    }
    
    protected Result find(String queryString, Object parameter, int start, int limit) {
    	return find(queryString, new Object[] { parameter }, start, limit);
    }
    
    protected Result find(String queryString, int start, int limit) {
    	return find(queryString, null, start, limit);
    }
    
    protected Result find(String queryString, Object[] values, int start, int limit) {
		return find(queryString, values, null, start, limit);
    }
    
    protected Result find(String queryString, Object[] values, Type[] types, int start, int limit) {
    	return find(null, queryString, values, types, start, limit, true);
    }
    
    protected Result find(String countQueryString, String queryString, Object parameter, int start, int limit) {
    	return find(countQueryString, queryString, new Object[] { parameter }, null, start, limit, true);
    }
    
    protected List findForUpdate(String queryString, Object[] values, Type[] types) {
    	return findForUpdate(null, queryString, values, types, -1, -1).getData();
    }
    
    protected Result findForUpdate(String countQueryString, String queryString, Object[] values, Type[] types, int start, int limit) {
    	return find(countQueryString, queryString, values, types, start, limit, false);
    }
    
    protected Result find(String countQueryString, 
    		String queryString, 
    		Object[] values, Type[] types, 
    		int start, int limit) {
    	return find(countQueryString, queryString, values, types, start, limit, true);
    }
    	
    protected Result find(final String countQueryString, 
    						final String queryString, 
    						final Object[] values, final Type[] types, 
    						final int start, final int limit, boolean readOnly) {
    	
    	Result result = new Result(start, limit);
    	
    	if (countQueryString != null && start != -1 && limit != -1) {
    		result.setTotal(count(countQueryString, values, types));
    	}
    	
    	HibernateTemplate ht = readOnly ? getReadOnlyHibernateTemplate() : getHibernateTemplate();
    	
    	List data = ht.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				
				setParameters(queryObject, values, types);
				
				if (start >= 0) {
					queryObject.setFirstResult(start);
				}
				
				if (limit >= 0) {
					queryObject.setMaxResults(limit + 1);
				}
				
				return queryObject.list();
			}
		});
    	
		result.setData(data);
		
		if (start == -1 && limit == -1) {
			result.setTotal(data.size());
		}
		
		return result;
    }
    
    protected int count(String queryString) {
    	return count(queryString, null);
    }
    
    protected int count(String queryString, Object[] vlaues) {
    	return count(queryString, vlaues, null);
    }
    
    protected int count(final String queryString, final Object[] values, final Type[] types) {
    	Integer result = (Integer) getReadOnlyHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				
				setParameters(queryObject, values, types);
				
				return queryObject.uniqueResult();
			}
		}
    	);
    	
    	return result.intValue();
    }
    
    protected int bulkUpdate(final String queryString, final Object[] values, final Type[] types) throws DataAccessException {
		Integer updateCount = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				setParameters(queryObject, values, types);
				return new Integer(queryObject.executeUpdate());
			}
		}, true);
		return updateCount.intValue();
	}
    
    static void setParameters(Query queryObject, Object[] values, Type[] types) {
    	if (values != null) {
			if (types != null) {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i], types[i]);
				}
			} else {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i]);
				}
			}
		}
    }
    
    protected List findBySQL(final String sql,
    		final String entityAlias, final Class entityClass,
    		final Object[] values, final Type[] types) {
    	return findBySQL(null, sql, entityAlias, entityClass, values, types, -1, -1).getData();
    }
    
    protected Result findBySQL(final String countSql, 
    		final String sql, 
    		final String entityAlias, final Class entityClass,
    		final Object[] values, final Type[] types, 
    		final int start, final int limit) {
    	return findBySQL(countSql, sql, entityAlias, entityClass, values, types, start, limit, true);
    }
    
    protected Result findBySQL(final String countSql, 
    		final String sql, 
    		final String entityAlias, final Class entityClass,
    		final Object[] values, final Type[] types, 
    		final int start, final int limit, boolean readOnly) {

    	HibernateTemplate ht = readOnly ? getReadOnlyHibernateTemplate() : getHibernateTemplate();
    	
    	Result result = new Result(start, limit);

    	if (countSql != null && start != -1 && limit != -1) {
    		Number count = (Number) ht.execute(new HibernateCallback() {
    			public Object doInHibernate(Session session) throws HibernateException, SQLException {
    				return session.createSQLQuery(countSql).setParameters(values, types).uniqueResult();
    			}
			});
    		result.setTotal(count.intValue());
    	}

    	List data = ht.executeFind(new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException {
    			SQLQuery query = session.createSQLQuery(sql);

    			query.addEntity(entityAlias, entityClass);
    			query.setParameters(values, types);

    			if (start >= 0) {
    				query.setFirstResult(start);
    			}

    			if (limit >= 0) {
    				query.setMaxResults(limit + 1);
    			}

    			return query.list();
    		}
    	});

    	result.setData(data);

    	if (start == -1 && limit == -1) {
    		result.setTotal(data.size());
    	}

    	return result;
    }
}

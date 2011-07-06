/*
 * @(#)FeatureDAOHibernate.java 2007-12-12
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.FeatureDAO;
import com.painiu.core.model.Feature;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="FeatureDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author 5jxiang
 * @version $Id: FeatureDAOHibernate.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class FeatureDAOHibernate extends BaseDAOHibernate implements FeatureDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.dao.FeatureDAO#getFeature(java.lang.String)
	 */
	public Feature getFeature(String id) {
		Feature feature = (Feature) getHibernateTemplate().get(Feature.class,
				id);
		if (feature == null) {
			log.warn("uh oh, Feature[" + id + "] not found...");
			throw new ObjectRetrievalFailureException(Feature.class, id);
		}
		return feature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.dao.FeatureDAO#getFeatures(int, int)
	 */
	public Result getFeatures(int start, int limit) {
		String countSql = "select count(feature.id) from Feature feature ";
		String sql = "from Feature feature order by feature.createdDate desc , feature.order desc";
		return find(countSql, sql, null, null, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.dao.FeatureDAO#removeFeature(com.yupoo.model.Feature)
	 */
	public void removeFeature(Feature feature) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + feature);
		}
		getHibernateTemplate().delete(feature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.dao.FeatureDAO#saveFeature(com.yupoo.model.Feature)
	 */
	public void saveFeature(Feature feature) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + feature);
		}
		getHibernateTemplate().saveOrUpdate(feature);
	}

	/* (non-Javadoc)
	 * @see com.yupoo.dao.FeatureDAO#getShowedFeatures(int, int)
	 */
	public Result getShowedFeatures(int start, int limit) {
		String countSql = "select count(feature.id) from Feature feature where feature.order > 0";
		String sql = "from Feature feature where feature.order > 0 order by feature.createdDate desc , feature.order desc";
		return find(countSql, sql, null, null, start, limit);
	}

}

/*
 * @(#)FeatureManagerImpl.java 2007-12-12
 * 
 * Copyright 2008 Painiu All rights reserved.
 */
package com.painiu.core.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.FeatureDAO;
import com.painiu.core.service.FeatureManager;
import com.painiu.core.model.Feature;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="FeatureManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author 5jxiang
 * @version $Id: FeatureManagerImpl.java 135 2010-11-23 09:28:01Z zhangsf $
 */
@Transactional
public class FeatureManagerImpl implements FeatureManager {
	// ~ Static fields/initializers
	// =============================================

	// ~ Instance fields
	// ========================================================
	private FeatureDAO featureDAO;

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.FeatureManager#getFeatures(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getFeatures(int start, int limit) {
		return featureDAO.getFeatures(start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.FeatureManager#removeFeature(com.yupoo.model.Feature)
	 */
	public void removeFeature(Feature feature) {
		featureDAO.removeFeature(feature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.FeatureManager#saveFeature(com.yupoo.model.Feature)
	 */
	public void saveFeature(Feature feature) {
		featureDAO.saveFeature(feature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.FeatureManager#setFeatureDAO(com.yupoo.dao.FeatureDAO)
	 */
	@NonTransactional
	public void setFeatureDAO(FeatureDAO featureDAO) {
		this.featureDAO = featureDAO;
	}

	@NonTransactional
	public FeatureDAO getFeatureDAO() {
		return featureDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.FeatureManager#getFeature(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Feature getFeature(String id) {
		return featureDAO.getFeature(id);
	}

	/* (non-Javadoc)
	 * @see com.yupoo.logic.FeatureManager#getShowedFeatures(int, int)
	 */
	public Result getShowedFeatures(int start, int limit) {
		return featureDAO.getShowedFeatures(start, limit);
	}

	// ~ Accessors
	// ==============================================================

}

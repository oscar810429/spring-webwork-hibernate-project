/*
 * @(#)FeatureManager.java 2007-12-12
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.service;

import com.painiu.core.dao.FeatureDAO;
import com.painiu.core.model.Feature;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="FeatureManager.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author 5jxiang
 * @version $Id: FeatureManager.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public interface FeatureManager {
	// ~ Static fields/initializers
	// =============================================

	// ~ Instance fields
	// ========================================================

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================
	public void setFeatureDAO(FeatureDAO featureDAO);

	public Feature getFeature(String id);

	public void saveFeature(Feature feature);

	public void removeFeature(Feature feature);

	public Result getFeatures(int start, int limit);
	
	public Result getShowedFeatures(int start, int limit);
	// ~ Accessors
	// ==============================================================
}

/*
 * @(#)FeatureDAO.java 2007-12-12
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.dao;

import com.painiu.core.model.Feature;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="FeatureDAO.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author 5jxiang
 * @version $Id: FeatureDAO.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public interface FeatureDAO extends DAO {
	// ~ Static fields/initializers
	// =============================================

	// ~ Instance fields
	// ========================================================

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================
	public Feature getFeature(String id);

	public void saveFeature(Feature feature);

	public void removeFeature(Feature feature);

	public Result getFeatures(int start, int limit);
	
	public Result getShowedFeatures(int start, int limit);
	// ~ Accessors
	// ==============================================================

}

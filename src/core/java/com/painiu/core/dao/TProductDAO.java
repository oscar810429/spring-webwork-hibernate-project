/**
 * @(#)TProductDAO.java 2011-3-2
 * 
 * Copyright 2008 365Net. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.Relation;
import com.painiu.core.model.TProduct;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="TProductDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id$
 */

public interface TProductDAO {
	
	public TProduct getTProduct(Integer id);
	
	public void saveTProduct(TProduct product);
	
	public void removeTProduct(TProduct product);
	
	public Result getTProducts(String keyword,String cityname,String cid,String sortOrder,Date startDate,Date endDate,int start, int limit);
	
	public List getTProducts();
	

}

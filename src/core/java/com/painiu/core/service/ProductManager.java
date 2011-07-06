/**
 * @(#)ProductManager.java 2011-3-2
 * 
 * Copyright 2008 365Net. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.TProduct;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="ProductManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id$
 */

public interface ProductManager {
	
	public TProduct getTProduct(Integer id);
	
	public void saveTProduct(TProduct product);
	
	public void removeTProduct(TProduct product);
	
	public Result getTProducts(String keyword,String cityname,String cid,String sortOrder,Date startDate,Date endDate,int start, int limit);
	
	public List getTProducts();

}

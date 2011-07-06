/**
 * @(#)ProductManagerImpl.java 2011-3-2
 * 
 * Copyright 2008 365Net. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.TProductDAO;
import com.painiu.core.model.TProduct;
import com.painiu.core.search.Result;
import com.painiu.core.service.ProductManager;

/**
 * <p>
 * <a href="ProductManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id$
 */
@Transactional
public class ProductManagerImpl implements ProductManager{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================
	
	private TProductDAO tProductDAO ;

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	@Transactional(readOnly=true)
	public TProduct getTProduct(Integer id){
		return tProductDAO.getTProduct(id);
	}
	
	public void saveTProduct(TProduct product){
		tProductDAO.saveTProduct(product);
	}
	
	public void removeTProduct(TProduct product){
		tProductDAO.removeTProduct(product);
	}
	
	@Transactional(readOnly=true)
	public Result getTProducts(String keyword,String cityname,String cid,String sortOrder,Date startDate,Date endDate,int start, int limit){
		return tProductDAO.getTProducts(keyword, cityname, cid, sortOrder, startDate, endDate, start, limit);
	}
	
	@Transactional(readOnly=true)
	public List getTProducts(){
		return tProductDAO.getTProducts();
	}

	/**
	 * @param tProductDAO the tProductDAO to set
	 */
	@NonTransactional
	public void settProductDAO(TProductDAO tProductDAO) {
		this.tProductDAO = tProductDAO;
	}
	
	

	//~ Accessors ======================================================

}

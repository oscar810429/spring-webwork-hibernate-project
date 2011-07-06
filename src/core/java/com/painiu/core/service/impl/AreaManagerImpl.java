/**
 * @(#)AreaManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.AreaDAO;
import com.painiu.core.model.Area;
import com.painiu.core.search.Result;
import com.painiu.core.service.AreaManager;

/**
 * <p>
 * <a href="AreaManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AreaManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class AreaManagerImpl extends BaseManager implements AreaManager, InitializingBean{

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	 private AreaDAO areaDAO;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	 
	 
	 @NonTransactional
	 public void setAreaDAO(AreaDAO areaDAO) {
			this.areaDAO = areaDAO;
		}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(areaDAO);

	} 
	
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getArea(com.painiu.core.model.Area)
	 */
	@Transactional(readOnly = true)
	public Area getArea(Area area) {
         return areaDAO.getArea(area);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getArea(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Area getArea(Integer Id) {
         return areaDAO.getArea(Id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getAreas(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public List getAreas(Integer Id) {
        return areaDAO.getAreas(Id);
	}
	
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getAreas(java.lang.Integer, int, int)
	 */
	@Transactional(readOnly = true)
	public Result getAreas(Integer gradeId, int start, int limit) {
		return areaDAO.getAreas(gradeId, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getAreas(com.painiu.core.model.Area, int, int)
	 */
	@Transactional(readOnly = true)
	public Result getAreas(Area area, int start, int limit) {
		return areaDAO.getAreas(area, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#removeArea(com.painiu.core.model.Area)
	 */
	public void removeArea(Area area) {
	    areaDAO.removeArea(area);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#saveArea(com.painiu.core.model.Area)
	 */
	public void saveArea(Area area) {
		areaDAO.saveArea(area);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.service.AreaManager#getAreas(java.lang.String)
	 */
	
	@Transactional(readOnly = true)
	public List getAreas(String ename){
		return areaDAO.getAreas(ename);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.service.AreaManager#getAreas(java.lang.String, int, int)
	 */
	@Transactional(readOnly = true)
	public Result getAreas(String keyword,int start,int limit){
		return areaDAO.getAreas(keyword, start, limit);
	}
	
	/*
	 * @see com.painiu.core.service.AreaManager#getArea(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public Area getArea(String ename){
		return areaDAO.getArea(ename);
	}


	//~ Accessors ==============================================================

}

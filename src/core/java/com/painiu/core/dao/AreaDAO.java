/**
 * @(#)AreaDAO.java Nov 24, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Area;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="AreaDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AreaDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface AreaDAO {
	
	public void saveArea(Area area);
	
	public void removeArea(Area area);

	public Area getArea(Area area);
	
	public Area getArea(String ename);
	
	public Area getArea(Integer Id);
	
	public List getAreas(Integer Id);
	
	public Result getAreas(Area area,int start,int limit);
	
	public Result getAreas(String keyword,int start,int limit);
	
	public Result getAreas(Integer gradeId,int start,int limit);
	
	public List getAreas(String ename);

}

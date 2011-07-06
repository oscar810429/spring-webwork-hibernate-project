/**
 * @(#)CategoryTarget.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.methods;

import java.util.Iterator;
import java.util.List;

import com.painiu.core.model.Area;
import com.painiu.core.search.Result;
import com.painiu.service.api.ApiException;
import com.painiu.service.api.Parameters;
import com.painiu.service.api.ApiObject;

/**
 * <p>
 * <a href="CategoryTarget.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AreaTarget.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class AreaTarget extends BaseTarget{
 
	 	//~ Static fields/initializers =============================================

		//~ Instance fields ========================================================
	
	    private int parentId = 0;
	    private List areas;

		//~ Constructors ===========================================================

		//~ Methods ================================================================
	
	    public Object getList(Parameters params) throws ApiException {
	    	int gradeId = params.getInteger("gid");
	    	if(gradeId==-1){
	    	  areas = areaManager.getAreas(1);
	    	}else{
	    	  areas = areaManager.getAreas(gradeId);	
	    	}
	        ApiObject array = ApiObject.Array("areas");
	        for (Iterator i = areas.iterator(); i.hasNext();) {
	    		Area area = (Area)i.next();
	    		if(area.getParentArea()!=null){
	    		  parentId = area.getParentArea().getId();
	    		}
	    		ApiObject areaObj = ApiObject.Element("area")
				.addAttribute("id", area.getId())
				.addAttribute("name", area.getName())
				.addAttribute("pid", parentId)
				.addAttribute("gid", area.getGrade());
	    		array.addElement(areaObj);
	    	}
	    	
	    	return array;
	    }
	    
	    
	    public Object getCity(Parameters params) throws ApiException {
	    	int areaId = params.getInteger("aid");
	    	Area parentArea = areaManager.getArea(areaId);
	        Result areas = areaManager.getAreas(parentArea,-1,-1);
	        ApiObject array = ApiObject.Array("areas");
	        for (Iterator i = areas.getData().iterator(); i.hasNext();) {
	    		Area area = (Area)i.next();
	    		if(area.getParentArea()!=null){
	    		  parentId = area.getParentArea().getId();
	    		}
	    		ApiObject areaObj = ApiObject.Element("area")
				.addAttribute("id", area.getId())
				.addAttribute("name", area.getName())
				.addAttribute("pid", parentId)
				.addAttribute("gid", area.getGrade());
	    		array.addElement(areaObj);
	    	}
	    	
	    	return array;
	    }

		//~ Accessors ==============================================================
	
}

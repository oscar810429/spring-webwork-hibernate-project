/**
 * @(#)AreaAction.java Nov 24, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import org.apache.commons.lang.StringUtils;

import com.painiu.core.model.Area;
import com.painiu.core.search.Result;
import com.painiu.webapp.action.BaseAction;
import com.painiu.webapp.util.Pinying4jUtils;

/**
 * <p>
 * <a href="AreaAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AreaManagerAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class AreaManagerAction extends BaseAction{

	//~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = -2861179011963159609L;
	private static final int PAGE_SIZE = 45;
	private static final int PAGE_SIZE2 = 5;

	//~ Instance fields ========================================================
	
	private Area area;
	private Integer id;
	private Integer parentId;
	private Result result;
	private String keyword;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public String list() throws Exception{
		int page_size = PAGE_SIZE;
		Integer gradeId = 1;
		if(id==null){
		 id = gradeId;	
		}
		result = this.areaManager.getAreas(id, (page - 1) * page_size, page_size);
		return SUCCESS;
	}
	
	public String search() throws Exception{
		int page_size = PAGE_SIZE;
		result = this.areaManager.getAreas(keyword, (page - 1) * page_size, page_size);
		return SUCCESS;
	}
	
	public String citylist() throws Exception{
		assertParamExists("id", id);
		int page_size = PAGE_SIZE2;
		Area area = this.areaManager.getArea(id);
		result = this.areaManager.getAreas(area, (page - 1) * page_size, page_size);
		return SUCCESS;
	}
	
	public String create() throws Exception{
		area = this.areaManager.getArea(id);
		return SUCCESS;
	}
	
	public String edit() throws Exception{
		assertParamExists("id", id);
		area = this.areaManager.getArea(id);
		return SUCCESS;
	}
	
	public String save() throws Exception{
		if(area.getId()==null){
		  if("".equals(area.getName())){
		    area.setEname(Pinying4jUtils.PingyinToString(area.getName()));
		   }
		    areaManager.saveArea(area);
		}else{
			Area old = areaManager.getArea(area.getId());
			if(area.getGrade()>1){
			if("".equals(area.getEname())){
				 old.setEname(Pinying4jUtils.PingyinToString(area.getName()));
			}else{
				old.setEname(Pinying4jUtils.PingyinToString(area.getEname()));
			}
			}
			old.setName(area.getName());
			old.setAliasname1(area.getAliasname1());
			old.setAliasname2(area.getAliasname2());
			old.setPosition(area.getPosition());
			old.setAreaType(area.getAreaType());
			areaManager.saveArea(old);	
		}
		return SUCCESS;
	}
	
	public String testcity() throws Exception{
		result =  areaManager.getAreas(2, 0, 500);
		for(Object area : result.getData()){
			Area areaNew =  (Area) area;
			if(areaNew.getName().length()<5){
			  areaNew.setAliasname1(StringUtils.left(areaNew.getName(),areaNew.getName().length()-1).trim());
			  areaNew.setEname(Pinying4jUtils.PingyinToString(areaNew.getAliasname1()));
			  areaManager.saveArea(areaNew);
			}
			
		}
		return SUCCESS;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @return the areaId
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param areaId the areaId to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	

	//~ Accessors ==============================================================

}

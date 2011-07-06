/**
 * @(#)SoftwareAction.java 2010-6-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.List;

import com.painiu.core.model.Category;
import com.painiu.core.model.Relation;
import com.painiu.core.model.Software;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SoftwareAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */

public class SoftwareAction extends BaseAction{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================
	
	private String id;
	private Result result;
	private String cid;
	private Software software;
	private List categories;
	private Category category;
	private static final int PAGE_SIZE = 45;
	private int page = 1 ;

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	public String categorylist() throws Exception{
		categories = categoryManager.getSoftwareCategories();
        return SUCCESS;
	}
	
	public String view() throws Exception{
		software = softwareManager.getSoftware(id);
		return SUCCESS;
	}
	
	public String tag() throws Exception{
		category = categoryManager.getCategory(cid);
		categories = categoryManager.getSoftwareCategories(category);
		User user = null;
		Relation relation = Relation.NONE;
        result = softwareManager.getSoftwares(null, null, null, false, null, (page - 1) * PAGE_SIZE, PAGE_SIZE, relation);
		return SUCCESS;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the software
	 */
	public Software getSoftware() {
		return software;
	}

	/**
	 * @param software the software to set
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 * @return the categories
	 */
	public List getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List categories) {
		this.categories = categories;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
    	
	
	

	//~ Accessors ======================================================
	
	

}

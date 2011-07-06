/**
 * @(#)SearchAction.java 2010-3-15
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.painiu.core.model.Category;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;


/**
 * <p>
 * <a href="SearchAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SearchAction.java 77 2010-06-26 11:00:09Z zhangsf $
 */

public class SearchAction extends BaseAction{
	
	
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String c;
	private String s = "everyone";
	private String q;
	private String name;
	
	private int page = 1;
	private Result result;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public String execute() throws Exception {
		if ("photos".equals(c)) {
			if (INPUT.equals(softwares())) {
				return INPUT + "-softwares";
			}
			return "softwares";
			
		} else if ("people".equals(c)) {
			if (INPUT.equals(people())) {
				return INPUT + "-people";
			}
			return "people";
		} else {
			if (INPUT.equals(groups())) {
				return INPUT + "-groups";
			}
			return "groups";
		}
	}
	
	public String softwares() throws Exception {
		if (StringUtils.isBlank(q) || StringUtils.isBlank(s)) {
			addActionError(getText("errors.required.keyword"));
			return INPUT;
		}
		
		String[] tags = null;
		boolean taggedAll = false;
		
		if (q.indexOf('+') != -1) {
			tags = StringUtils.split(q, '+');
			taggedAll = true;
		} else {
			tags = StringUtils.split(q, ' ');
		}
		
		User user = null;
		Relation relation = Relation.NONE;
		
		if (!"everyone".equals(s)) {
			user = getCurrentUser();
			relation = Relation.SELF;
		}
		
		//result = softwareManager.getSoftwares(user, null, tags, taggedAll, null, (page - 1) * 24, 24, relation);
		
		return SUCCESS;
	}
	
	public String groups() throws Exception {
		Category category = categoryManager.getCategory("402881e225698f82012569d973b70001");
		if(name!=null && !"".equals(name)){
		    //result = groupManager.findGroups(category,name, q, (page - 1) * 10, 10);
		}else{
			if (StringUtils.isEmpty(q)) {
				addActionError(getText("errors.required.keyword"));
				return INPUT;
			}
			//result = groupManager.findGroups(category,null, q, (page - 1) * 10, 10);
		}
		
		return SUCCESS;
	}
	
	public String citys() throws Exception {
		//result = groupManager.findGroups(name, (page - 1) * 10, 10);
		return SUCCESS;
	}
	
	public String people() throws Exception {
		if (StringUtils.isEmpty(q)) {
			addActionError(getText("errors.required.keyword"));
			return INPUT;
		} else if (q.length() < 2) {
			addActionError(getText("errors.minLength.keyword"));
			return INPUT;
		}
		result = userManager.findUsers(q, (page - 1) * 10, 10);
		
		return SUCCESS;
	}

	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the q.
	 */
	public String getQ() {
		return q;
	}

	/**
	 * @param q The q to set.
	 */
	public void setQ(String keyword) {
		this.q = keyword;
	}

	/**
	 * @return Returns the page.
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page The page to set.
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return Returns the result.
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result The result to set.
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return Returns the s.
	 */
	public String getS() {
		return s;
	}

	/**
	 * @param s The s to set.
	 */
	public void setS(String person) {
		this.s = person;
	}
	
	/**
	 * @param c the c to set
	 */
	public void setC(String c) {
		this.c = c;
	}
	
	/**
	 * @return the c
	 */
	public String getC() {
		return c;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	


}

/**
 * @(#)HomeAction.java 2009-12-18
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.Date;
import java.util.List;

import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;

import com.painiu.cache.Cache;
import com.painiu.cache.CacheManager;
import com.painiu.core.model.Area;
import com.painiu.core.model.Category;
import com.painiu.core.model.Relation;
import com.painiu.core.model.TProduct;
import com.painiu.core.model.User;
import com.painiu.core.model.UserStat;
import com.painiu.core.search.Result;
import com.painiu.webapp.views.sitemesh.FreemarkerPageDecorator;
import com.painiu.webapp.personality.DomainContext;
import com.painiu.webapp.personality.MultiDomainContextHolder;
import com.painiu.webapp.personality.DomainContextHolder;
import com.painiu.webapp.util.Pinying4jUtils;

/**
 * <p>
 * <a href="HomeAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: HomeAction.java 156 2010-12-24 00:56:15Z zhangsf $
 */
public class HomeAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = 7992251151773467212L;
	private static final int PAGE_SIZE = 48;
	private User user;
	private Result result;
	private int page = 1 ;
	private int cpage = 1;
	private String username;
	private String id;
	private TaskExecutor taskExecutor;
	private Category category;
	private List categorys;
	private Area area;

	//~ Instance fields ========================================================
	
	//~ Constructors =========================================================

	//~ Methods ============================================================

	public String execute() throws Exception {
		/*User user = null;
		Relation relation = Relation.NONE;
        result = softwareManager.getSoftwares(null, null, null, false, null, (page - 1) * PAGE_SIZE, PAGE_SIZE, relation);
		if(id!=null){
		 category = categoryManager.getCategory(id);	
         categorys = categoryManager.getCategories(category);
		}else{
		 categorys = categoryManager.getSoftwareCategories();	
		}*/
       /*List products = productManager.getTProducts();
        for(Object product : products){
        	    TProduct productOld = (TProduct) product;
        	    String cityName= productOld.getCityName();
        	    String pyName = Pinying4jUtils.PingyinToString(cityName);
        	    productOld.setPyName(pyName);
        	    productManager.saveTProduct(productOld);
        }*/
		
		String areaname =  "hangzhou";

		if(DomainContextHolder.getContext()!=null){
			DomainContext domainContext = DomainContextHolder.getContext();
			areaname = domainContext.getArea().getEname();
			area = domainContext.getArea();
		}else{
			area= areaManager.getArea("hangzhou");
		}
		
		//Area a = areaManager.getArea("hangzhou");

		long now = System.currentTimeMillis();
		if (getTimestamp() >= now) {
			now = getTimestamp() + 1;
		}
		if (now % 1000 == 0) {
			now = now + 1;
		}
		
	    String minPostedNew = "";
	    String maxPostedNew = "";
	    String minPostedNow = TFDATE.format(new Date());
	    String maxPostedNow = TFDATE.format(new Date());
 	    minPostedNew = minPostedNow+" 00:00:00";
        maxPostedNew = maxPostedNow+" 23:59:59";
		Date minPostedDate = TF.parse(minPostedNew);
		Date maxPostedDate = TF.parse(maxPostedNew);
		
		result = productManager.getTProducts(null, areaname, null, null, minPostedDate, maxPostedDate, (page - 1) * PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
	}
	
	public String userHome() throws Exception {
		return null;
	}
	
	public String gotourl() throws Exception {
		TProduct tproduct = productManager.getTProduct(Integer.valueOf(id));
		redirect(tproduct.getSiteUrl());
		return SUCCESS;
	}
	
	public String gotosite() throws Exception {
		TProduct tproduct = productManager.getTProduct(Integer.valueOf(id));
		redirect(tproduct.getWebUrl());
		return SUCCESS;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return this.result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return this.page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the cpage
	 */
	public int getCpage() {
		return this.cpage;
	}

	/**
	 * @param cpage the cpage to set
	 */
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
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
	 * @return the categorys
	 */
	public List getCategorys() {
		return categorys;
	}

	/**
	 * @param categorys the categorys to set
	 */
	public void setCategorys(List categorys) {
		this.categorys = categorys;
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
	
	
	
	
     //~ Accessors ==============================================================
	
}

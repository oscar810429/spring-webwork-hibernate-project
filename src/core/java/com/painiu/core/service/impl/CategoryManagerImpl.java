package com.painiu.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.CategoryDAO;
import com.painiu.core.service.CategoryManager;
import com.painiu.core.model.Category;
import com.painiu.core.search.Result;

@Transactional
public class CategoryManagerImpl implements CategoryManager{

	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(CategoryManagerImpl.class);
	//~ Instance fields ========================================================
	private CategoryDAO categoryDAO;
//	private GroupCategoryDAO groupCategoryDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	@Transactional(readOnly=true)
	public Category getCategory(String categoryId) {
		if (log.isDebugEnabled()){
			log.debug("get category by id:"+categoryId);
		}
		
		return categoryDAO.getCategory(categoryId);
	}

	public void removeCategory(Category category) {
		if (log.isDebugEnabled()){
			log.debug("remove category:"+category);
		}
		categoryDAO.removeCategory(category);
		
	}

	@Transactional(readOnly=true) 
	public Category getCategoryName(String name){
		if (log.isDebugEnabled()){
			log.debug("get category by name:"+name);
		}
		return categoryDAO.getCategoryName(name);
	}

	public void saveCategory(Category category) {
		if (log.isDebugEnabled()){
			log.debug("save category:"+category);
		}
		categoryDAO.saveCategory(category);
	}

	@Transactional(readOnly=true)
	public List getSoftwareCategories(Category category) {
		return categoryDAO.getCategories(category);
	}
	
	@Transactional(readOnly=true)
	public List getCategories(Category category) {
		if(category==null){
		    return categoryDAO.getCategories();
		}else{
			return categoryDAO.getCategories(category);
		}
	}

	@Transactional(readOnly=true)
	public List getSoftwareCategories() {
		return categoryDAO.getCategories();
	}
	

	@Transactional(readOnly=true)
	public Result getSoftwares(Category category, int start, int limit) {
		return categoryDAO.getSoftwares(category, start, limit);
	}

	@Transactional(readOnly=true)
	public List getSoftwares(Category category, int limit) {
		return categoryDAO.getSoftwares(category, limit);
	}
	
	/*
	 * @see com.painiu.core.logic.CategoryManager#getGroups(com.painiu.core.model.Category, java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSoftwares(Category category, String name, int start, int limit) {

		return categoryDAO.getSoftwares(category, name, start, limit);
	}
	
	
	//~ Accessors ==============================================================
	@NonTransactional
	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

}

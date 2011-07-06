/**
 * @(#)CategoryAction.java Jul 11, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import com.painiu.core.model.Category;

/**
 * <p>
 * <a href="CategoryAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CategoryAction.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class CategoryAction extends BaseAction{

	//~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = 8320082611517761670L;
	
	//~ Instance fields ========================================================
	
	private Category category;
	private String id;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public String execute() throws Exception {
		
	    /*Category foodcategory = new Category();
		Category fruitcategory = new Category();
		Category applecategory = new Category();
		Category orangecategory = new Category();
		foodcategory.setName("food");
		fruitcategory.setName("fruit");
		applecategory.setName("apple");
		orangecategory.setName("orange");

		foodcategory.getChildCategories().add(fruitcategory);
		fruitcategory.setParentCategory(foodcategory);
		//applecategory.getChildCategories().add(fruitcategory);
		//applecategory.setParentCategory(fruitcategory);
		//orangecategory.getChildCategories().add(fruitcategory);
		//orangecategory.setParentCategory(fruitcategory);
		categoryManager.saveCategory(foodcategory);*/
		@SuppressWarnings("unused")
		Category category = categoryManager.getCategory("40288081226915b2012269207e420007");
        return SUCCESS;
        
	}
	
	public String update(){
		assertParamExists("id",id);
		Category category = categoryManager.getCategory(id);
	    category.setName("fruit");
	    categoryManager.saveCategory(category);
		return SUCCESS;
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


	//~ Accessors ==============================================================
}



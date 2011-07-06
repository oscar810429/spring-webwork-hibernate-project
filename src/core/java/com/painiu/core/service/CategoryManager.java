package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.CategoryDAO;
import com.painiu.core.model.Category;
import com.painiu.core.search.Result;

public interface CategoryManager {
	
	public void setCategoryDAO(CategoryDAO categoryDAO);
	
	public void saveCategory(Category category);
	
	public Category getCategoryName(String name);
	
	public Category getCategory(String categoryId);
	public void removeCategory(Category category);
	
	public List getCategories(Category category);
	
	public List getSoftwareCategories(Category category);
	public List getSoftwareCategories();
	
	// search groups
	public Result getSoftwares(Category category, String name, int start, int limit);
	public Result getSoftwares(Category category,int start, int limit);
	public List getSoftwares(Category category,int limit);
}

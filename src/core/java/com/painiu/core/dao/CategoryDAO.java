package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Category;
import com.painiu.core.search.Result;

public interface CategoryDAO extends DAO{
	
	public Category getCategory(String categoryId);
	
	public Category getCategoryName(String name);
	
	public void removeCategory(Category category);
	
	public void saveCategory(Category category);
	
	public List getCategories();
	
	public List getCategories(Category category);
	
	public List getSoftwares(Category category, int limit);
	public Result getSoftwares(Category category, int start, int limit);
	public Result getSoftwares(Category category, String name, int start, int limit);
}

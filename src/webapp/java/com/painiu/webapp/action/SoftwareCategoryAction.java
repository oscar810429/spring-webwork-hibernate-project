package com.painiu.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.core.model.Area;
import com.painiu.core.model.Category;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Software;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

public class SoftwareCategoryAction extends BaseAction{

	// ~ Static fields/initializers
	// =============================================
	private static final Log log = LogFactory.getLog(SoftwareCategoryAction.class);
	
	private static final int PAGE_SIZE = 20;

	private int page = 1;
	
//	private static List channels = Collections.synchronizedList(new ArrayList());
	
	// ~ Instance fields
	// ========================================================

	private Category category;
    private Software software;
	private String groupId;
	private String id;
	private String name;
	private List categories;
	
	private String parentId;
	private User user;
	
	private List softwares;
	
	private Result result;
	//private List groups;
	private String categoryName;
	 
	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================
	public String execute() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("softwareCategoryAction execute()...");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String save() throws Exception {
		if (id==null){
			String[] categoryStrings = categoryName.split("\\r\\n");
			for(Object name : categoryStrings){
				Category categoryold = new Category();
				categoryold.setCreatedDate(new Date());
				if (category.getParentId().equals("0")){
					categoryold.setParentCategory(null);
				}else{
					categoryold.setParentCategory(categoryManager.getCategory(category.getParentId()));
				}
				categoryold.setName(name.toString().trim());
				categoryold.setPrivacyCreateGroup(category.getPrivacyCreateGroup());
				categoryold.setPrivacyCreateSubcategory(category.getPrivacyCreateSubcategory());
				categoryManager.saveCategory(categoryold);
			}
			
		}else{
			Category tmpCategory = categoryManager.getCategory(id);
			tmpCategory.setName(category.getName());
			tmpCategory.setPrivacyCreateGroup(category.getPrivacyCreateGroup());
			tmpCategory.setPrivacyCreateSubcategory(category.getPrivacyCreateSubcategory());
//			if (category.getParentId().equals("0")){
//				category.setParentCategory(null);
//			}else{
//				category.setParentCategory(categoryManager.getCategory(category.getParentId()));
//			}
			categoryManager.saveCategory(tmpCategory);
		}
		return SUCCESS;
	}

	public String view() throws Exception {
		
		user = getCurrentUser(); 
		//group = groupManager.getGroup(groupId);
		if (id == null) {
			categories = categoryManager.getSoftwareCategories();
//		}if (id==null&&groupId!=null){
//			group = groupManager.getGroup(groupId);
//			categories = categoryManager.getGroupCategories(group);
		}else{
			category = categoryManager.getCategory(id);
			categories = categoryManager.getSoftwareCategories(category);
			//areas = areaManager.getAreas(1);
		}
		

		
		return SUCCESS;
	}
	public String edit() throws Exception {
		user = getCurrentUser();
		categories = categoryManager.getSoftwareCategories();
		category = categoryManager.getCategory(id);
		return SUCCESS;
	}
	public String remove() throws Exception{
		assertParamExists("id", id);
		//if has subcategories or groups ,return error message.
		// cannot delete it
		user = getCurrentUser();
		Category  tmpCategory = categoryManager.getCategory(id);
		Set tmpCategories = tmpCategory.getCategories();
		if (tmpCategories.size()>0){
			addActionError("Cannot delete category,cause it has subcategories.");
			categories = categoryManager.getSoftwareCategories();
			return INPUT;
		}
		List groups = categoryManager.getSoftwares(tmpCategory, 1);
		if (groups.size()>0) {
			addActionError("Cannot delete category,cause it has softwares.");
			categories = categoryManager.getSoftwareCategories();
			return INPUT;
		}
		categoryManager.removeCategory(tmpCategory);
		return SUCCESS;
	}
	
	public String searchSoftwares() throws Exception{
		try {
			categories = categoryManager.getSoftwareCategories();
			user = getCurrentUser();
			if (id.equals("0")) {
				result = categoryManager.getSoftwares(null,name,(page-1)*PAGE_SIZE, PAGE_SIZE);
			}else {
				
				category = categoryManager.getCategory(id);
				
				result = categoryManager.getSoftwares(category,name,(page-1)*PAGE_SIZE, PAGE_SIZE);
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String listSoftwares() throws Exception {
		
		categories = categoryManager.getSoftwareCategories();
		user = getCurrentUser();
		category = categoryManager.getCategory(id);
		result = categoryManager.getSoftwares(category,(page-1)*PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
		
	}
	// ~ Accessors
	// ==============================================================

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getCategories() {
		return categories;
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * @return the softwares
	 */
	public List getSoftwares() {
		return softwares;
	}

	/**
	 * @param softwares the softwares to set
	 */
	public void setSoftwares(List softwares) {
		this.softwares = softwares;
	}

    // added by vinson
//    public static ArrayList getCategories() {
//
//    	System.out.println("get categories from mem :"+channels.size());
//    	return new ArrayList(channels);
//    }
//    
//    public static void setCategories(List list) {
//
//    	System.out.println("set categories to mem :"+list.size());
//    	channels = list;
//    }
	
	
	
	
	
	
	


}

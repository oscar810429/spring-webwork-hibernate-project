package com.painiu.core.dao.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.CategoryDAO;
import com.painiu.core.model.Category;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

public class CategoryDAOHibernate extends BaseDAOHibernate implements
		CategoryDAO {

	public Category getCategory(String CategoryId) {
		return (Category) getHibernateTemplate().get(Category.class, CategoryId);
	}
	
	public Category getCategoryName(String name){
    	  List list = getHibernateTemplate().find("from Category category where category.name = ?", name);
        if (list != null & list.size() > 0) {
            return (Category) list.get(0);
        }
        if (log.isWarnEnabled()) {
            log.warn("can not find category[category:" + name + "]");
        }
        throw new ObjectRetrievalFailureException(Category.class, name);
	}

	public void removeCategory(Category Category) {
		getHibernateTemplate().delete(Category);
	}

	public void saveCategory(Category category) {
		getHibernateTemplate().saveOrUpdate(category);
	}

	public List getCategories() {
		List categories = getHibernateTemplate().find(
				"from Category category where category.parentCategory = null ");
		for (Iterator iter = categories.iterator(); iter.hasNext();) {
			Category element = (Category) iter.next();
			List subEle = getHibernateTemplate().find(" from Category category where category.parentCategory = ? ",element);
			element.setCategories(new HashSet(subEle));

		}
		return categories;
	}

	public List getCategories(Category category) {

		return getHibernateTemplate().find("from Category category where category.parentCategory = ?",category);

	}

	// get all groups by category is limited operation
	// BE CAREFUL
	public List getSoftwares(Category category, int limit) {
		return getSoftwares(category, -1, limit).getData();
	}

	// get all groups by category is limited operation
	// BE CAREFUL
	public Result getSoftwares(Category category, int start, int limit) {
		return find(
				"select count(s.id) from Software s where s.category = ? or s.category.parentCategory = ? ",
				"from Software s where s.category = ? or s.category.parentCategory = ? order by s.postedDate desc ",
				new Object[] { category, category }, new Type[] {
						Hibernate.entity(Category.class),
						Hibernate.entity(Category.class) }, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.CategoryDAO#getGroups(com.painiu.core.model.Category,
	 *      java.lang.String, int, int)
	 */
	public Result getSoftwares(Category category, String keyword, int start,
			int limit) {
		if (category != null && !keyword.equals("")) {
			return find(
					"select count(s.id) from Software s "
							+ "where (s.category = ? or s.category.parentCategory = ?) and s.name like ? ",
					"from Software s "
							+ "where (s.category = ? or s.category.parentCategory = ?) and s.name like ? "
							+ "order by s.postedDate desc",
					new Object[] { category, category, "%" + keyword + "%" },
					new Type[] { Hibernate.entity(Category.class),
							Hibernate.entity(Category.class), Hibernate.STRING },
					start, limit);
		}
		if (category == null && !keyword.equals("")) {
			return find("select count(s.id) from Software s "
					+ "where s.name like ? ", "from Software s "
					+ "where s.name like ? " + "order by s.postedDate desc",
					new Object[] { "%" + keyword + "%" },
					new Type[] { Hibernate.STRING }, start, limit);
		}
		if (category != null && keyword.equals("")) {
			return find(
					"select count(s.id) from Software s "
							+ "where (s.category = ? or s.category.parentCategory = ?) ",
					"from Software s "
							+ "where (s.category = ? or s.category.parentCategory = ?) "
							+ "order by s.postedDate desc", new Object[] {
							category, category }, new Type[] {
							Hibernate.entity(Category.class),
							Hibernate.entity(Category.class) }, start, limit);
		}
		return find("select count(s.id) from Software s ", "from Software s "
				+ "order by s.postedDate desc", new Object[] {},
				new Type[] {}, start, limit);
	}

}

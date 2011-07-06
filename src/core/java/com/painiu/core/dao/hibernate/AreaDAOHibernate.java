/**
 * @(#)AreaDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.AreaDAO;
import com.painiu.core.model.Area;
import com.painiu.core.model.Category;
import com.painiu.core.model.Privacy;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="AreaDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AreaDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class AreaDAOHibernate extends BaseDAOHibernate implements AreaDAO  {

    //~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getArea(com.painiu.core.model.Area)
	 */
	public Area getArea(Area area) {
		return this.getArea(area.getId());
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getArea(java.lang.Integer)
	 */
	public Area getArea(Integer Id) {
		Area area = (Area) getHibernateTemplate().get(Area.class, Id);
        if (area == null) {
            log.warn("uh oh, Area '" + Id + "' not found...");
            throw new ObjectRetrievalFailureException(Area.class, Id);
        }
        return area;
	}
	
	public Area getArea(String ename) {
		List areas = this.find("from Area area where area.ename=? and area.ename is not NULL", new Object[]{ename}, new Type[]{Hibernate.STRING});
		if(areas.size()>0 && areas!=null){
			Area area = (Area)areas.get(0);
			return area;
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getAreas(java.lang.Integer)
	 */
	public List getAreas(Integer Id) {
        return this.find("from Area area where area.grade=?", new Object[]{Id}, new Type[]{Hibernate.INTEGER});
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getAreas(com.painiu.core.model.Area, int, int)
	 */
	public Result getAreas(Area area, int start, int limit) {
        return this.find("select count(area.id) from Area area where area.parentArea=?","from Area area where area.parentArea=?",new Object[]{area},new Type[]{Hibernate.entity(Area.class)}, start, limit);
	}
	
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#getAreas(java.lang.Integer, int, int)
	 */
	public Result getAreas(Integer gradeId, int start, int limit) {
        return this.find("select count(area.id) from Area area where area.grade=?","from Area area where area.grade=?",new Object[]{gradeId},new Type[]{Hibernate.INTEGER}, start, limit);
	}
	
	public Result getAreas(String keyword,int start,int limit){
		return this.find("select count(area.id) from Area area where area.name like ? and area.areaType = ?","from Area area where area.name like ? and area.areaType = ?",
				                 new Object[]{"%" + keyword + "%",Area.AreaType.AREA_ENABLE},
				                 new Type[]{Hibernate.STRING,UserTypes.areaState()}, 
				                 start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#removeArea(com.painiu.core.model.Area)
	 */
	public void removeArea(Area area) {
	     getHibernateTemplate().delete(area);
    }

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AreaDAO#saveArea(com.painiu.core.model.Area)
	 */
	public void saveArea(Area area) {
		getHibernateTemplate().saveOrUpdate(area);
	}
	
	
	public List getAreas(String ename){
	    return find("from Area area where area.ename like ? and area.areaType = ? order by area.position desc ",new Object[] { ename + "%",Area.AreaType.AREA_ENABLE},new Type[] {Hibernate.STRING,UserTypes.areaState()});
	}

	//~ Accessors ==============================================================

}

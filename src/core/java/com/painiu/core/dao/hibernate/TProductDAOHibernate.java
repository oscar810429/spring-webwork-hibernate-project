/**
 * @(#)TProductDAOHibernate.java 2011-3-2
 * 
 * Copyright 2008 365Net. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.TProductDAO;
import com.painiu.core.model.TProduct;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="TProductDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id$
 */

public class TProductDAOHibernate extends BaseDAOHibernate implements TProductDAO{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	
	public TProduct getTProduct(Integer id){
		TProduct product = (TProduct) getHibernateTemplate().get(TProduct.class, id);
        if (product == null) {
        	if (log.isWarnEnabled()) {
        		log.warn("uh, oh, product[" + id + "] not found...");
        	}
        	throw new ObjectRetrievalFailureException(TProduct.class, id);
        }
        return product;
	}
	
	public void saveTProduct(TProduct product){
		 getHibernateTemplate().save(product);
	}
	
	public void removeTProduct(TProduct product){
		getHibernateTemplate().delete(product);
		
	}
	
	public Result getTProducts(String keyword,String cityname,String cid,String sortOrder,Date startDate,Date endDate,int start, int limit){
		
		String productFilter = "";
		List objects = new ArrayList(10);
		List types = new ArrayList(10);
		String filter_sort = "";
		
		/*if(startDate!=null && endDate!=null){
			productFilter  = " where p.startTime >= ? and p.endTime < ?";
			objects.add(startDate);
			objects.add(endDate);
			types.add(Hibernate.TIMESTAMP);
			types.add(Hibernate.TIMESTAMP);
		}*/
		
		if(!"".equals(cityname) && cityname!=null){
			productFilter += " where  p.pyName = ?";
			objects.add(cityname);
			types.add(Hibernate.STRING);
		}
		
		if(!"".equals(keyword) && keyword!=null){
			productFilter += " and p.title like ?";
			objects.add("%" + keyword + "%");
			types.add(Hibernate.STRING);
		}
		
       if("token_views".equals(sortOrder)){
			filter_sort = " order by p.amount";
		}
		
		if("token_date".equals(sortOrder) || "".equals(sortOrder)){
			filter_sort = " order by p.startTime";
		}
		
		if("token_price".equals(sortOrder)){
			filter_sort = " order by p.salePrice";
		}
		
	    if("token_discount".equals(sortOrder)){
			filter_sort = " order by p.discount";
		}
	    
		final Object[] paramValues = objects.toArray();
		final Type[] paramTypes = (Type[]) types.toArray(new Type[types.size()]);
		
		
        return find(
                "select count(p.id) from TProduct p"+productFilter,
                "from TProduct p"+productFilter+filter_sort,
                paramValues,
                paramTypes,
                start, limit);
	}
	
	public List getTProducts(){
		return find("from TProduct p ", null);
	}

	//~ Accessors ======================================================

}

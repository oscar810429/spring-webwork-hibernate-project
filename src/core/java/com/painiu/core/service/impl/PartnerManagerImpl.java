/*
 * @(#)PartnerManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.PartnerDAO;
//import com.painiu.core.dao.ProductDAO;
import com.painiu.core.service.PartnerManager;
import com.painiu.core.model.Partner;
//import com.painiu.core.model.Product;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="PartnerManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PartnerManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class PartnerManagerImpl implements PartnerManager {

	private PartnerDAO partnerDAO;
	//private ProductDAO productDAO;

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.PartnerManager#findPartnerList(int, int)
	 */
	@Transactional(readOnly=true)
	public Result findPartnerList(int start, int limit) {
		return partnerDAO.findPartnerList(start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.PartnerManager#findPartnerList(int)
	 */
	@Transactional(readOnly=true)
	public List findPartnerList(int amount) {
		return partnerDAO.findPartnerList(amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.PartnerManager#getPartner(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Partner getPartner(String id) {
		return partnerDAO.getPartner(id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.PartnerManager#remove(com.painiu.core.model.Partner)
	 */
	public void remove(Partner partner) {
		/*List productList = productDAO.findProductByPartnerId(partner.getId(), -1, 200).getData();
		Product product;
		for (Iterator i = productList.iterator(); i.hasNext(); ) {
			product = (Product)( i.next() );
			productDAO.remove(product);
		}*/
		partnerDAO.remove(partner);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.PartnerManager#save(com.painiu.core.model.Partner)
	 */
	public void save(Partner partner) {
		partnerDAO.save(partner);

	}

	@NonTransactional
	public PartnerDAO getPartnerDAO() {
		return partnerDAO;
	}

	 @NonTransactional
	public void setPartnerDAO(PartnerDAO partnerDAO) {
		this.partnerDAO = partnerDAO;
	}
	 
	/* @NonTransactional
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	 @NonTransactional
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}*/

}

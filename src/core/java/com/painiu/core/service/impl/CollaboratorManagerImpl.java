/*
 * @(#)CollaboratorManagerImpl.java  2006-7-31
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.CoUserDAO;
import com.painiu.core.dao.CollaboratorDAO;
import com.painiu.core.dao.ExchangeStatDAO;
import com.painiu.core.dao.PartnerDomainDAO;
import com.painiu.core.service.CollaboratorManager;
import com.painiu.core.model.CoUser;
import com.painiu.core.model.Collaborator;
import com.painiu.core.model.ExchangeStat;
import com.painiu.core.model.PartnerDomain;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="CollaboratorManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: CollaboratorManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class CollaboratorManagerImpl implements CollaboratorManager {
	
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private CollaboratorDAO collaboratorDAO;
	
	private CoUserDAO coUserDAO;
	
	private ExchangeStatDAO exchangeStatDAO;
	
	private PartnerDomainDAO partnerDomainDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#setExchangeStatDAO(com.painiu.core.dao.ExchangeStatDAO)
	 */
	@NonTransactional
	public void setExchangeStatDAO(ExchangeStatDAO dao) {
		this.exchangeStatDAO = dao;
	}
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#setCollaboratorDAO(com.painiu.core.dao.CollaboratorDAO)
	 */
	@NonTransactional
	public void setCollaboratorDAO(CollaboratorDAO collaboratorDAO) {
		this.collaboratorDAO = collaboratorDAO;
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#setCoUserDAO(com.painiu.core.dao.CoUserDAO)
	 */
	@NonTransactional
	public void setCoUserDAO(CoUserDAO coUserDAO) {
		this.coUserDAO = coUserDAO;
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#setPartnerDomainDAO(com.painiu.core.dao.PartnerDomainDAO)
	 */
	@NonTransactional
	public void setPartnerDomainDAO(PartnerDomainDAO partnerDomainDAO) {
		this.partnerDomainDAO = partnerDomainDAO;
	}
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getCollaborator(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Collaborator getCollaborator(String id) {
		return this.collaboratorDAO.getCollaborator(id);
	}
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#saveCollaborator(com.painiu.core.model.Collaborator)
	 */
	public void saveCollaborator(Collaborator colla) {
		this.collaboratorDAO.saveCollaborator(colla);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#removeCollaborator(com.painiu.core.model.Collaborator)
	 */
	public void removeCollaborator(Collaborator colla) {
		this.collaboratorDAO.removeCollaborator(colla);
	}


	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getCoUser(com.painiu.core.model.User, com.painiu.core.model.Collaborator)
	 */
	@Transactional(readOnly=true)
	public CoUser getCoUser(Collaborator colla, String collaUserId) {
		return this.coUserDAO.getCoUser(new CoUser.Id(colla.getId(), collaUserId));
	}
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getCoUser(com.painiu.core.model.User, com.painiu.core.model.Collaborator)
	 */
	public CoUser getCoUser(User user, Collaborator colla) {
		return this.coUserDAO.getCoUser(user, colla);
	}
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#saveCoUser(com.painiu.core.model.CoUser)
	 */
	public void saveCoUser(CoUser coUser) {
		this.coUserDAO.saveCoUser(coUser);
	}
	
	/*
	 * @see com.painiu.core.logic.CollaboratorManager#removeCoUser(com.painiu.core.model.CoUser)
	 */
	public void removeCoUser(CoUser coUser) {
		this.coUserDAO.removeCoUser(coUser);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.CollaboratorManager#getCollaborators(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getCollaborators(int start, int limit) {
		return this.collaboratorDAO.getCollaborators(start, limit);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#deleteExchangeStat(com.painiu.core.model.ExchangeStat)
	 */
	public void deleteExchangeStat(ExchangeStat stat) {
		this.exchangeStatDAO.deleteExchangeStat(stat);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getExchangeStat(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public ExchangeStat getExchangeStat(String id) {
		return exchangeStatDAO.getExchangeStat(id);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getExchangeStat(com.painiu.core.model.User, java.lang.String)
	 */
	@Transactional(readOnly=true)
	public ExchangeStat getExchangeStat(User user, String url) {
		return this.exchangeStatDAO.getExchangeStat(user, url);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getExchangeStats(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getExchangeStats(User user, int start, int limit) {
		return this.exchangeStatDAO.getExchangeStats(user, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#saveExchangeStat(com.painiu.core.model.ExchangeStat)
	 */
	public void saveExchangeStat(ExchangeStat stat) {
		exchangeStatDAO.saveExchangeStat(stat);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getExchangeStats(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getExchangeStats(int start, int limit) {
		return exchangeStatDAO.getExchangeStats(start, limit);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getTotalClickCount()
	 */
	@Transactional(readOnly=true)
	public Integer getTotalClickCount() {
		return exchangeStatDAO.getTotalClickCount();
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getTotalViewCount()
	 */
	@Transactional(readOnly=true)
	public Integer getTotalViewCount() {
		return exchangeStatDAO.getTotalViewCount();
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getTotalLinkCount()
	 */
	@Transactional(readOnly=true)
	public Integer getTotalLinkCount() {
		return exchangeStatDAO.getTotalLinkCount();
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getPartnerDomain(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public PartnerDomain getPartnerDomain(String id) {
		return partnerDomainDAO.getPartnerDomain(id);
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#getPartnerDomains()
	 */
	@Transactional(readOnly=true)
	public List getPartnerDomains() {
		return partnerDomainDAO.getPartnerDomains();
	}

	/*
	 * @see com.painiu.core.logic.CollaboratorManager#savePartnerDomain(com.painiu.core.model.PartnerDomain)
	 */
	public void savePartnerDomain(PartnerDomain partnerDomain) {
		partnerDomainDAO.savePartnerDomain(partnerDomain);
	}

	
	
}
/*
 * @(#)CollaboratorManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.CoUserDAO;
import com.painiu.core.dao.CollaboratorDAO;
import com.painiu.core.dao.ExchangeStatDAO;
import com.painiu.core.dao.PartnerDomainDAO;
import com.painiu.core.model.CoUser;
import com.painiu.core.model.Collaborator;
import com.painiu.core.model.ExchangeStat;
import com.painiu.core.model.PartnerDomain;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="CollaboratorManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CollaboratorManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface CollaboratorManager {
	
	public void setCollaboratorDAO(CollaboratorDAO collaboratorDAO);
	
	public void setCoUserDAO(CoUserDAO coUserDAO);
	
	public void setPartnerDomainDAO(PartnerDomainDAO partnerDomainDAO);
	
	public Collaborator getCollaborator(String id);
	
	public void saveCollaborator(Collaborator colla);
	
	public void removeCollaborator(Collaborator colla);
	
	public Result getCollaborators(int start, int limit);
	
	public CoUser getCoUser(Collaborator colla, String coUserId);
	
	public CoUser getCoUser(User user, Collaborator colla);
	
	public void saveCoUser(CoUser coUser);
	
	public void removeCoUser(CoUser coUser);
	
	public void setExchangeStatDAO(ExchangeStatDAO dao);
	public void saveExchangeStat(ExchangeStat stat);
	public void deleteExchangeStat(ExchangeStat stat);
	public ExchangeStat getExchangeStat(String id);
	public ExchangeStat getExchangeStat(User user, String url);
	public Result getExchangeStats(User user, int start, int limit);
	public Result getExchangeStats(int start, int limit);
	public Integer getTotalViewCount();
	public Integer getTotalClickCount();
	public Integer getTotalLinkCount();
	
	public void savePartnerDomain(PartnerDomain partnerDomain);
	public PartnerDomain getPartnerDomain(String id);
	public List getPartnerDomains();
}

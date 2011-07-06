/*
 * @(#)CooperationManageAction.java 2008-9-6
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.model.Application;
import com.painiu.core.model.Collaborator;
import com.painiu.core.model.PartnerDomain;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="CooperationManageAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 5jxiang
 * @version $Id: CooperationManageAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class CooperationManageAction extends BaseAction {
	//~ Static fields/initializers =============================================
	private static final int PAGE_SIZE = 15;
	//~ Instance fields ========================================================
	private int page = 1;	
	private Result result;
	private String id;
	private Collaborator collaborator;
	private String hostAddressesStr;
	private List list;
	private PartnerDomain partnerDomain;
	//private PartnerManager partnerManager;
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	//--About partner--
	public String editCollaborator() throws Exception {
		assertParamExists("id", id);
		collaborator = collaboratorManager.getCollaborator(id);	
		Set hostAddresses = collaborator.getHostAddresses();
		Object[] strs = hostAddresses.toArray();
		hostAddressesStr = "";
		for(int i = 0; i < strs.length; i++) {
			hostAddressesStr = hostAddressesStr + (String)strs[i];
			if (i < strs.length-1) {
				hostAddressesStr = hostAddressesStr + ",";
			}
		}
		return SUCCESS;
	}
	
	public String collaboratorList() throws Exception {		
		result = collaboratorManager.getCollaborators((page - 1) * PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
	}
	
	public String saveCollaborator() throws Exception {
		assertParamExists("id", collaborator.getId());
		try {
			collaboratorManager.getCollaborator(collaborator.getId());
			saveMessage("Save faild! The collaborator of id has been existent. Please input another id.");
			return INPUT;
		} catch (ObjectRetrievalFailureException e){
			if (StringUtils.isNotEmpty(hostAddressesStr)) {
				String[] strs = StringUtils.split(hostAddressesStr,',');
				Set hostAddresses =  new HashSet(strs.length);
				for (int i = 0; i< strs.length; i++) {
					hostAddresses.add(strs[i]);
				}
				collaborator.setHostAddresses(hostAddresses);
			}
			collaborator.setCreatedDate(new Date());
			collaborator.setSecret(RandomStringUtils.randomAlphabetic(32));
			collaboratorManager.saveCollaborator(collaborator);
			
			// create API Application
			Application app = new Application();
			app.setApiKey(collaborator.getId());
			app.setUser(null);
			app.setTitle(collaborator.getName() + "'s application");
			app.setDescription(collaborator.getDescription());
			app.setType(Application.Type.WEB_APP);
			app.setSecret(collaborator.getSecret());
			app.setCreatedDate(new Date());
			app.setConfigured(Boolean.TRUE);

			//applicationManager.saveApplication(app);
			
			saveMessage("Save success !");
			return SUCCESS;
		}
	}
	
	public String updateCollaborator() throws Exception {
		assertParamExists("id", collaborator.getId());
		try {
			Collaborator coll = collaboratorManager.getCollaborator(collaborator.getId());
			if (StringUtils.isNotEmpty(hostAddressesStr)) {
				String[] strs = StringUtils.split(hostAddressesStr,',');
				Set hostAddresses =  new HashSet(strs.length);
				for (int i = 0; i< strs.length; i++) {
					hostAddresses.add(strs[i]);
				}
				coll.setHostAddresses(hostAddresses);
			}
			coll.setDescription(collaborator.getDescription());
			coll.setHomeURL(collaborator.getHomeURL());
			coll.setLogoURL(collaborator.getLogoURL());
			coll.setName(collaborator.getName());
			collaboratorManager.saveCollaborator(coll);
			saveMessage("Save success!");
			return SUCCESS;
		} catch (ObjectRetrievalFailureException e){
			saveMessage("Save faild!");
			return "input";
		}
	}
	
	public String deleteCollaborator() throws Exception {
		assertParamExists("id", id);
		collaborator = collaboratorManager.getCollaborator(id);		
		collaboratorManager.removeCollaborator(collaborator);		
		saveMessage("The collaborator has been deleted.");		
		return SUCCESS;
	}
	
	public String partnerDomains() throws Exception {
		list = collaboratorManager.getPartnerDomains();
		return SUCCESS;
	}
	
	public String editPartnerDomain() throws Exception {
		partnerDomain = collaboratorManager.getPartnerDomain(id);
		return SUCCESS;
	}
	
	public String createPartnerDomain() throws Exception {
		if ("GET".equals(getRequest().getMethod())) {
			return INPUT;
		}
		if (id != null) {
			PartnerDomain old = collaboratorManager.getPartnerDomain(id);
			old.setPartnerName(partnerDomain.getPartnerName());
			old.setFlashDomain(partnerDomain.getFlashDomain());
			old.setHost(partnerDomain.getHost());
			old.setIconURLPattern(partnerDomain.getIconURLPattern());
			old.setMediaRoot(partnerDomain.getMediaRoot());
			old.setPhotoURLPattern(partnerDomain.getPhotoURLPattern());
			old.setUserDomainPattern(partnerDomain.getUserDomainPattern());
			collaboratorManager.savePartnerDomain(old);
		} else {
			collaboratorManager.savePartnerDomain(partnerDomain);
		}
		return SUCCESS;
	}
	
	//~ Accessors ==============================================================

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Collaborator getCollaborator() {
		return collaborator;
	}
	
	/**
	 * @param collaborator the collaborator to set
	 */
	public void setCollaborator(Collaborator collaborator) {
		this.collaborator = collaborator;
	}

	public String getHostAddressesStr() {
		return hostAddressesStr;
	}

	public void setHostAddressesStr(String hostAddressesStr) {
		this.hostAddressesStr = hostAddressesStr;
	}

	public List getList() {
		return list;
	}

	public PartnerDomain getPartnerDomain() {
		return partnerDomain;
	}

	public void setPartnerDomain(PartnerDomain partnerDomain) {
		this.partnerDomain = partnerDomain;
	}

}

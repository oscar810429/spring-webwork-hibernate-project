/*
 * @(#)PhotoConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;

import com.painiu.webapp.image.Repository;

/**
 * <p>
 * <a href="PhotoConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class PhotoConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String urlPattern;
	private String mailUploadApiKey;
	private int batchSize;
	
	private boolean pnfs;
	
	private Integer defaultHost;
	
	private Repository defaultRepository;
	
	private Map repositoryMap = new HashMap();
	private Set repositories = new HashSet();
	
	private Set imageTypes = new HashSet();
	private Set zipTypes = new HashSet();
	
	private FlowmeterConfig flowmeterConfig;
	private ProgressConfig progressConfig;
	
	private long maxLength;
	private int maxWidth;
	private int maxHeight;
	
	
	
	private List buckets = new ArrayList();
	
	//~ Constructors ===========================================================
	
	public Repository getRepository() {
		Integer host = (Integer) buckets.get(RandomUtils.nextInt(buckets.size()));
		return getRepository(host);
	}
	
	public Repository getRepository(Integer host) {
		return (Repository) repositoryMap.get(host);
	}
	
	public void addRepository(Repository repository) {
		repositories.add(repository);
		repositoryMap.put(repository.getHost(), repository);
		
		if (defaultHost != null && repository.getHost().equals(defaultHost)) {
			setDefaultRepository(repository);
		}
		
		if (!repository.readOnly()) {
			for (int i = 0; i < repository.getWeight(); i++) {
				buckets.add(repository.getHost());
			}
		}
	}
	
	public void addImageType(String contentType) {
		imageTypes.add(contentType);
	}
	
	public void addZipType(String contentType) {
		zipTypes.add(contentType);
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	/**
	 * @return the imageTypes
	 */
	public Set getImageTypes() {
		return imageTypes;
	}
	/**
	 * @param imageTypes the imageTypes to set
	 */
	public void setImageTypes(Set imageTypes) {
		this.imageTypes = imageTypes;
	}
	/**
	 * @return the mailUploadApiKey
	 */
	public String getMailUploadApiKey() {
		return mailUploadApiKey;
	}
	/**
	 * @param mailUploadApiKey the mailUploadApiKey to set
	 */
	public void setMailUploadApiKey(String mailUploadApiKey) {
		this.mailUploadApiKey = mailUploadApiKey;
	}
	/**
	 * @return the repositories
	 */
	public Set getRepositories() {
		return repositories;
	}
	/**
	 * @param repositories the repositories to set
	 */
	public void setRepositories(Set repositories) {
		this.repositories = repositories;
	}
	/**
	 * @return the repository
	 */
	public Repository getDefaultRepository() {
		return defaultRepository;
	}
	/**
	 * @param repository the repository to set
	 */
	public void setDefaultRepository(Repository repository) {
		this.defaultRepository = repository;
	}
	/**
	 * @return the urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}
	/**
	 * @param urlPattern the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	/**
	 * @return the zipTypes
	 */
	public Set getZipTypes() {
		return zipTypes;
	}
	/**
	 * @param zipTypes the zipTypes to set
	 */
	public void setZipTypes(Set zipTypes) {
		this.zipTypes = zipTypes;
	}
	/**
	 * @return the flowmeterConfig
	 */
	public FlowmeterConfig getFlowmeterConfig() {
		return flowmeterConfig;
	}
	/**
	 * @param flowmeterConfig the flowmeterConfig to set
	 */
	public void setFlowmeterConfig(FlowmeterConfig flowmeterConfig) {
		this.flowmeterConfig = flowmeterConfig;
	}
	/**
	 * @return the progressConfig
	 */
	public ProgressConfig getProgressConfig() {
		return progressConfig;
	}
	/**
	 * @param progressConfig the progressConfig to set
	 */
	public void setProgressConfig(ProgressConfig progressConfig) {
		this.progressConfig = progressConfig;
	}
	/**
	 * @return the defaultHost
	 */
	public Integer getDefaultHost() {
		return defaultHost;
	}
	/**
	 * @param defaultHost the defaultHost to set
	 */
	public void setDefaultHost(Integer defaultHost) {
		this.defaultHost = defaultHost;
		
		Repository repo = getRepository(defaultHost);
		
		if (repo != null) {
			setDefaultRepository(repo);
		}
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the maxHeight
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * @param maxHeight the maxHeight to set
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	/**
	 * @return the maxLength
	 * @Deprecated by bill
	 */
	@Deprecated
	public long getMaxLength() {
		return maxLength;
	}

	
	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(long maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * @param maxWidth the maxWidth to set
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	/**
	 * @param ypfs the pnfs to set
	 */
	public void setPnfs(boolean pnfs) {
		this.pnfs = pnfs;
	}
	
	/**
	 * @return the pnfs
	 */
	public boolean isPnfs() {
		return pnfs;
	}
	
	public boolean usePNFS() {
		return pnfs;
	}


}

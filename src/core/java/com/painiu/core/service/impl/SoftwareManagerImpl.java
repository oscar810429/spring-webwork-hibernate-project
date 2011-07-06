/**
 * @(#)SoftwareManagerimpl.java 2010-5-17
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.Context;
import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.SoftwareDAO;
import com.painiu.core.dao.TagDAO;
import com.painiu.core.dao.SoftwareStatDAO;
import com.painiu.core.model.Category;
import com.painiu.core.model.Relation;
import com.painiu.core.model.Software;
import com.painiu.core.model.Software.State;
import com.painiu.core.model.SoftwareTag;
import com.painiu.core.model.User;
import com.painiu.core.model.Tag;
import com.painiu.core.search.Result;
import com.painiu.core.service.SoftwareManager;
import com.painiu.util.TagUtils;
import com.painiu.core.security.RelationAware;
import com.painiu.event.Event;
import com.painiu.event.EventListener;
import com.painiu.event.EventPublisher;
import com.painiu.event.EventPublisherAware;
import com.painiu.event.software.AddTagSoftwareEvent;
import com.painiu.event.software.AfterRemoveSoftwareEvent;
import com.painiu.event.software.BeforeRemoveSoftwareEvent;
import com.painiu.event.software.RemoveTagSoftwareEvent;
import com.painiu.event.software.SoftwareEvent;


/**
 * <p>
 * <a href="SoftwareManagerimpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareManagerImpl.java 133 2010-11-23 08:14:43Z zhangsf $
 */
@Transactional
public class SoftwareManagerImpl implements SoftwareManager,EventPublisherAware,EventListener,InitializingBean{

	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(SoftwareManagerImpl.class);

	//~ Instance fields ===================================================
	
	private SoftwareDAO softwareDAO;
	private TagDAO tagDAO;
	private SoftwareStatDAO softwareStatDAO;
	
	private EventPublisher eventPublisher;


	//~ Constructors ====================================================

	//~ Methods =======================================================
	  	  
    /**
	 * @param eventPublisher the eventPublisher to set
	 */
	@NonTransactional 
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}


	/**
	 * @param softwareStatDAO the softwareStatDAO to set
	 */
	@NonTransactional
	public void setSoftwareStatDAO(SoftwareStatDAO softwareStatDAO) {
		this.softwareStatDAO = softwareStatDAO;
	}
	

	/**
	 * @param softwareDAO the softwareDAO to set
	 */
	 @NonTransactional
	public void setSoftwareDAO(SoftwareDAO softwareDAO) {
		this.softwareDAO = softwareDAO;
	}


	/**
	 * @param tagDAO the tagDAO to set
	 */
	 @NonTransactional
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}


	  /**
	    * @see com.painiu.core.service.impl.BaseManager#afterPropertiesSet()
	    */
	   @NonTransactional
     public void afterPropertiesSet() throws Exception {
		Assert.notNull(softwareDAO);
		Assert.notNull(tagDAO);
		Assert.notNull(eventPublisher);
     }
	   
	 @NonTransactional
	 public void onEvent(Event event){
		if(event instanceof BeforeRemoveSoftwareEvent){
			
		}
	 }
	   

	 @Transactional(readOnly = true)
      public Software getSoftware(Software software){
	       return getSoftware(software.getId());
      }

	 @Transactional(readOnly = true)
      public Software getSoftware(String softwareId){
    	       return softwareDAO.getSoftware(softwareId);
      }
    
    /**
     * @see com.painiu.core.service.SoftwareManager#removeSoftware(com.painiu.core.model.Software)
     */
	
	  public void removeSoftware(Software software){
		  softwareDAO.removeSoftware(software);
	  }
	
	/**
	 * @see com.painiu.core.service.SoftwareManager#saveSoftware(com.painiu.core.model.Software)
	 */
	
	   public void saveSoftware(Software software){
		if (log.isDebugEnabled()) {
			log.debug("Saving " + software);
		}
		 softwareDAO.saveSoftware(software);
	    }
	   
	   
	   /** 
	    * @see com.painiu.core.service.SoftwareManager#getMostViews(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	    */
         
	    @Transactional(readOnly = true)
	    public Result getMostViews(User user, int start, int limit, Relation relation){
	    	      return softwareDAO.getMostViews(user, start, limit, relation);
	    }
	    
	    /** 
	     * @see com.painiu.core.service.SoftwareManager#getMostFavorites(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	     */
		
	    @Transactional(readOnly = true)
		public Result getMostFavorites(User user, int start, int limit, Relation relation){
			 return softwareDAO.getMostFavorites(user, start, limit, relation);
		}
	    
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getMostComments(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	     */
		
	    @Transactional(readOnly = true)
		public Result getMostComments(User user, int start, int limit, Relation relation){
             return softwareDAO.getMostComments(user, start, limit, relation);
		}
	    
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getMostInteresting(java.util.Date, java.util.Date, int, int, int)
	     */
		
	    @Transactional(readOnly = true)
		public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit){
			return softwareDAO.getMostInteresting(fromDate, toDate, interests, start, limit);
		}
	    
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getMostInteresting(com.painiu.core.model.User, int, int, com.painiu.core.model.Relation)
	     */
		
	    @Transactional(readOnly = true)
		public Result getMostInteresting(User user, int start, int limit, Relation relation){
			return softwareDAO.getMostInteresting(user, start, limit, relation);
		}
		
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getRecentPublicSoftwares(int, int)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getRecentPublicSoftwares(int start, int limit){
			return softwareDAO.getRecentPublicSoftwares(start, limit);
		}
		
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getUserSoftwares(com.painiu.core.model.User, boolean, int, int, com.painiu.core.model.Relation)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getUserSoftwares(User user, boolean orderByTaken, int start, int limit, Relation relation){
			return softwareDAO.getUserSoftwares(user, orderByTaken, start, limit, relation);
		}

	    /** 
	     * @see com.painiu.core.service.SoftwareManager#getTaggedSoftwares(java.lang.String, int, int)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getTaggedSoftwares(String tagName, int start, int limit){
			return softwareDAO.getTaggedSoftwares(tagName, start, limit);
		}
		
	    
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getUserTaggedSoftwares(com.painiu.core.model.User, java.lang.String, int, int, com.painiu.core.model.Relation)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getUserTaggedSoftwares(User user, String tagName, int start, int limit, Relation relation){
			return softwareDAO.getUserTaggedSoftwares(user, tagName, start, limit, relation);
		}
		
	    
	    /** 
	     * @see com.painiu.core.service.SoftwareManager#getCommentedSoftwares(com.painiu.core.model.User, int, int)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getCommentedSoftwares(User user, int start, int limit){
			return softwareDAO.getCommentedSoftwares(user, start, limit);
		}
		
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getSoftwares(com.painiu.core.model.User, java.lang.String[], boolean, java.lang.String, int, int, com.painiu.core.model.Relation)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getSoftwares(User user,Category category, String[] tags, boolean taggedAll, String text, int start, int limit, Relation relation){
			return softwareDAO.getSoftwares(user,category, tags, taggedAll, text, start, limit, relation);
		}
		
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getSoftwaresPostedAt(java.util.Date, java.util.Date, int, int)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit){
			return softwareDAO.getSoftwaresPostedAt(from, to, start, limit);
		}

	    /**
	     * @see com.painiu.core.service.SoftwareManager#getSoftwaresPostedAt(java.util.Date, java.util.Date, int, int, boolean, boolean)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit, boolean descOrder, boolean update){
			return softwareDAO.getSoftwaresPostedAt(from, to, start, limit, descOrder, update);
		}

	    /**
	     * @see com.painiu.core.service.SoftwareManager#getSoftwaresPostedAt(java.util.Date, java.util.Date, int, int, java.util.List)
	     */
	    
	    @Transactional(readOnly = true)
		public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit, List photoStates){
			return softwareDAO.getSoftwaresPostedAt(from, to, start, limit, photoStates);
		}
		
	    /**
	     * @see com.painiu.core.service.SoftwareManager#getUserSoftwaresPostedAt(com.painiu.core.model.User, java.util.Date, java.util.Date, int, int, boolean, com.painiu.core.model.Relation)
	     */
	    @Transactional(readOnly = true)
		public Result getUserSoftwaresPostedAt(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation){
			return softwareDAO.getUserSoftwaresPostedAt(user, from, to, start, limit, detail, relation);
		}
	   
	   
		public void replaceSoftwareTag(SoftwareTag softwareTag, String newTags) {
			if (log.isDebugEnabled()) {
				log.debug("replacing tags of Photo[" + softwareTag.getSoftware().getId() + "], oldTag: " + softwareTag.getTagName() + ", newTags: " + newTags);
			}
			
			doRemoveSoftwareTag(softwareTag);
			
			String[] tags = TagUtils.parseTags(newTags);
			
			addSoftwareTags(softwareTag.getSoftware(), tags);
		}

		
		public void removeSoftwareTag(SoftwareTag softwareTag) {
			if (log.isDebugEnabled()) {
				log.debug("removing " + softwareTag);
			}
			
			doRemoveSoftwareTag(softwareTag);
		}

	   
	   
		@RelationAware
		public void addSoftwareTags(Software software, String rawTags) {
			if (log.isDebugEnabled()) {
				log.debug("adding tags for Software[" + software.getId() + "], rawtags: " + rawTags);
			}
			
			String[] tags = TagUtils.parseTags(rawTags);
			
			addSoftwareTags(software, tags);
		}
		
		
		private boolean addSoftwareTags(Software software, String[] tags) {
			boolean added = false;
			for (int i = 0; i < tags.length; i++) {
				if (addSoftwareTag(software, tags[i])) {
					added = true;
				}
			}
			return added;
		}
		
		
		private boolean addSoftwareTag(Software software, String tagName) {
			if (log.isDebugEnabled()) {
				log.debug("add tag[" + tagName + "] to photo[" + software.getId() + "]");
			}
			
			if (software.containsTag(tagName)) {
				if (log.isDebugEnabled()) {
					log.debug(tagName + " already exists.");
				}
				return false;
			}

			User currentUser = Context.getContext().getCurrentUser();
			
			if (currentUser == null) {
				return false;
			}
			
			Tag tag = tagDAO.getTag(tagName);
			
			if (tag == null) {
				tag = new Tag(tagName);
			}

			SoftwareTag softwareTag = new SoftwareTag(software, tag, currentUser);
			
			software.addSoftwareTag(softwareTag);
			
			tag.increase();
			
			try {
				tagDAO.saveTag(tag);
				tagDAO.saveSoftwareTag(softwareTag);
			} catch (DataAccessException e) {
				log.error("DataAccessException, may be caused by concurrent opertion, it's ok, just egnore it: ", e);
				return false;
			}
			
			publishAddTagSoftwareEvent(software, tag, currentUser);
			
			return true;
		}
		
		@SuppressWarnings("unchecked")
		private boolean updateSoftwareTags(Software software, String rawTags) {
			boolean removed = false;
			
			String[] tags = TagUtils.parseTags(rawTags);
			
			Set tagNames = new HashSet();
			Collections.addAll(tagNames, (Object[]) tags);
			
			Set oldTags = software.getSoftwareTags();
			List removeList = new ArrayList(oldTags.size());
			
			for (Iterator i = oldTags.iterator(); i.hasNext();) {
				SoftwareTag photoTag = (SoftwareTag) i.next();
				if (!tagNames.contains(photoTag.getTagName())) {
					removeList.add(photoTag);
				}
			}
			
			if (removeList.size() > 0) {
				for (Iterator i = removeList.iterator(); i.hasNext();) {
					SoftwareTag photoTag = (SoftwareTag ) i.next();
					removed = doRemoveSoftwareTag(photoTag);
				}
			}
			
			try {
				addSoftwareTags(software, TagUtils.toString(tagNames));
				return true;
			} catch (Exception ex) {
				// TODO
			}
			return removed;
		}
		
		
		@SuppressWarnings("unchecked")
		private void removeSoftwareTags(Collection photoTags) {
			List list = new ArrayList(photoTags);
			
			for (Iterator i = list.iterator(); i.hasNext();) {
				SoftwareTag photoTag = (SoftwareTag) i.next();
				doRemoveSoftwareTag(photoTag);
			}
		}
		
		private boolean doRemoveSoftwareTag(SoftwareTag softwareTag) {
			if (log.isDebugEnabled()) {
				log.debug("removing " + softwareTag);
			}

			Software software = softwareTag.getSoftware();
			
			Tag tag = softwareTag.getTag();
			tag.decrease();
		
			software.removeSoftwareTag(softwareTag);
			tagDAO.removeSoftwareTag(softwareTag);
		
			if (tag.getTimes().intValue() == 0) {
				tagDAO.removeTag(tag);
			} else {
				tagDAO.saveTag(tag);
			}
			
			publishRemoveTagSoftwareEvent(software, tag, Context.getContext().getCurrentUser());
			
			return true;
		}

		public void setSoftwaresState(User user, Software.State state) {
			softwareDAO.setSoftwaresState(user, state);
		}

		public void setSoftwareState(Software software, State state) {
			if (state != null) {
				software.setState(state);
				softwareDAO.saveSoftware(software);
			}
		}

		
		/**********************************************************************
		 * Events
		 **********************************************************************/

		private void publishBeforeRemoveEvent(Software software, User user) {
		    SoftwareEvent event = new BeforeRemoveSoftwareEvent(software, user);
			eventPublisher.publishEvent(event);
		}
		
		private void publishAfterRemoveEvent(Software software, User user) {
			SoftwareEvent event = new AfterRemoveSoftwareEvent(software, user);
			eventPublisher.publishEvent(event);
		}
		
		private void publishAddTagSoftwareEvent(Software software, Tag tag, User user) {
			AddTagSoftwareEvent event = new AddTagSoftwareEvent(software, tag.getName(), user);
			eventPublisher.publishEvent(event);
		}
		
		private void publishRemoveTagSoftwareEvent(Software software, Tag tag, User user) {
			AddTagSoftwareEvent event = new RemoveTagSoftwareEvent(software, tag.getName(), user);
			eventPublisher.publishEvent(event);
		}



	

	//~ Accessors ======================================================

}

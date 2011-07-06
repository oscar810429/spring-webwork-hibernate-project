/**
 * @(#)PhotoManagerImpl.java 2010-3-11
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
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.Context;
import com.painiu.Painiu;
import com.painiu.core.aop.transaction.NonTransactional;
//import com.painiu.core.dao.FavoritePhotoDAO;
//import com.painiu.core.dao.PhotoCustomSizeDAO;
import com.painiu.core.dao.PhotoDAO;
//import com.painiu.core.dao.TagDAO;
//import com.painiu.core.event.Event;
//import com.painiu.core.event.EventListener;
//import com.painiu.core.event.EventPublisher;
//import com.painiu.core.event.EventPublisherAware;
//import com.painiu.core.event.photo.AddTagPhotoEvent;
//import com.painiu.core.event.photo.AfterRemovePhotoEvent;
//import com.painiu.core.event.photo.BeforeRemovePhotoEvent;
//import com.painiu.core.event.photo.PhotoEvent;
//import com.painiu.core.event.photo.RemoveTagPhotoEvent;
import com.painiu.core.service.PhotoManager;
//import com.painiu.core.model.Album;
//import com.painiu.core.model.AlbumShare;
//import com.painiu.core.model.Group;
//import com.painiu.core.model.InterestingPhoto;
import com.painiu.core.model.License;
import com.painiu.core.model.Photo;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.Tag;
import com.painiu.core.model.User;
import com.painiu.core.model.Photo.State;
import com.painiu.core.search.Result;
import com.painiu.core.security.RelationAware;
import com.painiu.core.security.RelationContextHolder;
//import com.painiu.core.util.TagUtils;
//import com.painiu.core.ypfs.YPFS;

/**
 * <p>
 * <a href="PhotoManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoManagerImpl.java 50 2010-06-14 15:02:09Z zhangsf $
 */
@Transactional
public class PhotoManagerImpl implements PhotoManager{
	
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(PhotoManagerImpl.class);
	
	//~ Instance fields ========================================================

	private PhotoDAO photoDAO;
	
	//private EventPublisher eventPublisher;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.yupoo.event.EventPublisherAware#setEventPublisher(com.yupoo.event.EventPublisher)
	 */
	//@NonTransactional
	//public void setEventPublisher(EventPublisher publisher) {
	//	this.eventPublisher = publisher;
	//}

	/**
	 * @param photoCustomSizeDAO the photoCustomSizeDAO to set
	 */
	//@NonTransactional
	//public void setPhotoCustomSizeDAO(PhotoCustomSizeDAO photoCustomSizeDAO) {
	//	this.photoCustomSizeDAO = photoCustomSizeDAO;
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoDAO(com.yupoo.dao.PhotoDAO)
	 */
	@NonTransactional
	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}
	
	/******************************************************************
	 * Business methods
	 ******************************************************************/

	/*
	 * @see com.painiu.core.service.PhotoManager#blockUserPhotos(com.yupoo.model.User)
	 */
	public void blockUserPhotos(User user) {
		photoDAO.blockUserPhotos(user);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getRecentPublicPhotos(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getRecentPublicPhotos(int start, int limit) {
		return photoDAO.getRecentPublicPhotos(start, limit);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getUserPhotos(com.yupoo.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getUserPhotos(User user, int start, int limit) {
		return getUserPhotos(user, false, start, limit);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getUserPhotos(com.yupoo.model.User, boolean, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getUserPhotos(User user, boolean orderByTaken, int start, int limit) {
		return photoDAO.getUserPhotos(user, orderByTaken, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getUserPhotos(com.yupoo.model.User, boolean, int, int, com.yupoo.model.Relation)
	 */
	@Transactional(readOnly=true)
	public Result getUserPhotos(User user, boolean orderByTaken, int start, int limit, Relation relation) {
		return photoDAO.getUserPhotos(user, orderByTaken, start, limit, relation);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getTaggedPhotos(java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTaggedPhotos(String tagName, int start, int limit) {
		return photoDAO.getTaggedPhotos(tagName, start, limit);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getUserTaggedPhotos(com.yupoo.model.User, java.lang.String, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getUserTaggedPhotos(User user, String tagName, int start, int limit) {
		return photoDAO.getUserTaggedPhotos(user, tagName, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getPhotos(com.yupoo.model.User, java.lang.String[], boolean, java.lang.String, int, int, com.yupoo.model.Relation)
	 * watch out. 
	 */
	@Transactional(readOnly=true)
	/*public Result getPhotos(User user, Group group, String[] tags, boolean taggedAll, String text, int start, int limit, Relation relation) {
		return photoDAO.getPhotos(user, group, tags, taggedAll, text, start, limit, relation);
	}*/
	public Result getPhotos(User user, String[] tags, boolean taggedAll, String text, int start, int limit, Relation relation) {
		return photoDAO.getPhotos(user, tags, taggedAll, text, start, limit, relation);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getUserPhotosPostedAt(com.yupoo.model.User, java.util.Date, java.util.Date, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getUserPhotosPostedAt(User user, Date from, Date to, int start, int limit, boolean detail) {
		return photoDAO.getUserPhotosPostedAt(user, from, to, start, limit, detail, 
				RelationContextHolder.getContext().getRelation());
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getPhotosPostedAt(java.util.Date, java.util.Date, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit) {
		return photoDAO.getPhotosPostedAt(from, to, start, limit);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getPhotosPostedAt(java.util.Date, java.util.Date, int, int, boolean)
	 */
	@Transactional(readOnly=true)
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, boolean descOrder) {
		return photoDAO.getPhotosPostedAt(from, to, start, limit, descOrder, false);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getPhotosPostedAt(java.util.Date, java.util.Date, int, int, com.yupoo.model.User.UserRank)
	 */
	@Transactional(readOnly=true)
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, List photoStates) {
		return photoDAO.getPhotosPostedAt(from, to, start, limit, photoStates);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getUserPhotosTakenOn(com.yupoo.model.User, java.util.Date, java.util.Date, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getUserPhotosTakenOn(User user, Date from, Date to, int start, int limit, boolean detail) {
		return photoDAO.getUserPhotosTakenOn(user, from, to, start, limit, detail,
				RelationContextHolder.getContext().getRelation());
	}

	/*
     * @see com.painiu.core.service.PhotoManager#getCommentedPhotos(com.yupoo.model.User, int, int)
     */
	@Transactional(readOnly=true)
    public Result getCommentedPhotos(User user, int start, int limit) {
        return photoDAO.getCommentedPhotos(user, start, limit);
    }

	/*
	 * @see com.painiu.core.service.PhotoManager#getMostComments(com.yupoo.model.User, int, int)
	 */
    @RelationAware
    @Transactional(readOnly=true)
	public Result getMostComments(User user, int start, int limit) {
		return photoDAO.getMostComments(user, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getMostFavorites(com.yupoo.model.User, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getMostFavorites(User user, int start, int limit) {
		return photoDAO.getMostFavorites(user, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getMostInteresting(java.util.Date, java.util.Date, int, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit) {
		return photoDAO.getMostInteresting(fromDate, toDate, interests, start, limit);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getMostInteresting(com.yupoo.model.User, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getMostInteresting(User user, int start, int limit) {
		return photoDAO.getMostInteresting(user, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}
	
	@RelationAware
	@Transactional(readOnly=true)
	public Result getMostPopular(User user, int start, int limit) {
		return photoDAO.getMostPopular(user, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getMostViews(com.yupoo.model.User, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getMostViews(User user, int start, int limit) {
		return photoDAO.getMostViews(user, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getInterestingnessPhotoIdsGroupByDate(java.util.Date, java.util.Date, int)
	 */
	@Transactional(readOnly=true)
	public List getInterestingnessPhotoIdsGroupByDate(Date fromDate, Date toDate, int limit) {
		return photoDAO.getInterestingnessPhotoIdsGroupByDate(fromDate, toDate, limit);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getInterestingnessPhotos(java.util.Date, java.util.Date, int, int)
	 */
	//@Transactional(readOnly=true)
	//public Result getInterestingnessPhotos(Date fromDate, Date toDate, int start, int limit) {
	//	return photoDAO.getInterestingnessPhotos(fromDate, toDate, start, limit);
	//}
	
	/*
	 * 随机获取
	 * @see com.painiu.core.service.PhotoManager#getInterestingnessPhotos(java.util.Date, java.util.Date, int)
	 */
	//@Transactional(readOnly=true)
	//public List getInterestingnessPhotos(Date fromDate, Date toDate, int limit) {
	//	return photoDAO.getInterestingnessPhotos(fromDate, toDate, limit);
	//}
	
	//@Transactional(readOnly=true)
	//public Result getInterestingnessPhotos(int start, int limit) {
	//	return getInterestingnessPhotos(new Date(Yupoo.YUPOO_TIME), new Date(), start, limit);
	//}
	
	/* (non-Javadoc)
	 * @see com.painiu.core.service.PhotoManager#getInterestingnessPhotosNoMore(java.util.Date, java.util.Date, java.lang.String, int)
	 */
//	public List getInterestingnessPhotosNoMore(Date fromDate, Date toDate, String orderBy, int limit) {
//		return convertPhotoList(photoDAO.getInterestingnessPhotosNoMore(fromDate, toDate, orderBy, limit));
//	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getInterestingPhoto(com.yupoo.model.Photo)
	 */
	//@Transactional(readOnly=true)
	//public InterestingPhoto getInterestingPhoto(Photo photo) {
	//	return photoDAO.getInterestingPhoto(photo);
	//}
	
	//private void removeInterestingPhoto(Photo photo) {
	//	InterestingPhoto interestingPhoto = photoDAO.getInterestingPhoto(photo);
	//	if (interestingPhoto != null) {
	//		photoDAO.removeInterestingPhoto(interestingPhoto);
	//	}
	//}

	/*
	 * @see com.painiu.core.service.PhotoManager#getContactsPhotos(com.yupoo.model.User, java.util.Date, java.util.Date, int, int)
	 */
	//@Transactional(readOnly=true)
	//public List getContactsPhotos(User user, Date fromDate, Date toDate, int start, int limit) {
	//	return photoDAO.getContactsPhotos(user, fromDate, toDate, start, limit);
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getContactsLatestPhotos(com.yupoo.model.User, int)
	 */
	//@Transactional(readOnly=true)
	//public List getContactsLatestPhotos(User user, int limit) {
	//	return photoDAO.getContactsLatestPhotos(user, limit);
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getContactsLatestPhotos(com.yupoo.model.User, int, int)
	 */
	//@Transactional(readOnly=true)
	//public Result getContactsLatestPhotos(User user, int start, int limit) {
	//	return photoDAO.getContactsLatestPhotos(user, start, limit);
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getContactsLatestPhoto(com.yupoo.model.User, int)
	 */
	//@Transactional(readOnly=true)
	//public List getContactsLatestPhoto(User user, int limit) {
	//	return photoDAO.getContactsLatestPhoto(user, limit);
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getContactsLatestPhoto(com.yupoo.model.User, int, int)
	 */
	//@Transactional(readOnly=true)
	//public Result getContactsLatestPhoto(User user, int start, int limit) {
	//	return photoDAO.getContactsLatestPhoto(user, start, limit);
	//}
	
	@Transactional(readOnly=true)
	public List getCameraList() {
		return photoDAO.getCameraList();
	}
	
	@Transactional(readOnly=true)
	public Result getLicensePhotos(License license, int start, int limit, Date from, Date to) {
		return photoDAO.getLicensePhotos(license, start, limit, from, to);
	}
	
	@Transactional(readOnly=true)
	public Result getTaggedLicensePhotos(String tagName, License license, int start, int limit, Date from, Date to) {
		return photoDAO.getTaggedLicensePhotos(tagName, license, start, limit, from, to);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#getNotInAlbum(com.yupoo.model.User, java.util.Date, java.util.Date, java.util.Date, java.util.Date, int, int)
	 */
	@RelationAware
	@Transactional(readOnly=true)
	public Result getNotInAlbum(User user, Date minPostedDate, Date maxPostedDate, Date minTakenDate, Date maxTakenDate, int start, int limit) {
		return photoDAO.getNotInAlbum(user, minPostedDate, maxPostedDate, minTakenDate, maxTakenDate, start, limit, 
				RelationContextHolder.getContext().getRelation());
	}
	
	/**********************************************************************
	 * Events
	 **********************************************************************/

	/*private void publishBeforeRemoveEvent(Photo photo, User user) {
		PhotoEvent event = new BeforeRemovePhotoEvent(photo, user);
		eventPublisher.publishEvent(event);
	}
	
	private void publishAfterRemoveEvent(Photo photo, User user) {
		PhotoEvent event = new AfterRemovePhotoEvent(photo, user);
		eventPublisher.publishEvent(event);
	}
	
	private void publishAddTagPhotoEvent(Photo photo, Tag tag, User user) {
		AddTagPhotoEvent event = new AddTagPhotoEvent(photo, tag.getName(), user);
		eventPublisher.publishEvent(event);
	}
	
	private void publishRemoveTagPhotoEvent(Photo photo, Tag tag, User user) {
		AddTagPhotoEvent event = new RemoveTagPhotoEvent(photo, tag.getName(), user);
		eventPublisher.publishEvent(event);
	}*/
	
	/* (non-Javadoc)
	 * @see com.yupoo.event.EventListener#onEvent(com.yupoo.event.Event)
	 */
	//@NonTransactional
	//public void onEvent(Event event) {
	//	if (event instanceof BeforeRemovePhotoEvent) {
	//		this.removeInterestingPhoto(((PhotoEvent) event).getPhoto());
	//	}
	//}
	
	
	/*********************************************************************************************************
	 * 明确逻辑区分，以便事件/安全处理
	 *********************************************************************************************************/
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getPhoto(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Photo getPhoto(String id) {
		return photoDAO.getPhoto(id);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getPhoto(java.lang.String, com.yupoo.model.Relation)
	 */
	@Transactional(readOnly=true)
	public Photo getPhoto(String id, Relation relation) {
		return photoDAO.getPhoto(id);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#getPhoto(java.lang.String, java.lang.String)
	 */
	//@Transactional(readOnly=true)
	//public Photo getPhoto(String id, AlbumShare albumShare) {
	//	return photoDAO.getPhoto(id);
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#createPhoto(com.yupoo.model.Photo)
	 */
	public void createPhoto(Photo photo) {
		if (log.isDebugEnabled()) {
			log.debug("Creating new Photo[title:" + photo.getTitle() + ";user:" + 
					photo.getUser().getUsername() + "]");
		}
		
		photoDAO.savePhoto(photo);

		//addPhotoTags(photo, photo.getRawTags());
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#deletePhoto(com.yupoo.model.Photo)
	 */
	public void deletePhoto(Photo photo) {
		User user = Context.getContext().getCurrentUser();
		
        if (log.isDebugEnabled()) {
            log.debug(user + " deleting " + photo);
        }
        
        // remove tags.
        Set photoTags = photo.getPhotoTags();
        
        //removePhotoTags(photoTags);
        
        // remove comments. this will be done by cascade deleting	
        // remove notes. this will be done by cascade deleting
        // remove favorites. this will be done by cascade deleting
        
        // remove photo from albums which it belongs to. 这里不用cascading，是因为
        // 删除Album的Photo要对AlbumStat作相应修改
        // albumManager.removePhoto(photo);
        
        // remove photo from groups which it belongs to.这里不用cascading，是因为
        // 删除Group的Photo要对GroupStat作相应修改
        // groupManager.removePhoto(photo);
       //publishBeforeRemoveEvent(photo, user);
        
        // remove photo itself.
		photoDAO.removePhoto(photo);

		//publishAfterRemoveEvent(photo, user);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoLicense(com.yupoo.model.Photo, com.yupoo.model.License)
	 */
	public void setPhotoLicense(Photo photo, License license) {
		if (log.isDebugEnabled()) {
			log.debug("setting license for Photo[" + photo.getId() + "], value: " + license);
		}
		photo.setLicense(license);
		photoDAO.savePhoto(photo);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoMeta(com.yupoo.model.Photo, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setPhotoMeta(Photo photo, String title, String description, String rawTags) {
		if (log.isDebugEnabled()) {
			log.debug("setting metadata for Photo[" + photo.getId() + "], title: " + title + ", description: " + description + ", rawtags: " + rawTags);
		}
		
		if (title != null) {
			photo.setTitle(title);
		}
		
		if (description != null) {
			photo.setDescription(description);
		}
		
		/*if (rawTags != null) {
			if (updatePhotoTags(photo, rawTags)) {
				photo.setRawTags(photo.getTagsAsString());
			}
		}*/
		
		photoDAO.savePhoto(photo);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoCreative(com.yupoo.model.Photo, java.lang.Integer)
	 */
	public void setPhotoCreative(Photo photo, Integer creative) {
		if (log.isDebugEnabled()) {
			log.debug("setting creative type for Photo[" + photo.getId() + "], value: " + creative);
		}
		photo.setCreativeType(creative);
		photoDAO.savePhoto(photo);
	}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoPerms(com.yupoo.model.Photo, com.yupoo.model.Privacy, com.yupoo.model.PhotoPermission)
	 */
	/*public void setPhotoPerms(Photo photo, Privacy privacy, PhotoPermission perms) {
		if (log.isDebugEnabled()) {
			log.debug("setting permissions for Photo[" + photo.getId() + "], privacy: " + privacy + ", perms: " + perms);
		}
		if (privacy != null && !photo.getPrivacy().equals(privacy)) {
			photo.setPrivacy(privacy);
			
			if (!photo.getPrivacy().equals(Privacy.EVERYONE) && photo.getStat().getFavorites().intValue() > 0) {
				//photo.getStat().setFavorites(new Integer(0));
				//favoritePhotoDAO.removeFavoritePhotos(photo);
			}
			
			if (!photo.getPrivacy().equals(Privacy.EVERYONE) && photo.getStat().getScore() > 0) {
				//removeInterestingPhoto(photo);
			}
		}
		if (perms != null) {
			PhotoPermission perm = photo.getPermission();
			perm.setDownload(perms.getDownload());
			perm.setComment(perms.getComment());
			perm.setNote(perms.getNote());
			perm.setTag(perms.getTag());
			perm.setBlog(perms.getBlog());
			perm.setExif(perms.getExif());
		}
		
		photoDAO.savePhoto(photo);
	}*/
	
	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoScore(com.yupoo.model.Photo, java.lang.Integer)
	 */
	/*public void setPhotoScore(Photo photo, Integer score) {
		PhotoStat stat = photo.getStat();
		
		if (stat.getScore().intValue() == score.intValue()) {
			return;
		}
		
		stat.setScore(score);
		photoStatDAO.savePhotoStat(stat);*/
		
		//InterestingPhoto interestingPhoto = getInterestingPhoto(photo);
		
		/*if (score.intValue() > 0 && interestingPhoto == null) {
			interestingPhoto = new InterestingPhoto();
			interestingPhoto.setPhoto(photo);
			interestingPhoto.setStat(stat);
			interestingPhoto.setPresenter(Context.getContext().getCurrentUser());
			interestingPhoto.setCreatedDate(photo.getPostedDate());

			photoDAO.saveInterestingPhoto(interestingPhoto);
		} else if (score.intValue() <= 0 && interestingPhoto != null) {
			photoDAO.removeInterestingPhoto(interestingPhoto);
		}*/
	//}
	
	/*
	 * @see com.painiu.core.service.PhotoManager#increasePhotoViews(com.yupoo.model.Photo)
	 */
	/*public void increasePhotoViews(Photo photo) {
		photo.getStat().increaseViews();
		photoStatDAO.savePhotoStat(photo.getStat());
	}*/
	
	/*
	 * @see com.painiu.core.service.PhotoManager#addPhotoTags(com.yupoo.model.Photo, java.lang.String)
	 */
	/*@RelationAware
	public void addPhotoTags(Photo photo, String rawTags) {
		if (log.isDebugEnabled()) {
			log.debug("adding tags for Photo[" + photo.getId() + "], rawtags: " + rawTags);
		}
		
		//String[] tags = TagUtils.parseTags(rawTags);
		
		//addPhotoTags(photo, tags);
	}*/
	/**
	 *
	 * @param photo
	 * @param rawTags
	 */
	/*@RelationAware
	public void addAlbumPhotoTags(Photo photo, String rawTags) {
		//	todo: batch update album photo tags
		if (log.isDebugEnabled()) {
			log.debug("adding tags for Album Photo[" + photo.getId() + "], rawtags: " + rawTags);
		}
		
		//String[] tags = TagUtils.parseTags(rawTags);
		
		//addPhotoTags(photo, tags);
	}*/
	
	/*
	 * @see com.painiu.core.service.PhotoManager#removePhotoTag(com.yupoo.model.PhotoTag)
	 */
	/*public void removePhotoTag(PhotoTag photoTag) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + photoTag);
		}
		
		doRemovePhotoTag(photoTag);
	}
	 */
	
	/*
	 * @see com.painiu.core.service.PhotoManager#replacePhotoTag(com.yupoo.model.PhotoTag, java.lang.String)
	 */
	/*public void replacePhotoTag(PhotoTag photoTag, String newTags) {
		if (log.isDebugEnabled()) {
			log.debug("replacing tags of Photo[" + photoTag.getPhoto().getId() + "], oldTag: " + photoTag.getTagName() + ", newTags: " + newTags);
		}
		
		doRemovePhotoTag(photoTag);
		
		String[] tags = TagUtils.parseTags(newTags);
		
		addPhotoTags(photoTag.getPhoto(), tags);
	}*/
	
	/*private boolean addPhotoTags(Photo photo, String[] tags) {
		boolean added = false;
		for (int i = 0; i < tags.length; i++) {
			if (addPhotoTag(photo, tags[i])) {
				added = true;
			}
		}
		return added;
	}
	
	private boolean addPhotoTag(Photo photo, String tagName) {
		if (log.isDebugEnabled()) {
			log.debug("add tag[" + tagName + "] to photo[" + photo.getId() + "]");
		}
		
		//if (photo.containsTag(tagName)) {
		//	if (log.isDebugEnabled()) {
		//		log.debug(tagName + " already exists.");
		//	}
		//	return false;
		//}

		User currentUser = Context.getContext().getCurrentUser();
		
		if (currentUser == null) {
			return false;
		}
		
		//Tag tag = tagDAO.getTag(tagName);
		
		//if (tag == null) {
		//	tag = new Tag(tagName);
		//}

		//PhotoTag photoTag = new PhotoTag(photo, tag, currentUser);
		
		//photo.addPhotoTag(photoTag);
		
		//tag.increase();
		
		try {
			tagDAO.saveTag(tag);
			tagDAO.savePhotoTag(photoTag);
		} catch (DataAccessException e) {
			log.error("DataAccessException, may be caused by concurrent opertion, it's ok, just egnore it: ", e);
			return false;
		}
		
		publishAddTagPhotoEvent(photo, tag, currentUser);
		
		return true;
	}
	*/
	
	/*@SuppressWarnings("unchecked")
	private boolean updatePhotoTags(Photo photo, String rawTags) {
		boolean removed = false;
		
		String[] tags = TagUtils.parseTags(rawTags);
		
		Set tagNames = new HashSet();
		Collections.addAll(tagNames, (Object[]) tags);
		
		Set oldTags = photo.getPhotoTags();
		List removeList = new ArrayList(oldTags.size());
		
		for (Iterator i = oldTags.iterator(); i.hasNext();) {
			PhotoTag photoTag = (PhotoTag) i.next();
			if (!tagNames.contains(photoTag.getTagName())) {
				removeList.add(photoTag);
			}
		}
		
		if (removeList.size() > 0) {
			for (Iterator i = removeList.iterator(); i.hasNext();) {
				PhotoTag photoTag = (PhotoTag ) i.next();
				removed = doRemovePhotoTag(photoTag);
			}
		}
		
		try {
			addPhotoTags(photo, TagUtils.toString(tagNames));
			return true;
		} catch (Exception ex) {
			// TODO
		}
		return removed;
	}
	
	
	@SuppressWarnings("unchecked")
	private void removePhotoTags(Collection photoTags) {
		List list = new ArrayList(photoTags);
		
		for (Iterator i = list.iterator(); i.hasNext();) {
			PhotoTag photoTag = (PhotoTag) i.next();
			doRemovePhotoTag(photoTag);
		}
	}
	
	private boolean doRemovePhotoTag(PhotoTag photoTag) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + photoTag);
		}

		Photo photo = photoTag.getPhoto();
		
		Tag tag = photoTag.getTag();
		tag.decrease();
	
		photo.removePhotoTag(photoTag);
		tagDAO.removePhotoTag(photoTag);
	
		if (tag.getTimes().intValue() == 0) {
			tagDAO.removeTag(tag);
		} else {
			tagDAO.saveTag(tag);
		}
		
		publishRemoveTagPhotoEvent(photo, tag, Context.getContext().getCurrentUser());
		
		return true;
	}*/

	/* (non-Javadoc)
	 * @see com.painiu.core.service.PhotoManager#getUserPhotoTotal(com.yupoo.model.User)
	 */
	@Transactional(readOnly=true)
	public Integer getUserPhotoTotal(User user) {
		return photoDAO.getUserPhotoTotal(user);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#updatePhotosState(com.yupoo.model.User)
	 */
	public void setPhotosState(User user, Photo.State state) {
		photoDAO.setPhotosState(user, state);
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#setPhotoState(com.yupoo.model.Photo, com.yupoo.model.Photo.State)
	 */
	public void setPhotoState(Photo photo, State state) {
		if (state != null) {
			photo.setState(state);
			photoDAO.savePhoto(photo);
		}
	}

	/*
	 * @see com.painiu.core.service.PhotoManager#deletePhotoCustomSize(com.yupoo.model.PhotoCustomSize)
	 */
	//public void deletePhotoCustomSize(PhotoCustomSize photoCustomSize) {
	//	photoCustomSizeDAO.deletePhotoCustomSize(photoCustomSize);
	//}

	/*
	 * @see com.painiu.core.service.PhotoManager#getPhotoCustomSize(java.lang.String)
	 */
	//@Transactional(readOnly=true)
	//public PhotoCustomSize getPhotoCustomSize(String id) {
	//	return photoCustomSizeDAO.getPhotoCustomSize(id);
	//}

	/*
	 * @see com.painiu.core.service.PhotoManager#savePhotoCustomSize(com.yupoo.model.PhotoCustomSize)
	 */
	/*public void savePhotoCustomSize(PhotoCustomSize photoCustomSize) {
		int max = photoCustomSize.getWidth();
		if (max < photoCustomSize.getHeight()) {
			max = photoCustomSize.getHeight();
		}
		YPFS.setPhotoCustomSize(photoCustomSize.getPhoto().getAddress().getUsername(), 
				photoCustomSize.getPhoto().getAddress().getFileKey(), max);
		photoCustomSizeDAO.savePhotoCustomSize(photoCustomSize);
	}*/

	/* (non-Javadoc)
	 * @see com.painiu.core.service.PhotoManager#getInterestingnessPhotos(java.util.Date, java.util.Date)
	 */
	//@Transactional(readOnly=true)
	//public int getInterestingnessPhotos(Date fromDate, Date toDate) {
	//	return photoDAO.getInterestingnessPhotos(fromDate, toDate);
	//}



}

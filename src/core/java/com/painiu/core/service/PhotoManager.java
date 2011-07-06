/**
 * @(#)PhotoManager.java 2010-3-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;
import java.util.List;

//import com.painiu.core.dao.PhotoCustomSizeDAO;
//import com.painiu.core.model.Album;
//import com.painiu.core.model.AlbumShare;
//import com.painiu.core.model.Group;
//import com.painiu.core.model.InterestingPhoto;
import com.painiu.core.model.License;
import com.painiu.core.model.Photo;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;



/**
 * <p>
 * <a href="PhotoManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoManager.java 50 2010-06-14 15:02:09Z zhangsf $
 */

public interface PhotoManager {
	
	//public void setPhotoCustomSizeDAO(PhotoCustomSizeDAO photoCustomSizeDAO);
	public void setPhotoState(Photo photo, Photo.State state);
	public void setPhotosState(User user, Photo.State state);
	
	public void blockUserPhotos(User user);
	
	public Result getRecentPublicPhotos(int start, int limit);
	
	public Result getUserPhotos(User user, int start, int limit);
	
	public Result getUserPhotos(User user, boolean orderByTaken, int start, int limit);
	public Result getUserPhotos(User user, boolean orderByTaken, int start, int limit, Relation relation );
	
	public Result getUserTaggedPhotos(User user, String tagName, int start, int limit);

	public Result getTaggedPhotos(String tagName, int start, int limit);
	
	/*public Result getPhotos(User user,
			Group group,
			String[] tags, 
			boolean taggedAll, 
			String text,
			int start, 
			int limit, 
			Relation relation);*/
	public Result getPhotos(User user,
	String[] tags, 
	boolean taggedAll, 
	String text,
	int start, 
	int limit, 
	Relation relation);
	
	/**
	 * 这个方法返回所有指定时间内上传的照片，用于后台照片管理。<br/>
	 * 本方法只有admin和manager可以调用, see {@link com.painiu.core.security.advices.PhotoSecurity}
	 * 
	 * @param from
	 * @param to
	 * @param start
	 * @param limit
	 * @return
	 */
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit);

	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, boolean descOrder);
	
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, List photoStates);
	/**
	 * 获取指定用户指定时间内上传的照片。
	 * 
	 * @param user
	 * @param from
	 * @param to
	 * @param start
	 * @param limit
	 * @param detail
	 * @return
	 */
	public Result getUserPhotosPostedAt(User user, Date from, Date to, int start, int limit, boolean detail);
	
	public Result getUserPhotosTakenOn(User user, Date from, Date to, int start, int limit, boolean detail);
    
    
    public Result getCommentedPhotos(User user, int start, int limit);
    
	public Result getMostViews(User user, int start, int limit);
	
	public Result getMostFavorites(User user, int start, int limit);
	
	public Result getMostComments(User user, int start, int limit);
	
	public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit);
	
	public Result getMostInteresting(User user, int start, int limit);
	
	public Result getMostPopular(User user, int start, int limit);
	
	public List getInterestingnessPhotoIdsGroupByDate(Date fromDate, Date toDate, int limit);
	
//	public Result getInterestingnessPhotos(Date fromDate, Date toDate, int start, int limit);
	
//	public Result getInterestingnessPhotos(int start, int limit);
	
//	public List getInterestingnessPhotos(Date fromDate, Date toDate, int limit);
	
//	public List getInterestingnessPhotosNoMore(Date fromDate, Date toDate, String orderBy, int limit);
	
//	public void setPhotoInteresting(String photoId, User user, int score)  throws ObjectRetrievalFailureException, AccessDeniedException;
	
//	public void saveInterestingPhoto(InterestingPhoto interestingPhoto);
//	public void removeInterestingPhoto(InterestingPhoto interestingPhoto);
	//public InterestingPhoto getInterestingPhoto(Photo photo);
	
	//public List getContactsPhotos(User user, Date fromDate, Date toDate, int start, int limit);
	
	//public List getContactsLatestPhotos(User user, int limit);
	// one photo per contact
	//public List getContactsLatestPhoto(User user, int limit);
	
	//public Result getContactsLatestPhotos(User user, int start, int limit);
	// one photo per contact
	//public Result getContactsLatestPhoto(User user, int start, int limit);
	
	public List getCameraList();
	
	public Result getNotInAlbum(User user, Date minPostedDate, Date maxPostedDate,
			Date minTakenDate, Date maxTakenDate, int start, int limit);
	
	
	public Result getLicensePhotos(License license, int start, int limit, Date from, Date to);
	public Result getTaggedLicensePhotos(String tagName, License license, int start, int limit, Date from, Date to);
	
	
	/*********************************************************************************************************
	 * 明确逻辑区分，以便事件/安全处理
	 *********************************************************************************************************/
	
	//~ photos =================================================================================================
	
	public Photo getPhoto(String id);
	public Photo getPhoto(String id, Relation relation);
	//public Photo getPhoto(String id, AlbumShare albumShare);
	
	public void createPhoto(Photo photo);
	public void deletePhoto(Photo photo);

	// updates
	public void setPhotoLicense(Photo photo, License license);
	public void setPhotoMeta(Photo photo, String title, String description, String rawTags);
	//public void setPhotoPerms(Photo photo, Privacy privacy, PhotoPermission perms);
	public void setPhotoCreative(Photo photo, Integer creative);
	//public void setPhotoScore(Photo photo, Integer score);
	//public void increasePhotoViews(Photo photo);

	//~ tags ===================================================================================================
	
	//public void addPhotoTags(Photo photo, String rawTags);
	//public void addAlbumPhotoTags(Photo photo, String rawTags);
	//public void removePhotoTag(PhotoTag photoTag);
	//public void replacePhotoTag(PhotoTag photoTag, String newTags);
	
	public Integer getUserPhotoTotal(User user);
	
	//public void savePhotoCustomSize(PhotoCustomSize photoCustomSize);
	//public void deletePhotoCustomSize(PhotoCustomSize photoCustomSize);
	//public PhotoCustomSize getPhotoCustomSize(String id);
	
	//public int getInterestingnessPhotos(Date fromDate, Date toDate);


}

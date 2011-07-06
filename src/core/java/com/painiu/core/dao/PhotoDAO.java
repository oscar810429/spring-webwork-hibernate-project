/**
 * @(#)PhotoDAO.java 2010-3-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

//import com.painiu.core.model.Album;
//import com.painiu.core.model.Group;
//import com.painiu.core.model.InterestingPhoto;
import com.painiu.core.model.License;
import com.painiu.core.model.Photo;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
//import com.painiu.core.model.UserRecentPhoto;
import com.painiu.core.search.Result;


/**
 * <p>
 * <a href="PhotoDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoDAO.java 41 2010-06-10 17:30:08Z zhangsf $
 */

public interface PhotoDAO extends DAO{
	
	/**
	 * Get photo by its id.
	 * 
	 * @param id
	 * @return Photo
	 */
	public Photo getPhoto(String id);
	
	/**
	 * Create new photo or save an updated photo.
	 * 
	 * @param photo photo to be saved.
	 */
	public void savePhoto(Photo photo);
	
	/**
	 * Remove a specified photo
	 * @param photo
	 */
	public void removePhoto(Photo photo);
	
	/**
	 * Get recent photos
	 * 
	 * @param start
	 * @param limit
	 */
	public Result getRecentPublicPhotos(int start, int limit);
	
	/**
	 * Get user photos.
	 * 
	 * @param user
	 * @param relation
	 * @param start
	 * @param limit
	 * @return
	 */
	public Result getUserPhotos(User user, boolean orderByTaken, int start, int limit, Relation relation);

	
	public Result getTaggedPhotos(String tagName, int start, int limit);
	
	public Result getUserTaggedPhotos(User user, String tagName, int start, int limit, Relation relation);
	
	// comments you've made
	public Result getCommentedPhotos(User user, int start, int limit);
	
	// recent activities.
	public Result getRecentActivePhotos(User user, Date afterDate, int start, int limit);
	
	public List getInterestingnessPhotoIdsGroupByDate(Date fromDate, Date toDate, int limit);
	
	//public Result getInterestingnessPhotos(Date fromDate, Date toDate, int start, int limit);
	public Result getInterestingnessPhotosByTags(String tags, Date fromDate, Date toDate, int start, int limit);
	
//	public Result getInterestingnessPhotos(int start, int limit);
	
	//public List getInterestingnessPhotos(Date fromDate, Date toDate, int limit);
	
//	public List getInterestingnessPhotosNoMore(Date fromDate, Date toDate, String orderBy, int limit);
	
	//public void saveInterestingPhoto(InterestingPhoto interestingPhoto);
	
	//public void removeInterestingPhoto(InterestingPhoto interestingPhoto);
	
	//public InterestingPhoto getInterestingPhoto(Photo photo);
	
	public Result getMostViews(User user, int start, int limit, Relation relation);
	
	public Result getMostFavorites(User user, int start, int limit, Relation relation);
	
	public Result getMostComments(User user, int start, int limit, Relation relation);
	
	public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit);
	
	public Result getMostInteresting(User user, int start, int limit, Relation relation);
	
	public Result getMostPopular(User user, int start, int limit, Relation relation);

	//public Result getPhotos(User user, Group group, String[] tags, boolean taggedAll, String text, int start, int limit, Relation relation);
	public Result getPhotos(User user, String[] tags, boolean taggedAll, String text, int start, int limit, Relation relation);
	
	public Result getPhotosPostedAt(Date from, Date to, int start, int limit);

	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, boolean descOrder, boolean update);

	public Result getPhotosPostedAt(Date from, Date to, int start, int limit, List photoStates);
	
	public Result getUserPhotosPostedAt(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation);

	public Result getUserPhotosTakenOn(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation);
	
	
	//public List getContactsPhotos(User user, Date fromDate, Date toDate, int start, int limit);
	
	//public List getContactsLatestPhotos(User user, int limit);
	// one photo per contact
	//public List getContactsLatestPhoto(User user, int limit);
	
	//public Result getContactsLatestPhotos(User user, int start, int limit);
	// one photo per contact
	//public Result getContactsLatestPhoto(User user, int start, int limit);
	
	public List getCameraList();
	
	public Result getNotInAlbum(User user, Date minPostedDate, Date maxPostedDate,
								Date minTakenDate, Date maxTakenDate, 
								int start, int limit, Relation relation);
	
	/**
	 * Set state of photos of the specified user to <code>Photo.State.BLOCKED</code>
	 * 
	 * @param user
	 */
	public void blockUserPhotos(User user);
	
	public void setPhotosState(User user, Photo.State state);
	
	public Result getLicensePhotos(License license, int start, int limit, Date from, Date to);
	public Result getTaggedLicensePhotos(String tagName, License license, int start, int limit, Date from, Date to);
	
	/**
	 *  Functions of User Recent Photos;
	 */
	//public void saveUserRecentPhoto(UserRecentPhoto userRecentPhoto);
	//public void removeUserRecentPhoto (UserRecentPhoto userRecentPhoto);
	//public UserRecentPhoto getUserRecentPhoto(Photo photo);
	//public UserRecentPhoto getUserRecentPhoto(String id);
	public List getUserRecentPhotos(User user, Privacy privacy, int limit);
	public List getTopUserPhotos (User user, Privacy privacy, int limit);

	public Integer getUserPhotoTotal(User user);
	public void mergeUserPhoto(User user, User merged);
	
	//public int getInterestingnessPhotos(Date fromDate, Date toDate);
	

}

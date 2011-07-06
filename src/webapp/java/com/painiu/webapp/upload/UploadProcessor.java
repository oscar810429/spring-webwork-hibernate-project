/*
 * @(#)UploadProcessor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentData;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.opensymphony.xwork.TextProvider;
import com.painiu.Painiu;
import com.painiu.webapp.image.Image;
import com.painiu.webapp.image.ImageException;
import com.painiu.webapp.image.ImageFactory;
import com.painiu.webapp.image.ImageTooLargeInDimensionException;
import com.painiu.webapp.image.MetadataUtils;
import com.painiu.webapp.image.UnsupportedImageFormatException;
import com.painiu.webapp.image.PhotoProcessor;
import com.painiu.core.service.PhotoManager;
import com.painiu.core.model.Photo;
import com.painiu.core.model.PhotoAddress;
import com.painiu.core.model.User;
import com.painiu.core.model.UserPreference;
import com.painiu.webapp.util.PhotoAddressUtils;
import com.painiu.webapp.util.PhotoUtils;
import com.painiu.webapp.util.FileUtils;
/**
 * <p>
 * <a href="UploadProcessor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadProcessor.java 50 2010-06-14 15:02:09Z zhangsf $
 */
public class UploadProcessor {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(UploadProcessor.class);
	
    private static final Set JPEGS = new HashSet(3);
    
    static {
    	JPEGS.add("image/jpg");
    	JPEGS.add("image/jpeg");
    	JPEGS.add("image/pjpeg");
    }

    private static final Date MIN_TAKEN_DATE = new Date(631123200000L); // 1990-01-01
    
    public static class Result implements Serializable {
    	private List<Photo> succeed;
    	private List<String> errors;
    	
    	public Result() {
    		this.succeed = new ArrayList(5);
    		this.errors = new ArrayList(5);
    	}
    	
    	public Result(List<Photo> succeed, List<String> errors) {
    		this.succeed = succeed;
    		this.errors = errors;
    	}
    	
    	public List<Photo> getSucceed() {
    		return succeed;
    	}
    	
    	public List<String> getErrors() {
    		return errors;
    	}
    }
    
    private static final int maxWidth = Painiu.getPhotoConfig().getMaxWidth();
    private static final int maxHeight = Painiu.getPhotoConfig().getMaxHeight();
	
	//~ Instance fields ========================================================

    private PhotoManager photoManager;
    
	//~ Constructors ===========================================================

	//~ Methods ================================================================
    
    /**
	 * @param photoManager the photoManager to set
	 */
	public void setPhotoManager(PhotoManager photoManager) {
		this.photoManager = photoManager;
	}
    
    public Result process(HttpServletRequest request,List uploadedFiles) {
    	
    	String title = request.getParameter("title");
    	String description = request.getParameter("description");
    	String tags = request.getParameter("tags");
    	
    	/*if ( Blacklist.getBlacklist().isBlacklisted(title)) {
    		title = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm");
    	}
    	if ( Blacklist.getBlacklist().isBlacklisted(description)) {
    		description = "";
    	}
    	if ( Blacklist.getBlacklist().isBlacklisted(tags)) {
    		tags = "";
    	}*/
    	
    	Integer creative = getCreative(request);
    	//Privacy privacy = PhotoUtils.getPrivacy(request);
    	
//    	String activity = request.getParameter("activity");
    	
    	List<Photo> photos = new ArrayList<Photo>(uploadedFiles.size());
    	List<String> errors = new ArrayList<String>();
    	
    	// sort filenames
        Collections.sort(uploadedFiles);
    	
    	int total = uploadedFiles.size();
    	

    	for (Iterator i = uploadedFiles.iterator(); i.hasNext();) {
			UploadedFile uploadedFile = (UploadedFile) i.next();
            
			// TODO display process result
			try {
				Photo photo = processPhoto(null, null, uploadedFile, title, description, tags, creative);
				photos.add(photo);
			} catch (UnsupportedImageFormatException e) {
				log.warn("Unsupported image format: ", e);
				//errors.add(textProvider.getText("messages.invalid.content.type", new String[] { e.getFilename() }));
			} catch (ImageTooLargeInDimensionException e) {
				log.warn("Large dimension photo: " + e.getSize());
				//errors.add(textProvider.getText("errors.dimension.too.large", new String[] { e.getFilename() }));
			} catch(Exception e) {
				log.warn("Error occurred while processing image file: " + uploadedFile.getDiskFile().getAbsolutePath());
				log.error(e);
				//errors.add(textProvider.getText("errors.upload.processing", new String[] { uploadedFile.getOriginalFilename() }));
			}

		}

    	
    	return new Result(photos, errors);
    }

    public Result process(HttpServletRequest request, 
    		User user, UserPreference pref, List uploadedFiles, 
    		UploadMonitor monitor,
    		TextProvider textProvider) {
    	
    	String title = request.getParameter("title");
    	String description = request.getParameter("description");
    	String tags = request.getParameter("tags");
    	
    	/*if ( Blacklist.getBlacklist().isBlacklisted(title)) {
    		title = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm");
    	}
    	if ( Blacklist.getBlacklist().isBlacklisted(description)) {
    		description = "";
    	}
    	if ( Blacklist.getBlacklist().isBlacklisted(tags)) {
    		tags = "";
    	}*/
    	
    	Integer creative = getCreative(request);
    	//Privacy privacy = PhotoUtils.getPrivacy(request);
    	
//    	String activity = request.getParameter("activity");
    	
    	List<Photo> photos = new ArrayList<Photo>(uploadedFiles.size());
    	List<String> errors = new ArrayList<String>();
    	
    	// sort filenames
        Collections.sort(uploadedFiles);
    	
    	int total = uploadedFiles.size();
    	
    	if (monitor != null) {
    		monitor.updateUploadProgress(new ProgressEvent(this, total, 0, ProgressEvent.START));
    	}
    	
    	for (Iterator i = uploadedFiles.iterator(); i.hasNext();) {
			UploadedFile uploadedFile = (UploadedFile) i.next();
            
			// TODO display process result
			try {
				Photo photo = processPhoto(user, pref, uploadedFile, title, description, tags, creative);
				photos.add(photo);
			} catch (UnsupportedImageFormatException e) {
				log.warn("Unsupported image format: ", e);
				errors.add(textProvider.getText("messages.invalid.content.type", new String[] { e.getFilename() }));
			} catch (ImageTooLargeInDimensionException e) {
				log.warn("Large dimension photo: " + e.getSize());
				errors.add(textProvider.getText("errors.dimension.too.large", new String[] { e.getFilename() }));
			} catch(Exception e) {
				log.warn("Error occurred while processing image file: " + uploadedFile.getDiskFile().getAbsolutePath());
				log.error(e);
				errors.add(textProvider.getText("errors.upload.processing", new String[] { uploadedFile.getOriginalFilename() }));
			}
			
			if (monitor != null) {
				monitor.updateProcessProgress(new ProgressEvent(this, total, 1, ProgressEvent.PROGRESS, uploadedFile.getOriginalFilename()));
			}
		}
    	
    	if (monitor != null) {
    		monitor.updateProcessProgress(new ProgressEvent(this, total, total, ProgressEvent.FINISH));
    	}
    	
    	return new Result(photos, errors);
    }
    
    private Photo processPhoto(
    		User user, UserPreference pref, 
    		UploadedFile uploadedFile, 
    		String title, String description,
			String tags, Integer creative) throws Exception {
    	
    	String filename = uploadedFile.getOriginalFilename();
    	
    	//if ( Blacklist.getBlacklist().isBlacklisted(filename)) {
    	//	filename = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm");
    	//}
    	
    	String contentType = uploadedFile.getContentType();
    	File file = uploadedFile.getDiskFile();
    	
    	if (log.isInfoEnabled()) {
			log.info("Processing Photo: " + filename);
		}
    	
		Photo photo = createPhoto(user, filename, title, description, 
								new Integer((int) file.length()), 
								tags, creative);
		
		// Default PhotoPerm
		//if (privacy.grant(Relation.NONE)) {
		//	photo.setPermission(newPermission(pref));
		//}
		// default license
		//photo.setLicense(pref.getLicense());
		
		// first ping the image file, check its dimension...
		Image image = ImageFactory.getFactory().createImage(file).ping();
		
		Dimension size = image.getSize();
		
		if (size.width > maxWidth || size.height > maxHeight) {
			throw new ImageTooLargeInDimensionException(filename, size);
		}
		
		if (user.isInRole(Painiu.FROZEN_ROLE) || (size.width < 500 && size.height < 500)) {
			photo.setState(Photo.State.BLOCKED);
		}
		
		photo.setWidth(size.width);
		photo.setHeight(size.height);
		
		
		// Old photo repository: to be removed
		String path = new StringBuffer().append(getRepository(photo.getAddress().getHost()))
				.append(File.separator).append(photo.getAddress().getDir()).append(File.separator)
				.append(photo.getAddress().getFilename()).toString();
		
		String distFilename = path + "_" + photo.getAddress().getSecret() + ".jpg";

/*		Image.Type type = null;
		
		// ok, load it and process...
		try {
			image.destroy();
			image = ImageFactory.getFactory().createImage(file).load();
//			type = image.getType();
//			PhotoProcessor.process(image, distFilename);
			// remeber to destroy it explicitly, i do not want to lie on JVM's GC.
//		} catch (ImageException e) {
//			log.warn("PhotoProcessing Error: ", e);
//			throw e;
//		} finally {
//			image.destroy();
//		}
		
		// Metadata: Exif Iptc...
//		photo.setMetadata(new PhotoMeta());
		
		// YPFS ---------------- testing -----------------------------
		PhotoFile photoFile = new PhotoFile(user.getUsername(), file, 
				type.getContentType(), photo.getWidth().intValue(), photo.getHeight().intValue());
		
		try {
			photoFile.store();
		} catch (YPFSException e) {
			log.error("YPFSException: ", e);
			throw e;
		}
		
		PhotoAddress addr = photo.getAddress();
		addr.setUsername(photoFile.getUsername());
		addr.setFileKey(photoFile.getFilename());
		addr.setSecret(photoFile.getSecret());
		// YPFS
		*/
		// read metadata
		if (isJpegImage(contentType)) {
		//	String metaFilename = path + "_" + photo.getAddress().getSecret() + Painiu.SUFFIX_META;
        //	readJpegMetadata(photo, file, metaFilename);
			readJpegMetadata(photo, file);
		}
		
		// copy original photo
		File distFile = new File(path + "_" + photo.getAddress().getSecret() + ".jpg");
		File parentFile = distFile.getParentFile();		
		if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
		
		// ok, load it and process...
		try {
			image.destroy();
			image = ImageFactory.getFactory().createImage(file).load();
			log.info(image);
			PhotoProcessor.process(image, distFilename);
			// remeber to destroy it explicitly, i do not want to lie on JVM's GC.
		} catch (ImageException e) {
			log.warn("PhotoProcessing Error: ", e);
			throw e;
		} finally {
			image.destroy();
		}
		
	    FileUtils.copyFile(file, distFile);
	
		savePhoto(photo);
		return photo;
    }
    
    private void savePhoto(Photo photo) {
		if (log.isInfoEnabled()) {
			log.info("Saving Photo: " + photo.getOriginalFilename());
		}
		photoManager.createPhoto(photo);
	}
    
    private void readJpegMetadata(Photo photo, File file) {
    	try {
    		JpegSegmentData data = MetadataUtils.readSegments(file);
    		
    		Metadata metadata = MetadataUtils.extractMetadataFromSegmentData(data);

    		if (log.isDebugEnabled()) {
    			log.debug("********* Metadata of uploaded photo *********");
    			// iterate over the exif data and print it
    			Iterator directories = metadata.getDirectoryIterator();
    			while (directories.hasNext()) {
    				Directory directory = (Directory) directories.next();
    				Iterator tagsIterator = directory.getTagIterator();
    				while (tagsIterator.hasNext()) {
    					Tag tag = (Tag) tagsIterator.next();
    					log.debug(tag);
    				}
    				if (directory.hasErrors()) {
    					Iterator errors = directory.getErrors();
    					while (errors.hasNext()) {
    						log.debug("ERROR: " + errors.next());
    					}
    				}
    			}
    		}

    		Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);

    		Date takenDate = null;
    		if (exifDirectory.containsTag(ExifDirectory.TAG_DATETIME_ORIGINAL)) {
    			takenDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
    		} else if (exifDirectory.containsTag(ExifDirectory.TAG_DATETIME_DIGITIZED)) {
    			takenDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME_DIGITIZED);
    		} else if (exifDirectory.containsTag(ExifDirectory.TAG_DATETIME)) {
    			takenDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME);
    		}

    		if (takenDate != null && takenDate.after(MIN_TAKEN_DATE)) {
    			photo.setTakenDate(takenDate);
    		}

    		if (photo.getTitle() == null) {
    			String titleFromMeta = extractTitleFromMetadata(metadata);
    			if (titleFromMeta != null) {
    				photo.setTitle(titleFromMeta);
    			}
    		}

    		if (photo.getDescription() == null) {
    			photo.setDescription(extractDescFromMetadata(metadata));
    		}

    		if (exifDirectory.containsTag(ExifDirectory.TAG_MODEL)) {
    			String cameraModel = exifDirectory.getString(ExifDirectory.TAG_MODEL);
    			photo.setCameraModel(cameraModel);
    			// save metadata as file
    			File localFile = new File(file.getAbsolutePath() + ".meta");
    			MetadataUtils.toFile(localFile, data);
    			
    			// PNFS testing -----------------------------------
    			/*MetaFile metaFile = new MetaFile(
    					photo.getAddress().getUsername(), 
    					photo.getAddress().getFileKey(), 
    					localFile);
    			
    			try {
    				metaFile.store();
    			} catch (YPFSException e) {
    				log.error("YPFSException: " + e);
    				photo.setCameraModel(null);
    			} finally {
    				localFile.delete();
    			}*/
    			// PNFS
    		} // we dont save any metadata, if there have no camera tag
    	} catch (JpegProcessingException e) {
    		log.warn("JpegProcessingException: ", e);
    	} catch (MetadataException e) {
    		log.warn("MetadataException: ", e);
    	} catch (IOException e) {
    		log.warn("IOException: ", e);
    	}
    }
    
    private static String extractTitleFromMetadata(Metadata metadata) throws MetadataException {
		String title = null;
		
		Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
		
		if (exifDirectory.containsTag(ExifDirectory.TAG_DOCUMENT_NAME)) {
			title = exifDirectory.getDescription(ExifDirectory.TAG_DOCUMENT_NAME);
		} else if (exifDirectory.containsTag(ExifDirectory.TAG_WIN_TITLE)) {
			title = exifDirectory.getDescription(ExifDirectory.TAG_WIN_TITLE);
		} else { // try to find title from iptc directory
			Directory iptcDirectory = metadata.getDirectory(IptcDirectory.class);
			if (iptcDirectory.containsTag(IptcDirectory.TAG_OBJECT_NAME)) {
				title = iptcDirectory.getDescription(IptcDirectory.TAG_OBJECT_NAME);
			}
		}
		
		return title;
	}
    
	private static String extractDescFromMetadata(Metadata metadata) throws MetadataException {
		String desc = null;
		
		Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
		
		if (exifDirectory.containsTag(ExifDirectory.TAG_IMAGE_DESCRIPTION)) {
			desc = exifDirectory.getDescription(ExifDirectory.TAG_IMAGE_DESCRIPTION);
		} else if (exifDirectory.containsTag(ExifDirectory.TAG_WIN_COMMENT)) {
			desc = exifDirectory.getDescription(ExifDirectory.TAG_WIN_COMMENT);			
		} else { // try to find description from iptc directory
			Directory iptcDirectory = metadata.getDirectory(IptcDirectory.class);
			if (iptcDirectory.containsTag(IptcDirectory.TAG_CAPTION)) {
				desc = iptcDirectory.getDescription(IptcDirectory.TAG_CAPTION);
			}
		}
		
		return desc;
	}
    
    //~ Utitlities ================================================================

    private static boolean isJpegImage(String contentType) {
    	return JPEGS.contains(contentType);
    }
    
    /*private static PhotoPermission newPermission(UserPreference preference) {
    	PhotoPermission perm = new PhotoPermission();
    	
    	perm.setBlog(preference.getBlog());
    	perm.setComment(preference.getComment());
    	perm.setDownload(preference.getDownload());
    	perm.setExif(preference.getExif());
    	perm.setNote(preference.getNote());
    	perm.setTag(preference.getTag());
    	
    	return perm;
    }*/
    
	private static Integer getCreative(HttpServletRequest request) {
		int creative = Painiu.CREATIVE_AUTHOR;
		String string = request.getParameter("creative");
		if (String.valueOf(Painiu.CREATIVE_REFERENCED).equals(string)) {
			creative = Painiu.CREATIVE_REFERENCED;
		}
		return new Integer(creative);
	}

    /**
	 * @return
	 */
	
	private static String getRepository(Integer host) {
		return Painiu.getPhotoConfig().getRepository(host).getDirectory();
	}
	
	
	private static Photo createPhoto(User user, 
									String originalFilename, 
									String title, 
									String description, 
									Integer fileLength, 
									String tags,
									Integer creative) {
		
		Photo photo = new Photo();
		photo.setUser(user);
		photo.setAddress(PhotoAddressUtils.newAddress());
		photo.setOriginalFilename(originalFilename);
		photo.setTitle(title != null ? title : FilenameUtils.getBaseName(originalFilename));
		if (description != null) {
			photo.setDescription(description);
		}
		photo.setFileLength(fileLength);
		photo.setRawTags(tags);
		photo.setCreativeType(creative);
		//photo.setPrivacy(privacy);
		//photo.setPermission(new PhotoPermission(privacy));
		//photo.setStat(new PhotoStat());
		
		photo.setState( Photo.State.valueOf( user.getUserRank().value() 
							+ Photo.OFFSET_UNCHECKED_PHOTO_FROM_USER ) );
		
		long now = System.currentTimeMillis();
		
		// make sure one person's photos have different timestamp.
		if (getTimestamp() >= now) {
			now = getTimestamp() + 1;
		}
		if (now % 1000 == 0) {
			// prevent posted date overlap. (user editing).
			now = now + 1;
		}
		photo.setPostedDate(new Date(now));
		photo.setTimestamp(new Long(now));
		photo.setTakenDate(photo.getPostedDate());

		setTimestamp(now);
		
		return photo;
	}
	
	private static ThreadLocal timestampHolder = new ThreadLocal();
	
	public static void setTimestamp(long timestamp) {
		timestampHolder.set(new Long(timestamp));
	}
	
	public static long getTimestamp() {
		if (timestampHolder.get() == null) {
			return -1;
		}
		
		return ((Long) timestampHolder.get()).longValue();
	}

}

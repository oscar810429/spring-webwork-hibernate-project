package com.painiu.webapp.action;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.painiu.Painiu;
import com.painiu.core.model.Icon;
import com.painiu.core.model.PhotoAddress;
import com.painiu.core.model.User;
import com.painiu.core.security.SecurityUtils;
import com.painiu.webapp.image.Image;
import com.painiu.webapp.image.ImageException;
import com.painiu.webapp.image.ImageFactory;
import com.painiu.webapp.image.ImageTooLargeInDimensionException;
import com.painiu.webapp.image.PhotoProcessor;
import com.painiu.webapp.upload.UploadFileValidator;
import com.painiu.webapp.util.PhotoAddressUtils;
import com.painiu.webapp.util.FileUtils;

public class BuddyIconAction extends BaseAction{
	
//~ Static fields/initializers =============================================
	
	private static final int MAX_IMAGE_WIDTH  = 4000;
	private static final int MAX_IMAGE_HEIGHT = 4000;
	
	//~ Instance fields ========================================================
	
	protected User user;
	
	protected File icon;
	protected String iconContentType;
	protected String iconFileName;
	
	private String forward;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String execute() throws Exception {
		user = getCurrentUser();
		
		return SUCCESS;
	}
	
	public String upload() throws Exception {
		user = getCurrentUser();
		
		if (!UploadFileValidator.isImageFile(iconContentType)) {
        	List args = new ArrayList(2);
        	args.add(iconFileName);
        	args.add(iconContentType);
        	addActionError(getText("errors.content.type.not.allowed", args));
        	if (StringUtils.isNotEmpty(from)) {
        		return redirect(from);
        	}
        	return INPUT;
        }
		
		Icon old = user.getBuddyIcon();
		Icon icon = null;
		
		try {
			icon = processIcon(user);
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("errors.processing.icon"));
			return INPUT;
		}
		
		userManager.saveBuddyIcon(user, icon);
		
        //SecurityUtils.setCurrentUser(user);
		// For session replication in cluster environment
        getSession().setAttribute(Painiu.USER_KEY, user);

		deleteIcon(old);
		
		if (StringUtils.isNotEmpty(forward)) {
    		return redirect(forward);
    	}
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		user = getCurrentUser();
		
		//Icon old = user.getBuddyIcon();
		
		user.setBuddyIcon(null);
		userManager.saveUser(user);
		
//		SecurityUtils.setCurrentUser(user);
		// For session replication in cluster environment
//		getSession().setAttribute(Yupoo.USER_KEY, user);
		saveMessage(getText("messages.delete.success"));
		
		//deleteIcon(old);
		
		return SUCCESS;
	}
	
	protected void deleteIcon(Icon icon) {
		/*No Use PNFS*/
		PhotoAddress photoAddress = new PhotoAddress();
		photoAddress.setHost(icon.getHost());
		photoAddress.setDir(icon.getDir());
		photoAddress.setFilename(icon.getFilename());
		String fileName = photoAddress.getURL(Painiu.PHOTO_MEDIUM);
		File fileNameFile = new File(fileName); 
		FileUtils.deleteDir(fileNameFile);
		
		/*Use PNFS*/
		/*if (icon != null && icon.getUsername() != null && icon.getFileKey() != null) {
			IconFile iconFile = new IconFile(icon.getUsername(), icon.getFileKey());
	        
			try {
				iconFile.delete();
			} catch (PNFSException e) {
				log.warn("YPFSException: " + e.getMessage());
			}
		}*/
	}
	
	protected Icon processIcon(User user) {
		if (log.isInfoEnabled()) {
			log.info("Processing Icon: " + iconFileName);
		}
		
		String filename = icon.getAbsolutePath();
		
		PhotoAddress address = PhotoAddressUtils.newAddress();
		String tempFilename = getBuddyIconPath(address);
		String distFilename = getBuddyIconPath(address); 
//		String distFilename = filename + ".thumb";
		
		Image image = ImageFactory.getFactory().createImage(filename).ping();
		
		Dimension size = image.getSize();
		
		if (size.width > MAX_IMAGE_WIDTH || size.height > MAX_IMAGE_HEIGHT) {
			throw new ImageTooLargeInDimensionException(filename, size);
		}
         //ok, load it and process...
		try {
			image.destroy();
			image = ImageFactory.getFactory().createImage(filename).load();
			PhotoProcessor.makeIcon(image, distFilename, 120);
		} catch (ImageException e) {
			log.warn("PhotoProcessing Error: ", e);
			throw e;
		} finally {
			// remeber to destroy it explicitly, i do not want to lie on JVM's GC.
			image.destroy();
		}
		
		Icon icon = new Icon();
		icon.setUsername(user.getUsername());
		icon.setDir(address.getDir());
		icon.setHost(address.getHost());
		icon.setFilename(address.getFilename());
		File tmpFile = new File(tempFilename);
		File distFile = new File(distFilename);
		try {
			FileUtils.copyFile(tmpFile, distFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/*File tmpFile = new File(distFilename);
		IconFile iconFile = new IconFile(user.getUsername(), tmpFile);
        
		try {
			iconFile.store();
		} catch (YPFSException e) {
			log.warn("YPFSException: " + e.getMessage());
			throw e;
		} finally {
			tmpFile.delete();
		}*/
        
		//icon.setFileKey(iconFile.getFilename());
		
        return icon;
	}

	protected String getBuddyIconPath(PhotoAddress address) {
        String path = new StringBuffer().append(getRepository(address.getHost())).append(
                File.separator).append(address.getDir()).append(File.separator)
                .append(address.getFilename()).append(Painiu.SUFFIX_MEDUIM).toString();
		return path;
	}
	
	protected String getRepository(Integer host) {
		return Painiu.getPhotoConfig().getRepository(host).getDirectory();
	}
	
	//~ Accessors ==============================================================

	public User getUser() {
		return user;
	}
	
	/**
	 * @param icon The icon to set.
	 */
	public void setIcon(File icon) {
		this.icon = icon;
	}
	
	/**
	 * @return Returns the icon.
	 */
	public File getIcon() {
		return icon;
	}
	
	/**
	 * @param iconContentType The iconContentType to set.
	 */
	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}
	
	/**
	 * @param iconFileName The iconFileName to set.
	 */
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}
	
	public String getForward() {
		return forward;
	}
	
	public void setForward(String forward) {
		this.forward = forward;
	}

	

}

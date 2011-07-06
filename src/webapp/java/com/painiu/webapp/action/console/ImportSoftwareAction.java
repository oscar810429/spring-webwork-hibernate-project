/**
 * @(#)ImportSoftware.java 2010-5-16
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.painiu.core.model.Category;
import com.painiu.core.model.Software;
import com.painiu.core.model.SoftwareStat;
import com.painiu.core.model.User;
import com.painiu.webapp.action.BaseAction;
import com.painiu.webapp.util.ExcelUtils;

/**
 * <p>
 * <a href="ImportSoftware.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImportSoftwareAction.java 85 2010-07-01 19:37:02Z zhangsf $
 */

public class ImportSoftwareAction extends BaseAction{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================
	
	private String fileContentType;
	private String fileFileName;
	private String fileRealPath ;
	private File file;
	private User user;
	private String cid;

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	 public String upload() throws Exception{
		user = getCurrentUser();
	    return SUCCESS;
	 }
	 
	 public String save_upload() throws Exception{
		    user = getCurrentUser();
			long now = System.currentTimeMillis();
			
			// make sure one person's photos have different timestamp.
			if (getTimestamp() >= now) {
				now = getTimestamp() + 1;
			}
			if (now % 1000 == 0) {
				// prevent posted date overlap. (user editing).
				now = now + 1;
			}
			if (file != null)
			{
				try {
					ExcelUtils excelUtils = new ExcelUtils(this.getRequest(),this.getResponse());
					List<Object[]> files = excelUtils.read(file.getAbsolutePath());
					int icount = 0 ;
					for(Object[] software : files){
						if(icount!=0){
							
						 Category category = categoryManager.getCategoryName(software[1].toString().trim());	
					     Software softwareNew = new Software();
					     softwareNew.setUser(user);
					     //softwareNew.setPermission(new Permission(user.getPreference()));
					     softwareNew.setStat(new SoftwareStat());
					     softwareNew.setContent(software[4].toString());
					     softwareNew.setName(software[0].toString().trim());
					     softwareNew.setLanguage(software[2].toString().trim());
					     softwareNew.setOsName(software[3].toString().trim());
					     softwareNew.setPostedDate(new Date(now));
					     softwareNew.setTokenDate(new Date(now));
					     softwareNew.setTimestamp(new Long(now));
					     softwareNew.setCategory(category);
					     softwareNew.setUsername(user.getUsername());
					     category.increaseAmount();
					     softwareManager.saveSoftware(softwareNew);
					     categoryManager.saveCategory(category);
					     setTimestamp(now);
						}
						icount++;
					}
				} catch (Exception e) {
					log.debug(e);
					return ERROR;
				} 
			} else {
			    log.debug("file is null.");
				return ERROR;
			}
		 
		    return SUCCESS;
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
	//~ Accessors ======================================================
	 
		/**
		 * @return the fileContentType
		 */
		public String getFileContentType() {
			return fileContentType;
		}

		/**
		 * @param fileContentType the fileContentType to set
		 */
		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}

		/**
		 * @return the fileFileName
		 */
		public String getFileFileName() {
			return fileFileName;
		}

		/**
		 * @param fileFileName the fileFileName to set
		 */
		public void setFileFileName(String fileFileName) {
			this.fileFileName = fileFileName;
		}

		/**
		 * @return the fileRealPath
		 */
		public String getFileRealPath() {
			return fileRealPath;
		}

		/**
		 * @param fileRealPath the fileRealPath to set
		 */
		public void setFileRealPath(String fileRealPath) {
			this.fileRealPath = fileRealPath;
		}

		/**
		 * @return the file
		 */
		public File getFile() {
			return file;
		}

		/**
		 * @param file the file to set
		 */
		public void setFile(File file) {
			this.file = file;
		}

		/**
		 * @return the user
		 */
		public User getUser() {
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(User user) {
			this.user = user;
		}

		/**
		 * @return the cid
		 */
		public String getCid() {
			return cid;
		}

		/**
		 * @param cid the cid to set
		 */
		public void setCid(String cid) {
			this.cid = cid;
		}

		
	    
	 
	 

}

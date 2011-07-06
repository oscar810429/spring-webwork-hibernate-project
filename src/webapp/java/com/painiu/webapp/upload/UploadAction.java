/*
 * @(#)UploadAction.java Jun 4, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.util.List;

//import com.yupoo.model.Flowmeter;

/**
 * <p>
 * <a href="UploadAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: UploadAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface UploadAction {
	
	//public void setFlowmeter(Flowmeter flowmeter);
	
	public void setOverflowed(boolean overflowed);
	
	public boolean isOverflowed();
	
	public void setUploadedFiles(List<UploadedFile> files);
	
	public void addInvalidFile(UploadedFile file);
	public void addOverflowedFile(UploadedFile file);
	public void addUploadError(String error);
}

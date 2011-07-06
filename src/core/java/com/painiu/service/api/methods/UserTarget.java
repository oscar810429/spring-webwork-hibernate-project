/**
 * @(#)UserTarget.java 2010-7-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.methods;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.model.Token;
import com.painiu.core.model.User;
import com.painiu.core.model.UserProfile;
import com.painiu.service.api.ApiContextHolder;
import com.painiu.service.api.ApiException;
import com.painiu.service.api.Parameters;

/**
 * <p>
 * <a href="UserTarget.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserTarget.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class UserTarget extends BaseTarget{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	public Object setMeta (Parameters params) throws ApiException{
		
		String userId = params.getString("user_id");
		String nickname  = params.getString("nickname");
		String description  = params.getString("description");
		
		assertParamExists(userId);
		
		UserProfile userProfile = null;
		Token token = null;
		User user = null;
         User authUser = ApiContextHolder.getContext().getUser();
		
		try {
			user = userManager.getUser(userId);
			userProfile = user.getProfile();
			if(nickname!=null && !"".equals(nickname)){
			  user.setNickname(nickname);
			}
			if(description!=null && !"".equals(description)){
			  userProfile.setDescription(description);
			  user.setProfile(userProfile);
			}
		} catch (ObjectRetrievalFailureException e) {
			throw new ApiException("1", "User not found");
		}
		
		if(user.equals(authUser)){
	        userManager.saveUser(user);
		}else{
			throw new ApiException("1", "User not found");	
		}
		
		return null;
	}

	//~ Accessors ======================================================

}

/*
 * @(#)UserTypes.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence;

import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import com.painiu.core.model.Area;
import com.painiu.core.model.Comment;
//import com.painiu.core.model.GroupPhoto;
//import com.painiu.core.model.GroupUser;
import com.painiu.core.model.Forum;
import com.painiu.core.model.License;
import com.painiu.core.model.Photo;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.model.Message;
import com.painiu.core.model.Abuse;

/**
 * <p>
 * <a href="UserTypes.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserTypes.java 65 2010-06-15 17:45:39Z zhangsf $
 */
public class UserTypes {
	
	private static final Properties TYPEPROPS_PRIVACY = new Properties();
	private static final Properties TYPEPROPS_RELATION = new Properties();
	private static final Properties TYPEPROPS_LICENSE = new Properties();
	private static final Properties TYPEPROPS_USER_STATE = new Properties();
	private static final Properties TYPEPROPS_USER_RANK = new Properties();
	private static final Properties TYPEPROPS_MESSAGE_STATE = new Properties();
	private static final Properties TYPEPROPS_ABUSE_STATE = new Properties();
	private static final Properties TYPEPROPS_COMMENT_SITUATION = new Properties();
	private static final Properties TYPEPROPS_PHOTO_STATE = new Properties();
	private static final Properties TYPEPROPS_AREA_STATE = new Properties();
	private static final Properties TYPEPROPS_FORUM_TYPE = new Properties();
	private static final Properties TYPEPROPS_SOFTWARE_STATE = new Properties();


	

	static {
		TYPEPROPS_PRIVACY.put("enumClassname", Privacy.class.getName());
		TYPEPROPS_RELATION.put("enumClassname", Relation.class.getName());
		TYPEPROPS_LICENSE.put("enumClassname", License.class.getName());
		TYPEPROPS_USER_STATE.put("enumClassname", User.State.class.getName());
		TYPEPROPS_FORUM_TYPE.put("enumClassname", Forum.Type.class.getName());
		TYPEPROPS_USER_RANK.put("enumClassname", User.UserRank.class.getName());
		TYPEPROPS_MESSAGE_STATE.put("enumClassname", Message.State.class.getName());
		TYPEPROPS_ABUSE_STATE.put("enumClassname", Abuse.State.class.getName());
		TYPEPROPS_COMMENT_SITUATION.put("enumClassname", Comment.Situation.class.getName());
		TYPEPROPS_PHOTO_STATE.put("enumClassname", Photo.State.class.getName());
		TYPEPROPS_AREA_STATE.put("enumClassname", Area.AreaType.class.getName());
		//TYPEPROPS_SOFTWARE_STATE.put("enumClassname", Software.State.class.getName());

		


	}
	
	
	public static Type privacy() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_PRIVACY);
	}
	
	public static Type relation() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_RELATION);
	}
	
	public static Type license() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_LICENSE);
	}
	
	public static Type userState() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_USER_STATE);
	}
	
	public static Type forumType() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_FORUM_TYPE);
	}
	
	public static Type userRank() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_USER_RANK);
	}
	
	public static Type messageState() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_MESSAGE_STATE);
	}
	
	public static Type abuseStateType() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_ABUSE_STATE);
	}
	
	public static Type commentSituationType() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_COMMENT_SITUATION);
	}
	
	public static Type photoState() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_PHOTO_STATE);
	}
	
	public static Type areaState() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_AREA_STATE);
	}
	
	public static Type softwareState() {
		return Hibernate.custom(IntegerEnumUserType.class, TYPEPROPS_SOFTWARE_STATE);
	}

	
}	

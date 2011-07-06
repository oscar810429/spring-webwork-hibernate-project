/*
 * @(#)NicknameExistsException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.exception;

/**
 * <p>
 * <a href="NicknameExistsException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: NicknameExistsException.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class NicknameExistsException extends UserExistsException {
	private String nickName;
	
	public NicknameExistsException(String nickName) {
		super("Nickname '" + nickName + "' is exists");
		this.nickName = nickName;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}

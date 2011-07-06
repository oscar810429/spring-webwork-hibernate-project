/*
 * @(#)Commentable.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;


/**
 * <p>
 * <a href="Commentable.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Commentable.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface Commentable {
	
	public User getUser();
	
	public void addComment(Comment comment);
	
	public void removeComment(Comment comment);
}

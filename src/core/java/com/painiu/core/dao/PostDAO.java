/*
 * @(#)PostDAO.java  2006-2-21
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.Comment;
import com.painiu.core.model.Post;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="PostDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: PostDAO.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public interface PostDAO extends DAO {

	public Post getPost(String id);
	
	public void savePost(Post post);
	
	public void removePost(Post post);
	
	public List getPosts(User user, int limit);
	
	/*For manage coments in console.
	 * */
	public Result getPosts(User user, String key, Comment.Situation situation, Date startDate, Date endDate, int start, int limit);
	
}

/**
 * @(#)SoftwareDAO.java 2010-5-17
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.Category;
import com.painiu.core.model.Relation;
import com.painiu.core.model.Software;
import com.painiu.core.model.Software.State;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SoftwareDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareDAO.java 133 2010-11-23 08:14:43Z zhangsf $
 */

public interface SoftwareDAO extends DAO {
	
	public Software getSoftware(String softwareId);
	
	public Software getSoftware(Software software);
	
	public void removeSoftware(Software software);
	
	public void saveSoftware(Software software);
	
	public void setSoftwaresState(User user,State state);
	
	//public Result getSoftwares(Category category, int start, int limit);
	
	//public Result getSoftwares(Category category, String name, int start, int limit);
	
    public Result getMostViews(User user, int start, int limit, Relation relation);
	
	public Result getMostFavorites(User user, int start, int limit, Relation relation);
	
	public Result getMostComments(User user, int start, int limit, Relation relation);
	
	public Result getMostInteresting(Date fromDate, Date toDate, int interests, int start, int limit);
	
	public Result getMostInteresting(User user, int start, int limit, Relation relation);
	
	public Result getRecentPublicSoftwares(int start, int limit);
	
	public Result getUserSoftwares(User user, boolean orderByTaken, int start, int limit, Relation relation);

	public Result getTaggedSoftwares(String tagName, int start, int limit);
	
	public Result getUserTaggedSoftwares(User user, String tagName, int start, int limit, Relation relation);
	
	public Result getCommentedSoftwares(User user, int start, int limit);
	
	public Result getSoftwares(User user,Category category, String[] tags, boolean taggedAll, String text, int start, int limit, Relation relation);
	
	public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit);

	public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit, boolean descOrder, boolean update);

	public Result getSoftwaresPostedAt(Date from, Date to, int start, int limit, List photoStates);
	
	public Result getUserSoftwaresPostedAt(User user, Date from, Date to, int start, int limit, boolean detail, Relation relation);

}

/*
 * @(#)CotagDAO.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Collection;
import java.util.List;

import com.painiu.core.model.Cotag;

/**
 * <p>
 * <a href="CotagDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CotagDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface CotagDAO extends DAO {
	
	public Cotag getCotag(Cotag.Id id);
	
	public void saveCotag(Cotag cotag);
	
	public void saveCotags(Collection cotags);
	
	public void removeCotag(Cotag cotag);
	
	public List getCotags(String tag, int limit);
	
}

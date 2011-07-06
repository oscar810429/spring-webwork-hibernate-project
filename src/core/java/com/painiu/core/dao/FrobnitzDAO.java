/*
 * @(#)FrobnitzDAO.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import com.painiu.core.model.Frobnitz;

/**
 * <p>
 * <a href="FrobnitzDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FrobnitzDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface FrobnitzDAO extends DAO {
	
	public Frobnitz getFrobnitz(String id);
	
	public void saveFrobnitz(Frobnitz frobnitz);
	
	public void removeFrobnitz(Frobnitz frobnitz);
}

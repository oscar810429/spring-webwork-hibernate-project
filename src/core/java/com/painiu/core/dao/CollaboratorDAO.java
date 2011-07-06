/*
 * @(#)CollaboratorDAO.java  2009-7-29
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import com.painiu.core.model.Collaborator;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="CollaboratorDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: CollaboratorDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface CollaboratorDAO extends DAO {
	
	public Collaborator getCollaborator(String id);
	
	public Result getCollaborators(int start, int limit);
	
	public void saveCollaborator(Collaborator collaborator);
	
	public void removeCollaborator(Collaborator collaborator);
	
}

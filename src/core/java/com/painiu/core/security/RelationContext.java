/*
 * @(#)RelationContext.java  2009-12-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;

import java.io.Serializable;

//import com.mingda.core.model.Contact;
import com.painiu.core.model.Relation;

/**
 * <p>
 * <a href="RelationContext.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RelationContext.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface RelationContext extends Serializable {
	
	//public Contact getContact();
	
	public Relation getRelation();
	
	//public void setContact(Contact contact);
	
	public void setRelation(Relation relation);
	
}

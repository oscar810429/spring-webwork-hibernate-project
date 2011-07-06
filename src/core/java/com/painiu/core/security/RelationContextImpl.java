/*
 * @(#)RelationContextImpl.java  2009-12-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;

//import com.mingda.core.model.Contact;
import com.painiu.core.model.Relation;

/**
 * <p>
 * <a href="RelationContextImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RelationContextImpl.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class RelationContextImpl implements RelationContext {
	private static final long serialVersionUID = -2635933829134055484L;

	//private Contact contact = null;
	
	private Relation relation = Relation.NONE;
	
	/* (non-Javadoc)
	 * @see com.mingda.security.RelationContext#getContact()
	 */
	//public Contact getContact() {
	//	return contact;
	//}
	
	/* (non-Javadoc)
	 * @see com.mingda.security.RelationContext#setContact(com.mingda.model.Contact)
	 */
	//public void setContact(Contact contact) {
	//	this.contact = contact;
	//}
	
	/*
	 * @see com.mingda.security.RelationContext#getRelation()
	 */
	public Relation getRelation() {
		return relation;
	}

	/*
	 * @see com.mingda.security.RelationContext#setRelation(com.mingda.model.Relation)
	 */
	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof RelationContextImpl) {
			RelationContextImpl test = (RelationContextImpl) obj;

            if ((this.getRelation() == null) &&
                    (test.getRelation() == null)) {
                return true;
            }

            if ((this.getRelation() != null) &&
                    (test.getRelation() != null) &&
                    this.getRelation().equals(test.getRelation())) {
                return true;
            }
		}
		return false;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.relation == null) {
            return -1;
        }
		return this.relation.hashCode();
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());

        if (this.relation == null) {
            sb.append(": Null Relation");
        } else {
            sb.append(": Relation: " + this.relation);
        }

        return sb.toString();
	}
}

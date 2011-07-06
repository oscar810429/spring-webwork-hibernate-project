/*
 * @(#)Relation.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;

import com.painiu.Painiu;

/**
 * <p>
 * <a href="Relation.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Relation.java 66 2010-06-15 20:39:47Z zhangsf $
 */
public class Relation extends IntegerEnum implements Serializable {
	
     private static final long serialVersionUID = 5150760928915922012L;
    // Relations : anyone
	public static final Relation ANYONE  = new Relation(Painiu.PRIVACY_NONE);
	// Relations : members
	public static final Relation NONE    = new Relation(Painiu.PRIVACY_EVERYONE);
	public static final Relation MEMBER  = NONE;
	// Relations : CONTACT 
	public static final Relation CONTACT = new Relation(Painiu.PRIVACY_CONTACTS);
	// Relations : Friends
	public static final Relation FRIEND = new Relation(Painiu.PRIVACY_FRIENDS);
	// Relations : Family
	public static final Relation FAMILY = new Relation(Painiu.PRIVACY_FAMILY);
    // Relations : Friends + Family 
    public static final Relation FRIENDS_FAMILY = new Relation(Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_FAMILY);
    // Relations : All Contacts
	public static final Relation ALL_CONTACT = new Relation(Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_PRIVATE);
	// Relations : SELF
	public static final Relation SELF    = new Relation(Painiu.PRIVACY_PUBLIC);
	// end
  
     public static final Relation ADMIN_NONE    = new Relation(NONE.value()     | Painiu.PRIVACY_SYSTEM);
     public static final Relation ADMIN_CONTACT = new Relation(CONTACT.value()  | Painiu.PRIVACY_SYSTEM);
     public static final Relation ADMIN_FRIEND  = new Relation(FRIEND.value()   | Painiu.PRIVACY_SYSTEM);
     public static final Relation ADMIN_FAMILY  = new Relation(FAMILY.value()   | Painiu.PRIVACY_SYSTEM);
     public static final Relation ADMIN_SELF    = new Relation(SELF.value()     | Painiu.PRIVACY_SYSTEM);
  
  
     public static final Relation FAMILY_FRIEND_CONTACT_NONE = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_EVERYONE);
     public static final Relation FAMILY_FRIEND_CONTACT = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_CONTACTS);
     public static final Relation FAMILY_FRIEND_NONE = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_EVERYONE);
     public static final Relation FAMILY_CONTACT_NONE = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_EVERYONE);
     public static final Relation FRIEND_CONTACT_NONE =  new Relation(Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_EVERYONE);
     public static final Relation FAMILY_FRIEND = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_FRIENDS);
     public static final Relation FAMILY_CONTACT = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_CONTACTS);
     public static final Relation FAMILY_NONE = new Relation(Painiu.PRIVACY_FAMILY | Painiu.PRIVACY_EVERYONE);
     public static final Relation FRIEND_CONTACT = new Relation(Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_CONTACTS);
     public static final Relation FRIEND_NONE = new Relation(Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_EVERYONE);
     public static final Relation CONTACT_NONE = new Relation(Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_EVERYONE);
  
	private Relation(int value) {
		super(value);
	}

	public static Relation valueOf(int value) {
		return (Relation) IntegerEnum.valueOf(Relation.class, value);
	}
	
	public static Relation asAdmin(Relation r1) {
		return valueOf(r1.value() | Painiu.PRIVACY_SYSTEM);
	}
	
	public boolean isAdmin() {
		return (value() & Painiu.PRIVACY_SYSTEM) >= Painiu.PRIVACY_SYSTEM;
	}
	
	public Relation asGeneral() {
		if (this.equals(SELF)) {
			return this;
		}
		if (isAdmin()) {
			return valueOf(this.value() - Painiu.PRIVACY_SYSTEM);
		}
		return valueOf(this.value());
	}
	
	/*
	 * @see com.Painiu.model.IntegerEnum#toString()
	 */
	public String toString() {
		if (this.equals(NONE)) {
			return "none";
		} else if(this.equals(ANYONE)) {
			return "anyone";
		} else if(this.equals(MEMBER)) {
			return "member";
		} else if(this.equals(CONTACT)) {
			return "contact";
		} else if (this.equals(FRIEND)) {
			return "friend";
		} else if (this.equals(FAMILY)) {
			return "family";
		} else if (this.equals(FRIENDS_FAMILY)) {
			return "friendfamily";
		} else if (this.equals(ALL_CONTACT)) {
			return "allcontacts";
		} else if (this.equals(SELF)) {
			return "self";
		} else if (this.equals(ADMIN_NONE)) {
			return "none";
		} else if (this.equals(ADMIN_CONTACT)) {
			return "contact";
		} else if (this.equals(ADMIN_FRIEND)) {
			return "friend";
		} else if (this.equals(ADMIN_FAMILY)) {
			return "family";
		} else if (this.equals(ADMIN_SELF)) {
			return "self";
		}
		
		return super.toString();
	}
}

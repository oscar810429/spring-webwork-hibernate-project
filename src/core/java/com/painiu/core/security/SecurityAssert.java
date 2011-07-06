/*
 * @(#)SecurityAssert.java Dec 28, 2006
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;

import org.acegisecurity.AccessDeniedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.Context;
import com.painiu.Painiu;
import com.painiu.core.exception.PrivacyForbiddenException;
import com.painiu.core.model.Privacy;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="SecurityAssert.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: SecurityAssert.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SecurityAssert {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(SecurityAssert.class);

	private static final String DEFAULT_ERROR_MESSAGE = "Access denied";
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	private SecurityAssert() {}
	
	//~ Methods ================================================================
	
	public static void notNull(Object object) {
		notNull(object, DEFAULT_ERROR_MESSAGE);
	}
	
	public static void notNull(Object object, String errorMsg) {
		if (object == null) {
			log.warn("Assertion failed: the required object is null");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void isTrue(boolean expression) {
		isTrue(expression, DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isTrue(boolean expression, String errorMsg) {
		if (!expression) {
			log.warn("Assertion failed: expression is not true");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void isAdministrator() {
		isAdministrator(DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isAdministrator(String errorMsg) {
		if (!SecurityUtils.isAdministrator()) {
			log.warn("Assertion failed: current user is not administrator");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void isStaff() {
		isStaff(DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isAuditingUser() {
		isAuditingUser(DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isAuditingUser(String errorMsg) {
		if (!SecurityUtils.isStaff() ) {
			log.warn("Assertion failed: current user is not staff");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void isStaff(String errorMsg) {
		if (!SecurityUtils.isStaff()) {
			log.warn("Assertion failed: current user is not staff");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void isNotBlocked() {
		isNotBlocked(DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isNotBlocked(String errorMsg) {
		User currentUser = Context.getContext().getCurrentUser();
		if (currentUser != null && currentUser.isInRole(Painiu.FROZEN_ROLE)) {
			log.warn("Assertion failed: current user has been blocked");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void currentUserExists() {
		currentUserExists(DEFAULT_ERROR_MESSAGE);
	}
	
	public static void currentUserExists(String errorMsg) {
		if (Context.getContext().getCurrentUser() == null) {
    		log.warn("Assertion failed: currentUser is not exists.");
    		throw new AccessDeniedException(errorMsg); 
    	}
	}
	
	public static void isOwner(Personal personal) {
		isOwner(personal, DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isOwner(Personal personal, String errorMsg) {
		if (!SecurityUtils.isCurrentUser(personal.getPerson())) {
			log.warn("Assertion failed: current user is not owner of " + personal + "!");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void isCurrentUser(User user) {
		isCurrentUser(user, DEFAULT_ERROR_MESSAGE);
	}
	
	public static void isCurrentUser(User user, String errorMsg) {
		if (!SecurityUtils.isCurrentUser(user)) {
			log.warn("Assertion failed: " + user + " is not current user.");
			throw new AccessDeniedException(errorMsg);
		}
	}
	
	public static void grant(Privacy privacy) {
		grant(privacy, DEFAULT_ERROR_MESSAGE);
	}
	
	public static void grant(Privacy privacy, String errorMsg) {
		Relation relation = RelationContextHolder.getContext().getRelation();
		
		if (!privacy.grant(relation)) {
			log.warn("Assertion failed: privacy forbidden");
			throw new PrivacyForbiddenException(errorMsg);
		}
	}
}

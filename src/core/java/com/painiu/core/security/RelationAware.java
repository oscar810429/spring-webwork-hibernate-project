/*
 * @(#)RelationAware.java 2009-12-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * <a href="RelationAware.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RelationAware.java 35 2010-06-01 01:53:10Z zhangsf $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RelationAware {

}

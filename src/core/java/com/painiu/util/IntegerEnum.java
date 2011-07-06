/**
 * @(#)IntegerEnum.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.util;


/**
 * <p>
 * <a href="IntegerEnum.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: IntegerEnum.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface IntegerEnum<E extends Enum<E> & IntegerEnum<E>> {
	int value();
}

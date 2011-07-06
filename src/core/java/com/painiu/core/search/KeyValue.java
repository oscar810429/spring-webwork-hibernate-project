/**
 * @(#)KeyValue.java Sep 1, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.search;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="KeyValue.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: KeyValue.java 2008-10-07 03:44:39Z Zhang Songfu$
 */
public class KeyValue<K,V> implements Serializable {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = 412146523441483895L;

	//~ Instance fields ========================================================

	private K key;
	
	private V value;
	
	//~ Constructors ===========================================================

	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}
	
	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("key", key).append("value", value).toString();
	}
}

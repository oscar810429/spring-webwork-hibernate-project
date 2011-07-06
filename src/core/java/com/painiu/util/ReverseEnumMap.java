/**
 * @(#)ReverseEnumMap.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <a href="ReverseEnumMap.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ReverseEnumMap.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ReverseEnumMap<V extends Enum<V> & IntegerEnum<V>> {
	private Map<Integer, V> map = new HashMap<Integer, V>();

	public ReverseEnumMap(Class<V> valueType) {
		for (V v : valueType.getEnumConstants()) {
			map.put(v.value(), v);
		}
	}

	public V get(int num) {
		V v = map.get(num);
		if (v == null) {
			throw new IllegalArgumentException("Invalid value of enum type:" + num);
		}
		return v;
	}
}
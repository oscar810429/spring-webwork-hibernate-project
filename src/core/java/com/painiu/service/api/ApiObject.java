/*
 * @(#)ApiObject.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="ApiObject.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiObject.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiObject implements Serializable {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private String name;
	
	private Object value;

	//	private Map attributes;
	private List attributes; // array and element have attributes
	
	private boolean array = false;
	private boolean attribute = false;
	private boolean element = false;
	private boolean text = false;
	
	//~ Constructors ===========================================================

	
	private ApiObject(String name, Object value, boolean array, boolean attribute, boolean element, boolean text) {
		this.name = name;
		this.value = value;
		this.array = array;
		this.attribute = attribute;
		this.element = element;
		this.text = text;
	}
	
	public static ApiObject Array(String name) {
		return Array(name, null);
	}
	
	public static ApiObject Array(String name, Collection collection) {
		return new ApiObject(name, collection, true, false, false, false);
	}
	
	public static ApiObject Attribute(String name) {
		return Attribute(name, null);
	}
	
	public static ApiObject Attribute(String name, Object value) {
		return new ApiObject(name, value, false, true, false, false);
	}
	
	public static ApiObject Element() {
		return Element(null);
	}
	
	public static ApiObject Element(String name) {
		return Element(name, null);
	}
	
	public static ApiObject Element(String name, Object value) {
		return new ApiObject(name, value, false, false, true, false);
	}
	
	public static ApiObject Text(String name, String value) {
		return new ApiObject(name, value, false, false, false, true);
	}
	
	//~ Methods ================================================================

	public String getName() {
		return name;
	}
	
	public ApiObject setName(String name) {
		this.name = name;
		return this;
	}
	
	public Object getValue() {
		return value;
	}
	
	public ApiObject setValue(Object value) {
		this.value = value;
		return this;
	}

	
	public ApiObject addAttribute(String key, Object value) {
		return addAttribute(Attribute(key, value));
	}
	
	public ApiObject addAttribute(ApiObject attribute) {
		if (isAttribute()) {
			throw new RuntimeException("cannot add attribute to an Attribute");
		}
		if (!attribute.isAttribute()) {
			throw new IllegalArgumentException("it is not an attribute object");
		}
		return add(attribute);
	}
	
	public ApiObject addElement(String key, Object value) {
		return addElement(Element(key, value));
	}
	
	public ApiObject addElement(ApiObject element) {
		if (isAttribute()) {
			throw new RuntimeException("cannot add element to an Attribute");
		}
		
		if (!element.isElement()) {
			throw new IllegalArgumentException("it is not an element object");
		}
		
		return add(element);
	}
	
	public ApiObject addText(String key, String value) {
		return addText(ApiObject.Text(key, value));
	}
	
	public ApiObject addText(ApiObject text) {
		if (isAttribute()) {
			throw new RuntimeException("cannot add element to an Attribute");
		}
		if (!text.isText()) {
			throw new IllegalArgumentException("it is not an text object");
		}
		return add(text);
	}
	
	public ApiObject addArray(String name, Collection collection) {
		return addArray(Array(name, collection));
	}
	
	public ApiObject addArray(ApiObject array) {
		if (isAttribute()) {
			throw new RuntimeException("cannot add array to an Attribute");
		}
		if (!array.isArray()) {
			throw new IllegalArgumentException("it is not an array object");
		}
		return add(array);
	}
	
	public ApiObject add(ApiObject object) {
		if (this.isText()) {
			throw new RuntimeException("cannot add object to an Text object");
		}
		if (object.isAttribute()) {
			if (attributes == null) {
				attributes = new ArrayList(5);
			}
			attributes.add(object);
		} else {
			if (isArray()) {
				if (value == null) {
					value = new ArrayList();
				}
				Collection c = (Collection) value;
				c.add(object);
			} else {
				if (value == null) {
					setValue(object);
				} else if (value instanceof Collection) {
					Collection c = (Collection) value;
					c.add(object);
				} else {
					List list = new ArrayList();
					list.add(value);
					list.add(object);
					setValue(list);
				}
			}
		}
		
		return this;
	}
	
	/**
	 * @return Returns the attributes.
	 */
	public List getAttributes() {
		return attributes;
	}

	/**
	 * @return Returns the array.
	 */
	public boolean isArray() {
		return array;
	}
	
	/**
	 * @return Returns the attribute.
	 */
	public boolean isAttribute() {
		return attribute;
	}
	
	/**
	 * @return Returns the element.
	 */
	public boolean isElement() {
		return element;
	}
	
	/**
	 * @return the text
	 */
	public boolean isText() {
		return text;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ApiObject)) {
			return false;
		}
		ApiObject rhs = (ApiObject) object;
		return new EqualsBuilder()
				.append(this.name, rhs.name)
				.append(this.value, rhs.value)
				.append(this.array, rhs.array)
				.append(this.element, rhs.element)
				.append(this.text, rhs.text)
				.append(this.attribute, rhs.attribute).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1134268527, -618862905)
				.append(this.name)
				.append(this.value)
				.append(this.array)
				.append(this.element)
				.append(this.text)
				.append(this.attribute).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("name", this.name)
			.append("value", this.value)
			.append("array", this.array)
			.append("element", this.element)
			.append("text", this.text)
			.append("attribute", this.attribute).toString();
	}
}

/*
 * @(#)Config.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Config.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Config.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Config extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -8338216615298442497L;

	public static class Value {
		private String type;
		private Object value;
		
		public Value() {}
		
		public Value(Object value) {
			this.value = value;
			if (value instanceof String) {
				this.type = "string";
			} else if (value instanceof Number) {
				this.type = "number";
			} else if (value instanceof List) {
				this.type = "list";
			} else if (value instanceof Boolean) {
				this.type = "boolean";
			} else {
				throw new IllegalArgumentException("invalide config value");
			}
		}
		
		public Value(String type, Object value) {
			this.type = type;
			this.value = value;
		}
		
		public Object getValue() {
			return value;
		}
		
		public static Value fromString(String string) {
			int start = string.indexOf('[');
			if (start == -1 || !string.endsWith("]")) {
				throw new IllegalArgumentException("config value format error.");
			}
			String type = string.substring(0, start);
			String content = string.substring(start + 1, string.length() - 1);
			if ("string".equals(type)) {
				return new Value(type, content);
			} else if ("number".equals(type)) {
				try {
					return new Value(type, Long.valueOf(content));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("config value is not a valid number.");
				}
			} else if ("list".equals(type)) {
				String[] items = StringUtils.split(content, ",");
				List list = new ArrayList(items.length);
				Collections.addAll(list, (Object[]) items);
				return new Value(type, list);
			} else if ("boolean".equals(type)) {
				return new Value(type, Boolean.valueOf("true".equals(content)));
			} else {
				throw new IllegalArgumentException("unsupported config value type.");
			}
		}
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(type).append("[");
			if ("string".equals(type) || "number".equals(type) || "boolean".equals(type)) {
				sb.append(value.toString());
			} else if ("list".equals(type)) {
				Collection collection = (Collection) value;
				for (Iterator i = collection.iterator(); i.hasNext();) {
					sb.append(i.next());
					if (i.hasNext()) {
						sb.append(',');
					}
				}
			}
			sb.append(']');
			return sb.toString();
		}
	}
	
	//~ Instance fields ========================================================

	private String key;
	private String description;
	private Value value;
	private Integer version;
	
	//~ Constructors ===========================================================

	public Config() {}
	
	public Config(String key) {
		this(key, null);
	}
	
	public Config(String key, Object value) {
		setKey(key);
		if (value != null) {
			setValue(new Value(value));
		}
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key The key to set.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return Returns the value.
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(Value value) {
		this.value = value;
	}
	
	/**
	 * @return Returns the version.
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version The version to set.
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Config)) {
			return false;
		}
		Config rhs = (Config) object;
		return new EqualsBuilder()
				.append(this.key, rhs.key)
				.append(this.value, rhs.value)
				.append(this.version, rhs.version)
				.append(this.description, rhs.description).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(2074286219, -1996533523)
				.append(this.key).append(this.value)
				.append(this.version)
				.append(this.description).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("key", this.key)
				.append("value", this.value)
				.append("description", this.description).toString();
	}

	public static void main(String[] args) {
		System.out.println(new Value("number[232434234]").getValue());
	}
}

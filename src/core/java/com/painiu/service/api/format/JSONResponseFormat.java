/*
 * @(#)JSONResponseFormat.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.painiu.service.api.ApiException;
import com.painiu.service.api.ApiObject;
import com.painiu.service.api.Call;
import com.painiu.service.api.ResponseFormat;

/**
 * <p>
 * <a href="JSONResponseFormat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: JSONResponseFormat.java 35 2010-06-01 01:53:10Z zhangsf $
 */
@SuppressWarnings("unchecked")
public class JSONResponseFormat implements ResponseFormat {

	public static final String CONTENT_TYPE = "text/plain; charset=utf-8"; 
	
	/*
	 * @see com.painiu.core.service.api.ResponseFormat#getContentType()
	 */
	public String getContentType() {
		return CONTENT_TYPE;
	}

	/*
	 * @see com.painiu.core.service.api.ResponseFormat#format(java.io.Writer, java.lang.Object)
	 */
	public void format(Writer out, Object object) throws IOException {
		JSONObject j = null;
		
		try {
			j = convertToJSONObject(object);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		if (j != null) {
			out.write(j.toString());
		}
	}

	private static JSONObject convertToJSONObject(Object object) throws JSONException {
		JSONObject j = new JSONObject();
		
		if (object == null) {
			j.put("stat", "ok");
		} else if (object instanceof ApiException) {
			createErrorJSONObject(j, (ApiException) object);
		} else if (object instanceof ApiObject){
			j.put("stat", "ok");
			createJSONObject(j, (ApiObject) object, false);
		} else {
			throw new IllegalArgumentException("unsupported object");
		}
		
		return j;
	}
	
	private static void createErrorJSONObject(JSONObject parent, ApiException e) throws JSONException {
		parent.put("stat", "fail");
		
		JSONObject err = new JSONObject().put("code", e.getCode()).put("msg", e.getMessage());
		
		if (e.getDetail() != null) {
			err.put("detail", e.getDetail());
		}
		
		if (e.getCall() != null) {
			Call call = e.getCall();
			
			JSONObject params = new JSONObject();
			
			for (Iterator i = call.getParameters().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				if (!"method".equals(key)) {
					if (value instanceof Object[]) {
						Object[] arrayValue = (Object[]) value;
						for (int j = 0; j < arrayValue.length; j++) {
							params.put(key, arrayValue[j]);
						}
					} else {
						params.put(key, value);
					}
				}
			}
			err.put("debug", 
					new JSONObject().put("method", call.getFullMethodName())
									.put("params", params));
			
		}
		
		parent.put("err", err);
	}
	
	private static void createJSONObject(JSONObject parent, ApiObject object, boolean itemOfArray) throws JSONException {
		if (object.isAttribute()) {
			addAttribute(parent, object);
			
		} else {
			if (object.isArray()) {
				Collection value = (Collection) object.getValue();
				
				List attributes = object.getAttributes();
				
				if (attributes == null) {
					parent.put(object.getName(), createJSONArray(value));
				} else {
					JSONObject j = new JSONObject();
					parent.put(object.getName(), j);
					
					for (Iterator i = attributes.iterator(); i.hasNext();) {
						ApiObject attr = (ApiObject) i.next();
						addAttribute(j, attr);
					}
					
					String name = "array";
					
					if (value != null && value.size() > 0) {
						name = ((ApiObject) value.iterator().next()).getName();
						// TODO 
						name = name + "s";
					}
					
					j.put(name, createJSONArray(value));
				}
			} else if (object.isElement()) {
				if (isSimpleElement(object)) {
					addAttribute(parent, object);

				} else {
					JSONObject j = parent;
					
					if (!itemOfArray && object.getName() != null) {
						j = new JSONObject();
						parent.put(object.getName(), j);
					}
					
					List attributes = object.getAttributes();
					
					if (attributes != null) {
						for (Iterator i = attributes.iterator(); i.hasNext();) {
							ApiObject attr = (ApiObject) i.next();
							createJSONObject(j, attr, false);
						}
					}
					
					Object value = object.getValue();
					
					if (value instanceof Collection) {
						Collection c = (Collection) value;
						
						for (Iterator i = c.iterator(); i.hasNext();) {
							createJSONObject(j, (ApiObject) i.next(), false);
						}
					} else if (value instanceof ApiObject) {
						createJSONObject(j, (ApiObject) value, false);
					} else {
						j.put("value", value);
					}
				}
			} else if (object.isText()) {
				parent.put(object.getName(), object.getValue());
			}
		}
	
	}
	
	private static boolean isSimpleElement(ApiObject object) {
		return (object.getAttributes() == null) && 
				!(object.getValue() instanceof Collection) && 
				!(object.getValue() instanceof ApiObject);
	}
	
	private static JSONObject addAttribute(JSONObject j, ApiObject object) throws JSONException {
		String name = object.getName();
		Object value = object.getValue();
		
		if (value instanceof Integer) {
			j.put(name, ((Integer) value).intValue());
		} else if (value instanceof Long) {
			j.put(name, ((Long) value).longValue());
		} else {
			j.put(name, value == null ? null : value.toString());
		}
		
		return j;
	}
	
	private static JSONArray createJSONArray(Collection collection) throws JSONException {
		JSONArray ja = new JSONArray();
		
		if (collection != null) {
			for (Iterator i = collection.iterator(); i.hasNext();) {
				ApiObject object = (ApiObject) i.next();
				
				if (isSimpleElement(object)) {
					ja.put(object.getValue());
				} else {
					JSONObject item = new JSONObject();
					
					createJSONObject(item, object, true);
					ja.put(item);
				}
			}
		}
		
		return ja;
	}
}

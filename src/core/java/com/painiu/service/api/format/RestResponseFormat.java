/*
 * @(#)RestResponseFormat.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.format;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.painiu.service.api.ApiException;
import com.painiu.service.api.ApiObject;
import com.painiu.service.api.Call;
import com.painiu.service.api.ResponseFormat;

/**
 * <p>
 * <a href="RestResponseFormat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RestResponseFormat.java 35 2010-06-01 01:53:10Z zhangsf $
 */
@SuppressWarnings("unchecked")
public class RestResponseFormat implements ResponseFormat {

	public static final String CONTENT_TYPE = "text/xml; charset=utf-8"; 
	
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
		if (object == null) {
			writeEmptyOK(out);
		} else if (object instanceof ApiException) {
			writeError(out, (ApiException) object);
		} else if (object instanceof ApiObject){
			writeObject(out, (ApiObject) object);
		} else {
			throw new IllegalArgumentException("unsupported object");
		}
	}
	
	protected static void writeObject(Writer writer, ApiObject object) throws IOException {
		Document doc = createDocument();
		Element root = doc.addElement("rsp");
		root.addAttribute("stat", "ok");
		
		createElement(root, object);
		
		doc.write(writer);
	}
	
	protected static void createElement(Element parent, ApiObject object) {
		if (object.isAttribute()) {
			parent.addAttribute(object.getName(), object.getValue() == null ? "" : object.getValue().toString());
			
		} else {
			Element element = null;
	
			if (object.getName() != null) {
				element = parent.addElement(object.getName());

				List attributes = object.getAttributes();
				if (attributes != null) {
					for (Iterator i = attributes.iterator(); i.hasNext();) {
						ApiObject attr = (ApiObject) i.next();
						element.addAttribute(attr.getName(), attr.getValue() == null ? "" : attr.getValue().toString());
					}
				}
			} else {
				element = parent;
			}
			
			if (object.isArray()) {
				Collection value = (Collection) object.getValue();
				
				if (value == null) {
					value = new ArrayList(0);
				}
				for (Iterator i = value.iterator(); i.hasNext();) {
					ApiObject item = (ApiObject) i.next();
					createElement(element, item);
				}
				
			} else if (object.isElement()) {
				Object value = object.getValue();
				
				if (value != null) {
					
					if (value instanceof Collection) {
						Collection c = (Collection) value;
						
						for (Iterator i = c.iterator(); i.hasNext();) {
							ApiObject item = (ApiObject) i.next();
							createElement(element, item);
						}
					} else if (value instanceof ApiObject) {
						ApiObject valueObj = (ApiObject) value;
						if (valueObj.isText()) {
							element.addText((String) valueObj.getValue());
						} else {
							createElement(element, (ApiObject) value);
						}
					} else {
						element.addText(value.toString());
					}
				}
			} else if (object.isText()) {
				element.addText((String) object.getValue());
			}
		}
			
	}
	
	protected static void writeError(Writer writer, ApiException e) throws IOException {
		Document doc = createDocument();
		Element root = doc.addElement("rsp");
		root.addAttribute("stat", "fail");
		Element err = root.addElement("err")
							.addAttribute("code", e.getCode())
							.addAttribute("msg", e.getMessage());
		if (e.getDetail() != null) {
			err.addText(e.getDetail());
		}
		if (e.getCall() != null) {
			Call call = e.getCall();
			
			Element debug = err.addElement("debug");
			debug.addElement("method").setText(call.getFullMethodName());
			Element params = debug.addElement("params");
			
			for (Iterator i = call.getParameters().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				if (!"method".equals(key)) {
					if (value instanceof Object[]) {
						Object[] arrayValue = (Object[]) value;
						for (int j = 0; j < arrayValue.length; j++) {
							params.addElement("param").addAttribute("name", key)
									.addAttribute("value", arrayValue[j].toString());
						}
					} else {
						params.addElement("param").addAttribute("name", key)
								.addAttribute("value", value.toString());
					}
				}
			}
		}
		
		doc.write(writer);
	}
	
	protected static void writeEmptyOK(Writer writer) throws IOException {
		Document doc = createDocument();
		Element root = doc.addElement("rsp");
		root.addAttribute("stat", "ok");
		doc.write(writer);
	}
	
	protected static Document createDocument() {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("utf-8");
		return doc;
	}
}

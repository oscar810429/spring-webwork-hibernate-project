/**
 * @(#)TagUtils.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * <a href="TagUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagUtils.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class TagUtils {

	private static final char QUOTE = '\"';
	private static final char SPACE = ' ';
	//private static final char WSPACE = '"';
	private static final char COMMA	= ',';
	//private static final char WCOMMA = '，';
	
	
	/**
	 * Parse tags from user input. <br />
	 * 
	 * <ul>
	 *   <li>input: "buddy icon yupoo"    =>  ["buddy", "icon", "yupoo"] </li>
	 *   <li>input: "\"buddy icon\" yupoo"  =>  ["buddy icon", "yupoo"] </li>
	 * </ul>
	 * 
	 * @param input
	 * @return
	 */
	public static String[] parseTags(String input) {
		if (input == null) {
			return new String[0];
		}
		
		List tags = new ArrayList();
		
		int quotes = StringUtils.countMatches(input, String.valueOf(QUOTE));
		int meets = 0;
		
		char[] chars = input.toCharArray();
		StringBuffer buf = new StringBuffer();
		
    	for (int i = 0; i < chars.length; i++) {
    		if (chars[i] == SPACE || chars[i] == COMMA ) {
    			if (meets % 2 == 1 && meets < quotes) { // we are in quotes
    				buf.append(chars[i]);
    			} else {
    				if (buf.length() > 0) {
    					tags.add(filter(buf.toString().trim()));
    					buf = new StringBuffer();
    				}
    			}
    		} else if (chars[i] == QUOTE) {
    			meets++;
    		} else {
    			buf.append(chars[i]);
    		}
    	}
    	
    	if (buf.length() > 0) {
    		tags.add(filter(buf.toString().trim()));
    	}
		
		return (String[]) tags.toArray(new String[tags.size()]);
	}
	
	private static String filter(String tag) {
		StringBuffer sb = new StringBuffer(tag.length());
		char[] chars = tag.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			int value = chars[i];
			if (value > 127 || (value >= 97 && value <= 122) 
					|| (value >= 65 && value <= 90) 
					|| (value >= 48 && value <= 57)
					|| value == 32) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}
	
	public static String toString(Collection softwareTags) {
		StringBuffer sb = new StringBuffer();
		for (Iterator i = softwareTags.iterator(); i.hasNext();) {
			Object softwareTag = i.next();
			String tagName = null;
			/*if (softwareTag instanceof SoftwareTag) {
				tagName = ((SoftwareTag) softwareTag).getTagName();
			} else {
				tagName = softwareTag.toString();
			}*/
			
			sb.append(SPACE);
			if (tagName.indexOf(SPACE) != -1) {
				sb.append(QUOTE).append(tagName).append(QUOTE);
			} else {
				sb.append(tagName);
			}
		}	
		return sb.toString().trim();
	}
	
	public static void main(String[] args) {
	    String[] result = parseTags("nihao, aaa \"hello nihao\"");
        for (int i = 0; i < result.length; i++) {
            String string = result[i];
            System.out.println(string);
        }
    }

	
}

/*
 * @(#)NewlineTextFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.text;

/**
 * <p>
 * <a href="NewlineTextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: NewlineTextFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class NewlineTextFilter implements TextFilter {

    private static final char[] BR_TAG = "<br />".toCharArray();
    
    /*
     * @see com.yupoo.webapp.views.text.TextFilter#filter(java.lang.String)
     */
    public String filter(String text) {
        if (text == null) {
            return null;
        }

        char [] chars = text.toCharArray();
        int cur = 0;
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);

        // Loop through each character lookin for newlines.
        for (int i = 0; i < len; i++) {
            // If we've found a Unix newline, add BR tag.
            if (chars[i] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                cur = i + 1;
            }
            // If we've found a Windows newline, add BR tag.
            else if (chars[i] == '\r' && i < len - 1 && chars[i+1] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                i++;
                cur = i + 1;
            }
        }
        // Add whatever chars are left to buffer.
        buf.append(chars, cur, len-cur);
        return buf.toString();
    }

}

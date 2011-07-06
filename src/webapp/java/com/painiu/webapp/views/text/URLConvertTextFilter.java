/*
 * @(#)URLConvertTextFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.text;

/**
 * <p>
 * <a href="URLConvertTextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: URLConvertTextFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class URLConvertTextFilter implements TextFilter {
	
	private static final int CHAR_LIMIT = 50;
	
    private boolean newWindowEnabled = true;
    
    /**
     * Returns true if URL clicks will open in a new window.
     *
     * @return true if new window mode is enabled.
     */
    public boolean isNewWindowEnabled() {
        return newWindowEnabled;
    }

    /**
     * Enables or disables the new window mode. When active, URL clicks will
     * open in a new window.
     *
     * @param enabled true if new window mode should be enabled.
     */
    public void setNewWindowEnabled(boolean enabled) {
        this.newWindowEnabled = enabled;
    }
    
    public String filter(String text) {
        return convertURL(text);
    }

    /**
     * This method takes a string which may contain URLs and replaces them with
     * working links. It does this by adding the html tags &lt;a href&gt; and
     * &lt;/a&gt;.
     * <p>
     * The patterns recognized are <code>ftp://path-of-url</code>,
     * <code>http://path-of-url</code>, <code>https://path-of-url</code>
     * <code>[url path-of-url]descriptive text[/url]</code>
     * and <code>[url=path-of-url]descriptive text[/url]</code> the
     * <code>[url]</code> allows any path to be defined as link.
     * 
     * @param input
     *            the text to be converted.
     * @return the input string with the URLs replaced with links.
     */
    private String convertURL(String input) {
        // Check if the string is null or zero length
        // -- if so, return what was sent in.
        if (input == null || input.length() == 0) {
            return input;
        }

        // Build the response in a buffer
        StringBuffer buf = new StringBuffer(input.length() + 25);
        char[] chars = input.toCharArray();
        int len = input.length();

        int index = -1, i = 0;
        int j = 0, oldend = 0;
        int u1, u2;
        char cur;

        // Handle the occurences of the ftp:// , http:// , https:// patterns
        // and the [url] pattern.
        while (++index < len) {
            cur = chars[i = index];

            // save valuable cpu resources by expanding the tests here instead
            // of calling String.indexOf()
            j = -1;
            if ((cur == 'f' && index < len - 6 && chars[++i] == 't' && chars[++i] == 'p')
                    || (cur == 'h' && (i = index) < len - 7
                            && chars[++i] == 't' && chars[++i] == 't'
                            && chars[++i] == 'p' && (chars[++i] == 's' || chars[--i] == 'p'))) {
                if (i < len - 4 && chars[++i] == ':' && chars[++i] == '/'
                        && chars[++i] == '/') {
                    j = ++i;
                }
            }

            // did we find http or ftp?
            if (j > 0) {
                // check context, dont handle patterns preceded by any of '"<=
                if (index == 0
                        || ((cur = chars[index - 1]) != '\'' && cur != '"'
                                && cur != '<' && cur != '=')) {
                    cur = chars[j];
                    // now collect url pattern upto next " <\n\r\t"
                    while (j < len) {
                        // Is a space?
                        if (cur == ' ')
                            break;
                        if (cur == '\t')
                            break;
                        // Is a quote?
                        if (cur == '\'')
                            break;
                        if (cur == '\"')
                            break;
                        // Is html?
                        if (cur == '<')
                            break;
                        if (cur == '[')
                            break;
                        // Is a Unix newline?
                        if (cur == '\n')
                            break;
                        // Is Win32 newline?
                        if (cur == '\r' && j < len - 1 && chars[j + 1] == '\n')
                            break;

                        j++;
                        if (j < len) {
                            cur = chars[j];
                        }
                    }
                    // Check the ending character of the URL. If it's a ".,)]"
                    // then we'll remove that part from the URL.
                    cur = chars[j - 1];
                    if (cur == '.' || cur == ',' || cur == ')' || cur == ']') {
                        j--;
                    }
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<a href=\"");
                    buf.append(chars, index, j - index);
                    buf.append('\"');
                    if (newWindowEnabled) {
                        buf.append(" target=\"_blank\"");
                    }
                    buf.append('>');
                    if ((j - index) > CHAR_LIMIT) {
                    	buf.append(chars, index, CHAR_LIMIT).append("...");
                    } else {
                    	buf.append(chars, index, j - index);
                    }
                    buf.append("</a>");
                } else {
                    buf.append(chars, oldend, j - oldend);
                }
                oldend = index = j;
            } else if (cur == '[' && index < len - 6
                    && chars[i = index + 1] == 'u' && chars[++i] == 'r'
                    && chars[++i] == 'l'
                    && (chars[++i] == '=' || chars[i] == ' ')) {
                j = ++i;
                u1 = u2 = input.indexOf("]", j);
                if (u1 > 0) {
                    u2 = input.indexOf("[/url]", u1 + 1);
                }
                if (u2 < 0) {
                    buf.append(chars, oldend, j - oldend);
                    oldend = j;
                } else {
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<a href =\"");
                    buf.append(input.substring(j, u1).trim());
                    if (newWindowEnabled) {
                        buf.append("\" target=\"_blank");
                    }
                    buf.append("\">");
                    String label = input.substring(u1 + 1, u2).trim();
                    if (label.length() > CHAR_LIMIT) {
                    	label = label.substring(0, CHAR_LIMIT) + "...";
                    }
                    buf.append(label);
                    buf.append("</a>");
                    oldend = u2 + 6;
                }
                index = oldend;
            }
        }
        if (oldend < len) {
            buf.append(chars, oldend, len - oldend);
        }
        return buf.toString();
    }
    
    public static void main(String[] args) {
        TextFilter f = new URLConvertTextFilter();
        System.out.println(f.filter("test &lt;a href=\"http://www.yupoo.com/hib\"&gt;zola.com/</a>\nni hao."));
        System.out.println(f.filter("test \"http://zola.comasdfasf.com/?id=sadfjakslfjafja\nni hao."));
        System.out.println(f.filter("[url http://zola.comasdfasf.com/?id=sadfjakslfjafja;fjdasfjas;fasfkkjkjfksa]HELLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO[/url]\nni hao."));
    }
}

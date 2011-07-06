/*
 * @(#)HtmlUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <h3>Some HTML is OK</h3>
 * Here's the list: <br />
 * <ul>
 * <li>'a' => 'href', 'target' </li>
 * <li>'b' => None </li>
 * <li>'blockquote' => None </li>
 * <li>'em' => None </li>
 * <li>'i' => None </li>
 * <li>'img' => 'src', 'width', 'height', 'alt', 'title' </li>
 * <li>'strong' => None </li>
 * <li>'u' => None </li>
 * </ul>
 * <p>
 * <a href="HtmlUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: HtmlUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public abstract class HtmlUtils {
	
	protected static final String[] TAGS = {
			"a", "address", "applet", "area", "b", "base", "basefont", "big", "blockquote",
			"body", "br", "caption", "center", "cite", "code", "dd", "dfn", "dir", "div", 
			"dl", "dt", "em", "font", "form", "frame", "frameset", "h1", "h2", "h3", "h4",
			"h5", "h6", "head", "hr", "html", "i", "img", "input", "isindex", "kbd", "li",
			"link", "map", "menu", "meta", "nobr", "noframes", "object", "ol", "option", "p",
			"param", "pre", "samp", "script", "select", "small", "span", "strike", "s", 
			"strong", "style", "sub", "sup", "table", "td", "textarea", "th", "title", "tr",
			"tt", "u", "ul", "var"
	};
    
    protected static final String[] ALLOWED_TAGS = {
            "a", "b", "blockquote", "em", "i", "img", "strong", "u"
    };
    
    protected static final String[] ESCAPE_TAGS = {
    		"quot", "amp", "lt", "gt"
    };
    
    protected static final Map ALLOWED_ATTRIBUTES = new HashMap(2);
    
    static {
        ALLOWED_ATTRIBUTES.put("a", new String[] { "href", "target" });
        ALLOWED_ATTRIBUTES.put("img", new String[] { "src", "width", "height", "alt", "title" });
    }

    protected static final int MAX_LENGTH   = 300;
    protected static final int AND          = '&';
    protected static final int LT           = '<';
    protected static final int GT           = '>';
    protected static final int SPACE        = ' ';
    protected static final int EQUAL        = '=';
    protected static final int SLASH        = '/';
    protected static final int SEMICOLON    = ';';
    protected static final int SINGLE_QUOTE = '\'';
    protected static final int DOUBLE_QUOTE = '\"';
    
    protected static final int OFFSET            = '1';
    protected static final int MAX_INDEX         = 'z';
    protected static final int MAX_TAG_LENGTH    = 10;
    protected static final int MAX_ESCAPE_LENGTH = 4;
    protected static final int[][] _tagChars = new int[MAX_TAG_LENGTH][MAX_INDEX - OFFSET + 1];
    protected static final int[][] _allowedTagChars = new int[MAX_TAG_LENGTH][MAX_INDEX - OFFSET + 1];
    protected static final int[][] _escapeChars = new int[MAX_ESCAPE_LENGTH][MAX_INDEX - OFFSET + 1];

    /**
     * We use arrays of char in the lookup table because it is faster
     * appending this to a StringBuffer than appending a String
     */
    protected static final char[][] _stringChars = new char[MAX_LENGTH][];

    static {
        // Initialize the mapping table
        initMapping();
        initTagIndex();
        initAllowedTagIndex();
        initEscapeIndex();
    }

    /**
     * Call escapeHTML(s, false)
     */
    public static final String escapeHTML(String s) {
        return escapeHTML(s, false);
    }

    /**
     * Escape HTML.
     *
     * @param s           string to be escaped
     * @param escapeEmpty if true, then empty string will be escaped.
     */
    public static final String escapeHTML(String s, boolean escapeEmpty) {
        int len = s.length();

        if (len == 0) {
            return s;
        }

        if (!escapeEmpty) {
            String trimmed = s.trim();

            if ((trimmed.length() == 0) || ("\"\"").equals(trimmed)) {
                return s;
            }
        }

        int i = 0;

        // First loop through String and check if escaping is needed at all
        // No buffers are copied at this time
        do {
            int index = s.charAt(i);

            if (index >= MAX_LENGTH) {
                if (index != 0x20AC) { // If not euro symbol

                    continue;
                }

                break;
            } else if (_stringChars[index] != null) {
                break;
            }
        } while (++i < len);

        // If the check went to the end with no escaping then i should be == len now
        // otherwise we must continue escaping for real
        if (i == len) {
            return s;
        }

        // We found a character to escape and broke out at position i
        // Now copy all characters before that to StringBuffer sb
        // Since a char[] will be used for copying we might as well get
        // a complete copy of it so that we can use array indexing instead of charAt
        StringBuffer sb = new StringBuffer(len + 40);
        char[] chars = new char[len];

        // Copy all chars from the String s to the chars buffer
        s.getChars(0, len, chars, 0);

        // Append the first i characters that we have checked to the resulting StringBuffer
        sb.append(chars, 0, i);

        int last = i;
        char[] subst;
        
        int firstGtIdx;
        int closeTagIdx;
        int tagLength;
        
        int tagStart;
        
        int j, x;

        for (; i < len; i++) {
            int index = chars[i];

            if (index < MAX_LENGTH) {
                boolean inAllowedTag = false;
                
                if (index == LT) { // we got a '<'
                    if (i + 6 < chars.length) {
                        // search forward, to find tag...>...</tag>
                        firstGtIdx = -1;
                        closeTagIdx = -1;
                        tagLength = -1;
                        
                        tagStart = i + 1;
                        
                        j = tagStart;
                        
                        for (; j < chars.length; j++) {
                            char c = chars[j];
                            
                            if (c == GT && j > tagStart) {
                                if (firstGtIdx == -1) { // we got first >
                                    firstGtIdx = j;
                                    
                                    if (chars[j - 1] == SLASH) { // empty body tag like <img src="" />
                                        if (tagLength == -1) {
                                            tagLength = j - tagStart - 1;
                                        }
                                        closeTagIdx = j - 1;
                                        inAllowedTag = true;
                                        break;
                                    }
                                }
                                if (tagLength == -1) {
                                    tagLength = j - tagStart;
                                }
                            } else if (c == SPACE && j > tagStart) {
                                if (tagLength == -1 && firstGtIdx == -1) {
                                    tagLength = j - tagStart;
                                }
                            } else if (c == LT) {
                                if (firstGtIdx == -1) { // got a < before first >
                                    break ;
                                }
								int minLength = j + tagLength + 2; // j + "/tag>".length
								if (minLength < chars.length) { 
								    boolean found = false;
								    if (chars[j + 1] == SLASH && chars[minLength] == GT) {
								        for (int k = 0; k < tagLength; k++) {
								            if (chars[j + 2 + k] != chars[tagStart + k]) {
								                found = false;
								                break ;
								            }
								            if (k == tagLength - 1) {
								                found = true;
								            }
								        }
								    }
								    if (found) { // ok, we got close tag
								        closeTagIdx = j;
								        inAllowedTag = true;
								        break;
								    }
								} else {
								    break;
								}
                            } else if (tagLength == -1) {
                                if (j - tagStart >= _allowedTagChars.length) {
                                    break ;
                                }
                                
                                x = Character.toLowerCase(c);
                                
                                if (x < OFFSET || x > MAX_INDEX) {
                                    break ;
                                }
                                
                                if (_allowedTagChars[j - tagStart][x - OFFSET] == 0) {
                                    break ;
                                }
                            }
                        }
                        
                        // we got an allowed tag. format it, filter attributes not allowed.
                        if (inAllowedTag) {
                            if (i > last) {
                                sb.append(chars, last, i - last);
                            }
                            
                            String tagName = new String(chars, tagStart, tagLength);
                            String body = null;
                            
                            if (closeTagIdx > firstGtIdx) {
                                body = new String(chars, firstGtIdx + 1, closeTagIdx - firstGtIdx -1);
                            }
                            
                            Map attributes = null;
                            
                            int attriStart = tagStart + tagLength;
                            
                            if (firstGtIdx > attriStart) {
                                // parse attributes
                                int doubleQuotes = 0;
                                int equals = 0;
                                
                                for (int k = attriStart; k < firstGtIdx; k++) {
                                    if (chars[k] == DOUBLE_QUOTE) {
                                        doubleQuotes++;
                                    } else if (chars[k] == EQUAL) {
                                        equals++;
                                    }
                                }
                                
                                if (equals > 0) {
                                	attributes = new HashMap(equals);
                                	String key = null;
                                	
                                	int lastIdx = attriStart;
                                	int meets = 0;
                                	
                                	int k = attriStart;
                                	for (; k < firstGtIdx; k++) {
                                		char c = chars[k];
                                		
                                		if (Character.isSpaceChar(c)) {
                                			if (meets % 2 == 0) { // we outside quotes
                                				if (key != null) {
                                					attributes.put(key.toLowerCase(), String.valueOf(chars, lastIdx, k - lastIdx));
                                					key = null;
                                				}
                                				lastIdx = k + 1;
                                			}
                                		} else if (c == DOUBLE_QUOTE) {
                                			meets++;
                                			if (meets % 2 == 0 && key != null) {
                                				if (k > lastIdx) {
                                					attributes.put(key.toLowerCase(), String.valueOf(chars, lastIdx, k - lastIdx));
                                					key = null;
                                				}
                                			}
                                			lastIdx = k + 1;
                                		} else if (c == EQUAL) {
                                			if (meets % 2 == 0) { // we outside quotes
                                				key = null;
                                				if (k > lastIdx) {
                                					key = String.valueOf(chars, lastIdx, k - lastIdx);
                                				}
                                				lastIdx = k + 1;
                                			}
                                		}
                                	}
                                	
                                	if (k > lastIdx && key != null) {
                                		attributes.put(key.toLowerCase(), String.valueOf(chars, lastIdx, k - lastIdx));
                                	}
                                
                                }
                            }
                            
                            Tag tag = new Tag(tagName, body, attributes);
                            sb.append(tag.toString());
                            
                            if (closeTagIdx > firstGtIdx) {
                                i = closeTagIdx + tagLength + 2;
                            } else {
                                i = firstGtIdx;
                            }
                            last = i + 1;
                        }
                    }
                } else if (index == AND) {
                	for (int k = 0; (k < MAX_ESCAPE_LENGTH && i + k + 1 < chars.length); k++) {
                		char c = chars[i + k + 1];
                		if (c == SEMICOLON) {
                			inAllowedTag = true;
                		} else {
                			x = Character.toLowerCase(c);
                            
                            if (x < OFFSET || x > MAX_INDEX) {
                                break ;
                            }
                            
                            if (_escapeChars[k][x - OFFSET] == 0) {
                                break ;
                            }
                		}
                	}
                }
                
                if (!inAllowedTag) {
                    subst = _stringChars[index];
                    
                    // It is faster to append a char[] than a String which is why we use this
                    if (subst != null) {
                        if (i > last) {
                            sb.append(chars, last, i - last);
                        }
                        
                        sb.append(subst);
                        last = i + 1;
                    }
                }
            }
            // Check if it is the euro symbol. This could be changed to check in a second lookup
            // table in case one wants to convert more characters in that area
            else if (index == 0x20AC) {
                if (i > last) {
                    sb.append(chars, last, i - last);
                }

                sb.append("&euro;");
                last = i + 1;
            }
        }

        if (i > last) {
            sb.append(chars, last, i - last);
        }

        return sb.toString();
    }
    
    /**
     *  a  b  c  d  e  f ...
     * [1, 1, 0, 0, 1, 1 ...]
     * [1, 0, 1, 1, 1, 0 ...]
     */
    protected static void initTagIndex() {    	
    	for (int i = 0; i < _tagChars.length; i++) {
    		for (int j = 0; j < TAGS.length; j++) {
    			String tag = TAGS[j];
    			
    			if (i < tag.length()) {
    				int index = Character.toLowerCase(tag.charAt(i));
    				_tagChars[i][index - OFFSET] = 1;
    			}
    		}
    	}
    }
    
    protected static void initAllowedTagIndex() {
        for (int i = 0; i < _allowedTagChars.length; i++) {
            for (int j = 0; j < ALLOWED_TAGS.length; j++) {
                String tag = ALLOWED_TAGS[j];
                
                if (i < tag.length()) {
                    int index = Character.toLowerCase(tag.charAt(i));
                    _allowedTagChars[i][index - OFFSET] = 1;
                }
            }
        }
    }
    
    protected static void initEscapeIndex() {
    	for (int i = 0; i < _escapeChars.length; i++) {
    		for (int j = 0; j < ESCAPE_TAGS.length; j++) {
				String tag = ESCAPE_TAGS[j];
				
				if (i < tag.length()) {
					int index = Character.toLowerCase(tag.charAt(i));
					_escapeChars[i][index - OFFSET] = 1;
				}
			}
    	}
    }
    
    protected static void addMapping(int c, String txt, String[] strings) {
        strings[c] = txt;
    }

    protected static void initMapping() {
        String[] strings = new String[MAX_LENGTH];

        addMapping(0x22, "&quot;", strings); // "
        addMapping(0x26, "&amp;", strings); // &
        addMapping(0x3c, "&lt;", strings); // <
        addMapping(0x3e, "&gt;", strings); // >

        addMapping(0xa1, "&iexcl;", strings); //
        addMapping(0xa2, "&cent;", strings); //
        addMapping(0xa3, "&pound;", strings); //
        addMapping(0xa9, "&copy;", strings); // ?
        addMapping(0xae, "&reg;", strings); // ?
        addMapping(0xbf, "&iquest;", strings); //

        addMapping(0xc0, "&Agrave;", strings); // ?
        addMapping(0xc1, "&Aacute;", strings); // ?
        addMapping(0xc2, "&Acirc;", strings); // ?
        addMapping(0xc3, "&Atilde;", strings); // ?
        addMapping(0xc4, "&Auml;", strings); // ?
        addMapping(0xc5, "&Aring;", strings); // ?
        addMapping(0xc6, "&AElig;", strings); // ?
        addMapping(0xc7, "&Ccedil;", strings); // ?
        addMapping(0xc8, "&Egrave;", strings); //
        addMapping(0xc9, "&Eacute;", strings); //
        addMapping(0xca, "&Ecirc;", strings); //
        addMapping(0xcb, "&Euml;", strings); //
        addMapping(0xcc, "&Igrave;", strings); //
        addMapping(0xcd, "&Iacute;", strings); //
        addMapping(0xce, "&Icirc;", strings); //
        addMapping(0xcf, "&Iuml;", strings); //

        addMapping(0xd0, "&ETH;", strings); //
        addMapping(0xd1, "&Ntilde;", strings); //
        addMapping(0xd2, "&Ograve;", strings); //
        addMapping(0xd3, "&Oacute;", strings); //
        addMapping(0xd4, "&Ocirc;", strings); //
        addMapping(0xd5, "&Otilde;", strings); //
        addMapping(0xd6, "&Ouml;", strings); // ?
        addMapping(0xd7, "&times;", strings); //
        addMapping(0xd8, "&Oslash;", strings); //
        addMapping(0xd9, "&Ugrave;", strings); //
        addMapping(0xda, "&Uacute;", strings); //
        addMapping(0xdb, "&Ucirc;", strings); //
        addMapping(0xdc, "&Uuml;", strings); //
        addMapping(0xdd, "&Yacute;", strings); //
        addMapping(0xde, "&THORN;", strings); //
        addMapping(0xdf, "&szlig;", strings); //

        addMapping(0xe0, "&agrave;", strings); //
        addMapping(0xe1, "&aacute;", strings); //
        addMapping(0xe2, "&acirc;", strings); //
        addMapping(0xe3, "&atilde;", strings); //
        addMapping(0xe4, "&auml;", strings); // ?
        addMapping(0xe5, "&aring;", strings); // ?
        addMapping(0xe6, "&aelig;", strings); //
        addMapping(0xe7, "&ccedil;", strings); //
        addMapping(0xe8, "&egrave;", strings); //
        addMapping(0xe9, "&eacute;", strings); //
        addMapping(0xea, "&ecirc;", strings); //
        addMapping(0xeb, "&euml;", strings); //
        addMapping(0xec, "&igrave;", strings); //
        addMapping(0xed, "&iacute;", strings); //
        addMapping(0xee, "&icirc;", strings); //
        addMapping(0xef, "&iuml;", strings); //

        addMapping(0xf0, "&eth;", strings); //
        addMapping(0xf1, "&ntilde;", strings); //
        addMapping(0xf2, "&ograve;", strings); //
        addMapping(0xf3, "&oacute;", strings); //
        addMapping(0xf4, "&ocirc;", strings); //
        addMapping(0xf5, "&otilde;", strings); //
        addMapping(0xf6, "&ouml;", strings); // ?
        addMapping(0xf7, "&divide;", strings); //
        addMapping(0xf8, "&oslash;", strings); //
        addMapping(0xf9, "&ugrave;", strings); //
        addMapping(0xfa, "&uacute;", strings); //
        addMapping(0xfb, "&ucirc;", strings); //
        addMapping(0xfc, "&uuml;", strings); //
        addMapping(0xfd, "&yacute;", strings); //
        addMapping(0xfe, "&thorn;", strings); //
        addMapping(0xff, "&yuml;", strings); //

        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];

            if (str != null) {
                _stringChars[i] = str.toCharArray();
            }
        }
    }
    
    
    protected static class Tag {
        private String name;
        private String body;
        private Map attributes;
        private boolean malformed = false;
        
        public Tag(String name, String body, Map attributes) {
            this.name = name.toLowerCase();
            if (body != null) {
                this.body = escapeHTML(body);
            }
            this.setAttributes(attributes);
        }
        
        public void setAttributes(Map attributes) {
        	if (attributes != null) {
                String[] allowedAttri = (String[]) ALLOWED_ATTRIBUTES.get(this.name);
                if (allowedAttri != null) {
                    this.attributes = new HashMap(attributes.size());
                    for (int i = 0; i < allowedAttri.length; i++) {
                        String value = (String) attributes.get(allowedAttri[i]);
                        if (value != null) {
                        	value = escapeHTML(value);
                        	if (("href".equals(allowedAttri[i]) || "src".equals(allowedAttri[i]))
                        			&& !isValidURL(value)) {
                        		// prevents XSS attack
                        		this.malformed = true;
                        	}
                        	this.attributes.put(allowedAttri[i], value);
                        }
                    }
                }
            }
        }
        
        private boolean isValidURL(String url) {
        	if (url == null) {
        		return false;
        	}
        	String value = url.toLowerCase();
        	return (value.startsWith("ftp://") || value.startsWith("http://")
        			|| value.startsWith("https://"));
        }
        
        public String toString() {
        	char[] startTagChars = this.malformed ? _stringChars[LT] : new char[] {'<'};
        	char[] endTagChars = this.malformed ? _stringChars[GT] : new char[] {'>'};
        	
            StringBuffer sb = new StringBuffer();
            sb.append(startTagChars).append(name);
            if (attributes != null) {
                for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
                    Map.Entry e = (Map.Entry) i.next();
                    sb.append(' ').append(e.getKey()).append('=')
                        .append('\"').append(e.getValue()).append('\"');
                }
            }
            if (body == null) {
                sb.append('/').append(endTagChars);
            } else {
                sb.append(endTagChars);
                sb.append(body);
                sb.append(startTagChars).append('/').append(name).append(endTagChars);
            }
            return sb.toString();
        }
    }
    
    /**
     * Remove HTML TAGS.
     * 
     * @param s
     * @return
     */
    public static final String cleanHTML(String s) {
    	int len = s.length();
    	
    	if (len == 0) {
    		return s;
    	}
    	
    	int i = 0;
    	
    	// First loop through String and check if escaping is needed at all
        // No buffers are copied at this time
        do {
            int index = s.charAt(i);

            if (index >= MAX_LENGTH) {
                if (index != 0x20AC) { // If not euro symbol

                    continue;
                }

                break;
            } else if (_stringChars[index] != null) {
                break;
            }
        } while (++i < len);

        // If the check went to the end with no escaping then i should be == len now
        // otherwise we must continue escaping for real
        if (i == len) {
            return s;
        }
        
        // We found a character to escape and broke out at position i
        // Now copy all characters before that to StringBuffer sb
        // Since a char[] will be used for copying we might as well get
        // a complete copy of it so that we can use array indexing instead of charAt
        StringBuffer sb = new StringBuffer(len + 40);
        char[] chars = new char[len];

        // Copy all chars from the String s to the chars buffer
        s.getChars(0, len, chars, 0);

        // Append the first i characters that we have checked to the resulting StringBuffer
        sb.append(chars, 0, i);

        int last = i;
        char[] subst;
 
        int firstGtIdx;
        int tagLength;
        boolean closeTag;

        int tagStart;

        int j, x;

        for (; i < len; i++) {
            int index = chars[i];

            if (index < MAX_LENGTH) {
                boolean inTag = false;
                
                if (index == LT) { // we got a '<'
                    if (i + 2 < chars.length) {
                        // search forward, to find tag...>...</tag>
                        firstGtIdx = -1;
                        tagLength = -1;
                        closeTag = false;
                        
                        tagStart = i + 1;
                        
                        j = tagStart;
                        
                        for (; j < chars.length; j++) {
                            char c = chars[j];
                            
                            if (c == SLASH) {
                            	if (j == tagStart) {
                            		closeTag = true;
                            		tagStart++;
                            	} else if (chars[j+1] == GT) {
                            		if (firstGtIdx == -1) {
                                        firstGtIdx = j + 1;
                                    }
                            		inTag = true;
                            		break;
                            	}
                            }
                            else if (c == GT && j > tagStart) {
                                if (firstGtIdx == -1) {
                                    firstGtIdx = j;
                                }
                                if (tagLength == -1) {
                                    tagLength = j - tagStart;
                                }
                                inTag = true;
                                break;
                            }
                            else if (c == SPACE) {
                            	if (closeTag || j == tagStart) {
                            		break;
                            	}
                                if (tagLength == -1 && firstGtIdx == -1) {
                                    tagLength = j - tagStart;
                                }
                            }
                            else if (c == LT) {
                            	break;
                            } 
                            else if (tagLength == -1) {
                            	if (j - tagStart >= _tagChars.length) {
                            		break ;
                            	}
                            	
                            	x = Character.toLowerCase(c);
                            	
                            	if (x < OFFSET || x > MAX_INDEX) {
                            		break ;
                            	}
                            	
                            	if (_tagChars[j - tagStart][x - OFFSET] == 0) {
                            		break ;
                            	}
                            }
                        }
                        
                        // ignore the tag
                        if (inTag) {
                            if (i > last) {
                                sb.append(chars, last, i - last);
                            }
                            
                            i = firstGtIdx;
                            
                            last = i + 1;
                        }
                    }
                } else if (index == AND) {
                	for (int k = 0; (k < MAX_ESCAPE_LENGTH && i + k + 1 < chars.length); k++) {
                		char c = chars[i + k + 1];
                		if (c == SEMICOLON) {
                			inTag = true;
                		} else {
                			x = Character.toLowerCase(c);
                            
                            if (x < OFFSET || x > MAX_INDEX) {
                                break ;
                            }
                            
                            if (_escapeChars[k][x - OFFSET] == 0) {
                                break ;
                            }
                		}
                	}
                }
                
                if (!inTag) {
                    subst = _stringChars[index];
                    
                    // It is faster to append a char[] than a String which is why we use this
                    if (subst != null) {
                        if (i > last) {
                            sb.append(chars, last, i - last);
                        }
                        
                        sb.append(subst);
                        last = i + 1;
                    }
                }
            }
            // Check if it is the euro symbol. This could be changed to check in a second lookup
            // table in case one wants to convert more characters in that area
            else if (index == 0x20AC) {
                if (i > last) {
                    sb.append(chars, last, i - last);
                }

                sb.append("&euro;");
                last = i + 1;
            }
        }

        if (i > last) {
            sb.append(chars, last, i - last);
        }

        return sb.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(escapeHTML("<a href=\"http://www.yupoo.com/\"><IMG src=\"\" alt=\"hello asdf\" width =\"56\"/></a>"));
        System.out.println(escapeHTML("<a href=\"/yupoo/photos/view?id=402880900a9c1ab5010a9d151abe0005\"><img src=\"http://photo1.yupooo.com/20060415/232.jpg\" /></a>"));
        System.out.println(escapeHTML("&amp;&lt;a href=\"url\"&gt;Link&lt;/a&gt;"));
    	System.out.println(cleanHTML("<br/><img src=\"asdfasf\"/>hello<p> yupoo.com&<b> hahah</b></p>"));
    }
}

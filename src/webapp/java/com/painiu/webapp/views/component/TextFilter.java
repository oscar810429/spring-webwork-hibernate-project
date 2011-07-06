/*
 * @(#)TextFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.component;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.views.util.TextUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.painiu.webapp.views.text.HtmlCleanTextFilter;
import com.painiu.webapp.views.text.HtmlEscapeTextFilter;
import com.painiu.webapp.views.text.JavascriptEscapeTextFilter;
import com.painiu.webapp.views.text.NewlineTextFilter;
import com.painiu.webapp.views.text.URLConvertTextFilter;

/**
 * <p>
 * <a href="TextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TextFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class TextFilter extends Component {
    //~ Static fields/initializers =============================================
    
    private static final Log log = LogFactory.getLog(TextFilter.class);

    private static final com.painiu.webapp.views.text.TextFilter HTML_ESCAPE = new HtmlEscapeTextFilter();
    private static final com.painiu.webapp.views.text.TextFilter HTML_CLEAN = new HtmlCleanTextFilter();
    private static final com.painiu.webapp.views.text.TextFilter JAVASCRIPT = new JavascriptEscapeTextFilter();
    private static final com.painiu.webapp.views.text.TextFilter NEWLINE = new NewlineTextFilter();
    private static final com.painiu.webapp.views.text.TextFilter URL = new URLConvertTextFilter();
    
    //~ Instance fields ========================================================

    private String defaultValue;
    private String value;
    private boolean findValue = true;
    
    private boolean html = true;
    private boolean clean = false;
    private boolean escapeAll = false; // 是否严格过滤所有HTML标签
    private boolean url = true;
    private boolean newline = true;
    private boolean javascript = false;
    
    private int length;
    
    //~ Constructors ===========================================================

    public TextFilter(OgnlValueStack stack) {
        super(stack);
    }
    
    //~ Methods ================================================================

    /**
     * The default value to be used if <u>value</u> attribute is null
     */
    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * value to be displayed
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Whether to find value
     */
    public void setFind(boolean findValue) {
        this.findValue = findValue;
    }
    
    /**
     * @param html The html to set.
     */
    public void setHtml(boolean html) {
        this.html = html;
    }
    
    /**
	 * @param escapeAll The escapeAll to set.
	 */
	public void setEscapeAll(boolean escapeAll) {
		this.escapeAll = escapeAll;
	}
    
    /**
     * @param url The url to set.
     */
    public void setUrl(boolean url) {
        this.url = url;
    }
    
    /**
     * @param newline The newline to set.
     */
    public void setNewline(boolean newline) {
        this.newline = newline;
    }
    
    /**
	 * @param clean the clean to set
	 */
	public void setClean(boolean clean) {
		this.clean = clean;
	}
	
    /**
     * @param javascript The javascript to set.
     */
    public void setJavascript(boolean javascript) {
        this.javascript = javascript;
    }
    
    /**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
    
    public boolean end(Writer writer, String body) {
        String actualValue = null;

        if (value != null && findValue && altSyntax()) {
            // the same logic as with findValue(String)
            // if value start with %{ and end with }, just cut it off!
            if (value.startsWith("%{") && value.endsWith("}")) {
                value = value.substring(2, value.length() - 1);
            }
        }

        // exception: don't call findString(), since we don't want the
        //            expression parsed in this one case. it really
        //            doesn't make sense, in fact.
        if (value != null) {
            if (findValue) {
                actualValue = (String) getStack().findValue(value, String.class);
            } else {
                actualValue = value;
            }
        }
        
        if (actualValue == null) {
            if (body != null && body.length() > 0) {
                actualValue = body;
            } else if (defaultValue != null) {
                actualValue = defaultValue;
            }
        }

        try {
            if (actualValue != null) {
                writer.write(prepare(actualValue));
            }
        } catch (IOException e) {
            log.info("Could not print out value '" + value + "'", e);
        }

        return false;
    }

    private String prepare(String value) {
        String result = value;
        if (clean) {
        	result = HTML_CLEAN.filter(result);
        }
        if (url) {
        	result = URL.filter(result);
        }
        if (javascript) {
        	result = JAVASCRIPT.filter(result);
        }
        if (!clean && html) {
        	if (escapeAll) {
        		result = TextUtil.escapeHTML(result);
        	} else {
        		result = HTML_ESCAPE.filter(result);
        	}
        }
        if (newline) {
        	result = NEWLINE.filter(result);
        }
        
        if (length > 0 && result.length() > length) {
        	result = result.substring(0, length) + "...";
        }
        
        return result;
    }
}

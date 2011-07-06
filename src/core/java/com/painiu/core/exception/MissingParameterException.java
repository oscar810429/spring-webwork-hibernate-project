/*
 * @(#)MissingParameterException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.exception;

/**
 * <p>
 * <a href="MissingParameterException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MissingParameterException.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class MissingParameterException extends RuntimeException {
    private String paramName;
    
    public MissingParameterException(String paramName) {
        super("parameter '" + paramName + "' is required.");
        this.paramName = paramName;
    }
    
    public String getParamName() {
        return paramName;
    }
}

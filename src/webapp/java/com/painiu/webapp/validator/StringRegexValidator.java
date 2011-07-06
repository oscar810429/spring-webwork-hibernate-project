/*
 * @(#)StringRegexValidator.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork.validator.ValidationException;

/**
 * <p>
 * <a href="StringRegexValidator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: StringRegexValidator.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class StringRegexValidator extends
		com.opensymphony.xwork.validator.validators.StringRegexValidator {
	
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String value = (String) this.getFieldValue(fieldName, object);

        if (value == null) {
            return;
        }
//		value = value.trim();
        
		if (value.length() == 0) {
		    return;
		}

        Pattern pattern = null;
        if (isCaseSensitive())
            pattern = Pattern.compile(getRegex());
        else
            pattern = Pattern.compile(getRegex(), Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            addFieldError(fieldName, object);
        }
    }
}

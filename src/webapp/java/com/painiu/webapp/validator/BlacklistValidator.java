/*
 * @(#)BlacklistValidator.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.validator;

import com.opensymphony.xwork.validator.ValidationException;
import com.opensymphony.xwork.validator.validators.FieldValidatorSupport;
import com.painiu.util.Blacklist;

/**
 * <p>
 * <a href="BlacklistValidator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: BlacklistValidator.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class BlacklistValidator extends FieldValidatorSupport {

	/*
	 * @see com.opensymphony.xwork.validator.Validator#validate(java.lang.Object)
	 */
	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);

        if (Blacklist.getBlacklist().isBlacklisted(value.toString())) {
            addFieldError(fieldName, object);
        }
	}

}

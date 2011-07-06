/*
 * @(#)SignUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.service.api.ApiException;
import com.painiu.service.api.Parameters;

/**
 * <p>
 * <a href="SignUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SignUtils.java 35 2010-06-01 01:53:10Z zhangsf $
 */
@SuppressWarnings("unchecked")
public class SignUtils {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(SignUtils.class);

	public static final String AUTH_SIGN_PARAMETER_NAME = "api_sig"; 

	private static Set EXCLUDED_PARAMS;
	
	static {
		EXCLUDED_PARAMS = new HashSet();
		EXCLUDED_PARAMS.add(AUTH_SIGN_PARAMETER_NAME);
		EXCLUDED_PARAMS.add("photo");
	}
	
	private static String getParametersSequence(Parameters parameters) {
		Set names = parameters.names();
		
		List paramNames = new ArrayList(names.size());
		for (Iterator i = names.iterator(); i.hasNext();) {
			String name = (String) i.next();
			if (!EXCLUDED_PARAMS.contains(name)) {
				paramNames.add(name);
			}
		}
		
		Collections.sort(paramNames);
		
		StringBuffer sb = new StringBuffer();
		
		for (Iterator i = paramNames.iterator(); i.hasNext();) {
			String name = (String) i.next();
			sb.append(name);
			sb.append(parameters.get(name));
		}
		
		return sb.toString();
	}

	public static void validate(String secret, Parameters parameters) throws ApiException {
	
		String authSign = parameters.getString(AUTH_SIGN_PARAMETER_NAME);
		
		if (authSign == null) {
			throw ApiException.MISSING_SIGNATURE();
		}
		
		String source = secret + getParametersSequence(parameters);
		
		if (log.isDebugEnabled()) {
			log.debug("signature source: " + source);
		}
		
		String result = md5(source, "UTF-8");
		
        if (log.isDebugEnabled()) {
        	log.debug("md5 result(UTF-8): " + result);
        }
        
        if (!authSign.equals(result)) {
        	result = md5(source, null); // try default charset
        	
        	if (log.isDebugEnabled()) {
            	log.debug("md5 result(System charset): " + result);
            }
        	
        	if (!authSign.equals(result)) {
        		throw ApiException.INVALID_SIGNATURE();
        	}
        }
	}
	
	public static String sign(String secret, Map params) {
		return md5(secret + getParametersSequence(new Parameters(params)), "UTF-8");
	}
	
	public static String md5(String source, String charset) {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException", e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		md.reset();

		if (charset != null) {
			// call the update method one or more times
			// (useful when you don't know the size of your data, eg. stream)
			try {
				md.update(source.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				// should not happen
				log.error("UnsupportedEncodingException", e);
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		} else {
			md.update(source.getBytes());
		}

        // now calculate the hash
        byte[] encoded = md.digest();
        
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encoded.length; i++) {
            if ((encoded[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(encoded[i] & 0xff, 16));
        }
        
        return buf.toString();
	}
}

/**
 * @(#)ImageResult.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionInvocation;

/**
 * <p>
 * <a href="ImageResult.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageResult.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ImageResult extends WebWorkResultSupport {

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private HttpSession session;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	
    @Override
    protected void doExecute(String arg0, ActionInvocation invocation) throws Exception
    {
        
        //���Request
        HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(
                ServletActionContext.HTTP_REQUEST);
        //���response
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(
                ServletActionContext.HTTP_RESPONSE);
        //�����޻���
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        VerifyImage verify = new VerifyImage();
        OutputStream os = response.getOutputStream();
        //��������֤��
        String str = verify.GetImage(os);
        //���session
        session = request.getSession(true);
        //����֤�����session
        session.setAttribute("rand", str);
    }
    
	

}

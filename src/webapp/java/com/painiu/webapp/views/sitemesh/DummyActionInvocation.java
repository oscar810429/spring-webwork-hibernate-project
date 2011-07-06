package com.painiu.webapp.views.sitemesh;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.opensymphony.xwork.util.OgnlValueStack;

public class DummyActionInvocation implements ActionInvocation {
	private static final long serialVersionUID = 1L;
	
	ActionSupport action;

    public DummyActionInvocation(ActionSupport action) {
        this.action = action;
    }

    public Object getAction() {
        return action;
    }

    public boolean isExecuted() {
        return false;
    }

    public ActionContext getInvocationContext() {
        return null;
    }

    public ActionProxy getProxy() {
        return null;
    }

    public Result getResult() throws Exception {
        return null;
    }

    public String getResultCode() {
        return null;
    }

    public void setResultCode(String resultCode) {
    }

    public OgnlValueStack getStack() {
        return null;
    }

    public void addPreResultListener(PreResultListener listener) {
    }

    public String invoke() throws Exception {
        return null;
    }

    public String invokeActionOnly() throws Exception {
        return null;
    }
}

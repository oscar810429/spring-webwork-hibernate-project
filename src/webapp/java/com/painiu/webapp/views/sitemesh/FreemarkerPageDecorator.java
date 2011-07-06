package com.painiu.webapp.views.sitemesh;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.filter.PageFilter;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * <p>
 * <a href="FreemarkerPageDecorator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FreemarkerPageDecorator.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class FreemarkerPageDecorator extends PageFilter {
	
	public static final String DECORATE_EXCLUDE = "__decorate_exclude_";
	

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        super.init(filterConfig);
        this.filterConfig = filterConfig;
    }
	
    @SuppressWarnings("unchecked")
	protected void applyDecorator(Page page, Decorator decorator,
            HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	if ( Boolean.TRUE.equals(req.getAttribute(DECORATE_EXCLUDE)) ) {
			writeOriginal(req, res, page);
			return ;
		}
    	
    	try {
			FreemarkerManager fmm = FreemarkerManager.getInstance();
			ServletContext servletContext = filterConfig.getServletContext();
			ActionContext ctx = ServletActionContext.getActionContext(req);
			if (ctx == null) {
				// ok, one isn't associated with the request, so let's get a ThreadLocal one (which will create one if needed)
				OgnlValueStack vs = new OgnlValueStack();
				vs.getContext().putAll(DispatcherUtils.getInstance().createContextMap(req, res, null, servletContext));
				ctx = new ActionContext(vs.getContext());
				if (ctx.getActionInvocation() == null) {
					// put in a dummy ActionSupport so basic functionality still works
					ActionSupport action = new ActionSupport();
					vs.push(action);
					ctx.setActionInvocation(new DummyActionInvocation(action));
				}
			}
			
			ActionInvocation invocation = ctx.getActionInvocation();

			// get the configuration and template
			Configuration config = fmm.getConfiguration(servletContext);
			Template template = config.getTemplate(decorator.getPage());

			// get the main hash
			SimpleHash model = fmm.buildTemplateModel(ctx.getValueStack(), invocation.getAction(), servletContext, req, res, config.getObjectWrapper());

			// populate the hash with the page
			model.put("page", page);
			if (page instanceof HTMLPage) {
				HTMLPage htmlPage = ((HTMLPage) page);
				model.put("head", htmlPage.getHead());
			}
			model.put("title",page.getTitle());
			model.put("body",page.getBody());

			// finally, render it
			template.process(model, res.getWriter());
		} catch (Exception e) {
			String msg = "Error applying decorator: " + e.getMessage();
//			LOG.error(msg, e);
			throw new ServletException(msg, e);
		}
    }
	
	/** Write the original page data to the response. */
    private void writeOriginal(HttpServletRequest request, HttpServletResponse response, Page page) throws IOException {
//        response.setContentLength(page.getContentLength()); length 应该是对的，但是实际写的长度不对，不知道为什么
       // if (request.getAttribute(USING_STREAM).equals(Boolean.TRUE)) {
            PrintWriter writer = new PrintWriter(response.getOutputStream());
            page.writePage(writer);
            //flush writer to underlying outputStream
            writer.flush();
            response.getOutputStream().flush();
        //} else {
        //   page.writePage(response.getWriter());
        //    response.getWriter().flush();
        //}
    }
    
    
}

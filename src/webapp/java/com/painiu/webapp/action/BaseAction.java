package com.painiu.webapp.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;

import com.painiu.Painiu;
import com.painiu.core.model.Area;
import com.painiu.core.model.User;
import com.painiu.core.model.UserPreference;
import com.painiu.core.security.SecurityUtils;
import com.painiu.core.service.AbuseManager;
import com.painiu.core.service.AreaManager;
import com.painiu.core.service.CategoryManager;
import com.painiu.core.service.CollaboratorManager;
import com.painiu.core.service.EventManager;
import com.painiu.core.service.ForumManager;
import com.painiu.core.service.InviteManager;
import com.painiu.core.service.LinkManager;
import com.painiu.core.service.MessageEngine;
import com.painiu.core.service.PhotoManager;
import com.painiu.core.service.ProductManager;
import com.painiu.core.service.RoleManager;
import com.painiu.core.service.SoftwareManager;
import com.painiu.core.service.SystemMessageManager;
import com.painiu.core.service.SystemNewsManager;
import com.painiu.core.service.TagsManager;
import com.painiu.core.service.UserManager;
import com.painiu.core.service.TokenManager;
import com.painiu.core.service.MessageManager;
import com.painiu.core.exception.MissingParameterException;
import com.painiu.util.URLUtils;
import com.painiu.webapp.personality.DomainContext;
import com.painiu.webapp.personality.DomainContextHolder;
import com.painiu.webapp.personality.MultiDomainContextHolder;
import com.painiu.webapp.views.freemarker.FreemarkerManager;



/**
 * Implementation of <strong>ActionSupport</strong> that contains 
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Action classes.
 *
 * <p>
 * <a href="BaseAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:songfu.zhang@gmail.com">Zhang Songfu</a>
 */
public class BaseAction extends ActionSupport {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = -7234807279490535306L;

	//~ Instance fields ========================================================
    
	protected transient final Log log = LogFactory.getLog(getClass());
	protected static ThreadLocal timestampHolder = new ThreadLocal();
	protected static final DateFormat TF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected static final DateFormat TFDATE = new SimpleDateFormat("yyyy-MM-dd");
    protected String from = null;
    protected User currentUser = null;
    protected Area currentRegion = null;
    protected int page = 1;
    
	//~ Methods ================================================================

    public void saveMessage(String msg) {
        List messages = (List) getSession().getAttribute(FreemarkerManager.MESSAGES);
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        getSession().setAttribute(FreemarkerManager.MESSAGES, messages);
    }
    
	public void saveError(String error) {
		List errors = (List) getSession().getAttribute(FreemarkerManager.ERRORS);
		if (errors == null) {
			errors = new ArrayList();
		}
		errors.add(error);
		getSession().setAttribute(FreemarkerManager.ERRORS, errors);
	}
	
	public void saveErrors(Collection errors) {
		for (Iterator i = errors.iterator(); i.hasNext();) {
			saveError((String) i.next());
		}
	}
	
	public void persistMessages() {
		if (hasActionMessages()) {
			for (Iterator i = getActionMessages().iterator(); i.hasNext();) {
				saveMessage((String) i.next());
			}
		}
		saveErrors(mergeActionErrors());
	}
	
	public List mergeActionErrors() {
		List errors = new ArrayList();
		if (hasActionErrors()) {
			for (Iterator i = getActionErrors().iterator(); i.hasNext();) {
				errors.add(i.next());
			}
		}
		if (hasFieldErrors()) {
			Map fieldErrors = getFieldErrors();
			for (Iterator i = fieldErrors.values().iterator(); i.hasNext();) {
				List errorList = (List) i.next();
				for (Iterator iter = errorList.iterator(); iter.hasNext();) {
					errors.add(iter.next());
				}
			}
		}
		return errors;
	}
	
	public void saveActionMessage(String message) {
		List messages = (List) getSession().getAttribute(FreemarkerManager.LOCAL_MESSAGES);
		if (messages == null) {
			messages = new ArrayList();
		}
		messages.add(message);
		getSession().setAttribute(FreemarkerManager.LOCAL_MESSAGES, messages);
	}
	
	public void saveActionError(String error) {
		List errors = (List) getSession().getAttribute(FreemarkerManager.LOCAL_ERRORS);
		if (errors == null) {
			errors = new ArrayList();
		}
		errors.add(error);
		getSession().setAttribute(FreemarkerManager.LOCAL_ERRORS, errors);
	}
    
    public String redirect(String url) throws IOException {
       getResponse().sendRedirect(getResponse().encodeRedirectURL(url));
    	   return NONE;
    }
    
	protected static void setTimestamp(long timestamp) {
		timestampHolder.set(new Long(timestamp));
	}
	
	protected static long getTimestamp() {
		if (timestampHolder.get() == null) {
			return -1;
		}
		
		return ((Long) timestampHolder.get()).longValue();
	}
    
    public String loginRequired() throws IOException {
    	   return redirect(URLUtils.getURL("/account/login?j_uri=") + URLEncoder.encode(com.painiu.util.URLUtils.getRequestedURL(), "UTF-8"));
    }
    
    /**
     * Convenience method to get the request
     * @return current request
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }
    
    /**
     * Convenience method to get the response
     * @return current response
     */
    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * Convenience method to get the session
     */
    public HttpSession getSession() {
      	return getRequest().getSession();
    }
    
    /**
     * Convenience method to get the session
     */
    public HttpSession getSession(boolean create) {
    		return getRequest().getSession(create);
    }
    
    /**
     * Convenience method to get current user.
     * @return
     */
    @SuppressWarnings("deprecation")
	public User getCurrentUser() {
    	if (currentUser != null) {
    		return currentUser;
    	}
    	
    	currentUser = SecurityUtils.getCurrentUser();
    	
    	return currentUser;
    }
    
	public Area getCurrentRegion() {
    	if (currentRegion != null) {
    		return currentRegion;
    	}
    	
    	DomainContext domainContext = DomainContextHolder.getContext();
    	currentRegion = domainContext.getArea();
    	
    	return currentRegion;
    }
    
    public boolean isAdmin() {
		return getRequest().isUserInRole(Painiu.ADMIN_ROLE);
	}
    
    public boolean isManager() {
    		return getRequest().isUserInRole(Painiu.MANAGER_ROLE);
    }
    
    public boolean isVolunteer() {
    		return getRequest().isUserInRole(Painiu.VOLUNTEER_ROLE);
    }
    
    public boolean isPro() {
    		return getRequest().isUserInRole(Painiu.PRO_ROLE);
    }
    
    public boolean isVIPNormal() {
   		return getRequest().isUserInRole(Painiu.VIP_NORMAL_ROLE);
    }
    
    public boolean isVIPBusiness() {
    		return getRequest().isUserInRole(Painiu.VIP_BUSINESS_ROLE);
    }
    
    public boolean isVIP() {
    	return (isVIPNormal() || isVIPBusiness()); 
    }
    

    public boolean isFrozen() {
    		return getRequest().isUserInRole(Painiu.FROZEN_ROLE);
    }
    
    public boolean isCs() {
    		return getRequest().isUserInRole(Painiu.CS_ROLE);
    }
    
    public boolean isAgent() {
    		return getRequest().isUserInRole(Painiu.AGENT_ROLE);
    }
    
    public boolean isAgentManager() {
    		return getRequest().isUserInRole(Painiu.AGENT_MANAGER_ROLE);
    }
    
    public boolean isFinance() {
    		return getRequest().isUserInRole(Painiu.FINANCE_ROLE);
    }
    
    /**
     * Convenience method for setting a "from" parameter to indicate the last page.
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getFrom() {
    	return from;
    }

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
    
    protected void assertParamExists(String name, Object value) {
    	if (value == null) {
    		throw new MissingParameterException(name);
    	}
    }
    
    protected void assertParamExists(String[] names, Object[] values) {
    	for (int i = 0; i < values.length; i++) {
			assertParamExists(names[i], values[i]);
		}
    }
    
    /*protected SoftwarePermission newPermission(UserPreference preference) {
      	SoftwarePermission perm = new SoftwarePermission();
    	    perm.setBlog(preference.getBlog());
    	    perm.setComment(preference.getComment());
    	    perm.setDownload(preference.getDownload());
    	    perm.setExif(preference.getExif());
    	    perm.setNote(preference.getNote());
      	perm.setTag(preference.getTag());
        return perm;
    }*/
    
    /*************************************************************************
     * Managers
     *************************************************************************/

    protected UserManager userManager = null;
    protected CategoryManager categoryManager = null;
    protected AreaManager areaManager = null;
    protected LinkManager linkManager = null;
    protected RoleManager roleManager = null;
    protected SystemNewsManager systemNewsManager = null;
    protected SystemMessageManager systemMessageManager = null;
    protected EventManager eventManager = null;
    protected TagsManager tagsManager = null;
    protected PhotoManager photoManager = null;
    protected ForumManager forumManager = null;
    protected MessageEngine messageEngine = null;
    protected CollaboratorManager collaboratorManager = null;
    protected TokenManager tokenManager = null;
    protected MessageManager messageManager = null;
    protected InviteManager inviteManager = null;
    protected ProductManager productManager = null;
    protected SoftwareManager softwareManager = null;
    protected AbuseManager abuseManager = null;


	/**
	 * @param messageEngine the messageEngine to set
	 */
	public void setMessageEngine(MessageEngine messageEngine) {
		this.messageEngine = messageEngine;
	}

	/**
	 * @param userManager the userManager to set
	 */
    public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @param categoryManager the categoryManager to set
	 */
    public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	/**
	 * @param areaManager the areaManager to set
	 */
    public void setAreaManager(AreaManager areaManager) {
		this.areaManager = areaManager;
	}

	/**
	 * @param linkManager the linkManager to set
	 */
	public void setLinkManager(LinkManager linkManager) {
		this.linkManager = linkManager;
	}

	/**
	 * @param systemNewsManager the systemNewsManager to set
	 */
	public void setSystemNewsManager(SystemNewsManager systemNewsManager) {
		this.systemNewsManager = systemNewsManager;
	}

	/**
	 * @param systemMessageManager the systemMessageManager to set
	 */
	public void setSystemMessageManager(SystemMessageManager systemMessageManager) {
		this.systemMessageManager = systemMessageManager;
	}

	/**
	 * @param roleManager the roleManager to set
	 */
	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	/**
	 * @param eventManager the eventManager to set
	 */
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	/**
	 * @param collaboratorManager the collaboratorManager to set
	 */
	public void setCollaboratorManager(CollaboratorManager collaboratorManager) {
		this.collaboratorManager = collaboratorManager;
	}

	/**
	 * @param tokenManager the tokenManager to set
	 */
	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * @param messageManager the messageManager to set
	 */
	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}
	
	/**
	 * @param photoManager the messageManager to set
	 */
	public void setPhotoManager(PhotoManager photoManager) {
		this.photoManager = photoManager;
	}

	/**
	 * @param inviteManager the inviteManager to set
	 */
	public void setInviteManager(InviteManager inviteManager) {
		this.inviteManager = inviteManager;
	}

	/**
	 * @param productManager the productManager to set
	 */
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	/**
	 * @param tagsManager the tagsManager to set
	 */
	public void setTagsManager(TagsManager tagsManager) {
		this.tagsManager = tagsManager;
	}

	/**
	 * @param forumManager the forumManager to set
	 */
	public void setForumManager(ForumManager forumManager) {
		this.forumManager = forumManager;
	}

	/**
	 * @param abuseManager the abuseManager to set
	 */
	public void setAbuseManager(AbuseManager abuseManager) {
		this.abuseManager = abuseManager;
	}
	
	/**
	 * @param softwareManager the softwareManager to set
	 */
	public void setSoftwareManager(SoftwareManager softwareManager) {
		this.softwareManager = softwareManager;
	}
	
	
	public String getDomain() {
		return MultiDomainContextHolder.getContext().getDomain();
	}

	
	
	
	
}

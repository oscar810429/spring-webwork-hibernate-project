/*
 * @(#)ManageUserAction.java  2007-12-17
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.Painiu;
import com.painiu.core.model.User;
import com.painiu.core.model.User.UserRank;
//import com.painiu.core.model.User.UserType;
import com.painiu.core.search.Result;
import com.painiu.util.URLUtils;
import com.painiu.webapp.action.BaseAction;
import com.painiu.webapp.util.DateUtils;

/**
 * <p>
 * <a href="ManageUserAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author bill
 * @version $Id: ManageUserAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ManageUserAction extends BaseAction {

	//~ Static fields/initializers =============================================
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static int PAGE_SIZE = 50;
	//~ Instance fields ========================================================
	private User user;
	private String id;
	private String[] ids;
	private int rank;
	private int type = 0;
	private int page = 1;
	private int pageSize = 20;
	private String startDate;

	private String endDate;
	private Result result;
	private int orderbyLogin;
	private int[] ranks;
	//private ApplyFastCheck userApply;
	private String email;
	private int year = 0;
	private int currentYear;
	private int month = 0;
	private Long outFlow;
	private Long maxOutFlow;
	private int currentMonth;
	private List outFlows;
	private List oldFlows;
	private List maxFlows;
	private List currentVipProducts;
	//private VipProduct currentVipProduct;
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	public String saveRank() throws Exception {
		assertParamExists("userid", id);
		if (id.length() == 32) {
			try {
				user = userManager.getUser(id);
			} catch (ObjectRetrievalFailureException ex) {
				user = userManager.getUserByUsername(id);
			}
		} else {
			user = userManager.getUserByUsername(id);
		}
		//userManager.setUserRank(user, UserRank.valueOf(rank));
		if (from != null) {
			return "from";
		}
		return SUCCESS;
	}
	
	public String saveRanks() throws Exception {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				if (ids[i].length() == 32) {
					user = userManager.getUser(ids[i]);
				} else {
					user = userManager.getUserByUsername(ids[i]);
				}
				User.UserRank oldRank = user.getUserRank();
				if (!user.isInRole(Painiu.VIP_BUSINESS_ROLE)) {
					/*userManager.setUserRank(user, UserRank.valueOf(rank));
					if (User.UserRank.GREEN.equals(oldRank) && rank != 2) {
						try {
							userApply = userManager.getApplyFastCheck(user.getId());
							if (userApply != null) {
								Map model = new HashMap(1);
						        model.put("rank", rank);
								messageEngine.sendMessage(null, user, model,"messages/account_checked.ftl", user.getLocale());
							}
						} catch (ObjectRetrievalFailureException ore) {
							//ignore;
						}
					}*/
				} else {
					saveActionMessage(getText("He/She is commercial user, don't change rank"));
				}
//				photoManager.setPhotosState(user, Photo.State.valueOf(5 + rank));
			}
			saveMessage("Saved");
		}
		if (from != null) {
			return "from";
		}
		return SUCCESS;
	}

	public String saveType() throws Exception {
		user = userManager.getUser(id);
		//userManager.setUserType(user, UserType.valueOf(type));
		saveMessage("Saved");
		if (from != null) {
			return "from";
		}
		return redirect(URLUtils.getUserHomeURL(user));
	}
	
	
	public String search() throws Exception {
		if (id != null && StringUtils.isNotEmpty(id)) {
			if (id.length() == 32) {
				try {
					user = userManager.getUser(id);
				} catch (ObjectRetrievalFailureException e) {
					user = userManager.getUserByUsername(id);
				}
			} else {
				user = userManager.getUserByUsername(id);
			}
		} else {
			if (startDate != null) {
				Date[] defaultDates = DateUtils.getDatesBetweenLastdays(-30);
				Date sDate =  defaultDates[0];
				if (StringUtils.isNotEmpty(startDate)) {
					sDate = DF.parse(startDate);
				}
				Date eDate = defaultDates[1];
		
				if (StringUtils.isNotEmpty(endDate)) {
					eDate = DF.parse(endDate);
				}
				List userRanks = new ArrayList(6);
				if (ranks != null && ranks.length > 0) {
					for (int i = 0; i < ranks.length; i++) {
						userRanks.add(User.UserRank.valueOf(ranks[i]));
					}
				} else {
					for (int i = 0; i < 8; i++) {
						userRanks.add(User.UserRank.valueOf(i));
					}
				}
				if (this.orderbyLogin == 1) {
					result = userManager.getActiveUsers(sDate, eDate, (page - 1) * pageSize, pageSize, userRanks);
				} else {
					//result = userManager.findUploadUsers(sDate, eDate, (page - 1) * pageSize, pageSize, userRanks);
				}
			}
		}
		return SUCCESS;
	}
	
	public String searchApply() throws Exception {
		if (id != null && StringUtils.isNotEmpty(id)) {
			user = userManager.getUser(id);
			//userApply = userManager.getApplyFastCheck(id);
			return SUCCESS;
		}
		if (startDate != null) {
			Date[] defaultDates = DateUtils.getDatesBetweenLastdays(-30);
			Date sDate =  defaultDates[0];
			if (StringUtils.isNotEmpty(startDate)) {
				sDate = DF.parse(startDate);
			}
			Date eDate = defaultDates[1];

			if (StringUtils.isNotEmpty(endDate)) {
				eDate = DF.parse(endDate);
			}

			//result = userManager.getApplyFastChecks(sDate, eDate, (page - 1) * pageSize, pageSize);
		}
		return SUCCESS;
	}
	
	public String listUserIPRecord() throws Exception {
		if (id != null && StringUtils.isNotEmpty(id)) {
			user = userManager.getUser(id);
			Date[] defaultDates = DateUtils.getDatesBetweenLastdays(-30);
			Date sDate =  defaultDates[0];
			if (StringUtils.isNotEmpty(startDate)) {
				sDate = DF.parse(startDate);
			}
			Date eDate = defaultDates[1];
	
			if (StringUtils.isNotEmpty(endDate)) {
				eDate = DF.parse(endDate);
			}
			//result = userManager.getUserLoginIPs(user, sDate, eDate, (page - 1) * pageSize, pageSize);
		}
		return SUCCESS;
	}
	
	
	public String searchByEmail() {
		if (StringUtils.isNotEmpty(email)) {
			try {
				user = userManager.getUserByEmail(email);
			} catch (ObjectRetrievalFailureException e) {
				
			}
		}
		return SUCCESS;
	}
	
	public String searchOutFlow() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		currentYear = cal.get(Calendar.YEAR);
		currentMonth = cal.get(Calendar.MONTH) + 1;
		if (year < 2006 || year > currentYear) {
			year = currentYear;
		}
		
		if (StringUtils.isEmpty(id)) {
			return SUCCESS;
		}
		if (id.length() == 32) {
			try {
				user = userManager.getUser(id);
			} catch (ObjectRetrievalFailureException ex) {
				user = userManager.getUserByUsername(id);
			}
		} else {
			user = userManager.getUserByUsername(id);
		}
		if (user == null) {
			saveActionError("The user is not found !");
		} else {
			outFlows = new ArrayList();
			if (year != currentYear) {
				currentMonth = 12;
			}
			for (int i = 1; i <= currentMonth; i++) {
				outFlows.add(new Long(0));
			}
			/*List<UserFlow> linkFlows = YPFS.getUserFlows(user.getUsername());
			Iterator iter = linkFlows.iterator();
			while (iter.hasNext()) {
				UserFlow userFlow = (UserFlow)iter.next();
				if (Integer.parseInt(userFlow.getMonth().substring(0, 4)) == year) {
					outFlows.set(Integer.parseInt(userFlow.getMonth().substring(5))-1, userFlow.getFlow());
				}
			}*/
		}
		return SUCCESS;
	}
	
	public String searchVipOutFlow() {
		/*if (type == 0) 
			//result = userManager.findUncommercialUsers((page - 1) * PAGE_SIZE, PAGE_SIZE); 
		else if (type == 1) 
			//result = userManager.findCommercialUsers((page - 1) * PAGE_SIZE, PAGE_SIZE); */
		if (result!=null && result.getTotal()>0) {
			List list = result.getData();
			oldFlows = new ArrayList(list.size());
			outFlows = new ArrayList(list.size());
			maxFlows = new ArrayList(list.size());
			currentVipProducts = new ArrayList(list.size());
			Iterator iter = list.iterator();
			while(iter.hasNext()) {
				User user = (User)iter.next();
				List flowList = getUserOutFlow(user);
				oldFlows.add(flowList.get(0));
				outFlows.add(flowList.get(1));
				maxFlows.add(getMaxOutFlow(user));
				currentVipProducts.add(getCurrentVipProduct(user));
			}
			
		}
		return SUCCESS;
	}
	
	public List getUserOutFlow(User user) {
		List outFlows = new ArrayList();
		outFlows.add(0L);
		outFlows.add(0L);
		try {
			if (user != null) {
				/*List<UserFlow> linkFlows = YPFS.getUserFlows(user.getUsername());
				Collections.reverse(linkFlows);
				Iterator iter = linkFlows.iterator();
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				while (iter.hasNext()) {
					UserFlow userFlow = (UserFlow)iter.next();
					if (Integer.parseInt(userFlow.getMonth().substring(0, 4)) == cal.get(Calendar.YEAR) && Integer.parseInt(userFlow.getMonth().substring(5)) == cal.get(Calendar.MONTH)) {
						outFlows.set(0, userFlow.getFlow());
					}
					
					if (Integer.parseInt(userFlow.getMonth().substring(0, 4)) == cal.get(Calendar.YEAR) && Integer.parseInt(userFlow.getMonth().substring(5)) == 1 + cal.get(Calendar.MONTH)) {
						outFlows.set(1, userFlow.getFlow());
					}
				}*/
			}
		} catch(Exception e) {
			return outFlows; 
		}
		return outFlows; 
	}
	
	public long getMaxOutFlow(User user) {
		if (user == null) return 0L;
		maxOutFlow = 0l;
		/*VipProduct vipProduct = null;
		maxOutFlow = 0l;
		
		if (!(user.isInRole(Painiu.VIP_BUSINESS_ROLE) || user.isInRole(Painiu.VIP_NORMAL_ROLE))) {
			return 0L;
		} else {
			VIPOption vipOption = null;
			try {
				vipOption = userManager.getVIPOption(user.getId());
			} catch (ObjectRetrievalFailureException oe) {
				
			}
			if (vipOption != null) {
				maxOutFlow = vipOption.getOutFlowmeter();
				vipProduct = vipOption.getVipProduct();
			}
			
		}
		if (vipProduct != null && vipProduct.getRank() > 9 ) {
			maxOutFlow = -1L;
		}*/
		return maxOutFlow;
	}
	
	public String getCurrentVipProduct(User user) {
		/*if (user != null && (user.isInRole(Painiu.VIP_BUSINESS_ROLE) || user.isInRole(Painiu.VIP_NORMAL_ROLE))) {
			VIPOption vipOption = null;
			try {
				vipOption = userManager.getVIPOption(user.getId());
			} catch (ObjectRetrievalFailureException oe) {
				return "none";
			}
			if (vipOption != null) {
				return vipOption.getVipProduct().getDisplayWords();
			}
		}*/
		return "none";
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pagesize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPageSize(int pagesize) {
		this.pageSize = pagesize;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return the orderbyLogin
	 */
	public int getOrderbyLogin() {
		return orderbyLogin;
	}

	/**
	 * @param orderbyLogin the orderbyLogin to set
	 */
	public void setOrderbyLogin(int orderbyLogin) {
		this.orderbyLogin = orderbyLogin;
	}

	/**
	 * @return the ranks
	 */
	public int[] getRanks() {
		return ranks;
	}

	/**
	 * @param ranks the ranks to set
	 */
	public void setRanks(int[] ranks) {
		this.ranks = ranks;
	}

	/**
	 * @return the userApply
	 */
	/*public ApplyFastCheck getUserApply() {
		return userApply;
	}*/



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public Long getOutFlow() {
		return outFlow;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public List getOutFlows() {
		return outFlows;
	}


	/*public VipProduct getCurrentVipProduct() {
		return currentVipProduct;
	}*/

	public Long getMaxOutFlow() {
		return maxOutFlow;
	}

	public List getCurrentVipProducts() {
		return currentVipProducts;
	}

	public List getMaxFlows() {
		return maxFlows;
	}

	public List getOldFlows() {
		return oldFlows;
	}

	//~ Accessors ==============================================================
}

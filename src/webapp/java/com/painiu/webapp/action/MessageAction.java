/*
 * @(#)MessageAction.java  2009-12-18
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.painiu.Painiu;
import com.painiu.core.exception.MissingParameterException;
//import com.painiu.core.model.BlockedUser;
import com.painiu.core.model.Contact;
import com.painiu.core.model.Message;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.model.Message.State;
import com.painiu.core.search.Result;
import com.painiu.util.Blacklist;

/**
 * <p>
 * <a href="MessageAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MessageAction extends BaseAction {
	//~ Static fields/initializers =============================================

//	private static final Log log = LogFactory.getLog(MessageAction.class);
	
	//~ Instance fields ========================================================
	
	private String id;
	private Message message;
	
	private String[] ids;
	
	private String reply;
	private Message replyMessage;
	private String[] to;
	private User recipient;
	
	private int page = 1;
	private int limit = 15;
	
	private Result result;
	
	private List contacts;
	private List friends;
	private List family;
	
	private String op;
	private String operations;
	private String keyword;
	private String box;
	//~ Constructors ===========================================================


	//~ Methods ================================================================
	public String execute() throws Exception {
		result = messageManager.getReceivedMessages(
				getCurrentUser(), (page - 1) * limit, limit);
		
		return SUCCESS;
	}
	
	
	public String sent() throws Exception {
		result = messageManager.getSentMessages(
				getCurrentUser(), (page - 1) * limit, limit);
		
		return SUCCESS;
	}
	
	public String view() throws Exception {
		assertParamExists("id", id);
		message = messageManager.getMessage(id);
		
		if (message.getState().equals(Message.State.UNREAD)) {
			message.setState(Message.State.READ);
			messageManager.saveMessage(message);
		}
		
		// force reload unread messages
		getSession().removeAttribute(Painiu.INBOX_MESSAGES_KEY);
		
		return SUCCESS;
	}
	
	public String operation() throws Exception {
		assertParamExists("box", box);
		if (op == null && operations == null) {
			throw new MissingParameterException("op|operations");
		}
		
		if (StringUtils.isEmpty(operations) && op.equals("search")) { 
			//search
			
			if ("out".equals(box)) {
				result = messageManager.getSentMessages(
					getCurrentUser(), keyword, (page - 1) * limit, limit);
				return "outbox";
			}else if ("in".equals(box)) {
				result = messageManager.getReceivedMessages(
						getCurrentUser(), keyword, (page - 1) * limit, limit);
				return "inbox";
			}
			
		}else if (operations.equals("") && op.equals("delete")) {
			//delete
			if (id == null && ids == null) {
				throw new MissingParameterException("id|ids");
			}
			
			if (id != null) {
				message = messageManager.getMessage(id);
				messageManager.removeMessage(message);
			} else {
				for (int i = 0; i < ids.length; i++) {
					message = messageManager.getMessage(ids[i]);
					messageManager.removeMessage(message);
				}
			}

			if (from != null) {
				return redirect(from);
			}
			
		} else { // other operation  NOTICE: REPLIED message can not be changed
			if ("".equals(op)) {
				
				if (id == null && ids == null) {
					throw new MissingParameterException("id|ids");
				}
				
				if ("tr".equals(operations)) {
					// to readed
					if (id != null) {
						Message old = messageManager.getMessage(id);
						if (Message.State.UNREAD.equals(old.getState())) {
							old.setState(Message.State.READ);
							messageManager.saveMessage(old);
						}
					} else {
						for (int i = 0; i < ids.length; i++) {
							Message old = messageManager.getMessage(ids[i]);
							if (Message.State.UNREAD.equals(old.getState())) {
								old.setState(Message.State.READ);
								messageManager.saveMessage(old);
							}
						}
					}
					
					if (from != null) {
						return redirect(from);
					}
					
				} else if ("tu".equals(operations)) {
					// to unread
					if (id != null) {
						Message old = messageManager.getMessage(id);
						if (Message.State.READ.equals(old.getState())) {
							old.setState(Message.State.UNREAD);
							messageManager.saveMessage(old);
						}
					} else {
						for (int i = 0; i < ids.length; i++) {
							Message old = messageManager.getMessage(ids[i]);
							if (Message.State.READ.equals(old.getState())) {
								old.setState(Message.State.UNREAD);
								messageManager.saveMessage(old);
							}
						}
					}
					
					if (from != null) {
						return redirect(from);
					}
					
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (id == null && ids == null) {
			throw new MissingParameterException("id|ids");
		}
		if (id != null) {
			message = messageManager.getMessage(id);
			messageManager.removeMessage(message);
		} else {
			for (int i = 0; i < ids.length; i++) {
				message = messageManager.getMessage(ids[i]);
				messageManager.removeMessage(message);
			}
		}
		
		if (from != null) {
			return redirect(from);
		}
		
		return SUCCESS;
	}
	
	public String compose() throws Exception {
		if (message == null) {
			message = new Message();
		}
		
		if (to != null && to.length == 1) {
			recipient = userManager.getUser(to[0]);
		} else if (reply != null) {
			replyMessage = messageManager.getMessage(reply);
			recipient = replyMessage.getSender();
			message.setSubject("Re:" + replyMessage.getSubject());
		} else {
			/*List all = contactManager.getContacts(getCurrentUser());
			
			contacts = new ArrayList();
			friends = new ArrayList();
			family = new ArrayList();
			
			for (Iterator i = all.iterator(); i.hasNext();) {
				Contact c = (Contact) i.next();
				if (c.getRelation().equals(Relation.CONTACT)) {
					contacts.add(c);
				} else if (c.getRelation().equals(Relation.FRIEND)) {
					friends.add(c);
				} else if (c.getRelation().equals(Relation.FAMILY)) {
					family.add(c);
				}
			}*/
		}
		
		return SUCCESS;
	}
	
	public String send() throws Exception {
		validateMessage();
		if (hasFieldErrors()) {
			compose();
			return INPUT;
		}
		
		/*if ( Blacklist.getBlacklist().isBlacklisted(message.getSubject())) {
       	 	saveActionError(getText("errors.subject.in.blacklist"));
       	 	return INPUT;
		}
		if ( Blacklist.getBlacklist().isBlacklisted(message.getContent())) {
       	 	saveActionError(getText("errors.content.in.blacklist"));
       	 	return INPUT;
		}*/
		
		User sender = getCurrentUser();
		
		message.setSender(sender);
		message.setSentDate(new Date());
		message.setState(Message.State.UNREAD);
		
		Set toIds = new HashSet(to.length);
		
		List blocked = new ArrayList(to.length);
		for (int i = 0; i < to.length; i++) {
			if (!toIds.contains(to[i])) {
				recipient = userManager.getUser(to[i]);
				if (!sender.equals(recipient)) {
					Message msg = new Message();
					BeanUtils.copyProperties(msg, message);
					msg.setRecipient(recipient);
					msg.setOwner(recipient);
					messageManager.saveMessage(msg);
				}
				toIds.add(to[i]);
				/*BlockedUser blockedUser = blockedUserManager.getBlockedUser(sender, recipient);
				BlockedUser reversed = blockedUserManager.getBlockedUser(recipient, sender);
				if (blockedUser == null && reversed == null) {
					if (!sender.equals(recipient)) {
						Message msg = new Message();
						BeanUtils.copyProperties(msg, message);
						msg.setRecipient(recipient);
						msg.setOwner(recipient);
						messageManager.saveMessage(msg);
					}
					toIds.add(to[i]);
				} else {
					blocked.add(recipient.getNickname());
				}*/
			}
		}
		
		
		if (recipient != null && !(sender.equals(recipient))) {
			Message copy = new Message();
			BeanUtils.copyProperties(copy, message);
			copy.setRecipient(recipient);
			copy.setOwner(sender);
			copy.setState(Message.State.READ);
			messageManager.saveMessage(copy);
		}
		
		if (reply != null) {
			replyMessage = messageManager.getMessage(reply);
			replyMessage.setState(State.REPLIED);
			messageManager.saveMessage(replyMessage);
		}
		if (blocked.size() > 0) {
			saveError(getText("errors.messages.failed", new String[] {StringUtils.join(blocked.toArray(),",")}));
		} else {
			saveMessage(getText("messages.message.sent"));
		}
		return SUCCESS;
	}
	
	private void validateMessage() {
		if (to == null || to.length == 0) {
			addFieldError("to", getText("errors.message.to.required"));
		}
		if (StringUtils.isEmpty(message.getSubject())) {
			addFieldError("message.subject", getText("errors.message.subject.required"));
		}
		if (StringUtils.isEmpty(message.getContent())) {
			addFieldError("message.content", getText("errors.message.content.required"));
		}
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @param page The page to set.
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * @return Returns the result.
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @return Returns the recipient.
	 */
	public User getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient The recipient to set.
	 */
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return Returns the to.
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * @param to The to to set.
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * @return Returns the message.
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * @return Returns the reply.
	 */
	public String getReply() {
		return reply;
	}

	/**
	 * @param reply The reply to set.
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	/**
	 * @return Returns the replyMessage.
	 */
	public Message getReplyMessage() {
		return replyMessage;
	}
	/**
	 * @return Returns the contacts.
	 */
	public List getContacts() {
		return contacts;
	}
	/**
	 * @return Returns the friends.
	 */
	public List getFriends() {
		return friends;
	}
	/**
	 * @return Returns the family.
	 */
	public List getFamily() {
		return family;
	}
	/**
	 * @param ids The ids to set.
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the op
	 */
	public String getOp() {
		return op;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(String op) {
		this.op = op;
	}

	/**
	 * @return the operations
	 */
	public String getOperations() {
		return operations;
	}

	/**
	 * @param operations the operations to set
	 */
	public void setOperations(String operations) {
		this.operations = operations;
	}
	

	/**
	 * @return the box
	 */
	public String getBox() {
		return box;
	}

	/**
	 * @param box the box to set
	 */
	public void setBox(String box) {
		this.box = box;
	}
	
}

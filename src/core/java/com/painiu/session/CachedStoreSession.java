/*
 * @(#)CachedStoreSession.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <p>
 * <a href="CachedStoreSession.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CachedStoreSession.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class CachedStoreSession implements Session {
	//~ Static fields/initializers =============================================

	private String id;
	
	private long creationTime = 0L;
	
	private long lastAccessedTime = creationTime;
	
	private long thisAccessedTime = creationTime;
	
	private int maxInactiveInterval = -1;
	
	private boolean isValid = true;
	private boolean isNew = true;
	
	private transient boolean isDirty = false;
	
    private Map attributes = new HashMap();
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

    public CachedStoreSession(String id) {
    	this.id = id;
    	this.creationTime = System.currentTimeMillis();
    	this.lastAccessedTime = creationTime;
    	this.thisAccessedTime = creationTime;
    }
    
	//~ Methods ================================================================

	/*
	 * @see com.mingda.session.Session#getCreationTime()
	 */
	public long getCreationTime() {
		assertValid();
		return creationTime;
	}

	/*
	 * @see com.mingda.session.Session#getId()
	 */
	public String getId() {
		return id;
	}

	/*
	 * @see com.mingda.session.Session#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		assertValid();
		return lastAccessedTime;
	}

	/*
	 * @see com.mingda.session.Session#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
		this.isDirty = true;
        if (isValid && interval == 0) {
            expire();
        }
	}

	/*
	 * @see com.mingda.session.Session#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	/*
	 * @see com.mingda.session.Session#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
        assertValid();
        
        return attributes.get(name);
	}

	/*
	 * @see com.mingda.session.Session#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		assertValid();
		
		if (value == null) {
            removeAttribute(name);
            return;
        }
		if (!(value instanceof Serializable)) {
			throw new IllegalArgumentException("Set session attribute failed: non serializable value.");
		}
		
		attributes.put(name, value);
		
		// Do store session just once a request.
		isDirty = true;
//		SessionManagerFactory.getSessionManager().saveSession(this);
	}
	
	/* (non-Javadoc)
	 * @see com.mingda.session.Session#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		assertValid();
		
		return (new Enumerator(attributes.keySet(), true));
	}
	
	/*
	 * @see com.mingda.session.Session#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		assertValid();
		
		Object value = attributes.remove(name);
		
		if (value != null) {
			// Do store session just once a request.
			isDirty = true;
//			SessionManagerFactory.getSessionManager().saveSession(this);
		}
	}

	/*
	 * @see com.mingda.session.Session#invalidate()
	 */
	public void invalidate() {
        assertValid();
        // Cause this session to expire
        expire();
	}

	/*
	 * @see com.mingda.session.Session#isValid()
	 */
    public boolean isValid() {
        if (!this.isValid ) {
            return false;
        }

        if (maxInactiveInterval >= 0) { 
            long timeNow = System.currentTimeMillis();
            int timeIdle = (int) ((timeNow - thisAccessedTime) / 1000L);
            if (timeIdle >= maxInactiveInterval) {
                expire();
            }
        }

        return (this.isValid);
    }

	/*
	 * @see com.mingda.session.Session#expire()
	 */
	public void expire() {
        synchronized (this) {
//            accessCount = 0;
            isValid = false;

            // Remove this session from our manager's active sessions
            SessionManagerFactory.getSessionManager().removeSession(this);
//            manager.removeSession(this);
        }
	}

    /*
	 * @see com.mingda.session.Session#access()
	 */
	public void access() {
		this.lastAccessedTime = this.thisAccessedTime;
		this.thisAccessedTime = System.currentTimeMillis();
		this.isDirty = true;
	}

	/* (non-Javadoc)
	 * @see com.mingda.session.Session#isNew()
	 */
	public boolean isNew() {
		return isNew;
	}

	/* (non-Javadoc)
	 * @see com.mingda.session.Session#setNew(boolean)
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	/* (non-Javadoc)
	 * @see com.mingda.session.Session#isDirty()
	 */
	public boolean isDirty() {
		return isDirty;
	}

	private void assertValid() {
    	if (!isValid()) {
            throw new IllegalStateException("session is invalide");
        }
    }
	

	static class Enumerator implements Enumeration {
	    private Iterator iterator = null;
		
	    public Enumerator(Collection collection) {
	        this(collection.iterator());
	    }

	    public Enumerator(Collection collection, boolean clone) {
	        this(collection.iterator(), clone);
	    }

	    public Enumerator(Iterator iterator) {
	        this.iterator = iterator;
	    }

	    public Enumerator(Iterator iterator, boolean clone) {
	        if (!clone) {
	            this.iterator = iterator;
	        } else {
	            List list = new ArrayList();
	            while (iterator.hasNext()) {
	                list.add(iterator.next());
	            }
	            this.iterator = list.iterator();   
	        }
	    }

	    public Enumerator(Map map) {
	        this(map.values().iterator());
	    }

	    public Enumerator(Map map, boolean clone) {
	        this(map.values().iterator(), clone);
	    }

	    public boolean hasMoreElements() {
	        return (iterator.hasNext());
	    }

	    public Object nextElement() throws NoSuchElementException {
	        return (iterator.next());
	    }
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-791033205, -2035490857)
				.append(this.creationTime)
				.append(this.attributes)
				.append(this.maxInactiveInterval)
				.append(this.thisAccessedTime)
				.append(this.lastAccessedTime)
				.append(this.isNew)
				.append(this.isValid)
				.append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CachedStoreSession)) {
			return false;
		}
		CachedStoreSession rhs = (CachedStoreSession) object;
		return new EqualsBuilder().append(this.id, rhs.id).isEquals();
	}
	
}


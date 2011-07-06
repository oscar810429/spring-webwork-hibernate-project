/*
 * @(#)ApiObject.java  2006-2-20
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.search;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <a href="ApiObject.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Result.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public class Result<T> implements Iterable<T>, Serializable {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = -3227685040036327514L;

	//~ Instance fields ========================================================
	
	private int offset;
    private int size;
    private int total;

    private List<T> data;
    
    private List<T> requestedData;

    //~ Constructors ===========================================================
    
    public Result() {
    }
    
    public Result(int offset, int size) {
    	this(null, offset, size);
    }
    
    public Result(List<T> data, int offset, int size) {
    	this.data = data;
    	this.offset = offset;
    	this.size = size;
    }

    //~ Methods ================================================================

    public boolean isFirst() {
    	return offset == 0;
    }
    
    public boolean isLast() {
    	return !hasNext();
    }
    
    public boolean hasNext() {
    	if (data == null || size < 0) {
    		return false;
    	}
    	return data.size() > size;
    }
    
    public int getTotalPage() {
    	if (size < 0) return 1;
    	
    	return total % size == 0 ? total/size : total/size + 1;
    }
    
    public int getPage() {
    	return offset/size + 1;
    }
    
    //~ Accessors ==============================================================

    public void setData(List<T> data) {
        this.data = data;
        this.requestedData = null;
    }

    public List<T> getData() {
    	if (requestedData == null) {
    		if (size < 0 || data.size() <= size) {
    			requestedData = data;
    		} else {
    			requestedData = data.subList(0, size);
    		}
    	}
    	return requestedData;
    }

    public List<T> getOriginalData() {
    	return data;
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return getData().iterator();
	}

    /**
	 * @return Returns the offset.
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset The offset to set.
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
    /**
     * @return Returns the size.
     */
    public int getSize() {
        return size;
    }
    /**
     * @param size The size to set.
     */
    public void setSize(int size) {
        this.size = size;
    }
    /**
     * @return Returns the total.
     */
    public int getTotal() {
        return total;
    }
    /**
     * @param total The total to set.
     */
    public void setTotal(int total) {
        this.total = total;
    }

}


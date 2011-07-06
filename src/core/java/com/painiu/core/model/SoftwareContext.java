/**
 * @(#)SoftwareContext.java 2010-6-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="SoftwareContext.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareContext.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareContext extends BaseObject{
    
	//~ Static fields/initializers =============================================
	 
	 private static final long serialVersionUID = 3576622399898931114L;
	
	 //~ Instance fields ===================================================
	
	 private Software prev;
	 private Software next;
	

	//~ Constructors ====================================================
	 
	 public SoftwareContext(){}
	 
	 public SoftwareContext(Software prev,Software next){
			this.prev = prev;
			this.next = next;
	 }


	//~ Methods =======================================================
	 

		/**
		 * @return the prev
		 */
		public Software getPrev() {
			return prev;
		}

		/**
		 * @param prev the prev to set
		 */
		public void setPrev(Software prev) {
			this.prev = prev;
		}

		/**
		 * @return the next
		 */
		public Software getNext() {
			return next;
		}

		/**
		 * @param next the next to set
		 */
		public void setNext(Software next) {
			this.next = next;
		}
		
		/**
		 * @see java.lang.Object#equals(Object)
		 */
		public boolean equals(Object object) {
			if (!(object instanceof SoftwareContext)) {
				return false;
			}
			SoftwareContext rhs = (SoftwareContext) object;
			return new EqualsBuilder().append(this.prev, rhs.prev).append(
					this.next, rhs.next).isEquals();
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return new HashCodeBuilder(1435472341, 31255639).append(this.prev)
					.append(this.next).toHashCode();
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return new ToStringBuilder(this).append("prev", this.prev).append(
					"next", this.next).toString();
		}	

	//~ Accessors ======================================================
	 
	 

}

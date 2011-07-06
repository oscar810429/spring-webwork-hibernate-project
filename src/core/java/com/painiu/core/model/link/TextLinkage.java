/*
 * @(#)TextLinkage.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model.link;

/**
 * <p>
 * <a href="TextLinkage.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TextLinkage.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class TextLinkage extends TextLink{
		private int order;

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}
}

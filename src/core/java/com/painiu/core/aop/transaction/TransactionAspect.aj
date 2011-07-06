/*
 * @(#)TransactionAspect.aj Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.aop.transaction;

import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.aspectj.AbstractTransactionAspect;
import com.painiu.core.aop.BeforeTx;
import com.painiu.core.aop.Tx;

/**
 * <p>
 * <a href="TransactionAspect.aj.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TransactionAspect.aj 75 2010-06-21 02:08:45Z zhangsf $
 */
@Tx
public aspect TransactionAspect extends AbstractTransactionAspect {
	public TransactionAspect() {
		super(new AnnotationTransactionAttributeSource(true));
	}
	
	declare precedence : (@BeforeTx *), (@Tx *);
	
	private pointcut serviceTypes() : execution(* com.painiu.core.service..*.*(..));

	private pointcut executionOfAnyPublicMethodInAtTransactionalType() :
		execution(public * ((@Transactional *)+).*(..)) && @this(Transactional);
	

	private pointcut executionOfTransactionalMethod() :
		execution(* *(..)) && @annotation(Transactional);
	
	private pointcut nonTransactional() : @annotation(NonTransactional);
	
	/**
	 * Definition of pointcut from super aspect - matched join points
	 * will have Spring transaction management applied.
	 */	
	protected pointcut transactionalMethodExecution(Object txObject) :
		serviceTypes() &&
		(executionOfAnyPublicMethodInAtTransactionalType()
		 || executionOfTransactionalMethod() )
		 && !nonTransactional()
		 && this(txObject);
}

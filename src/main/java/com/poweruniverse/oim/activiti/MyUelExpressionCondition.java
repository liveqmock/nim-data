package com.poweruniverse.oim.activiti;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.el.UelExpressionCondition;


/**
 * {@link Condition} that resolves an UEL expression at runtime.  
 * 
 * @author Joram Barrez
 * @author Frederik Heremans
 */
public class MyUelExpressionCondition extends UelExpressionCondition {

	public MyUelExpressionCondition(Expression expression) {
		super(expression);
	}

}

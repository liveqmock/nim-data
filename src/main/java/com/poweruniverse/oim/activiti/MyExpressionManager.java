package com.poweruniverse.oim.activiti;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.ExpressionManager;

public class MyExpressionManager extends ExpressionManager {

	public MyExpressionManager() {
		super();
	}
	
	public Expression createSequenceFlowExpression(String expressionText,String processDefKey,String seqFlowId) {
		return new SequenceFlowConditionExpression(expressionText,processDefKey,seqFlowId);
	}

	public Expression createTaskCandidateExpression(String expressionText) {
		return new TaskCandidateExpression(expressionText);
	}

}

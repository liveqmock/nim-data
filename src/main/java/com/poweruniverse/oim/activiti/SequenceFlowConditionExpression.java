
package com.poweruniverse.oim.activiti;

import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.Expression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import com.poweruniverse.nim.data.service.utils.TaskUtils;

/*
 * 用平台权限系统 计算并判断分支条件是否满足
 */
public class SequenceFlowConditionExpression implements Expression {

	protected Integer gongNengLCDM=null;
	protected String seqFlowId=null;
	protected String processDefKey=null;
	
	public SequenceFlowConditionExpression(String expressionText,String processDefKey,String seqFlowId) {
		this.seqFlowId=seqFlowId;
		this.processDefKey=processDefKey;
		if(expressionText!=null){
			gongNengLCDM= Integer.valueOf(expressionText);
		}else{
			gongNengLCDM = null;
		}
	}
	
	//根据流程条件（gongNengLCDM） 以及流程变量
	//判断当前分支是否满足流转条件
	public Object getValue(VariableScope variableScope) {
		//流程数据
		Integer dataId = null;
		if(variableScope instanceof ExecutionEntity ){
			ProcessInstance processInstance =((ExecutionEntity)variableScope).getProcessInstance();
			if(processInstance!=null){
				String businessKey =processInstance.getBusinessKey();
				if(businessKey!=null){
					dataId = Integer.valueOf(businessKey);
				}
			}
		}
		if(dataId == null){
			System.out.println("未绑定流程数据对象！");
			return false;
		}
		//流程用户
		Integer yongHuDM = null;
		if(variableScope.hasVariable("yongHuDM") ){
			yongHuDM = (Integer)variableScope.getVariable("yongHuDM");
		}
		if(yongHuDM == null){
			System.out.println("未绑定流程用户！");
			return false;
		}
		boolean ret = false;
		
		try {
			String gndh = processDefKey;
			if(processDefKey.indexOf("-")>0){
				gndh = processDefKey.substring(0,processDefKey.indexOf("-"));
			}
			ret = TaskUtils.checkSequenceFlowCondition(gongNengLCDM, dataId, yongHuDM,gndh,seqFlowId);
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
		}
		return ret;
	}
	
	public void setValue(Object value, VariableScope variableScope) {
		
	}
	
	public String toString() {
		return "流程分支条件：(gongNengLCDM"+":"+gongNengLCDM+")";
	}
	
	public String getExpressionText() {
		return gongNengLCDM==null?null:""+gongNengLCDM;
	}
}

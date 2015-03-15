
package com.poweruniverse.oim.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.Expression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import com.poweruniverse.nim.data.entity.sys.LiuChengJS;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.service.utils.TaskUtils;

/*
 * 用平台权限系统 计算有权限进行此项操作的用户
*/
public class TaskCandidateExpression implements Expression {
	
	protected Integer gongNengCZDM;
	
	public TaskCandidateExpression(String expressionText) {
		if(expressionText!=null){
			gongNengCZDM= Integer.valueOf(expressionText);
		}else{
			gongNengCZDM = null;
		}
	}

	public Object getValue(VariableScope variableScope) {
		List<String> candidateUserDHs = new ArrayList<String>();
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
			return candidateUserDHs;
		}
		LiuChengJS liuChengJS = (LiuChengJS)variableScope.getVariable("liuChengJS");
		boolean isInstanceStart = false;
		if(variableScope.hasVariable("isInstanceStart") ){
			isInstanceStart = (Boolean)variableScope.getVariable("isInstanceStart");
		}
		try {
			List<YongHu> candidateUsers = TaskUtils.getCandidateUsers(gongNengCZDM, dataId,isInstanceStart,liuChengJS);
			for(YongHu yh:candidateUsers){
				candidateUserDHs.add(yh.getDengLuDH());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return candidateUserDHs;
	}

	public void setValue(Object value, VariableScope variableScope) {
	}

	public String toString() {
		return "任务执行人条件：(gongNengCZDM"+":"+gongNengCZDM+")";
	}

	public String getExpressionText() {
		return gongNengCZDM==null?null:""+gongNengCZDM;
	}
}

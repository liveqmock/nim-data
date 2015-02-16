package com.poweruniverse.oim.activiti;

import java.io.Serializable;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;

public class ClearProcessInstanceBussinessKeyCmd implements Command<Void>, Serializable {
		  
	  private static final long serialVersionUID = 1L;
	  protected String processInstanceId;

	  public ClearProcessInstanceBussinessKeyCmd(String processInstanceId) {
	    this.processInstanceId = processInstanceId;
	  }

	  public Void execute(CommandContext commandContext) { 
	    if(processInstanceId == null) {
	      throw new ActivitiException("processInstanceId is null");
	    }
//	    ExecutionEntity execution = commandContext.getExecutionManager().findExecutionById(processInstanceId);
	    
//	    HistoricProcessInstanceEntity execution = commandContext.getHistoricProcessInstanceManager().findHistoricProcessInstance(processInstanceId);
	    HistoricProcessInstanceEntity execution =  commandContext.getDbSqlSession().selectById(HistoricProcessInstanceEntity.class, processInstanceId);
	    execution.setBusinessKey(null);
	    execution.setDeleteReason("暂时状态:移除business_key_");
	    return null;
	  }

}

package com.poweruniverse.nim.data.entity.system.base;

import java.util.List;

import com.poweruniverse.nim.data.entity.system.BuMen;
import com.poweruniverse.nim.data.entity.system.LiuChengJS;
import com.poweruniverse.nim.data.entity.system.YongHu;

public interface BusinessI extends EntityI{
	// 所属部门 （suoShuBM）
	public BuMen getSuoShuBM();
	public void setSuoShuBM(BuMen zhuanYeGS);
	
	// 所有者 （suoYouZhe）
	public YongHu getSuoYouZhe();
	public void setSuoYouZhe(YongHu suoYouZhe);
	
	// 任务执行者 （assignee 通过转委托等得到的权限）
	public YongHu getAssignee();
	public void setAssignee(YongHu assignee);
	
	// 录入人 （luRuRen）
	public java.lang.String getLuRuRen();
	public void setLuRuRen(java.lang.String luRuRen);
	// 录入日期 （luRuRQ）
	public java.util.Date getLuRuRQ();
	public void setLuRuRQ(java.util.Date luRuRQ);

	// 属性：修改人 （xiuGaiRen）
	public java.lang.String getXiuGaiRen();
	public void setXiuGaiRen(java.lang.String xiuGaiRen);
	// 属性：修改日期 （xiuGaiRQ）
	public java.util.Date getXiuGaiRQ();
	public void setXiuGaiRQ(java.util.Date xiuGaiRQ);

	// 属性：审核人 （shenHeRen）
	public java.lang.String getShenHeRen();
	public void setShenHeRen(java.lang.String shenHeRen);
	// 属性：审核日期 （shenHeRQ）
	public java.util.Date getShenHeRQ();
	public void setShenHeRQ(java.util.Date shenHeRQ);
	
	// 属性：流程实例（processInstanceId）
	public java.lang.String getProcessInstanceId();
	public void setProcessInstanceId(java.lang.String processInstanceId);
	
	// 属性：流程完成状态（processInstanceEnded）
	public java.lang.Boolean getProcessInstanceEnded();
	public void setProcessInstanceEnded(java.lang.Boolean processInstanceEnded);
	
	// 属性：流程终止状态（processInstanceTerminated）
	public java.lang.Boolean getProcessInstanceTerminated();
	public void setProcessInstanceTerminated(java.lang.Boolean processInstanceTerminated);
	
	// 属性：流程执行状态 （processInstanceStatus）
	public java.lang.String getProcessInstanceStatus();
	public void setProcessInstanceStatus(java.lang.String processInstanceStatus);

	// 属性：流程暂停状态（processInstanceSuspended）
	public java.lang.Boolean getProcessInstanceSuspended();
	public void setProcessInstanceSuspended(java.lang.Boolean processInstanceSuspended);

	// 属性：流程暂停原因 （processInstanceSuspendReason）
	public java.lang.String getProcessInstanceSuspendReason();
	public void setProcessInstanceSuspendReason(java.lang.String processInstanceSuspendReason);
	
	// 属性：审核意见 （shenHeYJ）
	public java.lang.String getShenHeYJ();
	public void setShenHeYJ(java.lang.String shyj);
	
	// 属性：删除状态 （shanChuZT）
	public java.lang.Boolean getShanChuZT();
	public void setShanChuZT(java.lang.Boolean shanChuZT);

	// 属性：审核状态 （shenHeZT）
	public java.lang.Integer getShenHeZT();
	public void setShenHeZT(java.lang.Integer shenHeZT);
	
	//返回所有已完成及待办的流程节点
	public List<LiuChengJS> getHistoricTasks(String gndh);
	
	//返回一个流程节点 （重复经过的task 取最新一个）
	public LiuChengJS getTodoTask(String gndh,String czdh);
	
	//返回一个待办流程节点 （同时有多个待办task 取最早一个）
	public LiuChengJS getTodoTask(String gndh);
}

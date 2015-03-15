package com.poweruniverse.nim.data.service.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.message.Result;
import com.poweruniverse.nim.data.entity.sys.GongNeng;
import com.poweruniverse.nim.data.entity.sys.GongNengCZ;
import com.poweruniverse.nim.data.entity.sys.GongNengCZBL;
import com.poweruniverse.nim.data.entity.sys.GongNengGZL;
import com.poweruniverse.nim.data.entity.sys.GongNengLC;
import com.poweruniverse.nim.data.entity.sys.JueSe;
import com.poweruniverse.nim.data.entity.sys.LiuChengJS;
import com.poweruniverse.nim.data.entity.sys.LiuChengJSBL;
import com.poweruniverse.nim.data.entity.sys.LiuChengJSSJ;
import com.poweruniverse.nim.data.entity.sys.LiuChengJSXJ;
import com.poweruniverse.nim.data.entity.sys.ShiTiLei;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.entity.sys.ZiDuanLX;
import com.poweruniverse.nim.data.entity.sys.base.BusinessI;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
import com.poweruniverse.oim.activiti.ClearProcessInstanceBussinessKeyCmd;
import com.poweruniverse.oim.activiti.TaskCandidateExpression;


public class TaskUtils {
	private static SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static Logger logger = null;
	

	/**
	 * 对当前流程数据绑定的流程，完成指定的流程操作任务
	 * @param processObj 当前流程数据
	 * @param workFlowGNCZ 流程操作 
	 * @param yh			用户
	 * @param completeTask	是否提交任务
	 * @throws Exception
	 */
	public static synchronized void completeTask(BusinessI processObj,BusinessI oldBusiObj,GongNengCZ workFlowGNCZ,YongHu yh,boolean forceFinish) throws Exception{
		if(logger==null){
			logger = Logger.getLogger("oim_Workflow");
		}
		logger.debug("用户("+yh.getYongHuMC()+":"+yh.getDengLuDH()+")为数据（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+"）执行流程任务（"+workFlowGNCZ.getCaoZuoMC()+":"+workFlowGNCZ.getCaoZuoDH()+"）...");
		try {
			if(processObj.getProcessInstanceId()==null){
				logger.error("流程任务被忽略：数据("+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+":"+processObj.getProcessInstanceStatus()+")未绑定流程,无法执行流程任务！");
				return;
//				throw new Exception("此数据（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+"）未绑定流程,无法执行流程任务！");
			}
			
			
			Session sess = HibernateSessionFactory.getSession();
			//取得流程扩展方法 备用
			Method processInstanceEndedMethod=null;
			Object gnActionInstance =null;
			//如果功能定义了action类 取流程开始和流程结束方法备用
			if(workFlowGNCZ.getGongNeng().getGongNengClass()!=null){
				try {
					Class<?> gnActionClass = Class.forName(workFlowGNCZ.getGongNeng().getGongNengClass());
					gnActionInstance = gnActionClass.newInstance();
					try {
						processInstanceEndedMethod= gnActionClass.getMethod("processInstanceEnded",new Class[]{String.class,Integer.class,Integer.class});
					} catch (Exception e) {
					}
				} catch (Exception e) {
				}
			}
			
			//检查当前功能是否流程功能
			ProcessEngine myProcessEngine = ProcessEngines.getDefaultProcessEngine();
			TaskService taskService = myProcessEngine.getTaskService();
			HistoryService historyService = myProcessEngine.getHistoryService();
			//检查是启动新流程 还是执行已有流程
			//有记录流程实例id的对象 进行流程操作
			//查找对应的流程检视
			LiuChengJS currentLCJS = (LiuChengJS)sess.createCriteria(LiuChengJS.class)
					.add(Restrictions.eq("gongNengDH",workFlowGNCZ.getGongNeng().getGongNengDH()))
					.add(Restrictions.eq("gongNengObjId",processObj.pkValue()))
					.add(Restrictions.eq("caoZuoDH", workFlowGNCZ.getCaoZuoDH()))
					.add(Restrictions.eq("shiFouSC", false))
					.add(Restrictions.eq("shiFouWC", false))
					.uniqueResult();
			if(currentLCJS==null){
				logger.error("流程任务被忽略:数据("+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+":"+processObj.getProcessInstanceStatus()+")没有对应的待办流程检视信息("+
						workFlowGNCZ.getGongNeng().getGongNengDH()+"."+workFlowGNCZ.getCaoZuoDH()+"),无法执行流程任务！");
				
				List<LiuChengJS> currentLCJSs = (List<LiuChengJS>)sess.createCriteria(LiuChengJS.class)
						.add(Restrictions.eq("gongNengDH",workFlowGNCZ.getGongNeng().getGongNengDH()))
						.add(Restrictions.eq("gongNengObjId",processObj.pkValue()))
						.add(Restrictions.eq("shiFouSC", false))
						.add(Restrictions.eq("shiFouWC", false))
						.list();
				logger.debug("	当前数据的待办任务数量："+currentLCJSs.size());
				
				String processInstanceStatusInfo = "[";
				for(int ii=0;ii<currentLCJSs.size();ii++){
					LiuChengJS currentLCJS2 = currentLCJSs.get(ii);
					logger.debug("		待办任务"+ii+" ("+currentLCJS2.getCaoZuoDH()+":"+currentLCJS2.getCaoZuoMC()+") 操作人："+currentLCJS2.getCaoZuoRen());
					
					//有待办任务 未终止流程 
					//记录当前流程执行状态
					if(processInstanceStatusInfo.length()==1){
						processInstanceStatusInfo += currentLCJS2.getCaoZuoMC();
					}else{
						processInstanceStatusInfo += ","+currentLCJS2.getCaoZuoMC();
					}
				}
				processInstanceStatusInfo += "]";
				//大部分运行到此处的 都是因为没有刷新页面 导致重复执行同一个待办 所以忽略即可
				if(currentLCJSs.size() > 0 && !processInstanceStatusInfo.equals(processObj.getProcessInstanceStatus())){
					logger.info("更新流程状态信息:业务数据("+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+")状态信息:"+processObj.getProcessInstanceStatus()+" -> "+
							processInstanceStatusInfo+"");
					processObj.setProcessInstanceStatus(processInstanceStatusInfo);
					sess.update(processObj);
				}
				return ;
			}
			//查询任务对象 将任务绑定到当前用户
			Task task = taskService.createTaskQuery()
					.processInstanceId(processObj.getProcessInstanceId())//当前数据绑定的流程
					.taskCandidateUser(yh.getDengLuDH())//当前用户是潜在执行人之一（这里要用登陆代号 保证唯一）
					.taskDefinitionKey(workFlowGNCZ.getCaoZuoDH())//任务代号与当前执行的操作代号一致
					.singleResult();
			if(task!=null){
				//此任务未绑定到当前用户 进行绑定
				taskService.claim(task.getId(), yh.getDengLuDH());//为当前任务设置执行人（可能执行失败 因为任务被别人先完成了）
				
				//将操作人设置为当前用户
				currentLCJS.setCaoZuoRen(yh.getYongHuMC()+" ");
				currentLCJS.setCaoZuoXX("【处理中】");
				currentLCJS.setShiFouCK(true);
				currentLCJS.setShiFouCL(true);
				
				
			}else{
				//此任务可能已经绑定到当前用户
				task = taskService.createTaskQuery()
						.processInstanceId(processObj.getProcessInstanceId())//当前数据绑定的流程
						.taskAssignee(yh.getDengLuDH())//当前用户执行人 （这里要用登陆代号 保证唯一）
						.taskDefinitionKey(workFlowGNCZ.getCaoZuoDH())//任务代号与当前执行的操作代号一致
						.singleResult();
				if(task==null){
					if(currentLCJS!=null && forceFinish && (yh.isSuperUser() || currentLCJS.getCaoZuoRen().contains(yh.getYongHuMC()+" ")) ){
						//管理员或任务执行人  可以执行不同步的流程任务
						List<Task> tasks = taskService.createTaskQuery()
								.processInstanceId(processObj.getProcessInstanceId())//当前数据绑定的流程
								.list();
						if(tasks.size()==1){
							HistoricProcessInstanceEntity hisprocessInstance = (HistoricProcessInstanceEntity)historyService
									.createHistoricProcessInstanceQuery()
									.processInstanceId(processObj.getProcessInstanceId())
									.singleResult();
							
							//获取流程定义
							RepositoryServiceImpl repositoryService = (RepositoryServiceImpl)myProcessEngine.getRepositoryService();
							ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
									.getDeployedProcessDefinition(hisprocessInstance.getProcessDefinitionId());

							String taskDefineKey = ((TaskEntity)tasks.get(0)).getTaskDefinitionKey();
							ActivityImpl activityIml = ((ProcessDefinitionImpl)processDefinition).findActivity(taskDefineKey);
							
//							List<Execution> executions = myProcessEngine.getRuntimeService().createExecutionQuery().executionId(tasks.get(0).getExecutionId()).list();
//							ExecutionEntity execution = (ExecutionEntity)executions.get(0);
							List<PvmTransition> transitions = activityIml.getIncomingTransitions();
							for(PvmTransition transition:transitions){
								if(transition.getSource().getId().equals(currentLCJS.getCaoZuoDH())){
									//添加流程检视记录 使业务系统与工作流引擎的流程保持一致
									synLiuChengJS2Workflow(workFlowGNCZ, currentLCJS, processObj, oldBusiObj, activityIml,transition,yh);
									break;
								}
							}
							//执行不同步的流程任务
							logger.info("添加流程检视记录,使业务系统与工作流引擎的流程保持一致:流程检视状态：("+workFlowGNCZ.getCaoZuoDH()+":"+workFlowGNCZ.getCaoZuoMC()+") -> 工作流引擎状态("+tasks.get(0).getId()+":"+tasks.get(0).getName()+") ");
						}else{
							//执行不同步的流程任务
							logger.error("可能需要添加流程检视记录，使业务系统与工作流引擎的流程保持一致。但工作流引擎中存在多于一条的待办任务，系统不能自动处理)！");
						}
						return;
					}else{
						//不存在任务 就返回 以后改成先检查 后执行
						logger.error("流程任务被忽略:工作流引擎中不存在与数据("+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+":"+processObj.getProcessInstanceId()+")相关的用户("+yh.getYongHuMC()+")的流程任务("+
								workFlowGNCZ.getGongNeng().getGongNengDH()+"."+workFlowGNCZ.getCaoZuoDH()+")！");
						return;
					}
//					throw new Exception("工作流引擎中不存在用户("+yh.getYongHuMC()+")的流程任务（"+workFlowGNCZ.getCaoZuoMC()+":"+workFlowGNCZ.getCaoZuoDH()+"）...！");
				}
			}
			//记录流程变量
			createLiuChengJSBL(workFlowGNCZ, currentLCJS, processObj, oldBusiObj);
			sess.update(currentLCJS);
			//同步的原因是 不允许同时对一个流程实例 进行任务操作
			synchronized(processObj){
				//在完成任务之前 先将Assignee清除 才不会影响下一节点用户的选择
				processObj.setAssignee(null);
				sess.update(processObj);
				
				//完成此任务
				Map<String, Object> variableMap = new HashMap<String, Object>();
					variableMap.put("yongHuDM", yh.getYongHuDM());
					variableMap.put("isInstanceStart", false);
					variableMap.put("dengLuDH", yh.getDengLuDH());
					variableMap.put("yongHuMC", yh.getYongHuMC());
//							variableMap.put("historyId", historyId);
					variableMap.put("liuChengJS", null);
					
				taskService.complete(task.getId(), variableMap);//然后完成任务()
				
				
				//查询当前流程中 此刻的待办任务 
				List<Task> toDoTasks = taskService.createTaskQuery()
						.processInstanceId(processObj.getProcessInstanceId())//当前数据绑定的流程
						.list();
				//如果没有待办任务  检查流程实例  判断流程是否完成（终止 或成功）
				if(toDoTasks==null || toDoTasks.size()==0){
					HistoricProcessInstanceEntity hisprocessInstance = (HistoricProcessInstanceEntity)historyService.createHistoricProcessInstanceQuery().processInstanceId(processObj.getProcessInstanceId()).singleResult();
					if(hisprocessInstance.getEndActivityId()!=null){
						//流程终止
						if(hisprocessInstance.getEndActivityId().equals("Terminate")){
							processObj.setProcessInstanceTerminated(true);
							processObj.setProcessInstanceStatus("终止");
						}else if(hisprocessInstance.getEndActivityId().equals("End")){
							processObj.setProcessInstanceStatus("成功");
						}else{
							logger.error("当前任务执行完成后，此流程已结束，但结束节点不正确("
									+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+":"+processObj.getProcessInstanceId()
								+") 结束节点名称("+hisprocessInstance.getEndActivityId()+")！");

							throw new Exception("当前任务执行完成后，此流程已结束，但结束节点不正确("+hisprocessInstance.getEndActivityId()+")，请联系管理员!");
						}
						//流程完成
						processObj.setProcessInstanceEnded(true);
						sess.update(processObj);
						//流程完成  执行可能存在的完成事件代码
						if(processInstanceEndedMethod!=null ){
							Result pRet =  (Result)processInstanceEndedMethod.invoke(gnActionInstance, new Object[]{workFlowGNCZ.getCaoZuoDH(),processObj.pkValue(),yh.getYongHuDM()});
							if(!pRet.isSuccess()){
								logger.error("	流程完成事件执行失败："+pRet.getErrorMsg());
								throw new Exception("流程完成事件执行失败："+pRet.getErrorMsg());
							}
						}
					}else {
						//流程未完成 却没有了待办任务
						
						//检查流程是否终止(并发情况下 流程不会自动终止)
						boolean isProcessTerminated = false;
						for(LiuChengJSXJ lcjsxj:currentLCJS.getXjs()){
							if("Terminate".equals(lcjsxj.getXiaJiLCJS().getCaoZuoDH())){
								isProcessTerminated = true;
								break;
							}
						}
						
						if(isProcessTerminated){
							//删除流程
							myProcessEngine.getRuntimeService().deleteProcessInstance(processObj.getProcessInstanceId(), "并发节点之一终止了流程");
							//流程完成
							processObj.setProcessInstanceEnded(true);
							processObj.setProcessInstanceTerminated(true);
							processObj.setProcessInstanceStatus("终止");
							//
							sess.update(processObj);
						}else{
							logger.error("	当前任务执行完成后，此流程已不存在待办任务，且流程未结束，请联系管理员!");
							throw new Exception("当前任务执行完成后，此流程已不存在待办任务，且流程未结束，请联系管理员!");
						}
						
					}
				}else{
					//检查流程是否终止(并发情况下 流程不会自动终止)
					boolean isProcessTerminated = false;
					for(LiuChengJSXJ lcjsxj:currentLCJS.getXjs()){
						if("Terminate".equals(lcjsxj.getXiaJiLCJS().getCaoZuoDH())){
							isProcessTerminated = true;
							break;
						}
					}
					
					if(isProcessTerminated){
						//删除流程
						myProcessEngine.getRuntimeService().deleteProcessInstance(processObj.getProcessInstanceId(), "并发节点之一终止了流程");
						//查找当前在执行的流程检视
						List<LiuChengJS> remainLCJSs = (List<LiuChengJS>)sess.createCriteria(LiuChengJS.class)
								.add(Restrictions.eq("gongNengDH",workFlowGNCZ.getGongNeng().getGongNengDH()))
								.add(Restrictions.eq("gongNengObjId",processObj.pkValue()))
								.add(Restrictions.eq("shiFouGDJD", false))
								.add(Restrictions.eq("shiFouSC", false))
								.add(Restrictions.eq("shiFouWC", false))
								.list();
						for(LiuChengJS remainLCJS:remainLCJSs){
							LiuChengJS parentLcjs = remainLCJS.getParentLCJS().get(0);
							parentLcjs.getXjs().remove(remainLCJS);//这个任务被删除了
							sess.update(parentLcjs);
							
//						remainLCJS.getChildLCJS().remove(parentLcjs);//这里再细致一点 把图改过来 删除的节点不再显示为蓝色
							remainLCJS.setCaoZuoXX("【未处理->流程终止】");
							remainLCJS.setShiFouSC(true);
							sess.update(remainLCJS);
						}
						//流程完成
						processObj.setProcessInstanceEnded(true);
						processObj.setProcessInstanceTerminated(true);
						processObj.setProcessInstanceStatus("终止");
						//
						sess.update(processObj);
					}else{
						//有待办任务 未终止流程 
						//记录当前流程执行状态
						String processInstanceStatusInfo = "[";
						for(Task toDotask:toDoTasks){
							if(processInstanceStatusInfo.length()==1){
								processInstanceStatusInfo += toDotask.getName();
							}else{
								processInstanceStatusInfo += ","+toDotask.getName();
							}
						}
						processInstanceStatusInfo += "]";
						processObj.setProcessInstanceStatus(processInstanceStatusInfo);
						sess.update(processObj);
					}
				}
			}
			logger.info("		当前流程的最新状态为："+processObj.getProcessInstanceStatus());
			logger.debug("用户("+yh.getYongHuMC()+":"+yh.getDengLuDH()+")为数据（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+"）执行流程任务（"+workFlowGNCZ.getCaoZuoMC()+":"+workFlowGNCZ.getCaoZuoDH()+"）...成功");
		} catch (Exception e) {
			logger.error("用户("+yh.getYongHuMC()+":"+yh.getDengLuDH()+")为数据（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+"）执行流程任务（"+workFlowGNCZ.getCaoZuoMC()+":"+workFlowGNCZ.getCaoZuoDH()+"）...失败",e);
			throw e;
		}
	}
	
	/**
	 * 对当前流程，声明与当前操作相关的任务节点由当前用户来完成
	 * @param processObj 流程数据
	 * @param workFlowGNCZ 流程操作 
	 * @param yh			用户
	 * @param completeTask	是否提交任务
	 * @throws Exception
	 */
	public static synchronized void claimTask(BusinessI processObj,BusinessI oldBusiObj,GongNengCZ workFlowGNCZ,YongHu yh) throws Exception{
		if(processObj.getProcessInstanceId()==null){
			throw new Exception("此数据（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+"）未绑定流程,无法执行流程任务！");
		}
		
		//检查当前功能是否流程功能
		ProcessEngine myProcessEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = myProcessEngine.getTaskService();
		//检查是启动新流程 还是执行已有流程
		//有记录流程实例id的对象 进行流程操作
		//查找对应的流程检视
		Session sess = HibernateSessionFactory.getSession();
		LiuChengJS currentLCJS = (LiuChengJS)sess.createCriteria(LiuChengJS.class)
				.add(Restrictions.eq("gongNengDH",workFlowGNCZ.getGongNeng().getGongNengDH()))
				.add(Restrictions.eq("gongNengObjId",processObj.pkValue()))
				.add(Restrictions.eq("caoZuoDH", workFlowGNCZ.getCaoZuoDH()))
				.add(Restrictions.eq("shiFouSC", false))
				.add(Restrictions.eq("shiFouWC", false))
				.uniqueResult();
		//查询任务对象 将任务绑定到当前用户
		Task task = taskService.createTaskQuery()
				.processInstanceId(processObj.getProcessInstanceId())//当前数据绑定的流程
				.taskCandidateUser(yh.getDengLuDH())//当前用户是潜在执行人之一（这里要用登陆代号 保证唯一）
				.taskDefinitionKey(workFlowGNCZ.getCaoZuoDH())//任务代号与当前执行的操作代号一致
				.singleResult();
		if(task!=null){
			//此任务未绑定到当前用户 进行绑定
			taskService.claim(task.getId(), yh.getDengLuDH());//为当前任务设置执行人（这里要用登陆代号 保证唯一）（可能执行失败 因为任务被别人先完成了）
			//将操作人设置为当前用户
			currentLCJS.setCaoZuoRen(yh.getYongHuMC()+" ");
			currentLCJS.setCaoZuoXX("【处理中】");
			currentLCJS.setShiFouCK(true);
			currentLCJS.setShiFouCL(true);
		}else{
			//此任务可能已经绑定到当前用户 检查一下是否正确
			task = taskService.createTaskQuery()
					.processInstanceId(processObj.getProcessInstanceId())//当前数据绑定的流程
					.taskAssignee(yh.getDengLuDH())//当前用户执行人 （这里要用登陆代号 保证唯一）
					.taskDefinitionKey(workFlowGNCZ.getCaoZuoDH())//任务代号与当前执行的操作代号一致
					.singleResult();
			if(task==null){
				throw new Exception("当前流程中不存在用户已声明的流程任务（"+yh.getDengLuDH()+":"+workFlowGNCZ.getCaoZuoDH()+":"+processObj.pkValue()+"）！");
			}
		}
		//记录流程变量
		createLiuChengJSBL(workFlowGNCZ, currentLCJS, processObj, oldBusiObj);
		sess.update(currentLCJS);
	}
	
	/**
	 * 
	 * @param processObj 流程数据
	 * @param gnczl		 工作流定义，根据此参数确定启动哪个工作流，为null则根据工作流条件自动选择（此参数仅在启动新流程时起作用）
	 * @param openFormGNCZ 打开form的所使用的功能操作，记录此信息用于重新创建流程对象（此参数仅在启动新流程时起作用）
	 * @param openFormIdMap 打开form的所提供的参数，记录此信息用于重新创建流程对象（此参数仅在启动新流程时起作用）
	 * @param workFlowGNCZ 流程操作 
	 * @param yh			用户
	 * @param completeTask	是否提交任务
	 * @throws Exception
	 */
	public synchronized static void startProcessInstance(BusinessI processObj,BusinessI oldBusiObj,GongNengGZL gnczl,GongNengCZ workFlowGNCZ,YongHu yh,boolean completeTask) throws Exception{
		if(processObj.getProcessInstanceId()!=null){
			throw new Exception("此数据（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+processObj.pkValue()+"）已绑定流程,不允许再次绑定！");
		}
		if(!workFlowGNCZ.getGongNeng().getShiFouLCGN()){
			throw new Exception("此功能（"+workFlowGNCZ.getGongNeng().getGongNengDH()+":"+workFlowGNCZ.getGongNeng().getGongNengMC()+"）非流程功能！");
		}
		//先确定使用当前功能下的哪个工作流
		if(gnczl==null){
			Session sess = HibernateSessionFactory.getSession();
			@SuppressWarnings("unchecked")
			List<GongNengGZL> gzls = (List<GongNengGZL>)sess.createCriteria(GongNengGZL.class).add(Restrictions.eq("gongNeng.id", workFlowGNCZ.getGongNeng().getGongNengDM())).list();
			if(gzls.size()==1){
				gnczl = gzls.iterator().next();
			}else if(gzls.size()>1){
				//根据条件定义 确定工作流
				for(GongNengGZL temp_gnczl:gzls){
					//只有设置了模板条件 且不是全部允许的(暂时) 进行条件判断
					if(temp_gnczl.getMxs().size()>0 ){
						List<Permit> permits = QueryUtils.getPermitsByGZLTJ(yh, temp_gnczl);
						if(AuthUtils.meetCondition(workFlowGNCZ.getGongNeng().getShiTiLei(), processObj.pkValue(), yh, permits)){
							gnczl = temp_gnczl;
							break;
						}
					}else{
						gnczl = temp_gnczl;
						break;
					}
				}
				//
				if(gnczl==null){
					throw new Exception("未找到符合条件的工作流，请联系管理员!");
				}
			}else{
				throw new Exception("当前功能未定义工作流，请联系管理员!");
			}
		}
		//检查流程启动操作
		if(!workFlowGNCZ.equals(gnczl.getStartGNCZ())){
			throw new Exception("当前工作流（"+gnczl.getGongNengGZLMC()+"）的启动操作("+gnczl.getStartGNCZ().getCaoZuoDH()+")与当前操作("+workFlowGNCZ.getCaoZuoDH()+")不符！");
		}

		//取得流程扩展方法 备用
		Method processInstanceStartedMethod=null;
		Method processInstanceEndedMethod=null;
		Object gnActionInstance =null;
		//如果功能定义了action类 取流程开始和流程结束方法备用
		if(workFlowGNCZ.getGongNeng().getGongNengClass()!=null){
			try {
				Class<?> gnActionClass = Class.forName(workFlowGNCZ.getGongNeng().getGongNengClass());
				gnActionInstance = gnActionClass.newInstance();
				try {
					processInstanceStartedMethod= gnActionClass.getMethod("processInstanceStarted",new Class[]{String.class,Integer.class,Integer.class});
				} catch (Exception e) {
				}
				try {
					processInstanceEndedMethod= gnActionClass.getMethod("processInstanceEnded",new Class[]{String.class,Integer.class,Integer.class});
				} catch (Exception e) {
				}
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		
		//检查当前功能是否流程功能
		ProcessEngine myProcessEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = myProcessEngine.getTaskService();
		HistoryService historyService = myProcessEngine.getHistoryService();
		RuntimeService runtimeService = myProcessEngine.getRuntimeService();
		//启动流程
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("yongHuDM", yh.getYongHuDM());
		variableMap.put("dengLuDH", yh.getDengLuDH());
		variableMap.put("yongHuMC", yh.getYongHuMC());
		variableMap.put("isInstanceStart", true);
		variableMap.put("liuChengJS", null);
		ProcessInstance processInstance = null;
		
		try {
			processInstance = runtimeService.startProcessInstanceByKey(
				workFlowGNCZ.getGongNeng().getGongNengDH()+"-"+gnczl.getGongNengGZLBH(), 
				""+processObj.pkValue(), 
				variableMap
			);
		} catch (Exception e) {
			logger.error("启动流程失败（yh:"+yh.getYongHuMC()+" gn:"+workFlowGNCZ.getGongNeng().getGongNengDH()+",gzl:"+workFlowGNCZ.getGongNeng().getGongNengDH()+"-"+gnczl.getGongNengGZLBH()+" id:"+processObj.pkValue()+"）：",e);
			throw e;
		}
		
		//执行可能存在的流程开始事件
		if(processInstanceStartedMethod!=null){
			Result pRet =  (Result)processInstanceStartedMethod
					.invoke(gnActionInstance, new Object[]{workFlowGNCZ.getCaoZuoDH(),processObj.pkValue(),yh.getYongHuDM()});
			if(!pRet.isSuccess()){
				throw new Exception("流程开始事件执行失败："+pRet.getErrorMsg());
			}
		}
		//取启动任务（启动流程的操作 应该与流程图中开始节点后的第一个任务代号一致）
		Task task = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskDefinitionKey(workFlowGNCZ.getCaoZuoDH()).singleResult();
		if(task!=null){
				Session sess = HibernateSessionFactory.getSession();
				//在当前数据对象中 记录流程processInstance
				processObj.setProcessInstanceId(processInstance.getId());//在当前数据中 记录流程id
				sess.update(processObj);
				//为启动任务设置执行人
				taskService.claim(task.getId(), yh.getDengLuDH());//（这里要用登陆代号 保证唯一）
				//查找对应的流程检视 将操作人设置为当前用户
				LiuChengJS currentLCJS = (LiuChengJS)sess.createCriteria(LiuChengJS.class)
						.add(Restrictions.eq("gongNengDH",workFlowGNCZ.getGongNeng().getGongNengDH()))
						.add(Restrictions.eq("gongNengObjId",processObj.pkValue()))
						.add(Restrictions.eq("caoZuoDH", workFlowGNCZ.getCaoZuoDH()))
						.add(Restrictions.eq("shiFouWC", false))
						.add(Restrictions.eq("shiFouSC", false))
						.uniqueResult();
				currentLCJS.setCaoZuoRen(yh.getYongHuMC()+" ");
				currentLCJS.setCaoZuoXX("【处理中】");
				currentLCJS.setShiFouCK(true);
				currentLCJS.setShiFouCL(true);
				//记录流程变量
				createLiuChengJSBL(workFlowGNCZ, currentLCJS, processObj, oldBusiObj);
				sess.update(currentLCJS);
				//完成此任务
				if(completeTask){
					variableMap.put("isInstanceStart", true);
					variableMap.put("yongHuDM", yh.getYongHuDM());
					variableMap.put("dengLuDH", yh.getDengLuDH());
					variableMap.put("yongHuMC", yh.getYongHuMC());
					variableMap.put("liuChengJS", null);
					//
					taskService.complete(task.getId(), variableMap);
					
					//取所有待办任务 检查流程是否终止或完成
					List<Task> toDoTasks = taskService.createTaskQuery()
							.processInstanceId(processInstance.getId())//当前数据绑定的流程
							.list();
					if(toDoTasks==null || toDoTasks.size()==0){
						//没有待办任务 检查流程是否完成（检查完成节点 是终止 或成功）
						HistoricProcessInstanceEntity hisprocessInstance = (HistoricProcessInstanceEntity)historyService.createHistoricProcessInstanceQuery().processInstanceId(processObj.getProcessInstanceId()).singleResult();
						if(hisprocessInstance.getEndActivityId()!=null){
							//流程终止
							if(hisprocessInstance.getEndActivityId().equals("Terminate")){
								processObj.setProcessInstanceTerminated(true);
								processObj.setProcessInstanceStatus("终止");
							}else{
								processObj.setProcessInstanceStatus("成功");
							}
							//流程完成
							processObj.setProcessInstanceEnded(true);
							sess.update(processObj);
							//流程完成   执行可能存在的完成事件代码
							if(processInstanceEndedMethod!=null ){
								Result pRet =  (Result)processInstanceEndedMethod.invoke(gnActionInstance, new Object[]{workFlowGNCZ.getCaoZuoDH(),processObj.pkValue(),yh.getYongHuDM()});
								if(!pRet.isSuccess()){
									throw new Exception("流程完成事件执行失败："+pRet.getErrorMsg());
								}
							}
						}else{
							//流程未完成 却没有了待办任务
							throw new Exception("当前流程不存在待办任务，请联系管理员!");
						}
					}else{
						//记录当前流程执行状态
						String processInstanceStatusInfo = "[";
						for(Task toDotask:toDoTasks){
							if(processInstanceStatusInfo.length()==1){
								processInstanceStatusInfo += toDotask.getName();
							}else{
								processInstanceStatusInfo += ","+toDotask.getName();
							}
						}
						processInstanceStatusInfo += "]";
						processObj.setProcessInstanceStatus(processInstanceStatusInfo);
						sess.update(processObj);
					}
				}else{
					processObj.setProcessInstanceStatus("["+task.getName()+"]");
					sess.update(processObj);
				}
		}else{
			//确认为不需要启动 删除刚才启动的流程
			runtimeService.deleteProcessInstance(processInstance.getId(), "启动流程后，未找到与操作("+workFlowGNCZ.getGongNeng().getGongNengDH()+"."+workFlowGNCZ.getCaoZuoDH()+")一致的任务，删除此流程！");
			//
			throw new Exception("启动流程后，未找到与操作("+workFlowGNCZ.getGongNeng().getGongNengDH()+"."+workFlowGNCZ.getCaoZuoDH()+")一致的任务，请联系管理员!");
		}
	}
	
	
	public static LiuChengJS createLiuChengJSBL(GongNengCZ gncz,LiuChengJS liuChengJS,BusinessI newBusiObj,BusinessI oldBusiObj){
		if(newBusiObj==null){
			return null;
		}
		Session sess = HibernateSessionFactory.getSession();
		@SuppressWarnings("unchecked")
		List<GongNengCZBL> czbls = (List<GongNengCZBL>)sess.createCriteria(GongNengCZBL.class).add(Restrictions.eq("gongNengCZ.id", gncz.getGongNengCZDM())).list();
		
		for(GongNengCZBL czbl:czbls){
			//集合字段不处理
			if(!ZiDuanLX.isSetType(czbl.getZiDuan().getZiDuanLX().getZiDuanLXDH())){
				String zddh = czbl.getZiDuan().getZiDuanDH();
				//此字段的原值和现值
				Object oldZdValue = null;
				Object newZdValue = null;
				try {
					if(oldBusiObj!=null){
						oldZdValue = PropertyUtils.getProperty(oldBusiObj, zddh);
					}
					newZdValue = PropertyUtils.getProperty(newBusiObj, zddh);
				} catch (Exception e1) {
	//				e1.printStackTrace();
				}
				//检查是否有变化 或声明了没变化也记录
				if((newZdValue!=null && !newZdValue.equals(oldZdValue)) || (newZdValue==null && oldZdValue!=null ) || (!czbl.getShiFouJJLBH())){
					//
					Object oldZdValueWithType = getValueOfCorrectType(oldZdValue,czbl);
					Object newZdValueWithType = getValueOfCorrectType(newZdValue,czbl);
					//取得符合条件的流程检视变量
					LiuChengJSBL lcjsbl = null;
					for(LiuChengJSBL tempLcjsbl:liuChengJS.getBls()){
						if(tempLcjsbl.getLiuChengJSBLDH().equals(zddh)){
							lcjsbl = tempLcjsbl;
							break;
						}
					}
					//不存在的话 创建新的流程检视变量
					if(lcjsbl==null){
						lcjsbl = new LiuChengJSBL();
						lcjsbl.setLiuChengJSBLDH(zddh);
						lcjsbl.setLiuChengJSBLMC(czbl.getZiDuan().getZiDuanBT());
						lcjsbl.setZiDuanLX(czbl.getZiDuan().getZiDuanLX());
						//记录原值
						if(oldZdValueWithType!=null){
							if(oldZdValueWithType instanceof EntityI){
								lcjsbl.setYuanZ(((EntityI)oldZdValueWithType).pkValue().toString());
								lcjsbl.setYuanZXS(oldZdValueWithType.toString());
							}else if("text".equals(czbl.getZiDuan().getZiDuanLX().getZiDuanLXDH())){
								String oldZdValueStringWithoutTag = removeHTMLTag(oldZdValueWithType.toString());
								if(oldZdValueStringWithoutTag.length()>600){
									oldZdValueStringWithoutTag = oldZdValueStringWithoutTag.substring(0,600)+"(略去...)";
								}
								lcjsbl.setYuanZ(oldZdValueStringWithoutTag);
								lcjsbl.setYuanZXS(oldZdValueStringWithoutTag);
							}else{
								String oldZdValueString = oldZdValueWithType.toString();
								if(oldZdValueString.length()>600){
									oldZdValueString = oldZdValueString.substring(0,600)+"(略去...)";
								}
								lcjsbl.setYuanZ(oldZdValueString);
								lcjsbl.setYuanZXS(oldZdValueString);
							}
						}
						liuChengJS.addTobls(liuChengJS, lcjsbl);
					}
					
					//记录现值
					if(newZdValueWithType!=null){
						//根据不同的字段类型 分别处理
						if(newZdValueWithType instanceof EntityI){
							lcjsbl.setLiuChengJSBLZ(((EntityI)newZdValueWithType).pkValue().toString());
							lcjsbl.setLiuChengJSBLZXS(newZdValueWithType.toString());
						}else if("text".equals(czbl.getZiDuan().getZiDuanLX().getZiDuanLXDH())){
							String newZdValueStringWithoutTag = removeHTMLTag(newZdValueWithType.toString());
							if(newZdValueStringWithoutTag.length()>600){
								newZdValueStringWithoutTag = newZdValueStringWithoutTag.substring(0,600)+"(略去...)";
							}
							lcjsbl.setLiuChengJSBLZ(newZdValueStringWithoutTag);
							lcjsbl.setLiuChengJSBLZXS(newZdValueStringWithoutTag);
						}else{
							String newZdValueString = newZdValueWithType.toString();
							if(newZdValueString.length()>600){
								newZdValueString = newZdValueString.substring(0,600)+"(略去...)";
							}
							lcjsbl.setLiuChengJSBLZ(newZdValueString);
							lcjsbl.setLiuChengJSBLZXS(newZdValueString);
						}
						
					}
					//检查原值与现值是否一致
					if(czbl.getShiFouJJLBH()){
						if((lcjsbl.getLiuChengJSBLZ()==null && lcjsbl.getYuanZ()==null) ||
						   (lcjsbl.getLiuChengJSBLZ()!=null && lcjsbl.getLiuChengJSBLZ().equals(lcjsbl.getYuanZ())) ){
							//删除此流程检视变量
							liuChengJS.removeFrombls(liuChengJS, lcjsbl);
						}
					}
				}
			}
		}
		return liuChengJS;
	}
	
	/**
	 * 从流程对象中 消除此流程检视带来的影响
	 * @param liuChengJS
	 * @param busiObj
	 * @return
	 */
	public static void removeLcblFromObj(LiuChengJS liuChengJS,BusinessI busiObj){
		ShiTiLei stl = GongNeng.getGongNengByDH(liuChengJS.getGongNengDH()).getShiTiLei();
		for(LiuChengJSBL lcjsbl:liuChengJS.getBls()){
			try {
				String zdlxdh = lcjsbl.getZiDuanLX().getZiDuanLXDH();
				Object v = null;
				if(lcjsbl.getYuanZ()!=null){
					v = ValueUtils.getValueOfCorrectType(lcjsbl.getYuanZ(),zdlxdh, null);
					if("object".equalsIgnoreCase(zdlxdh) && v!=null){
						ShiTiLei zdStl = stl.getZiDuan(lcjsbl.getLiuChengJSBLDH()).getGuanLianSTL();
						Object zdObj = Class.forName(zdStl.getShiTiLeiClassName()).newInstance();
						PropertyUtils.setProperty(zdObj, zdStl.getZhuJianLie(), v);
						v = zdObj;
					}
				}
				PropertyUtils.setProperty(busiObj, lcjsbl.getLiuChengJSBLDH(), v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 对象类型的字段 直接返回对象 其它类型的字段 返回字符串
	 * @param value
	 * @param czbl
	 * @return
	 */
	private static Object getValueOfCorrectType(Object value,GongNengCZBL czbl){
		Object valueWithType = null;
		if(value!=null){
			int zdlxdm = czbl.getZiDuan().getZiDuanLX().getZiDuanLXDM();
			if(ZiDuanLX.ZiDuanLX_DATE == zdlxdm){
				valueWithType = dtf.format((Date)value);
			}else if(ZiDuanLX.ZiDuanLX_BOOLEAN == zdlxdm){
				valueWithType = value.toString();
			}else if(ZiDuanLX.ZiDuanLX_INT == zdlxdm){
				valueWithType = value.toString();
			}else if(ZiDuanLX.ZiDuanLX_OBJECT == zdlxdm){
					try {
						ShiTiLei glstl = czbl.getZiDuan().getGuanLianSTL();
						Integer zdPkValue = (Integer)PropertyUtils.getProperty(value, glstl.getZhuJianLie());
						valueWithType= HibernateSessionFactory.getSession().load(Class.forName(glstl.getShiTiLeiClassName()),zdPkValue);
					} catch (Exception e) {
					}
			}else if(ZiDuanLX.ZiDuanLX_SET == zdlxdm){
				valueWithType = null;
//					ModelData submitData = data.get(zddh);
//					String displayString = "";
//					String jsonString = "";
//					
//					List<ModelData> insertedList = submitData.get("inserted");
//					if(insertedList!=null){
//						displayString+="新增："+insertedList.size()+"行";
//					}
//					
//					List<ModelData> insertedList = submitData.get("inserted");
//					if(insertedList!=null){
//						displayString+="新增："+insertedList.size()+"行";
//					}
	//
//					lcjsbl.setLiuChengJSBLZ(jsonString);
//					lcjsbl.setLiuChengJSBLZXS(displayString);
			}else if(ZiDuanLX.ZiDuanLX_STRING == zdlxdm){
				valueWithType = value;
			}else{
				valueWithType = value.toString();
			}
		}
		return valueWithType;
	}
	
	
	public static Result doRejectToStartTask(String gongNengDH,String reason,Integer id,YongHu yh){

		Result result = null;
		Session sess = HibernateSessionFactory.getSession();
		//取得当前数据对象以及功能对象
		BusinessI processObj = null;
		GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
		processObj = (BusinessI)sess.load(gn.getShiTiLei().getShiTiLeiClassName(), id);
		//检查此流程是否已完成
		if(processObj.getProcessInstanceEnded()==null || processObj.getProcessInstanceEnded()==true){
			return new JSONMessageResult("当前流程已结束，不允许退回到开始节点！");
		}
		//取所有待办任务 删除之
		List<LiuChengJS> toDoLCJSs = (List<LiuChengJS>)sess.createCriteria(LiuChengJS.class)
				.add(Restrictions.eq("gongNengDH",gongNengDH))//当前功能
				.add(Restrictions.eq("gongNengObjId",id))//当前流程
				.add(Restrictions.eq("shiFouWC", false))//已完成
				.add(Restrictions.eq("shiFouSC", false))//未删除
				.add(Restrictions.eq("shiFouGDJD", false))//非过渡节点
				.add(Restrictions.isNotNull("caoZuoDH"))//非进行退回和收回操作的节点
				.addOrder(Order.desc("liuChengJSDM"))
				.list();
		for(LiuChengJS toDoLCJS:toDoLCJSs){
			toDoLCJS.getSjs().clear();
			toDoLCJS.getXjs().clear();
			toDoLCJS.getPjs().clear();
			toDoLCJS.setShiFouSC(true);
			sess.update(toDoLCJS);
		}
		//删除工作流
		ProcessEngine myProcessEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = myProcessEngine.getTaskService();
		RuntimeService runtimeService = myProcessEngine.getRuntimeService();
		//取得原流程define key
		ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processObj.getProcessInstanceId()).singleResult();
		String currentProcessInstanceDefId = p.getProcessDefinitionId();
		//删除流程 先将流程中的business_Key_清空（不删除流程检视及路径）
		((ServiceImpl)runtimeService).getCommandExecutor().execute(new ClearProcessInstanceBussinessKeyCmd(processObj.getProcessInstanceId()));
		runtimeService.deleteProcessInstance(processObj.getProcessInstanceId(), reason+"，删除原流程");
		
		//取得原流程检视中的根节点
		LiuChengJS rootLCJS = (LiuChengJS)sess.createCriteria(LiuChengJS.class)
				.add(Restrictions.eq("gongNengDH",gongNengDH))
				.add(Restrictions.eq("gongNengObjId",id))
				.add(Restrictions.eq("caoZuoDH", "Start"))
				.add(Restrictions.eq("shiFouSC", false))
				.uniqueResult();
		//原启动节点的流程检视(去除相应信息)
		LiuChengJSXJ oldfirstLCJSXj = rootLCJS.getXjs().iterator().next();
		LiuChengJS oldfirstLCJS = oldfirstLCJSXj.getXiaJiLCJS();
		//新增一条流程检视(退回到开始节点)
		LiuChengJS retakeLCJS = new LiuChengJS();
		retakeLCJS.setGongNengDH(gongNengDH);
		retakeLCJS.setGongNengObjId(id);
		retakeLCJS.setCaoZuoMC(reason);
		retakeLCJS.setShiFouCK(true);
		retakeLCJS.setShiFouCL(true);
		retakeLCJS.setShiFouWC(true);
		
		Calendar current = Calendar.getInstance();
		retakeLCJS.setChuangJianRQ(current.getTime());
		retakeLCJS.setWanChengRQ(current.getTime());
		retakeLCJS.setCaoZuoRen(yh.getYongHuMC()+" ");
		retakeLCJS.setCaoZuoXX("【退回："+oldfirstLCJS.getCaoZuoMC()+"】");
		sess.save(retakeLCJS);
		//新增一条未完成的开始节点待办信息
		LiuChengJS newStartLCJS = new LiuChengJS(oldfirstLCJS);
		
		current.add(Calendar.SECOND, 1);
		newStartLCJS.setChuangJianRQ(current.getTime());//新建的上级节点 新的创建日期
		newStartLCJS.setShiFouCK(true);
		newStartLCJS.setShiFouCL(true);//此处设置的处理标记 会在重新执行完流程后去除
		newStartLCJS.setShiFouWC(false);
		newStartLCJS.setWanChengRQ(null);
		newStartLCJS.setWanChengYH(oldfirstLCJS.getWanChengYH());//此处设置的完成用户 会在重新执行完流程后去除
		sess.save(newStartLCJS);
		//原启动节点流程检视（删除关联关系）
		oldfirstLCJS.getSjs().clear();
		oldfirstLCJS.getXjs().clear();
		oldfirstLCJS.getPjs().clear();
		sess.update(oldfirstLCJS);
		//启动节点的下级 设为本次新增的流程检视
		oldfirstLCJSXj.setXiaJiLCJS(newStartLCJS);
		sess.update(rootLCJS);
		//启动流程
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("yongHuDM", yh.getYongHuDM());
		variableMap.put("dengLuDH", yh.getDengLuDH());
		variableMap.put("yongHuMC", yh.getYongHuMC());
		variableMap.put("isInstanceStart", true);
		variableMap.put("liuChengJS", rootLCJS);
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(currentProcessInstanceDefId,""+id,variableMap);
		//在业务对象中 保存新的流程实例代码
		processObj.setProcessInstanceId(processInstance.getId());
		processObj.setProcessInstanceStatus("["+newStartLCJS.getCaoZuoMC()+"]");
		//根据已有流程检视 执行流程中的各环节
		completeTaskByLCJS(taskService,processInstance,rootLCJS);
		processObj.setAssignee(null);
		sess.update(processObj);
		
		result = new JSONMessageResult();
	
		return result;
	
	}
	
	/**
	 * 获取潜在执行人
	 * 对于某条数据 有权限进行某种功能操作的所有用户集合
	 * @return
	 */
	public static List<YongHu> getCandidateUsers(Integer gongNengCZDM,Integer objId,boolean isInstanceStart,LiuChengJS liuChengJS){
		List<YongHu> candidateUsers = new ArrayList<YongHu>();
		Session sess = HibernateSessionFactory.getSession();
		GongNengCZ gncz = (GongNengCZ)sess.load(GongNengCZ.class,gongNengCZDM);
		if(gncz.getDuiXiangXG()){
			//检查传递来的流程检视(退回重新遍历流程时不为null)
			//如果流程检视的下级中 包含此操作且已处理 此处不用取有权限的用户
			boolean needsAuthCandidate = true;
			if(liuChengJS!=null){
				//查找
				for(LiuChengJS childLCJS:liuChengJS.getChildLCJS()){
					if(gncz.getCaoZuoDH().equals(childLCJS.getCaoZuoDH())){
						if(childLCJS.getShiFouCL()){
							needsAuthCandidate = false;
						}
						break;
					}
				}
			}
			if(needsAuthCandidate){
				//首先取得对此功能操作有权限的用户集合
//				List<?> yhs = sess.createCriteria(YongHu.class)
//					.add(Restrictions.sqlRestriction("(yongHuDM=1 or yongHuDM in (select yhjs.yongHuDM from sys_yonghujs yhjs where yhjs.jueSeDM in (select jsgncz.jueSeDM from sys_jueseqxgncz jsgncz where jsgncz.gongNengCZDM ="+gongNengCZDM+")))"))
//					.list(); 
//				for(Object yhObj:yhs){
//					YongHu yh = (YongHu)yhObj;
//					try {
//						//然后逐一检查 对当前数据是否有权限
//						if(AuthUtils.checkAuth(gncz, objId, yh)){
//							YongHu trustUser = yh.searchTrustUser(true);
//							if(!candidateUsers.contains(trustUser)){
//								candidateUsers.add(trustUser);
//							}
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
				
				//首先取得对此功能操作有权限的用户集合
				List<?> jss = sess.createCriteria(JueSe.class)
					.add(Restrictions.sqlRestriction("jueSeDM in (select jsgncz.jueSeDM from sys_jueseqxgncz jsgncz where jsgncz.gongNengCZDM ="+gongNengCZDM+")"))
					.list(); 
				for(Object jsObj:jss){
					JueSe js = (JueSe)jsObj;
					try {
						//然后逐一检查 对当前数据是否有权限
						List<YongHu> yhs1 = AuthUtils.getAuthUsers(gncz, objId, js);
						for(YongHu yh:yhs1){
							if(!candidateUsers.contains(yh)){
								candidateUsers.add(yh);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				YongHu superUser = (YongHu)sess.load(YongHu.class, 1);
				candidateUsers.add(superUser);
			}
			
		}else{
			//对象无关的操作 只有非流程启动情况下 才根据对象检查并获取有权限的用户(只有所有者才是此任务的唯一潜在执行人)
			if(!isInstanceStart && objId!=null){
				try {
					BusinessI businessObj = (BusinessI)sess.load(Class.forName(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName()), objId);
					candidateUsers.add(businessObj.getSuoYouZhe());
					//主要是退回的时候 需要超级用户也可以处理此权限
					if(businessObj.getSuoYouZhe().getYongHuDM().intValue() != 1){
						YongHu superUserc = (YongHu)sess.load(YongHu.class, 1);
						candidateUsers.add(superUserc);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//返回有足够权限的所有用户
		return candidateUsers;
	}

	
	public static void synLiuChengJS2Workflow(GongNengCZ parentGNCZ,LiuChengJS parentLCJS,BusinessI  processObj,BusinessI  oldBusiObj,ActivityImpl activityIml,PvmTransition transition,YongHu yongHu){
		Session sess = HibernateSessionFactory.getSession();
		//事前处理
		createLiuChengJSBL(parentGNCZ, parentLCJS, processObj, oldBusiObj);
		//处理
		GongNengCZ childGNCZ = parentGNCZ.getGongNeng().getCaoZuoByDH(activityIml.getId());
		
		LiuChengJS childLCJS = new LiuChengJS();
		childLCJS.setGongNengDH(parentLCJS.getGongNengDH());
		childLCJS.setGongNengObjId(parentLCJS.getGongNengObjId());
		childLCJS.setCaoZuoDH(childGNCZ.getCaoZuoDH());
		childLCJS.setCaoZuoMC(childGNCZ.getCaoZuoMC());
		childLCJS.setShiFouCL(false);
		childLCJS.setShiFouWC(false);
		childLCJS.setChuangJianRQ(Calendar.getInstance().getTime());
		
		//待办用户
		TaskCandidateExpression asq = (TaskCandidateExpression)((UserTaskActivityBehavior)((ActivityImpl)transition.getDestination()).getActivityBehavior()).getTaskDefinition().getCandidateUserIdExpressions().iterator().next();
		Integer gongNengCZDM = Integer.valueOf(asq.getExpressionText());
		
		List<YongHu> candidateUsers = getCandidateUsers(gongNengCZDM, processObj.pkValue(),false,null);
		String destinationCandidateUserString ="[";
		for(YongHu yh:candidateUsers){
			if(!"ADMIN".equalsIgnoreCase(yh.getDengLuDH())){
				destinationCandidateUserString+= yh.getYongHuMC()+" ";
			}
		}
		destinationCandidateUserString +=" ]";
		childLCJS.setCaoZuoRen(destinationCandidateUserString);
		
		childLCJS.addTosjs(childLCJS, new LiuChengJSSJ(parentLCJS,transition.getId()));
		sess.save(childLCJS);
		
		
		//父节点设置完成状态 并记录子节点信息
		parentLCJS.setCaoZuoRen(yongHu.getYongHuMC()+" ");
		parentLCJS.setCaoZuoXX("【已办理】");
		parentLCJS.setShiFouWC(true);
		parentLCJS.setShiFouCL(true);
		parentLCJS.setShiFouCK(true);
		parentLCJS.setHistoryId(null);
		parentLCJS.setWanChengYH(yongHu);
		parentLCJS.setWanChengRQ(Calendar.getInstance().getTime());
	
		parentLCJS.addToxjs(parentLCJS, new LiuChengJSXJ(childLCJS,transition.getId()));
		String tartgetLCJSMCs = "";
		for(LiuChengJSXJ xj:parentLCJS.getXjs()){
			tartgetLCJSMCs+=xj.getXiaJiLCJS().getCaoZuoMC()+"；";
		}
		parentLCJS.setCaoZuoXX("【已办理 ->"+tartgetLCJSMCs+"】");
	
		//事后处理
		processObj.setProcessInstanceStatus("["+childLCJS.getCaoZuoMC()+"]");
		//在完成任务之前 先将Assignee清除 才不会影响下一节点用户的选择
		processObj.setAssignee(null);

		
		sess.update(parentLCJS);
		sess.update(processObj);

	}
	
	//替换字符串中的html标记
	public static String removeHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
       
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签
       
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签
       
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

       return htmlStr.trim(); //返回文本字符串
    } 
	
	//根据流程检视 从根节点重新执行task
	public static void completeTaskByLCJS(TaskService taskService,ProcessInstance processInstance,LiuChengJS liuChengJS){
		//查找对应的task 并完成之
		Task task = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskDefinitionKey(liuChengJS.getCaoZuoDH()).singleResult();
		if(task!=null){
			if(liuChengJS.getShiFouCL()){
				Session sess = HibernateSessionFactory.getSession();
				//已处理的流程检视
//				taskService.addCandidateUser(task.getId(), liuChengJS.getCaoZuoRen().trim());
//				setAssignee(task.getId(), liuChengJS.getCaoZuoRen().trim());
				if(liuChengJS.getWanChengYH()!=null){
					//如果流程检视中 有完成用户
					taskService.claim(task.getId(), liuChengJS.getWanChengYH().getDengLuDH());//已处理的节点 应该设置执行人
				}else{
					//如果流程检视中 没有完成用户
					YongHu yh = (YongHu)sess.createCriteria(YongHu.class).add(Restrictions.eq("yongHuMC", liuChengJS.getCaoZuoRen().trim()))
					.setMaxResults(1).uniqueResult();
					if(yh!=null){
						taskService.claim(task.getId(), yh.getDengLuDH());//已处理的节点 应该设置执行人
					}
				}
				if(!liuChengJS.getShiFouWC()){
					liuChengJS.setShiFouCL(false);//已处理 但未完成的任务 应该是当前流程中最后一个待办节点 设置为未处理
					liuChengJS.setWanChengYH(null);//已处理 但未完成的任务 应该是当前流程中最后一个待办节点 完成用户设置为null
				}
				
				sess.update(liuChengJS);
			}
			if(liuChengJS.getShiFouWC()){
				//按下级循环 取得下行路径
//				List<LiuChengJSXJ> xjTransaction = new ArrayList<LiuChengJSXJ>();
//				for(LiuChengJSXJ lcxj:liuChengJS.getXjs()){
//					xjTransaction.add(lcxj);
//				}
				//
				Map<String, Object> variableMap = new HashMap<String, Object>();
				variableMap.put("isInstanceStart", false);
				variableMap.put("liuChengJS", liuChengJS);
				taskService.complete(task.getId(),variableMap);
			}
		}
		//执行下级节点
		for(LiuChengJS child:liuChengJS.getChildLCJS()){
			if(child.getShiFouCL() || child.getShiFouWC()){
				//已处理的节点 会被赋予assignee
				completeTaskByLCJS(taskService,processInstance,child);
			}
		}
	}
	
	//activiti使用此方法确定流程分支走向
	public static boolean checkSequenceFlowCondition(Integer gnlcid,Integer id,Integer yhid,String gongNengDH,String sequenceFlowId) throws Exception{
		Session sess = HibernateSessionFactory.getSession();
		//取得流程条件
		GongNengLC gongNengLC =(GongNengLC)sess.load(GongNengLC.class, gnlcid);
		if(gongNengLC==null){
			System.out.println("流程:"+gnlcid+"不存在！");
			return false;
		}
		if(!gongNengLC.getGongNeng().getGongNengDH().equals(gongNengDH)){
			System.out.println("流程条件与功能代号不符:"+gongNengLC.getGongNeng().getGongNengDH()+"->"+gongNengDH+"！");
			return false;
		}
		//取得流程用户
		YongHu yh =(YongHu)sess.load(YongHu.class, yhid);
		if(yh==null){
			System.out.println("流程用户:"+yhid+"不存在！");
			return false;
		}
		//基础查询语法
		Criteria criteria = sess.createCriteria(gongNengLC.getGongNeng().getShiTiLei().getShiTiLeiClassName());
		
		//取得用户权限 permits=null means all
		ArrayList<Permit> permits = QueryUtils.getPermitsFromGongNengLC(gongNengLC);
		Criterion c = QueryUtils.createCriterionByPermits(gongNengLC.getGongNeng().getShiTiLei(),permits,yh);
		if(c!=null){
			criteria.add(c);
		}
		//按主键过滤
		criteria.add(Restrictions.eq(gongNengLC.getGongNeng().getShiTiLei().getZhuJianLie(), id));
		//取得符合条件的记录数
		int totalCount = ((Long)criteria.setProjection(Projections.rowCount())
				.uniqueResult())
				.intValue();
		if(totalCount==0){
			return false;
		}else if(totalCount >1){
			System.out.println("符合条件的记录多于一条！");
			return false;
		}
		return true;
	}
}

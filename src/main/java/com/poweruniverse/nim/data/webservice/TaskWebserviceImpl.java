package com.poweruniverse.nim.data.webservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.impl.json.JsonObjectConverter;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.baseClass.BasePlateformWebservice;
import com.poweruniverse.nim.data.entity.sys.GongNeng;
import com.poweruniverse.nim.data.entity.sys.GongNengCZ;
import com.poweruniverse.nim.data.entity.sys.LiuChengJS;
import com.poweruniverse.nim.data.entity.sys.base.BusinessI;
import com.poweruniverse.nim.data.service.utils.AuthUtils;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
import com.poweruniverse.nim.data.service.utils.TaskUtils;

@WebService
public class TaskWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	
	public TaskWebserviceImpl(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}
	/**
	 * 检查某节点执行情况
	 * @param gongNengDH
	 * @param caoZuoDH
	 * @param objectId
	 * @return
	 */
	public JSONMessageResult getStatus(@WebParam(name="gongNengDH") String gongNengDH,@WebParam(name="caoZuoDH") String caoZuoDH,@WebParam(name="objectId") Integer objectId ){
		JSONMessageResult ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//需要授权的功能操作 必须提供用户名、密码用于验证用户身份
			Integer yhdm = this.getYongHuDM(wsContext,true);
			//功能
			GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
			GongNengCZ gncz = gn.getCaoZuoByDH(caoZuoDH);
			//检查用户对当前对象是否有查看权限
			if(!AuthUtils.checkAuth(gncz, objectId, yhdm)){
				ret = new JSONMessageResult("记录("+objectId+")不存在或用户对此记录没有操作权限！");
			}else{
				ret = new JSONMessageResult();
				ret.put("data", getTaskStatus(gongNengDH,caoZuoDH,objectId));
			}
		}catch(Exception e){
			ret = new JSONMessageResult(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	/**
	 * 执行某节点任务
	 * @param gongNengDH
	 * @param caoZuoDH
	 * @param objectId
	 * @return
	 */
	public JSONMessageResult doTask(String gongNengDH,String caoZuoDH,String data,Integer objectId ){
		JSONMessageResult ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//需要授权的功能操作 必须提供用户名、密码用于验证用户身份
			Integer yhdm = this.getYongHuDM(wsContext,true);
			//功能
			GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
			GongNengCZ gncz = gn.getCaoZuoByDH(caoZuoDH);
			//检查用户对当前对象是否有查看权限
			if(!AuthUtils.checkAuth(gncz, objectId, yhdm)){
				ret = new JSONMessageResult("记录("+objectId+")不存在或用户对此记录没有操作权限！");
			}else{
//				ModelData taskData = DataUtils.applyJsonObjectToModel(data);
				//数据转换为ModelData格式
				JSONObject row = JSONObject.fromObject(data);
				//取得业务数据对象
//				BusinessI obj = (BusinessI)sess.load(gn.getShiTiLei().getShiTiLeiClassName(), objectId);
				
//				ExecResult<ModelData> saveRet = DataUtils.doSubmit(yh.getYongHuDM(),gongNengDH,caoZuoDH, Arrays.asList(objectId),caoZuoDH, postDataMap,false,true,true,null,null);
////				ExecResult<ModelData> result = DataUtils.submit(yh,gndh,czdh, idMap.get(gndh), execCZDH,postData, forceOriginal,iscomplete, true,this.getThreadLocalRequest(),this.getThreadLocalResponse());
//				//返回值转换为json
//				if(!saveRet.isSuccess()){
//					ret = new JSONMessageResult(saveRet.getErrorMsg());
//				}else{
//					ret = new JSONMessageResult();
//					ret.put("data",getTaskStatus(gongNengDH,caoZuoDH,objectId));
//				}
				
				
//				TaskUtils.doTask(obj,null,null,null,gncz,yh,taskData,true);
//				ExecResult<ModelData> saveRet = DataUtils.doSubmit(yh,gongNengDH,caoZuoDH, Arrays.asList(objectId),caoZuoDH, postDataMap,false,true,true,null,null);
				
				//
//				JSONObject process = getTaskStatus(gongNengDH,caoZuoDH,objectId);
//				ret.put("success", true);
//				ret.put("data", process);
			}
		}catch(Exception e){
			ret = new JSONMessageResult(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	private JSONObject getTaskStatus(String gongNengDH,String caoZuoDH,Integer objectId ){
		Session sess = null;
		JSONObject ret = new JSONObject();
		//数据库链接
		sess = HibernateSessionFactory.getSession();
		//功能
		GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
		//取得业务数据对象
		BusinessI obj = (BusinessI)sess.load(gn.getShiTiLei().getShiTiLeiClassName(), objectId);
		ret.put("processInstanceEnded", obj.getProcessInstanceEnded());
		ret.put("processInstanceTerminated", obj.getProcessInstanceTerminated());
		//取得节点信息
		LiuChengJS lcjs = (LiuChengJS)sess
				.createCriteria(LiuChengJS.class)
				.add(Restrictions.eq("gongNengDH", gongNengDH))
				.add(Restrictions.eq("caoZuoDH", caoZuoDH))
				.add(Restrictions.eq("gongNengObjId", objectId))
				.add(Restrictions.eq("shiFouSC", false))
				.addOrder(Order.desc("liuChengJSDM"))
				.setMaxResults(1)
				.uniqueResult();
		JSONObject task = new JSONObject();
		if(lcjs != null){
			task.put("shiFouCK",lcjs.getShiFouCK());
			task.put("shiFouCL",lcjs.getShiFouCL());
			task.put("shiFouWC",lcjs.getShiFouWC());
		}
		ret.put("task", task);
		
		return ret;
	}
	
}

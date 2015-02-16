package com.poweruniverse.nim.data.webservice;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.message.Result;
import com.poweruniverse.nim.base.webservice.BasePlateformWebservice;
import com.poweruniverse.nim.data.service.utils.AuthUtils;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
/**
 * 所有的功能性方法 
 * @author Administrator
 *
 */
@WebService
public class WorkWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	
	public WorkWebserviceImpl(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}
	
	/**
	 * 执行 功能操作(status类型)
	 */
	public Result execute(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="ids") String ids){
		//
		Result result = null;
		Session sess = null;
		try {
			if(ids ==null){
				return new JSONMessageResult("必须为exec操作提供id参数(例：[12,33])");
			}
			Integer yhdm = this.getYongHuDM(wsContext, false);
			if(yhdm==null){
				return new JSONMessageResult("未登录或已超时！");
			}
			List<Integer> idList = new ArrayList<Integer>();
			
			JSONArray idArray = JSONArray.fromObject(ids);
			for(int i=0;i<idArray.size();i++){
				idList.add(idArray.getInt(i));
			}
			result = DataUtils.doExec(gongNengDH,caoZuoDH, idList,yhdm);
			
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
	/**
	 * 收回任务  
	 */
	public JSONMessageResult retake(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="id") Integer id){
		//
		JSONMessageResult result = null;
		Session sess = null;
		try {
			Integer yhdm = this.getYongHuDM(wsContext, false);
			boolean authRsult = AuthUtils.checkAuth(gongNengDH,caoZuoDH,id,yhdm);
			result = new JSONMessageResult();
			result.put("data", authRsult);
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
	/**
	 * 取得当前用户的当前待办功能操作
	 */
	public JSONMessageResult getTodoGNCZ(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="id") Integer id){
		//
		JSONMessageResult result = null;
		Session sess = null;
		try {
			Integer yhdm = this.getYongHuDM(wsContext, false);
			boolean authRsult = AuthUtils.checkAuth(gongNengDH,caoZuoDH,id,yhdm);
			result = new JSONMessageResult();
			result.put("data", authRsult);
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
	/**
	 * 修改当前任务的待办
	 */
	public JSONMessageResult changeAssignUser(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="id") Integer id){
		//
		JSONMessageResult result = null;
		Session sess = null;
		try {
			Integer yhdm = this.getYongHuDM(wsContext, false);
			boolean authRsult = AuthUtils.checkAuth(gongNengDH,caoZuoDH,id,yhdm);
			result = new JSONMessageResult();
			result.put("data", authRsult);
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(HibernateSessionFactory.defaultSessionFactory,true);
			}
		}
		return result;
	}
	
}

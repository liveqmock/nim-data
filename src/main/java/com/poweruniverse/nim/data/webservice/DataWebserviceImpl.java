package com.poweruniverse.nim.data.webservice;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.bean.BaseJavaDatasource;
import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.message.JSONDataResult;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.message.ObjectResult;
import com.poweruniverse.nim.base.message.Result;
import com.poweruniverse.nim.base.webservice.BasePlateformWebservice;
import com.poweruniverse.nim.data.bean.MethodResult;
import com.poweruniverse.nim.data.entity.system.GongNeng;
import com.poweruniverse.nim.data.entity.system.GongNengCZ;
import com.poweruniverse.nim.data.entity.system.ShiTiLei;
import com.poweruniverse.nim.data.entity.system.YongHu;
import com.poweruniverse.nim.data.entity.system.ZiDuan;
import com.poweruniverse.nim.data.entity.system.ZiDuanLX;
import com.poweruniverse.nim.data.entity.system.base.BusinessI;
import com.poweruniverse.nim.data.entity.system.base.EntityI;
import com.poweruniverse.nim.data.service.utils.AuthUtils;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;
import com.poweruniverse.nim.data.service.utils.NativeSQLOrder;
import com.poweruniverse.nim.data.service.utils.TaskUtils;
import com.poweruniverse.oim.activiti.ClearProcessInstanceBussinessKeyCmd;

@WebService
public class DataWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	
	public DataWebserviceImpl(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}
	
	
	/**
	 * 根据实体类代号 取得请求的数据
	 * @param gndh
	 * @param czdh
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @param filterString
	 * @param sortString
	 * @return
	 */
	public JSONDataResult getStlList(
			@WebParam(name="shiTiLeiDH") String shiTiLeiDH,
			@WebParam(name="start") Integer start,
			@WebParam(name="limit") Integer limit,
			@WebParam(name="fields") String fields,
			@WebParam(name="filters") String filters,
			@WebParam(name="sorts") String sorts){
		JSONDataResult ret = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			ShiTiLei dataStl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			if(dataStl==null){
				return new JSONDataResult("实体类("+shiTiLeiDH+")不存在！");
			}
			
			Map<String,Object> qRsult =  DataUtils.getListBySTL(shiTiLeiDH,start,limit,filters,sorts);
			List<?> objs = (List<?>)qRsult.get("objs");
			int totalCount = (Integer)qRsult.get("totalCount");
			//创建返回对象
			JSONObject meta = new JSONObject();
			meta.put("zhuJianLie", dataStl.getZhuJianLie());
			meta.put("xianShiLie", dataStl.getXianShiLie());
			meta.put("paiXuLie", dataStl.getPaiXuLie());
			
			JSONArray fieldJsonArray = null;
			if(fields==null ||  fields.length() < 4){
				fieldJsonArray = JSONArray.fromObject("[{" +
						"name:'"+dataStl.getZhuJianLie()+"'" +
					"},{" +
						"name:'"+dataStl.getXianShiLie()+"'" +
					"},{" +
						"name:'"+dataStl.getPaiXuLie()+"'" +
					"}]");
			}else if( fields.equals("['...']") ||  fields.equals("[\"...\"]")){
				//全部的字段  当前级别以及对象，集合字段的显示列、排序列
				fieldJsonArray = new JSONArray();
				for(ZiDuan zd:dataStl.getZds()){
					JSONObject zdObj = new JSONObject();
					zdObj.put("name", zd.getZiDuanDH());
					
					if(zd.getZiDuanLX().getZiDuanLXDH().equals("set") || zd.getZiDuanLX().getZiDuanLXDH().equals("object") ){
						JSONArray subZdArray = new JSONArray();
						
						JSONObject zjlObj = new JSONObject();
						zjlObj.put("name", zd.getGuanLianSTL().getZhuJianLie());
						subZdArray.add(zjlObj);
						
						JSONObject xslObj = new JSONObject();
						xslObj.put("name", zd.getGuanLianSTL().getXianShiLie());
						subZdArray.add(xslObj);
						
						JSONObject pxlObj = new JSONObject();
						pxlObj.put("name", zd.getGuanLianSTL().getPaiXuLie());
						subZdArray.add(pxlObj);
						
						zdObj.put("fields", subZdArray);
						
						JSONObject subMeta = new JSONObject();
						subMeta.put("zhuJianLie", zd.getGuanLianSTL().getZhuJianLie());
						subMeta.put("xianShiLie", zd.getGuanLianSTL().getXianShiLie());
						subMeta.put("paiXuLie", zd.getGuanLianSTL().getPaiXuLie());
						meta.put(zd.getZiDuanDH(), subMeta);
					}
					
					fieldJsonArray.add(zdObj);
				}
			}else{
				fieldJsonArray = JSONArray.fromObject(fields);
				boolean hasZhuJianLieExists = false;
				for(int i=0;i<fieldJsonArray.size();i++){
					JSONObject zdObj = fieldJsonArray.getJSONObject(i);
					if(dataStl.getZhuJianLie().equals(zdObj.getString("name"))){
						hasZhuJianLieExists = true;
						break;
					}
				}
				if(!hasZhuJianLieExists){
					JSONObject zjlObj = new JSONObject();
					zjlObj.put("name", dataStl.getZhuJianLie());
					fieldJsonArray.add(zjlObj);
				}
			}
			
			//将数据转换为json格式 添加到页面中
			JSONArray rows = JSONConvertUtils.objectList2JSONArray(dataStl,objs,fieldJsonArray);
			//
			ret = new JSONDataResult(rows, totalCount, start, limit,meta);
		}catch (InvocationTargetException e){
			ret = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		} catch (Exception e) {
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}

	/**
	 * 根据功能代号、操作代号按当前用户的操作权限 取得请求的数据
	 * @param gndh
	 * @param czdh
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @param filterString
	 * @param sortString
	 * @return
	 */
	public JSONDataResult getGnList(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="start") Integer start,
			@WebParam(name="limit") Integer limit,
			@WebParam(name="fields") String fields,
			@WebParam(name="filters") String filters,
			@WebParam(name="sorts") String sorts){
		//
		JSONDataResult result = null;
		Session sess = null;
		try {
			//检查对应的功能操作是否存在
			sess = HibernateSessionFactory.getSession();
			
			GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
					.createAlias("gongNeng", "gncz_gn")
					.add(Restrictions.eq("gncz_gn.gongNengDH",gongNengDH))
					.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
			if(gncz==null){
				return new JSONDataResult("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
			}
			

			boolean needAuth = gncz.getKeYiSQ();//需要授权？
			//需要授权的功能操作 必须提供用户名、密码用于验证用户身份
			Integer yhdm = this.getYongHuDM(wsContext,needAuth);
			//这里
			GongNeng gn = gncz.getGongNeng();
			//
			//
			JSONObject meta = new JSONObject();
			meta.put("zhuJianLie", gn.getShiTiLei().getZhuJianLie());
			meta.put("xianShiLie", gn.getShiTiLei().getXianShiLie());
			meta.put("paiXuLie", gn.getShiTiLei().getPaiXuLie());

			JSONArray fieldJsonArray = null;
			if(fields==null ||  fields.length() < 4){
				fieldJsonArray = JSONArray.fromObject("[{" +
						"name:'"+gncz.getGongNeng().getShiTiLei().getZhuJianLie()+"'" +
					"},{" +
						"name:'"+gncz.getGongNeng().getShiTiLei().getXianShiLie()+"'" +
					"},{" +
						"name:'"+gncz.getGongNeng().getShiTiLei().getPaiXuLie()+"'" +
					"}]");
			}else if( fields.equals("['...']") ||  fields.equals("[\"...\"]")){
				//全部的字段  当前级别以及对象，集合字段的显示列、排序列
				fieldJsonArray = new JSONArray();
				for(ZiDuan zd:gncz.getGongNeng().getShiTiLei().getZds()){
					JSONObject zdObj = new JSONObject();
					zdObj.put("name", zd.getZiDuanDH());
					
					if(zd.getZiDuanLX().getZiDuanLXDH().equals("set") || zd.getZiDuanLX().getZiDuanLXDH().equals("object") ){
						JSONArray subZdArray = new JSONArray();
						
						JSONObject zjlObj = new JSONObject();
						zjlObj.put("name", zd.getGuanLianSTL().getZhuJianLie());
						subZdArray.add(zjlObj);
						
						JSONObject xslObj = new JSONObject();
						xslObj.put("name", zd.getGuanLianSTL().getXianShiLie());
						subZdArray.add(xslObj);
						
						JSONObject pxlObj = new JSONObject();
						pxlObj.put("name", zd.getGuanLianSTL().getPaiXuLie());
						subZdArray.add(pxlObj);
						
						zdObj.put("fields", subZdArray);
						
						JSONObject subMeta = new JSONObject();
						subMeta.put("zhuJianLie", zd.getGuanLianSTL().getZhuJianLie());
						subMeta.put("xianShiLie", zd.getGuanLianSTL().getXianShiLie());
						subMeta.put("paiXuLie", zd.getGuanLianSTL().getPaiXuLie());
						meta.put(zd.getZiDuanDH(), subMeta);
					}
					
					fieldJsonArray.add(zdObj);
				}
			}else{
				fieldJsonArray = JSONArray.fromObject(fields);
				boolean hasZhuJianLieExists = false;
				for(int i=0;i<fieldJsonArray.size();i++){
					JSONObject zdObj = fieldJsonArray.getJSONObject(i);
					if(gncz.getGongNeng().getShiTiLei().getZhuJianLie().equals(zdObj.getString("name"))){
						hasZhuJianLieExists = true;
						break;
					}
				}
				if(!hasZhuJianLieExists){
					JSONObject zjlObj = new JSONObject();
					zjlObj.put("name", gncz.getGongNeng().getShiTiLei().getZhuJianLie());
					fieldJsonArray.add(zjlObj);
				}
			}
			//
			Method mtd = null;
			Class<?> cls = null;
			//允许在list之前 触发可能存在的beforeList事件 
			Result beforeListResult = null;
			try {
				cls = Class.forName(gn.getGongNengClass());
				mtd = cls.getMethod("beforeList",new Class[]{String.class,Integer.class,Integer.class,String.class,String.class,String.class,Integer.class});
				beforeListResult = (Result)mtd.invoke(cls.newInstance(), new Object[]{caoZuoDH,start,limit,fieldJsonArray.toString(),filters,sorts,yhdm});
			} catch (Exception e) {
			}
			
			if(beforeListResult!=null && !beforeListResult.isSuccess()){
				result = new JSONDataResult(beforeListResult.getErrorMsg()) ;
			}else{
				//
				Map<String,Object> listResult = DataUtils.getListByGN(gongNengDH,caoZuoDH,start,limit,filters,sorts,yhdm);
				List<EntityI> objs =  (List<EntityI>)listResult.get("objs");
			
				//允许在list之后  触发可能存在的afterList事件  对取得的结果集进行处理
				try {
					cls = Class.forName(gn.getGongNengClass());
					mtd = cls.getMethod("afterList",new Class[]{String.class,Integer.class,Integer.class,String.class,String.class,String.class,List.class,Integer.class});
					JSONDataResult afterListResult = (JSONDataResult)mtd.invoke(cls.newInstance(), new Object[]{caoZuoDH,start,limit,fieldJsonArray.toString(),filters,sorts,objs,yhdm});
					if(afterListResult.isSuccess()){
						result = afterListResult;
					}
				} catch (Exception e) {
				}
				
				if(result==null){
					JSONArray rows = JSONConvertUtils.objectList2JSONArray(gn.getShiTiLei(), objs, fieldJsonArray);
					result = new JSONDataResult(rows, (Integer)listResult.get("totalCount"), start, limit,meta);
				}
			}
			
			//提交 并关闭session
			HibernateSessionFactory.closeSession(true);
			sess = null;
		}catch (InvocationTargetException e){
			result = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		} catch (Exception e) {
			result = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return result;
	}
	
	
	/**
	 * 根据功能代号、操作代号按当前用户的操作权限 取得请求的数据
	 * @param gndh
	 * @param czdh
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @param filterString
	 * @param sortString
	 * @return
	 */
	public JSONDataResult getGnRecord(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="id") Integer id,
			@WebParam(name="fields") String fields){
//		String gongNengDH = params.getString("gongNengDH");
//		String caoZuoDH = params.getString("caoZuoDH");
		
		JSONDataResult result = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			
			//取得数据
			GongNengCZ gncz = GongNeng.getGongNengByDH(gongNengDH).getCaoZuoByDH(caoZuoDH);
			if(gncz==null){
				throw new Exception("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
			}
			
			Integer yhdm = this.getYongHuDM(wsContext,gncz.getKeYiSQ());
			
			if(gncz.getDuiXiangXG() && id==null){
				throw new Exception("功能操作:"+gongNengDH+"."+caoZuoDH+"为对象相关的操作，必须提供id参数！");
			}
			
			//用这个openGNDH、openCZDH确定和检查权限
			if(!AuthUtils.checkAuth(gongNengDH,caoZuoDH, id, yhdm)){
				throw new Exception("记录("+gongNengDH+"."+caoZuoDH+"."+id+")不存在或用户没有权限！");
			}
			Object obj = sess.load(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName(), id);
			
			//创建返回对象
			JSONObject meta = new JSONObject();
			meta.put("zhuJianLie", gncz.getGongNeng().getShiTiLei().getZhuJianLie());
			meta.put("xianShiLie", gncz.getGongNeng().getShiTiLei().getXianShiLie());
			meta.put("paiXuLie", gncz.getGongNeng().getShiTiLei().getPaiXuLie());
			
			JSONArray fieldJsonArray = null;
			if(fields==null ||  fields.length() < 4){
				//未提供字段类别 默认取主键列 、显示列、排序列
				fieldJsonArray = JSONArray.fromObject("[{" +
						"name:'"+gncz.getGongNeng().getShiTiLei().getZhuJianLie()+"'" +
					"},{" +
						"name:'"+gncz.getGongNeng().getShiTiLei().getXianShiLie()+"'" +
					"},{" +
						"name:'"+gncz.getGongNeng().getShiTiLei().getPaiXuLie()+"'" +
					"}]");
			}else if( fields.equals("['...']") ||  fields.equals("[\"...\"]")){
				//全部的字段  当前级别以及对象，集合字段的显示列、排序列
				fieldJsonArray = new JSONArray();
				for(ZiDuan zd:gncz.getGongNeng().getShiTiLei().getZds()){
					JSONObject zdObj = new JSONObject();
					zdObj.put("name", zd.getZiDuanDH());
					
					if(zd.getZiDuanLX().getZiDuanLXDH().equals("set") || zd.getZiDuanLX().getZiDuanLXDH().equals("object") ){
						JSONArray subZdArray = new JSONArray();
						
						JSONObject zjlObj = new JSONObject();
						zjlObj.put("name", zd.getGuanLianSTL().getZhuJianLie());
						subZdArray.add(zjlObj);
						
						JSONObject xslObj = new JSONObject();
						xslObj.put("name", zd.getGuanLianSTL().getXianShiLie());
						subZdArray.add(xslObj);
						
						JSONObject pxlObj = new JSONObject();
						pxlObj.put("name", zd.getGuanLianSTL().getPaiXuLie());
						subZdArray.add(pxlObj);
						
						zdObj.put("fields", subZdArray);
						
						JSONObject subMeta = new JSONObject();
						subMeta.put("zhuJianLie", zd.getGuanLianSTL().getZhuJianLie());
						subMeta.put("xianShiLie", zd.getGuanLianSTL().getXianShiLie());
						subMeta.put("paiXuLie", zd.getGuanLianSTL().getPaiXuLie());
						meta.put(zd.getZiDuanDH(), subMeta);
					}
					
					fieldJsonArray.add(zdObj);
				}
			}else{
				fieldJsonArray = JSONArray.fromObject(fields);
				boolean hasZhuJianLieExists = false;
				for(int i=0;i<fieldJsonArray.size();i++){
					JSONObject zdObj = fieldJsonArray.getJSONObject(i);
					if(gncz.getGongNeng().getShiTiLei().getZhuJianLie().equals(zdObj.getString("name"))){
						hasZhuJianLieExists = true;
						break;
					}
				}
				if(!hasZhuJianLieExists){
					JSONObject zjlObj = new JSONObject();
					zjlObj.put("name", gncz.getGongNeng().getShiTiLei().getZhuJianLie());
					fieldJsonArray.add(zjlObj);
				}
			}
			
			//将数据转换为json格式 添加到页面中
			JSONObject row = JSONConvertUtils.object2JSONObject(gncz.getGongNeng().getShiTiLei(),obj,fieldJsonArray);
			
			
			JSONArray rows= new JSONArray();
			rows.add(row);
			
			result = new JSONDataResult(rows, 1, 0, 0,meta);
		//
		} catch (Exception e) {
			result = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return result;
	}
	
	
	
	/**
	 * 根据功能代号或实体类代号加载实体类定义信息
	 * @param gndh
	 * @param czdh
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @param filterString
	 * @param sortString
	 * @return
	 */
	public JSONDataResult getZdList(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="shiTiLeiDH") String shiTiLeiDH,
			@WebParam(name="property") String property,
			@WebParam(name="fields") String fields){
		JSONDataResult retMsg = null;
		try{
			if(gongNengDH == null && shiTiLeiDH==null){
				return new JSONDataResult("必须提供gongNengDH或shiTiLeiDH参数，用于调用此方法(DataWebservice.loadZD)！");
			}
			if(fields == null){
				return new JSONDataResult("必须提供fields参数，用于调用此方法(DataWebservice.loadZD)！");
			}
			JSONArray fieldJsonArray = JSONArray.fromObject(fields);
			
			ShiTiLei stl = ShiTiLei.getShiTiLeiByDH("SYS_ZiDuan");
			if(stl==null){
				return new JSONDataResult("实体类SYS_ZiDuan不存在！");
			}
			
			ShiTiLei stlObj = null;
			if(gongNengDH!=null){
				GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
				if(gn==null){
					return new JSONDataResult("功能:"+gongNengDH+"不存在！");
				}
				stlObj = gn.getShiTiLei();
			}else if(shiTiLeiDH!=null){
				stlObj = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			}
			//当前实体类对象
			if(property!=null && property.length()>0){
				ZiDuan zd = stlObj.getZiDuan(property);
				if(zd==null){
					return new JSONDataResult("字段("+stlObj.getShiTiLeiMC()+"."+property+")不存在！");
				}else if(zd.getGuanLianSTL()==null){
					return new JSONDataResult("字段("+stlObj.getShiTiLeiMC()+"."+property+")未定义关联实体类！");
				}
				stlObj = zd.getGuanLianSTL();
			}
			//查找此实体类下的所有字段
			Session sess = HibernateSessionFactory.getSession();
			@SuppressWarnings("unchecked")
			List<ZiDuan> zds = (List<ZiDuan>)sess.createCriteria(ZiDuan.class)
					.add(Restrictions.eq("shiTiLei.id", stlObj.getShiTiLeiDM()))
					.addOrder(NativeSQLOrder.raw("( case ziduanlxdm when 8 then -20 when 9 then -18 when 1 then -16 when 7 then -14 when 2 then -12 when 10 then -10 else ziduanlxdm end)"))
					.list();
			//
			JSONArray zdsArray = JSONConvertUtils.objectList2JSONArray(stl,zds,fieldJsonArray);
			
			retMsg = new JSONDataResult(zdsArray, zds.size(), 0, 0, null);
			
		}catch (Exception e){
			retMsg = new JSONDataResult(e.getMessage());
			e.printStackTrace();
		}
		return retMsg;
	}
	
	
	/**
	 * 根据实体类代号按当前用户的操作权限 取得请求的数据
	 * @param gndh
	 * @param czdh
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @param filterString
	 * @param sortString
	 * @return
	 */
	public JSONDataResult getStlRecord(
			@WebParam(name="shiTiLeiDH") String shiTiLeiDH,
			@WebParam(name="id") Integer id,
			@WebParam(name="fields") String fields){
		
		JSONDataResult result = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			
			//取得数据
			ShiTiLei stl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			if(stl==null){
				throw new Exception("实体类:"+shiTiLeiDH+"不存在！");
			}
			
			if(id==null){
				throw new Exception("未提供id参数！");
			}
			
			Object obj = DataUtils.getObjectBySTL(shiTiLeiDH, id);
			
			//创建返回对象
			JSONObject meta = new JSONObject();
			meta.put("zhuJianLie", stl.getZhuJianLie());
			meta.put("xianShiLie", stl.getXianShiLie());
			meta.put("paiXuLie", stl.getPaiXuLie());
			
			JSONArray fieldJsonArray = null;
			if(fields==null ||  fields.length() < 4){
				//未提供字段类别 默认取主键列 、显示列、排序列
				fieldJsonArray = JSONArray.fromObject("[{" +
						"name:'"+stl.getZhuJianLie()+"'" +
					"},{" +
						"name:'"+stl.getXianShiLie()+"'" +
					"},{" +
						"name:'"+stl.getPaiXuLie()+"'" +
					"}]");
			}else if( fields.equals("['...']") ||  fields.equals("[\"...\"]")){
				//全部的字段  当前级别以及对象，集合字段的显示列、排序列
				fieldJsonArray = new JSONArray();
				for(ZiDuan zd:stl.getZds()){
					JSONObject zdObj = new JSONObject();
					zdObj.put("name", zd.getZiDuanDH());
					
					if(zd.getZiDuanLX().getZiDuanLXDH().equals("set") || zd.getZiDuanLX().getZiDuanLXDH().equals("object") ){
						JSONArray subZdArray = new JSONArray();
						
						JSONObject zjlObj = new JSONObject();
						zjlObj.put("name", zd.getGuanLianSTL().getZhuJianLie());
						subZdArray.add(zjlObj);
						
						JSONObject xslObj = new JSONObject();
						xslObj.put("name", zd.getGuanLianSTL().getXianShiLie());
						subZdArray.add(xslObj);
						
						JSONObject pxlObj = new JSONObject();
						pxlObj.put("name", zd.getGuanLianSTL().getPaiXuLie());
						subZdArray.add(pxlObj);
						
						zdObj.put("fields", subZdArray);
						
						JSONObject subMeta = new JSONObject();
						subMeta.put("zhuJianLie", zd.getGuanLianSTL().getZhuJianLie());
						subMeta.put("xianShiLie", zd.getGuanLianSTL().getXianShiLie());
						subMeta.put("paiXuLie", zd.getGuanLianSTL().getPaiXuLie());
						meta.put(zd.getZiDuanDH(), subMeta);
					}
					
					fieldJsonArray.add(zdObj);
				}
			}else{
				fieldJsonArray = JSONArray.fromObject(fields);
				boolean hasZhuJianLieExists = false;
				for(int i=0;i<fieldJsonArray.size();i++){
					JSONObject zdObj = fieldJsonArray.getJSONObject(i);
					if(stl.getZhuJianLie().equals(zdObj.getString("name"))){
						hasZhuJianLieExists = true;
						break;
					}
				}
				if(!hasZhuJianLieExists){
					JSONObject zjlObj = new JSONObject();
					zjlObj.put("name", stl.getZhuJianLie());
					fieldJsonArray.add(zjlObj);
				}
			}
			
			//将数据转换为json格式 添加到页面中
			JSONObject row = JSONConvertUtils.object2JSONObject(stl,obj,fieldJsonArray);
			
			
			JSONArray rows= new JSONArray();
			rows.add(row);
			
			result = new JSONDataResult(rows, 1, 0, 0,meta);
		//
		} catch (Exception e) {
			result = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return result;
	}
	
	public static JSONDataResult loadSQLMeta(String sql) throws Exception{
		JSONDataResult ret = null;
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
        	conn = HibernateSessionFactory.getConnection();
        	
    		sql = 	"select t2.* from ("+
    				"	select t.*,rownum rn  from ( "+sql+") t where rownum <= 1"+
				 	") t2"+
    				" where t2.rn > 0";
    		pstmt = conn.prepareStatement(sql);
    		rs = pstmt.executeQuery();
    		ResultSetMetaData rsmd = rs.getMetaData();
    		
    		JSONArray zdArray = new JSONArray();
    		
    		for(int i=1;i<=rsmd.getColumnCount();i++){
    			String name = rsmd.getColumnName(i);
    			String label = rsmd.getColumnLabel(i);
    			String columnType = rsmd.getColumnTypeName(i);
    			JSONObject obj = new JSONObject();
    			obj.put("ziDuanDH", name);
    			obj.put("ziDuanBT", label);
    			
    			String typeDH = null;
    			int typeDM = 0;
    			if("NUMBER".equals(columnType) && rsmd.getScale(i) >0){
    				typeDH = "double";
    				typeDM = ZiDuanLX.ZiDuanLX_DOUBLE;
    			}else if("NUMBER".equals(columnType) && rsmd.getScale(i) == 0){
    				typeDH = "int";
    				typeDM = ZiDuanLX.ZiDuanLX_INT;
    			}else if("VARCHAR2".equals(columnType)){
    				typeDH = "string";
    				typeDM = ZiDuanLX.ZiDuanLX_STRING;
    			}else if("DATE".equals(columnType)){
    				typeDH = "date";
    				typeDM = ZiDuanLX.ZiDuanLX_DATE;
    			}else{
    				typeDH = "string";
    				typeDM = ZiDuanLX.ZiDuanLX_STRING;
    			}
    			obj.put("ziDuanLX", JSONObject.fromObject("{ziDuanLXDH:'"+typeDH+"',ziDuanLXDM:"+typeDM+"}"));
    			obj.put("ziDuanDM", i);
    			
    			zdArray.add(obj);
    		}
    		ret = new JSONDataResult(zdArray, 1, 0, 0,null);
		}catch (SQLException e) {
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
		}finally{
			if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
            	pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
		}
		return ret;
	}
	
	
	/**
	 * 根据sql 取得请求的数据变量
	 * @param gndh
	 * @param czdh
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @param filterString
	 * @param sortString
	 * @return
	 */
	public JSONMessageResult getSqlVariable(
			@WebParam(name="sql") String sql){
		
		JSONMessageResult result = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			Object sqlResult = sess.createSQLQuery(sql).uniqueResult();
			
		    if (sqlResult != null && sqlResult instanceof Clob) {
		    	Clob clob = (Clob)sqlResult;
		    	sqlResult = clob.getSubString(1, (int) clob.length());
		    }
		    if(sqlResult != null && sqlResult instanceof String){
				String oldString = (String)sqlResult;
				oldString = oldString.replaceAll("\\\\", "\\\\");
				oldString = oldString.replaceAll("\r", "");
				oldString = oldString.replaceAll("\n", "");
				oldString = oldString.replaceAll("'", "\\'");
				sqlResult = oldString.replaceAll("\"", "\\\"");
			}
		    result = new JSONMessageResult();
		    result.put("variable", sqlResult);
		} catch (Exception e) {
			result = new JSONMessageResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return result;
	}
	


	
	
	/**
	 * 根据sql 取得请求的数据集合
	 * @param sql
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @return
	 */
	public JSONDataResult getSqlList(
			@WebParam(name="sql") String sql,
			@WebParam(name="start") Integer start,
			@WebParam(name="limit") Integer limit,
			@WebParam(name="fields") String fields){
		JSONDataResult ret = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			
			JSONArray fieldJsonArray = JSONArray.fromObject(fields);

			Map<String,Object> listResult = DataUtils.getListBySQL(sql,start,limit,fieldJsonArray);
			JSONArray rows =  (JSONArray)listResult.get("objs");
			int totalCount = (Integer)listResult.get("totalCount");
			//创建返回对象
			ret = new JSONDataResult(rows, totalCount, start, limit,null);
		}catch (InvocationTargetException e){
			ret = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		} catch (Exception e) {
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	/**
	 * 根据java dataset实现类 取得请求的数据集合
	 * @param sql
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @return
	 */
	public JSONDataResult getJavaList(
			@WebParam(name="className") String className,
			@WebParam(name="start") Integer start,
			@WebParam(name="limit") Integer limit,
			@WebParam(name="filters") String filters,
			@WebParam(name="params") String params){
		JSONDataResult ret = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			JSONObject paramsObject = new JSONObject();
			if(params!=null){
				JSONArray ps = JSONArray.fromObject(params);
				for(int i=0;i<ps.size();i++){
					JSONObject p = ps.getJSONObject(i);
					paramsObject.put(p.get("name"), p.get("value"));
				}
			}
			JSONArray filterArray = null;
			if(filters!=null){
				filterArray = JSONArray.fromObject(filters);
			}
			
			//取得数据对象(用老的程序方法)
			BaseJavaDatasource javaDS = (BaseJavaDatasource)Class.forName(className).newInstance();
			JSONObject jsonData = javaDS.getData(filterArray,paramsObject,start,limit);
			ret = new JSONDataResult(jsonData.getJSONArray("rows"), jsonData.getInt("totalCount"), start, limit,null);
		} catch (Exception e) {
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	
	/**
	 * 根据java dataset实现类 取得请求的数据集合
	 * @param sql
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @return
	 */
	public JSONDataResult getTodoList(
			@WebParam(name="className") String className,
			@WebParam(name="start") Integer start,
			@WebParam(name="limit") Integer limit,
			@WebParam(name="fields") String fields,
			@WebParam(name="filters") String filters,
			@WebParam(name="sorts") String sorts,
			@WebParam(name="workflows") String workflows,
			@WebParam(name="todoOnly") Boolean todoOnly){
		JSONDataResult ret = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			String shiTiLeiDH = "SYS_LiuChengJS";
			
			ShiTiLei dataStl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			if(dataStl==null){
				return new JSONDataResult("实体类("+shiTiLeiDH+")不存在！");
			}
			
			//必须提供用户名
			Integer yhdm = this.getYongHuDM(wsContext,true);

			JSONObject qRsult = DataUtils.getTodoListByHibernate(
					shiTiLeiDH,start,limit,JSONArray.fromObject(fields),JSONArray.fromObject(workflows),filters,sorts,yhdm,todoOnly);
			int totalCount = 0;
			JSONArray rows = null;
			if(qRsult.getBoolean("success")){
				totalCount = qRsult.getInt("totalCount");
				rows = qRsult.getJSONArray("data");
			}else{
				rows = new JSONArray();
			}
			
			JSONObject meta = new JSONObject();
			meta.put("zhuJianLie", dataStl.getZhuJianLie());
			meta.put("xianShiLie", dataStl.getXianShiLie());
			meta.put("paiXuLie", dataStl.getPaiXuLie());
			
			ret = new JSONDataResult(rows, totalCount, start, limit,meta);
		}catch (InvocationTargetException e){
			ret = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		} catch (Exception e) {
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	
	public JSONDataResult execute(
			@WebParam(name="xiTongDH") String xiTongDH,
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="submitData") String submitData){
		JSONDataResult ret = null;
		Session sess = null;
		try {
			sess = HibernateSessionFactory.getSession();
			GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
					.add(Restrictions.eq("gncz_gn.gongNengDH",gongNengDH))
					.createAlias("gongNeng", "gncz_gn")
					.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
			if(gncz==null){
				return new JSONDataResult("功能操作（"+gongNengDH+"."+caoZuoDH+"）不存在！");
			}
			
			Integer yongHuDM = this.getYongHuDM(wsContext,true);
			//
			JSONArray idArray = JSONArray.fromObject(submitData);
			List<Integer> idList = new ArrayList<Integer>();
			//检查权限
			for(int i=0;i<idArray.size();i++){
				JSONObject idObj = idArray.getJSONObject(i);
				Integer id = idObj.getInt(gncz.getGongNeng().getShiTiLei().getZhuJianLie());
				idList.add(id);
			}
			//
			Result execResult = null;
			if("delete".equals(caoZuoDH)){
				execResult= DataUtils.doDelele(gongNengDH, caoZuoDH, idList, yongHuDM);
			}else{
				execResult= DataUtils.doExec(gongNengDH, caoZuoDH, idList, yongHuDM);
			}
			
			if(!execResult.isSuccess()){
				ret = new JSONDataResult(execResult.getErrorMsg());
			}else{
				ret = new JSONDataResult(idArray,idArray.size(),0,0,null);
			}
			
		}catch (InvocationTargetException e){
			ret = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}catch (Exception e){
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	/**
	 * 客户端提交修改后的数据 多条记录的新增、修改、删除
	 * @param sql
	 * @param start
	 * @param limit
	 * @param fieldString
	 * @return
	 */
	public JSONDataResult save(
			@WebParam(name="xiTongDH") String xiTongDH,
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="submitData") String submitData,
			@WebParam(name="iscomplete") Boolean iscomplete){
		JSONDataResult ret = null;
		Session sess = null;
		
		try {
			sess = HibernateSessionFactory.getSession();
			GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
					.add(Restrictions.eq("gncz_gn.gongNengDH",gongNengDH))
					.createAlias("gongNeng", "gncz_gn")
					.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
			if(gncz==null){
				return new JSONDataResult("功能操作（"+gongNengDH+"."+caoZuoDH+"）不存在！");
			}
			//保存数据
			Integer yhdm = this.getYongHuDM(wsContext,false);
			ShiTiLei stl = gncz.getGongNeng().getShiTiLei();
			JSONObject submitDataObj = JSONObject.fromObject(submitData);
			//当前处理的对象
			List<EntityI> objs = new ArrayList<EntityI>();
			List<EntityI> oldObjs = new ArrayList<EntityI>();
			
			JSONArray dataArray = new JSONArray();
			if(submitDataObj.containsKey("inserted")){
				JSONArray insertedDataArray = submitDataObj.getJSONArray("inserted");
				for(int i=0;i<insertedDataArray.size();i++){
					JSONObject dataObj = insertedDataArray.getJSONObject(i);
					//新增数据要清除主键值
					if(dataObj.containsKey(stl.getZhuJianLie())){
						dataObj.remove(stl.getZhuJianLie());
					}
					
					//检查权限
					if(!AuthUtils.hasGNCZAuth(gncz, yhdm)){
						return new JSONDataResult("保存失败:当前用户无此功能操作("+gncz.getGongNeng().getGongNengMC()+"."+gncz.getCaoZuoMC()+")的授权!");
					}
					//当前用户是否有权限 新增对象
					EntityI obj = (EntityI)DataUtils.newObjectBySTL(stl);
					objs.add(obj);
					oldObjs.add(obj.clone());
					
					dataObj.put("_entity", obj);
					dataArray.add(dataObj);
				}
			}
			
			if(submitDataObj.containsKey("modified")){
				JSONArray modifiedDataArray = submitDataObj.getJSONArray("modified");
				for(int i=0;i<modifiedDataArray.size();i++){
					JSONObject dataObj = modifiedDataArray.getJSONObject(i);
					Integer objId = null;
					if(dataObj.containsKey(stl.getZhuJianLie())){
						objId = dataObj.getInt(stl.getZhuJianLie());
						dataObj.remove(stl.getZhuJianLie());
					}
					
					if(objId==null){
						return new JSONDataResult("保存失败:主键值不允许为空!");
					}
					//检查权限
					if(!AuthUtils.checkAuth(gncz, objId, yhdm)){
						return new JSONDataResult("保存失败:记录"+objId+"不存在或用户对此记录没有“"+gncz.getCaoZuoMC()+"”权限！");
					}
					//调用原始操作对应的方法取得初始对象
					EntityI obj = (EntityI)DataUtils.getObjectByGNCZ(gncz, objId, yhdm);
					objs.add(obj);
					oldObjs.add(obj.clone());
					
					dataArray.add(dataObj);
				}
			}

			ObjectResult saveResult = saveData(gncz, objs, dataArray, submitDataObj, sess, yhdm);
			if(!saveResult.isSuccess()){
				HibernateSessionFactory.closeSession(false);
				return new JSONDataResult(saveResult.getErrorMsg());
			}
			
			HibernateSessionFactory.closeSession(true);
			sess = null;
			
			//提交工作流
			if(gncz.getGongNeng().getShiFouLCGN().booleanValue()==true){
				JSONDataResult taskResult = completeTask(gncz, objs, oldObjs, iscomplete, yhdm);
				if(!taskResult.isSuccess() || taskResult.getTotalCount() == 0){
					return new JSONDataResult("数据保存成功，工作流执行失败："+taskResult.getErrorMsg());
				}else if(taskResult.getTotalCount() < objs.size()){
					return new JSONDataResult("数据保存成功，部分工作流执行失败：成功"+taskResult.getTotalCount()+"条/共"+objs.size()+"条");
				}
			}
			//返回值
			JSONArray rows = new JSONArray();
			
			Map<JSONObject,EntityI> linkMap = (Map<JSONObject,EntityI>)saveResult.get("linkMap");
			Iterator<JSONObject> linkKeys = linkMap.keySet().iterator();
			while(linkKeys.hasNext()){
				JSONObject keyObj = linkKeys.next();
				EntityI val = linkMap.get(keyObj);
				
				JSONObject row = new JSONObject();
				row.put(val.pkName(), val.pkValue());
				row.put("_record_id", keyObj.get("_record_id"));
				rows.add(row);
			}
			ret = new JSONDataResult(rows,rows.size(),0,0,null);
			
		}catch (InvocationTargetException e){
			ret = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}catch (Exception e){
			ret = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	
	//保存实体类数据 并记录保存对象的主键值（仅新增和修改的对象主键值）
	private ObjectResult saveData(GongNengCZ gncz,List<EntityI> objs,JSONArray saveDataArray,JSONObject submitDataObj,Session sess,Integer yhdm) throws Exception{
			ShiTiLei stl = gncz.getGongNeng().getShiTiLei();
			
			//是否存在功能Action类
			//执行openGNDH 和execCZDH 指定的功能类和方法
			Class<?> gnActionClass = null;
			Object gnActionInstance = null;
			try{
				gnActionClass = Class.forName(gncz.getGongNeng().getGongNengClass());
				gnActionInstance = gnActionClass.newInstance();
			} catch (Exception e) {
			}
			String firstUpCZDH = gncz.getCaoZuoDH().replaceFirst(gncz.getCaoZuoDH().substring(0, 1),gncz.getCaoZuoDH().substring(0, 1).toUpperCase());
			//功能action类中 是否实现了prepareSave方法
			Method prepareSaveMethod = DataUtils.getPrepareMethod(gnActionClass,"prepare"+firstUpCZDH);
			if(prepareSaveMethod!=null){
				MethodResult prepareRet = (MethodResult)prepareSaveMethod.invoke(gnActionInstance, new Object[]{
					gncz.getGongNeng().getGongNengDH(),gncz.getCaoZuoDH(),objs,submitDataObj,yhdm
				});
				if(!prepareRet.isSuccess()){
					return new ObjectResult(prepareRet.getErrorMsg());
				}
			}
			//尝试将客户端传递来的变化 应用到对象
			Map<JSONObject,EntityI> linkMap = new HashMap<JSONObject,EntityI>();
			for(int i=0;i<objs.size();i++){
				EntityI obj = objs.get(i);
				JSONConvertUtils.JSONObject2object(stl, obj, saveDataArray.getJSONObject(i),linkMap);
			}
			
			//功能action类中 是否实现了beforeSave方法
			Method beforeSaveMethod = DataUtils.getBeforeMethod(gnActionClass,"before"+firstUpCZDH);
			if(beforeSaveMethod!=null){
				MethodResult beforeRet = (MethodResult)beforeSaveMethod.invoke(gnActionInstance, new Object[]{
						gncz.getGongNeng().getGongNengDH(),gncz.getCaoZuoDH(),objs,submitDataObj,yhdm
				});
				if(!beforeRet.isSuccess()){
					return new ObjectResult(beforeRet.getErrorMsg());
				}
			}
			
			YongHu yh = null;
			if(yhdm != null ){
				yh = (YongHu)sess.load(YongHu.class, yhdm);
			}
			//记录必要的业务信息
			for(EntityI entityI:objs){
				if(entityI instanceof BusinessI){
					//为业务对象的业务字段赋值
					BusinessI p = (BusinessI)entityI;
					if(p.pkValue() == null){
						//新增对象  保存的时候 为没有初值的业务字段赋值
						if(p.getSuoYouZhe()==null && yh!=null) p.setSuoYouZhe(yh);
						if(p.getSuoShuBM()==null && yh!=null) p.setSuoShuBM(yh.getBuMen());
						if(p.getLuRuRen()==null && yh!=null){ p.setLuRuRen(yh.getYongHuMC()); }else {p.setLuRuRen("匿名");}
						if(p.getLuRuRQ()==null) p.setLuRuRQ(Calendar.getInstance().getTime());
					}else{
						//编辑对象
						if(p.getLuRuRen()==null && yh!=null){ p.setXiuGaiRen(yh.getYongHuMC()); }else {p.setXiuGaiRen("匿名");}
						p.setXiuGaiRQ(Calendar.getInstance().getTime());
					}
				}
			}
			
			//功能action类中 是否实现了onXXX方法
			Method onSaveMethod = DataUtils.getOnMethod(gnActionClass,"on"+firstUpCZDH);
			if(onSaveMethod!=null){
				MethodResult onRet = (MethodResult)onSaveMethod.invoke(gnActionInstance, new Object[]{
					gncz.getGongNeng().getGongNengDH(),gncz.getCaoZuoDH(),objs,submitDataObj,yhdm
				});
				if(!onRet.isSuccess()){
					return new ObjectResult(onRet.getErrorMsg());
				}
			}else{
				//保存新增和修改的数据
				for(EntityI entityI:objs){
					sess.saveOrUpdate(entityI);
				}
				//删除提交来的数据
				if(submitDataObj.containsKey("deleted")){
					JSONArray deletedDataArray = submitDataObj.getJSONArray("deleted");
					for(int i=0;i<deletedDataArray.size();i++){
						Integer deletedPkValue = deletedDataArray.getJSONObject(i).getInt(gncz.getGongNeng().getShiTiLei().getZhuJianLie());
						//调用原始操作对应的方法取得初始对象
						EntityI deletedObj = (EntityI)DataUtils.getObjectByGNCZ(gncz,deletedPkValue,yhdm);
						sess.delete(deletedObj);
					}
				}
			}
			//功能action类中 是否实现了afterSave方法
			Method afterSaveMethod = DataUtils.getAfterMethod(gnActionClass,"after"+firstUpCZDH);
			if(afterSaveMethod!=null){
				MethodResult afterRet = (MethodResult)afterSaveMethod.invoke(gnActionInstance, new Object[]{
						gncz.getGongNeng().getGongNengDH(),gncz.getCaoZuoDH(),objs,submitDataObj,yhdm
				});
				if(!afterRet.isSuccess()){
					return new ObjectResult(afterRet.getErrorMsg());
				}
			}
			//全部处理成功 
			return new ObjectResult("linkMap",linkMap);
	}
	
	
	private JSONDataResult completeTask(GongNengCZ gncz,List<EntityI> objs,List<EntityI> oldObjs,boolean isComplete, Integer yhdm){
		JSONDataResult taskRet = null;
		try {
			JSONArray taskRows = new JSONArray();

			Session sess = HibernateSessionFactory.getSession();
			YongHu yh = (YongHu)sess.load(YongHu.class,yhdm);
			
			for(int jj=0;jj<objs.size();jj++){
				BusinessI newBusiObj = (BusinessI)objs.get(jj);
				sess.refresh(newBusiObj);
				JSONObject taskRow = new JSONObject();
				taskRow.put(gncz.getGongNeng().getShiTiLei().getZhuJianLie(), newBusiObj.pkValue());
				//
				try {
					if(newBusiObj.getProcessInstanceId()==null){
						TaskUtils.startProcessInstance(newBusiObj,(BusinessI)oldObjs.get(jj), null, gncz, yh, isComplete);
					}else if(isComplete){
						TaskUtils.completeTask(newBusiObj,(BusinessI)oldObjs.get(jj), gncz, yh,true);
					}else{
						TaskUtils.claimTask(newBusiObj,(BusinessI)oldObjs.get(jj), gncz, yh);
					}
					//每个流程提交一次
					HibernateSessionFactory.closeSession(true);
					sess = null;
					taskRow.put("success", true);
				} catch (Exception e) {
					if(sess!=null){
						HibernateSessionFactory.closeSession(false);
						sess = null;
					}
					taskRow.put("success", false);
					taskRow.put("errorInfo", e.getMessage());
					e.printStackTrace();
				}finally{
					if(sess!=null){
						HibernateSessionFactory.closeSession(true);
						sess = null;
					}
				}
				taskRows.add(taskRow);
				sess = HibernateSessionFactory.getSession();
			}
			taskRet = new JSONDataResult(taskRows,taskRows.size(),0,0,null);
		} catch (HibernateException e) {
			taskRet = new JSONDataResult(e.getMessage());
			e.printStackTrace();
		}
		return taskRet;
	}
	


}

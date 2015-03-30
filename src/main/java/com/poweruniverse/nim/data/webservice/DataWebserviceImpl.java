package com.poweruniverse.nim.data.webservice;


import java.lang.reflect.InvocationTargetException;
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

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.bean.BaseJavaDatasource;
import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.message.JSONDataResult;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.utils.NimJSONArray;
import com.poweruniverse.nim.base.utils.NimJSONObject;
import com.poweruniverse.nim.baseClass.BasePlateformWebservice;
import com.poweruniverse.nim.data.action.AfterAction;
import com.poweruniverse.nim.data.action.BeforeAction;
import com.poweruniverse.nim.data.action.LoadAction;
import com.poweruniverse.nim.data.action.OnAction;
import com.poweruniverse.nim.data.action.PrepareAction;
import com.poweruniverse.nim.data.entity.sys.GongNeng;
import com.poweruniverse.nim.data.entity.sys.GongNengCZ;
import com.poweruniverse.nim.data.entity.sys.ShiTiLei;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.entity.sys.ZiDuan;
import com.poweruniverse.nim.data.entity.sys.ZiDuanLX;
import com.poweruniverse.nim.data.entity.sys.base.BusinessI;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
import com.poweruniverse.nim.data.service.utils.AuthUtils;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;
import com.poweruniverse.nim.data.service.utils.NativeSQLOrder;
import com.poweruniverse.nim.data.service.utils.TaskUtils;

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
					if(ZiDuanLX.isObjectType(zd.getZiDuanLX().getZiDuanLXDH()) || ZiDuanLX.isSetType(zd.getZiDuanLX().getZiDuanLXDH())){
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
			NimJSONArray rows = JSONConvertUtils.Entities2JSONArray(dataStl,objs,fieldJsonArray);
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
			YongHu yh = null;
			if(yhdm!=null){
				yh = (YongHu)sess.load(YongHu.class, yhdm);
			}
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
					
					if(ZiDuanLX.isObjectType(zd.getZiDuanLX().getZiDuanLXDH()) || ZiDuanLX.isSetType(zd.getZiDuanLX().getZiDuanLXDH())){
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
			Map<String,Object> listResult = DataUtils.getListByGN(gongNengDH,caoZuoDH,start,limit,filters,sorts,yhdm);
			@SuppressWarnings("unchecked")
			List<EntityI> objs =  (List<EntityI>)listResult.get("objs");
		
			//允许在list之后  触发可能存在的loadAction  对取得的结果进行处理
			if(gncz.getLoadAction()!=null){
				Class<?> actionClass = Class.forName(gncz.getLoadAction());
				LoadAction loadAction = (LoadAction)actionClass.newInstance();
				
				for(EntityI obj:objs){
					JSONMessageResult loadActionResult = loadAction.invoke(yh, gncz, (EntityI)obj);
					if(!loadActionResult.isSuccess()){
						return new JSONDataResult(loadActionResult.getErrorMsg());
					}
				}
			}
			
			if(result==null){
				NimJSONArray rows = JSONConvertUtils.Entities2JSONArray(gn.getShiTiLei(), objs, fieldJsonArray);
				result = new JSONDataResult(rows, (Integer)listResult.get("totalCount"), start, limit,meta);
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
//			Object obj = sess.load(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName(), id);
			Object obj =  DataUtils.getObjectByGNCZ(gncz, id, yhdm);
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
					
					if(ZiDuanLX.isObjectType(zd.getZiDuanLX().getZiDuanLXDH()) || ZiDuanLX.isSetType(zd.getZiDuanLX().getZiDuanLXDH())){
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
			NimJSONObject row = JSONConvertUtils.Entity2JSONObject(gncz.getGongNeng().getShiTiLei(),obj,fieldJsonArray);
			
			
			NimJSONArray rows= new NimJSONArray();
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
			NimJSONArray zdsArray = JSONConvertUtils.Entities2JSONArray(stl,zds,fieldJsonArray);
			
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
					
					if(ZiDuanLX.isObjectType(zd.getZiDuanLX().getZiDuanLXDH()) || ZiDuanLX.isSetType(zd.getZiDuanLX().getZiDuanLXDH())){
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
			NimJSONObject row = JSONConvertUtils.Entity2JSONObject(stl,obj,fieldJsonArray);
			
			
			NimJSONArray rows= new NimJSONArray();
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
    		
    		NimJSONArray zdArray = new NimJSONArray();
    		
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
			NimJSONArray rows =  (NimJSONArray)listResult.get("rows");
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
			ret = new JSONDataResult(new NimJSONArray(jsonData.getJSONArray("rows")), jsonData.getInt("totalCount"), start, limit,null);
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
			NimJSONArray rows = null;
			if(qRsult.getBoolean("success")){
				totalCount = qRsult.getInt("totalCount");
				rows = new NimJSONArray(qRsult.getJSONArray("data"));
			}else{
				rows = new NimJSONArray();
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
			YongHu yh = null;
			if(yongHuDM!=null){
				yh = (YongHu)sess.load(YongHu.class, yongHuDM);
			}
			//
			NimJSONArray dataArray = NimJSONArray.fromObject(submitData);

			//取得事件对象
			BeforeAction beforeAction = null;
			OnAction onAction = null;
			AfterAction afterAction = null;
			
			if(gncz.getBeforeAction()!=null){
				try {
					beforeAction = (BeforeAction)Class.forName(gncz.getBeforeAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的before事件处理类("+gncz.getBeforeAction()+")不存在！");
				}
			}
			if(gncz.getOnAction()!=null){
				try {
					onAction = (OnAction)Class.forName(gncz.getOnAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的on事件处理类("+gncz.getOnAction()+")不存在！");
				}
				
			}
			if(gncz.getAfterAction()!=null){
				try {
					afterAction = (AfterAction)Class.forName(gncz.getAfterAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的after事件处理类("+gncz.getAfterAction()+")不存在！");
				}
			}
			
				
			for(int i=0;i<dataArray.size();i++){
				NimJSONObject dataObj = dataArray.getJSONObject(i);
				Integer id = dataObj.getInt(gncz.getGongNeng().getShiTiLei().getZhuJianLie());
				
				if(!AuthUtils.checkAuth(gncz, id, yongHuDM)){
					return new JSONDataResult("记录("+gongNengDH+"."+caoZuoDH+"."+id+")不存在或用户对此记录没有操作权限！");
				}
				//调用原始操作对应的方法取得初始对象
				EntityI entityI = (EntityI)DataUtils.getObjectByGNCZ(gncz,id,yongHuDM);
				if(gncz.getGongNeng().getShiTiLei().getShiFouYWB()){
					//为业务对象的业务字段赋值
					BusinessI p = (BusinessI)entityI;
					p.setXiuGaiRen(yh.getYongHuMC());
					p.setXiuGaiRQ(Calendar.getInstance().getTime());
				}
				//检查功能action类中 是否实现了before方法
				if(beforeAction!=null){
					JSONMessageResult beforeActionResult = beforeAction.invoke(yh, gncz, entityI,null);
					if(!beforeActionResult.isSuccess()){
						return new JSONDataResult(beforeActionResult.getErrorMsg());
					}
				}
				
				if(onAction!=null){
					//执行自定义代码
					JSONMessageResult onActionResult = onAction.invoke(yh, gncz, entityI,null);
					if(!onActionResult.isSuccess()){
						return new JSONDataResult(onActionResult.getErrorMsg());
					}
				}else if("delete".equals(caoZuoDH) && gncz.getGongNeng().getShiTiLei().getShiFouYWB()){
					//业务表 标记为删除
					BusinessI busiI = (BusinessI)entityI;
					busiI.setRelaShanChuZT(true);
					busiI.setProcessInstanceId(null);
					busiI.setProcessInstanceEnded(false);
					busiI.setProcessInstanceTerminated(false);
					sess.update(busiI);
				}else if("delete".equals(caoZuoDH) && !gncz.getGongNeng().getShiTiLei().getShiFouYWB()){
					//非业务表 删除数据
					sess.delete(entityI);
				}
				
				if(afterAction!=null){
					JSONMessageResult afterActionResult = afterAction.invoke(yh, gncz, entityI,null);
					if(!afterActionResult.isSuccess()){
						return new JSONDataResult(afterActionResult.getErrorMsg());
					}
				}
			}
			//是否成功完成 且为流程功能 
			if(gncz.getGongNeng().getShiFouLCGN()==true){
				//数据库先提交 重新取得数据库连接
				HibernateSessionFactory.closeSession(true);
				sess = HibernateSessionFactory.getSession();
				//流程(这样批量执行的 不会是启动流程)
				for(int i=0;i<dataArray.size();i++){
					NimJSONObject dataObj = dataArray.getJSONObject(i);
					Integer id = dataObj.getInt(gncz.getGongNeng().getShiTiLei().getZhuJianLie());
					if("delete".equals(caoZuoDH)){
						//需要删除流程实例
						TaskUtils.removeProcessInstance(gncz.getGongNeng(), id, true);
					}else{
						//需要执行流程任务
						BusinessI busiObj = (BusinessI)DataUtils.getObjectByGNCZ(gncz,id,yongHuDM);
						TaskUtils.completeTask(busiObj,null, gncz, yh,false);
					}
				}
			}
			ret = new JSONDataResult(dataArray,dataArray.size(),0,0,null);
		
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
			
			//取得事件对象
			PrepareAction prepareAction = null;
			BeforeAction beforeAction = null;
			OnAction onAction = null;
			AfterAction afterAction = null;
			
			if(gncz.getPrepareAction()!=null){
				try {
					prepareAction = (PrepareAction)Class.forName(gncz.getPrepareAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的prepare事件处理类("+gncz.getPrepareAction()+")不存在！");
				}
			}
			if(gncz.getBeforeAction()!=null){
				try {
					beforeAction = (BeforeAction)Class.forName(gncz.getBeforeAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的before事件处理类("+gncz.getBeforeAction()+")不存在！");
				}
			}
			if(gncz.getOnAction()!=null){
				try {
					onAction = (OnAction)Class.forName(gncz.getOnAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的on事件处理类("+gncz.getOnAction()+")不存在！");
				}
			
			}
			if(gncz.getAfterAction()!=null){
				try {
					afterAction = (AfterAction)Class.forName(gncz.getAfterAction()).newInstance();
				} catch (ClassNotFoundException e) {
					return new JSONDataResult("功能操作("+gongNengDH+"."+caoZuoDH+")的after事件处理类("+gncz.getAfterAction()+")不存在！");
				}
			}
			
			//记录json对象和entity对象之间的关联
			Map<JSONObject,EntityI> linkMap = new HashMap<JSONObject,EntityI>();
			//记录json对象和old entity对象之间的关联
			Map<JSONObject,EntityI> oldObjectMap = new HashMap<JSONObject,EntityI>();
			//用户信息
			Integer yhdm = this.getYongHuDM(wsContext,false);
			YongHu yh = null;
			if(yhdm != null ){
				yh = (YongHu)sess.load(YongHu.class, yhdm);
			}
			
			ShiTiLei stl = gncz.getGongNeng().getShiTiLei();
			JSONObject submitDataObj = JSONObject.fromObject(submitData);
			//当前处理的对象
			List<EntityI> objs = new ArrayList<EntityI>();
			
			//删除提交来的数据
			if(submitDataObj.containsKey("deleted")){
				JSONArray deletedDataArray = submitDataObj.getJSONArray("deleted");
				for(int i=0;i<deletedDataArray.size();i++){
					Integer deletedPkValue = deletedDataArray.getJSONObject(i).getInt(gncz.getGongNeng().getShiTiLei().getZhuJianLie());
					EntityI entityI = (EntityI)DataUtils.getObjectByGNCZ(gncz, deletedPkValue, yhdm);
					
					//检查功能action类中 是否实现了before方法
					if(beforeAction!=null){
						JSONMessageResult beforeActionResult = beforeAction.invoke(yh, gncz, entityI,null);
						if(!beforeActionResult.isSuccess()){
							return new JSONDataResult(beforeActionResult.getErrorMsg());
						}
					}
					//调用原始操作对应的方法取得初始对象
					if(onAction!=null){
						//执行自定义代码
						JSONMessageResult onActionResult = onAction.invoke(yh, gncz, entityI,null);
						if(!onActionResult.isSuccess()){
							return new JSONDataResult(onActionResult.getErrorMsg());
						}
					}else if(gncz.getGongNeng().getShiTiLei().getShiFouYWB()){
						//业务表 标记为删除
						BusinessI busiI = (BusinessI)entityI;
						busiI.setRelaShanChuZT(true);
						busiI.setProcessInstanceId(null);
						busiI.setProcessInstanceEnded(false);
						busiI.setProcessInstanceTerminated(false);
						sess.update(busiI);
					}else {
						//非业务表 删除数据
						sess.delete(entityI);
					}
					
					if(afterAction!=null){
						JSONMessageResult afterActionResult = afterAction.invoke(yh, gncz, entityI,null);
						if(!afterActionResult.isSuccess()){
							return new JSONDataResult(afterActionResult.getErrorMsg());
						}
					}
				}
			}
			//新增的数据
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
					EntityI entityI = (EntityI)DataUtils.getObjectByGNCZ(gncz, null, yhdm);
					objs.add(entityI);
					oldObjectMap.put(dataObj,(EntityI)entityI.clone());
					
					dataObj.put("_entity", entityI);
					//保存数据
					if(prepareAction!=null){
						JSONMessageResult prepareActionResult = prepareAction.invoke(yh, gncz, entityI, dataObj);
						if(!prepareActionResult.isSuccess()){
							return new JSONDataResult(prepareActionResult.getErrorMsg());
						}
					}
					//尝试将客户端传递来的变化 应用到对象
					JSONConvertUtils.JSON2Entity(stl, entityI, dataObj,linkMap);
					//为业务对象的业务字段赋值
					if(entityI instanceof BusinessI){
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
					if(beforeAction!=null){
						JSONMessageResult beforeActionResult = beforeAction.invoke(yh, gncz, entityI, dataObj);
						if(!beforeActionResult.isSuccess()){
							return new JSONDataResult(beforeActionResult.getErrorMsg());
						}
					}
					
					//功能action类中 是否实现了onXXX方法
					if(onAction!=null){
						JSONMessageResult onActionResult = onAction.invoke(yh, gncz, entityI, dataObj);
						if(!onActionResult.isSuccess()){
							return new JSONDataResult(onActionResult.getErrorMsg());
						}
					}else{
						//保存新增和修改的数据
						sess.save(entityI);
					}
					
					if(afterAction!=null){
						JSONMessageResult afterActionResult = afterAction.invoke(yh, gncz, entityI, dataObj);
						if(!afterActionResult.isSuccess()){
							return new JSONDataResult(afterActionResult.getErrorMsg());
						}
					}
				}
			}
			//修改的数据
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
					EntityI entityI = (EntityI)DataUtils.getObjectByGNCZ(gncz, objId, yhdm);
					objs.add(entityI);
					oldObjectMap.put(dataObj,(EntityI)entityI.clone());
					
					//尝试将客户端传递来的变化 应用到对象
					JSONConvertUtils.JSON2Entity(stl, entityI, dataObj,linkMap);
					//为业务对象的业务字段赋值
					if(entityI instanceof BusinessI){
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
					if(beforeAction!=null){
						JSONMessageResult beforeActionResult = beforeAction.invoke(yh, gncz, entityI, dataObj);
						if(!beforeActionResult.isSuccess()){
							return new JSONDataResult(beforeActionResult.getErrorMsg());
						}
					}
					
					//功能action类中 是否实现了onXXX方法
					if(onAction!=null){
						JSONMessageResult onActionResult = onAction.invoke(yh, gncz, entityI, dataObj);
						if(!onActionResult.isSuccess()){
							return new JSONDataResult(onActionResult.getErrorMsg());
						}
					}else{
						//保存新增和修改的数据
						sess.saveOrUpdate(entityI);
					}
					
					if(afterAction!=null){
						JSONMessageResult afterActionResult = afterAction.invoke(yh, gncz, entityI, dataObj);
						if(!afterActionResult.isSuccess()){
							return new JSONDataResult(afterActionResult.getErrorMsg());
						}
					}
				}
			}
			
			//先提交保存结果 然后重新取得连接
			HibernateSessionFactory.closeSession(true);
			sess = HibernateSessionFactory.getSession();
			
			//提交工作流
			if(gncz.getGongNeng().getShiFouLCGN()){
				//删除流程
				if(submitDataObj.containsKey("deleted")){
					JSONArray deletedDataArray = submitDataObj.getJSONArray("deleted");
					for(int i=0;i<deletedDataArray.size();i++){
						Integer deletedPkValue = deletedDataArray.getJSONObject(i).getInt(gncz.getGongNeng().getShiTiLei().getZhuJianLie());
						//需要删除流程实例 和相关的流程检视
						TaskUtils.removeProcessInstance(gncz.getGongNeng(), deletedPkValue, true);
					}
				}
				//启动流程
				if(submitDataObj.containsKey("inserted")){
					JSONArray insertedDataArray = submitDataObj.getJSONArray("inserted");
					for(int i=0;i<insertedDataArray.size();i++){
						JSONObject jsonObj = insertedDataArray.getJSONObject(i);
						BusinessI newObj = (BusinessI)linkMap.get(jsonObj);
						BusinessI oldObj = (BusinessI)linkMap.get(jsonObj);
						TaskUtils.completeTask(newObj,oldObj, gncz, yh,iscomplete);
					}
				}
				//提交任务
				if(submitDataObj.containsKey("modified")){
					JSONArray modifiedDataArray = submitDataObj.getJSONArray("modified");
					for(int i=0;i<modifiedDataArray.size();i++){
						JSONObject jsonObj = modifiedDataArray.getJSONObject(i);
						BusinessI newObj = (BusinessI)linkMap.get(jsonObj);
						BusinessI oldObj = (BusinessI)linkMap.get(jsonObj);
						TaskUtils.completeTask(newObj,oldObj, gncz, yh,iscomplete);
					}
				}
			}
			
			//返回值
			NimJSONArray rows = new NimJSONArray();
			
			Iterator<JSONObject> linkKeys = linkMap.keySet().iterator();
			while(linkKeys.hasNext()){
				JSONObject keyObj = linkKeys.next();
				EntityI val = linkMap.get(keyObj);
				
				NimJSONObject row = new NimJSONObject();
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
	
	
	
//	private JSONDataResult completeTask(GongNengCZ gncz,List<EntityI> objs,List<EntityI> oldObjs,boolean isComplete, Integer yhdm){
//		JSONDataResult taskRet = null;
//		try {
//			JSONArray taskRows = new JSONArray();
//
//			Session sess = HibernateSessionFactory.getSession();
//			YongHu yh = (YongHu)sess.load(YongHu.class,yhdm);
//			
//			for(int jj=0;jj<objs.size();jj++){
//				BusinessI newBusiObj = (BusinessI)objs.get(jj);
//				sess.refresh(newBusiObj);
//				JSONObject taskRow = new JSONObject();
//				taskRow.put(gncz.getGongNeng().getShiTiLei().getZhuJianLie(), newBusiObj.pkValue());
//				//
//				try {
//					if(newBusiObj.getProcessInstanceId()==null){
//						TaskUtils.startProcessInstance(newBusiObj,(BusinessI)oldObjs.get(jj), null, gncz, yh, isComplete);
//					}else if(isComplete){
//						TaskUtils.completeTask(newBusiObj,(BusinessI)oldObjs.get(jj), gncz, yh,true);
//					}else{
//						TaskUtils.claimTask(newBusiObj,(BusinessI)oldObjs.get(jj), gncz, yh);
//					}
//					//每个流程提交一次
//					HibernateSessionFactory.closeSession(true);
//					sess = null;
//					taskRow.put("success", true);
//				} catch (Exception e) {
//					if(sess!=null){
//						HibernateSessionFactory.closeSession(false);
//						sess = null;
//					}
//					taskRow.put("success", false);
//					taskRow.put("errorInfo", e.getMessage());
//					e.printStackTrace();
//				}finally{
//					if(sess!=null){
//						HibernateSessionFactory.closeSession(true);
//						sess = null;
//					}
//				}
//				taskRows.add(taskRow);
//				sess = HibernateSessionFactory.getSession();
//			}
//			taskRet = new JSONDataResult(taskRows,taskRows.size(),0,0,null);
//		} catch (HibernateException e) {
//			taskRet = new JSONDataResult(e.getMessage());
//			e.printStackTrace();
//		}
//		return taskRet;
//	}
	


}

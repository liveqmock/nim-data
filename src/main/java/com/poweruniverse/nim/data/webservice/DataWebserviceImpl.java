package com.poweruniverse.nim.data.webservice;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import com.poweruniverse.nim.base.message.JSONDataResult;
import com.poweruniverse.nim.base.message.Result;
import com.poweruniverse.nim.base.webservice.BasePlateformWebservice;
import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.ZiDuan;
import com.poweruniverse.nim.data.entity.base.EntityI;
import com.poweruniverse.nim.data.service.utils.AuthUtils;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.SystemSessionFactory;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

@WebService
public class DataWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	

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
	public JSONDataResult listGnData(
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
			
//			String gongNengDH = params.getString("gongNengDH");
//			String caoZuoDH = params.getString("caoZuoDH");
//			Integer start = params.getInt("start");
//			Integer limit = params.getInt("limit");
//			String fields = params.getString("fields");
//			String filters = params.getString("filters");
//			String sorts = params.getString("sorts");
			
			//检查对应的功能操作是否存在
			
			GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
					.createAlias("gongNeng", "gncz_gn")
					.add(Restrictions.eq("gncz_gn.gongNengDH",gongNengDH))
					.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
			if(gncz==null){
				return new JSONDataResult("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
			}
			
			sess = SystemSessionFactory.getSession();

			boolean needAuth = gncz.getKeYiSQ();//需要授权？
			//需要授权的功能操作 必须提供用户名、密码用于验证用户身份
			Integer yhdm = this.getYongHuDM(wsContext,needAuth);
			//这里
			GongNeng gn = gncz.getGongNeng();
			Method mtd = null;
			Class<?> cls = null;
			//允许在list之前 触发可能存在的beforeList事件 
			Result beforeListResult = null;
			try {
				cls = Class.forName(gn.getGongNengClass());
				mtd = cls.getMethod("beforeList",new Class[]{String.class,Integer.class,Integer.class,String.class,String.class,String.class,Integer.class});
				beforeListResult = (Result)mtd.invoke(cls.newInstance(), new Object[]{caoZuoDH,start,limit,fields,filters,sorts,yhdm});
			} catch (Exception e) {
			}
			
			if(!beforeListResult.isSuccess()){
				result = new JSONDataResult(beforeListResult.getErrorMsg()) ;
			}else{
				//
				Map<String,Object> listResult = DataUtils.getListByGN(gongNengDH,caoZuoDH,start,limit,filters,sorts,yhdm);
				List<EntityI> objs =  (List<EntityI>)listResult.get("objs");
			
				//允许在list之后  触发可能存在的afterList事件  对取得的结果集进行处理
				try {
					cls = Class.forName(gn.getGongNengClass());
					mtd = cls.getMethod("afterList",new Class[]{String.class,Integer.class,Integer.class,String.class,String.class,String.class,List.class,Integer.class});
					JSONDataResult afterListResult = (JSONDataResult)mtd.invoke(cls.newInstance(), new Object[]{caoZuoDH,start,limit,fields,filters,sorts,objs,yhdm});
					if(afterListResult.isSuccess()){
						result = afterListResult;
					}
				} catch (Exception e) {
				}
				
				if(result==null){
					JSONArray rows = JSONConvertUtils.objectList2JSONArray(gn.getShiTiLei(), objs, JSONArray.fromObject(fields));
					result = new JSONDataResult(rows, (Integer)listResult.get("totalCount"), start, limit);
				}
			}
			
			//提交 并关闭session
			SystemSessionFactory.closeSession(true);
			sess = null;
		}catch (InvocationTargetException e){
			result = new JSONDataResult(e.getTargetException().getMessage());
			e.printStackTrace();
			if (sess != null) {
				SystemSessionFactory.closeSession(false);
			}
		} catch (Exception e) {
			result = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				SystemSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				SystemSessionFactory.closeSession(true);
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
	public JSONDataResult loadGnData(
			@WebParam(name="gongNengDH") String gongNengDH,
			@WebParam(name="caoZuoDH") String caoZuoDH,
			@WebParam(name="id") Integer id,
			@WebParam(name="fields") String fields){
//		String gongNengDH = params.getString("gongNengDH");
//		String caoZuoDH = params.getString("caoZuoDH");
		
		JSONDataResult result = null;
		Session sess = null;
		try {
			sess = SystemSessionFactory.getSession();
			
			//取得数据
			GongNengCZ gncz = GongNeng.getGongNengByDH(gongNengDH).getCaoZuoByDH(caoZuoDH);
			if(gncz==null){
				throw new Exception("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
			}
			
			YongHu yh  = null;
			Integer yhdm = this.getYongHuDM(wsContext,gncz.getKeYiSQ());
			if(yhdm!=null){
				yh = (YongHu)sess.load(YongHu.class,yhdm);
			}
			
			if(gncz.getDuiXiangXG() && id==null){
				throw new Exception("功能操作:"+gongNengDH+"."+caoZuoDH+"为对象相关的操作，必须提供id参数！");
			}
			
			//用这个openGNDH、openCZDH确定和检查权限
			if(!AuthUtils.checkAuth(gongNengDH,caoZuoDH, id, yh)){
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
			}
			
			//将数据转换为json格式 添加到页面中
			JSONObject row = JSONConvertUtils.object2JSONObject(gncz.getGongNeng().getShiTiLei(),obj,fieldJsonArray);
			
			
			JSONArray rows= new JSONArray();
			rows.add(row);
			
			result = new JSONDataResult(rows, 1, 0, 0);
		//
		} catch (Exception e) {
			result = new JSONDataResult(e.getMessage());
			e.printStackTrace();
			if (sess != null) {
				SystemSessionFactory.closeSession(false);
			}
		}finally{
			if (sess != null) {
				SystemSessionFactory.closeSession(true);
			}
		}
		return result;
	}
	
}

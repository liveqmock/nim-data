package com.poweruniverse.nim.data.service.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.dom4j.Element;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.message.JSONDataResult;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.message.Result;
import com.poweruniverse.nim.base.utils.FreemarkerUtils;
import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.ZiDuan;
import com.poweruniverse.nim.data.entity.base.BusinessI;
import com.poweruniverse.nim.data.entity.base.EntityI;
import com.poweruniverse.nim.data.pageParser.DatasourceElParser;
import com.poweruniverse.oim.activiti.ClearProcessInstanceBussinessKeyCmd;

public class DataUtils {
	public static SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sbf = new SimpleDateFormat("MM-dd");

	/**
	 * 取功能数据对象
	 * 只能取到有权限的数据对象
	 */
	public static Object getObjectByGNCZ(String gongNengDH,String caoZuoDH,Integer id,Integer yongHuDM) throws Exception{
		
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		//取得数据
		GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
				.createAlias("gongNeng", "gncz_gn")
				.add(Restrictions.eq("gncz_gn.gongNengDH",gongNengDH))
				.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
		if(gncz==null){
			throw new Exception("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
		}
		
		return getObjectByGNCZ(gncz, id, yongHuDM);
	}
	public static Object getObjectByGNCZ(GongNengCZ gncz,Integer id,Integer yongHuDM) throws Exception{
		Object obj = null;
		
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		//取得数据
		
		if(yongHuDM==null){
			throw new Exception("未登录或已超时，请重新登录！");
		}
		
		//用这个openGNDH、openCZDH确定和检查权限
		if(AuthUtils.checkAuth(gncz, id, yongHuDM)){
			//正常方式 原始对象
			if(gncz.getDuiXiangXG()){
				if(id==null){
					throw new Exception("使用对象相关的功能操作("+gncz.getGongNeng().getGongNengMC()+"."+gncz.getCaoZuoMC()+")必须提供id！");
				}else{
					obj = sess.load(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName(), id);
				}
			}else{
				Class<?> stlClass = Class.forName(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName());
				obj = stlClass.newInstance();
			}
			
			if(gncz.getGongNeng().getGongNengClass()!=null){
				try {
					Class<?> gongNengClass = Class.forName(gncz.getGongNeng().getGongNengClass());
					Method getFormObjectMethod = gongNengClass.getMethod("getFormObject",new Class[]{String.class,Integer.class,Integer.class,Object.class,GongNengCZ.class});
					obj = getFormObjectMethod.invoke(gongNengClass.newInstance(), new Object[]{gncz.getCaoZuoDH(),id,yongHuDM,obj,gncz});
				} catch (NoSuchMethodException e) {
				} catch (ClassNotFoundException e1) {
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}else{
			throw new Exception("记录("+gncz.getGongNeng().getGongNengMC()+"."+gncz.getCaoZuoMC()+"."+id+")不存在或用户没有权限！");
		}
	
		return obj;
	}	
	/**
	 * 取功能数据对象
	 * 只能取到有权限的数据对象
	 */
	public static Object getObjectBySTL(String shiTiLeiDH,Integer id) throws Exception{
		
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);

		//取得数据
		ShiTiLei stl = (ShiTiLei)sess.createCriteria(ShiTiLei.class)
				.add(Restrictions.eq("shiTiLeiDH", shiTiLeiDH))
				.uniqueResult();
		if(stl==null){
			throw new Exception("实体类:"+shiTiLeiDH+"不存在！");
		}
		
		//用这个openGNDH、openCZDH确定和检查权限

		//正常方式 原始对象
		Object obj = sess.load(stl.getShiTiLeiClassName(), id);
		
		return obj;
	}
	
	/**
	 * 取功能数据对象
	 * 只能取到有权限的数据对象
	 */
	public static Object newObjectBySTL(ShiTiLei shiTiLei) throws Exception{
		//正常方式 原始对象
		Object obj = Class.forName(shiTiLei.getShiTiLeiClassName()).newInstance();
		return obj;
	}

	public static Object newObjectBySTLDH(String shiTiLeiDH) throws Exception{
		//取得数据
		ShiTiLei stl = (ShiTiLei)HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory).createCriteria(ShiTiLei.class)
				.add(Restrictions.eq("shiTiLeiDH", shiTiLeiDH))
				.uniqueResult();
		if(stl==null){
			throw new Exception("实体类:"+shiTiLeiDH+"不存在！");
		}
		return newObjectBySTL(stl);
	}

	
	/**
	 * 取sql数据对象
	 * 与权限无关
	 */
	public static JSONObject getObjectBySQL(String sql,JSONArray fieldJsonArray) throws Exception{
		JSONObject row = new JSONObject();
		if(fieldJsonArray!=null && fieldJsonArray.size()>0){
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try{
	        	conn = HibernateSessionFactory.getConnection(HibernateSessionFactory.defaultSessionFactory);
	        	
        		pstmt = conn.prepareStatement(sql);
        		rs = pstmt.executeQuery();
        		//
        		ResultSetMetaData rsmd = rs.getMetaData();
        		if(fieldJsonArray.size()==1 && "...".equals(fieldJsonArray.get(0))){
        			fieldJsonArray = new JSONArray();
        			for(int i=1;i<=rsmd.getColumnCount();i++){
        				
        				JSONObject fieldDefObj = new JSONObject();
        				
	    	    		String fieldName = rsmd.getColumnName(i);
    	    			String columnType = rsmd.getColumnTypeName(i);
    	    			
    	    			String fieldType = columnType;
    	    			if("NUMBER".equals(columnType) && rsmd.getScale(i) >0){
    	    				fieldType = "double";
    	    			}else if("NUMBER".equals(columnType) && rsmd.getScale(i) == 0){
    	    				fieldType = "int";
    	    			}else if("VARCHAR2".equals(columnType)){
    	    				fieldType = "string";
    	    			}else if("DATE".equals(columnType)){
    	    				fieldType = "date";
    	    			}
    	    			fieldDefObj.put("name", fieldName);
    	    			fieldDefObj.put("fieldType", fieldType);
    	    			
    	    			fieldJsonArray.add(fieldDefObj);
        			}
        		}
	    		
	    	    if(rs.next()){
	    	    	//检查
	    	    	for(int i=0;i<fieldJsonArray.size();i++){
	    	    		String fieldName = null;
	    	    		Object value = null;
	    	    		String fieldType = "string";
	    	    		
		    			Object fieldDef = fieldJsonArray.get(i);
		    			if(fieldDef instanceof String){
		    				fieldName = (String)fieldDef;
		    			}else if (fieldDef instanceof JSONObject){
		    				JSONObject fieldDefObj = (JSONObject)fieldDef;
		    				fieldName = fieldDefObj.getString("name");
		    				if(fieldDefObj.has("fieldType")){
		    					fieldType = fieldDefObj.getString("fieldType");
		    				}
		    			}
	    	    		if("string".equals(fieldType)){
		    				value = rs.getString(fieldName);
		    			}else if("date".equals(fieldType)){
		    				Date vDate = rs.getDate(fieldName);
		    				value = vDate==null?null:dtf.format(vDate);
		    			}else if("int".equals(fieldType)){
		    				value = rs.getInt(fieldName);
		    			}else if("double".equals(fieldType)){
		    				value = rs.getDouble(fieldName);
		    			}else{
		    				value = rs.getObject(fieldName);
		    			}
	    	    		row.put(fieldName, value);
		    		}
	    	    }
	    	    rs.close();
	    	    rs = null;
			}catch (SQLException e) {
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
		}
		
		return row;
	}
	
	/**
	 * 取sql数据对象
	 * 与权限无关
	 */
	public static Map<String,Object> getListBySQL(String sql,int start,int limit ,JSONArray fieldJsonArray) throws Exception{
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		JSONArray rows = new JSONArray();
		int totalCount = 0;
		if(fieldJsonArray!=null && fieldJsonArray.size()>0){
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try{
	        	conn = HibernateSessionFactory.getConnection(HibernateSessionFactory.defaultSessionFactory);
	        	
	        	rs = conn.createStatement().executeQuery("select count(*) rowCount from ("+sql+") ");
	        	if(rs.next()){
	        		totalCount = rs.getInt(1);
	        	}
	        	rs.close();
	    	    rs = null;
	        	
	        	if(limit >0){
	        		sql = 	"select t2.* from ("+
	        				"	select t.*,rownum rn  from ( "+sql+") t where rownum <= "+(start+limit)+
						 	") t2"+
	        				" where t2.rn > "+start;
	        	}
        		pstmt = conn.prepareStatement(sql);
        		rs = pstmt.executeQuery();
        		//
        		ResultSetMetaData rsmd = rs.getMetaData();
        		if(fieldJsonArray.size()==1 && "...".equals(fieldJsonArray.get(0))){
        			fieldJsonArray = new JSONArray();
        			for(int i=1;i<=rsmd.getColumnCount();i++){
        				
        				JSONObject fieldDefObj = new JSONObject();
        				
	    	    		String fieldName = rsmd.getColumnName(i);
    	    			String columnType = rsmd.getColumnTypeName(i);
    	    			
    	    			String fieldType = columnType;
    	    			if("NUMBER".equals(columnType) && rsmd.getScale(i) >0){
    	    				fieldType = "double";
    	    			}else if("NUMBER".equals(columnType) && rsmd.getScale(i) == 0){
    	    				fieldType = "int";
    	    			}else if("VARCHAR2".equals(columnType)){
    	    				fieldType = "string";
    	    			}else if("DATE".equals(columnType)){
    	    				fieldType = "date";
    	    			}
    	    			fieldDefObj.put("name", fieldName);
    	    			fieldDefObj.put("fieldType", fieldType);
    	    			
    	    			fieldJsonArray.add(fieldDefObj);
        			}
        		}
	    		
	    	    while(rs.next()){
		    		JSONObject obj = new JSONObject();
	    	    	//检查
	    	    	for(int i=0;i<fieldJsonArray.size();i++){
	    	    		String fieldName = null;
	    	    		Object value = null;
	    	    		String fieldType = "string";
	    	    		
		    			Object fieldDef = fieldJsonArray.get(i);
		    			if(fieldDef instanceof String){
		    				fieldName = (String)fieldDef;
		    			}else if (fieldDef instanceof JSONObject){
		    				JSONObject fieldDefObj = (JSONObject)fieldDef;
		    				fieldName = fieldDefObj.getString("name");
		    				if(fieldDefObj.has("fieldType")){
		    					fieldType = fieldDefObj.getString("fieldType");
		    				}
		    			}
	    	    		if("string".equals(fieldType)){
		    				value = rs.getString(fieldName);
		    			}else if("date".equals(fieldType)){
		    				Date vDate = rs.getDate(fieldName);
		    				value = vDate==null?null:dtf.format(vDate);
		    			}else if("int".equals(fieldType)){
		    				value = rs.getInt(fieldName);
		    			}else if("double".equals(fieldType)){
		    				value = rs.getDouble(fieldName);
		    			}else{
		    				value = rs.getObject(fieldName);
		    			}
	    	    		obj.put(fieldName, value);
		    		}
	    	    	rows.add(obj);
	    	    }
	    	    rs.close();
	    	    rs = null;
			}catch (SQLException e) {
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
		}
		
		result.put("totalCount", totalCount);
		result.put("rows", rows);
		return result;
	}

		
	public static Map<String,Object> getListByGN(String gndh,String czdh,int start,int limit,String filterString,String sortString,Integer yhdm) 
			throws Exception{
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		Map<String,Object> result = new HashMap<String,Object>();
		int totalCount = 0;
		//功能实体类
		GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
				.createAlias("gongNeng", "gncz_gn")
				.add(Restrictions.eq("gncz_gn.gongNengDH",gndh))
				.add(Restrictions.eq("caoZuoDH", czdh)).uniqueResult();
		if(gncz==null){
			throw new Exception("功能操作:"+gndh+"."+czdh+"不存在！");
		}
		
		//用户对象
		YongHu yh = null;
		if(yhdm!=null){
			yh = (YongHu)sess.load(YongHu.class, yhdm);
		}else if(gncz.getKeYiSQ()){
			throw new Exception("未登录或已超时，请重新登录！");
		}

		Class<?> gnClass = Class.forName(gncz.getGongNeng().getShiTiLei().getShiTiLeiClassName());
		//基础查询语法
		Criteria criteria = sess.createCriteria(gnClass);
//		
		//取得用户权限 permits=null means all
		if(yh!=null){
			List<Permit> permits = QueryUtils.getPermitsByYHAuth(yh,gncz,true);
			Criterion c = QueryUtils.createCriterionByPermits(gncz.getGongNeng().getShiTiLei(),permits,yh);
			if(c!=null){
				criteria.add(c);
			}
		}else{
			//用户未登录 只允许使用无需授权的操作
			if(gncz.getKeYiSQ().booleanValue()){
				//需要授权的功能操作 不允许得到任何记录
				criteria.add(Restrictions.sqlRestriction("1<>1"));
			}
		}
		//传递来的过滤条件
		List<Query> queries = QueryUtils.createQueriesByJson(filterString);
		Criterion c = QueryUtils.createCriterionByQueries(gncz.getGongNeng().getShiTiLei(),queries,yh);
		if(c!=null){
			criteria.add(c);
		}
		//传递来的sort参数 
		HashMap<String, String> existsSortAlias = new HashMap<String, String>(); 
		if(sortString!=null && sortString.length()>0){
			JSONArray orderBys = JSONArray.fromObject(sortString); 
			JSONObject orderBy = null; 
			boolean isAsc;
			for(int i=0;i<orderBys.size();i++){
				orderBy = orderBys.getJSONObject(i);
				//操作类型
				isAsc = true;
				if(orderBy.has("dir") && orderBy.getString("dir").equals("DESC")){
					isAsc = false;
				}
				criteria.addOrder(getSortCriterion(orderBy.getString("property"),isAsc,existsSortAlias));
			}
		}else{
			criteria.addOrder(Order.asc(gncz.getGongNeng().getShiTiLei().getPaiXuLie()));
		}
		//添加alia
		if(existsSortAlias!=null && existsSortAlias.size()>0){
			Iterator<String> keys = existsSortAlias.keySet().iterator();
			String key = null;
			while(keys.hasNext()){
				key = keys.next();
				criteria.createAlias(key, existsSortAlias.get(key));
			}
		}
		//如果传递来了分页参数 
		List<EntityI> objs =null;
		if(limit>0){
			//加入分页参数
			criteria.setFirstResult(start); 
			criteria.setMaxResults(limit); 
			objs = criteria.list();
			//取结果总数
			criteria.setFirstResult(0); 
			criteria.setMaxResults(1); 
			Object sa = criteria.setProjection(Projections.rowCount())
					.uniqueResult();
			if(sa!=null){
				totalCount = ((Long)sa).intValue();
			}
			
		}else{
			objs = criteria.list();
			totalCount = objs.size();
		}
		
		result.put("objs", objs);
		result.put("totalCount", totalCount);
	
		return result;
	}
	
	
	public static Map<String,Object> getListBySTL(String stldh,int start,int limit,String filterString,String sortString) 
			throws Exception{
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		Map<String,Object> result = new HashMap<String,Object>();
		int totalCount = 0;
		//功能实体类
		ShiTiLei stl = (ShiTiLei)sess.createCriteria(ShiTiLei.class)
				.add(Restrictions.eq("shiTiLeiDH", stldh)).uniqueResult();
		if(stl==null){
			throw new Exception("实体类:"+stldh+"不存在！");
		}

		Class<?> gnClass = Class.forName(stl.getShiTiLeiClassName());
		//基础查询语法
		Criteria criteria = sess.createCriteria(gnClass);
//		
		//传递来的过滤条件
		List<Query> queries = QueryUtils.createQueriesByJson(filterString);
		Criterion c = QueryUtils.createCriterionByQueries(stl,queries,null);
		if(c!=null){
			criteria.add(c);
		}
		//传递来的sort参数 
		HashMap<String, String> existsSortAlias = new HashMap<String, String>(); 
		if(sortString!=null && sortString.length()>0){
			JSONArray orderBys = JSONArray.fromObject(sortString); 
			JSONObject orderBy = null; 
			boolean isAsc;
			for(int i=0;i<orderBys.size();i++){
				orderBy = orderBys.getJSONObject(i);
				//操作类型
				isAsc = true;
				if(orderBy.has("dir") && orderBy.getString("dir").equals("DESC")){
					isAsc = false;
				}
				criteria.addOrder(getSortCriterion(orderBy.getString("property"),isAsc,existsSortAlias));
			}
		}else{
			criteria.addOrder(Order.asc(stl.getPaiXuLie()));
		}
		//添加alia
		if(existsSortAlias!=null && existsSortAlias.size()>0){
			Iterator<String> keys = existsSortAlias.keySet().iterator();
			String key = null;
			while(keys.hasNext()){
				key = keys.next();
				criteria.createAlias(key, existsSortAlias.get(key));
			}
		}
		//如果传递来了分页参数 
		List<?> objs =null;
		if(limit>0){
			//加入分页参数
			criteria.setFirstResult(start); 
			criteria.setMaxResults(limit); 
			objs = criteria.list();
			//取结果总数
			criteria.setFirstResult(0); 
			criteria.setMaxResults(1); 
			Object sa = criteria.setProjection(Projections.rowCount())
					.uniqueResult();
			if(sa!=null){
				totalCount = ((Long)sa).intValue();
			}
			
		}else{
			objs = criteria.list();
			totalCount = objs.size();
		}
		
		result.put("objs", objs);
		result.put("start", start);
		result.put("limit", limit);
		result.put("totalCount", totalCount);
	
		return result;
	}
	
	//将一个字段的排序定义 转变为hibernate排序语法
	public static Order getSortCriterion(String propertyString,boolean isAsc,HashMap<String, String> existsAlias){
		Order order = null;
		String[] ziDuanArray = null;
		String finalProperty;
		String aliaName;
		String aliaName2;
		//最终使用的字段名称 
		finalProperty = propertyString;
		//如果是多重字段 需要创建别名 并重新设置finalProperty
		if(propertyString.indexOf(".")>=0){
			//如果是.id可以不考虑
			if(propertyString.indexOf(".id")>=0){
				propertyString = propertyString.substring(0,propertyString.indexOf(".id"));
			}
			//
			if(propertyString.indexOf(".")>=0){
				ziDuanArray = propertyString.split("\\.");
				propertyString = null;
				aliaName = null;
				aliaName2 = null;
				for (int i=0;i<ziDuanArray.length -1;i++){
					if(i==0){
						propertyString = ziDuanArray[0];
						aliaName = ziDuanArray[0];
					}else{
						propertyString += "."+ziDuanArray[i];
						aliaName += "_"+ziDuanArray[i];
					}
					if(!existsAlias.containsKey(propertyString) ){
						aliaName2 = aliaName+"_alia";
//								System.out.println("createAlias...key:"+propertyString+",alia:"+aliaName2);
//								criteria.createAlias(propertyString,aliaName2);
						existsAlias.put(propertyString,aliaName2);
					}else{
						aliaName2 = existsAlias.get(propertyString);
					}
				}
				finalProperty = aliaName2+"."+ziDuanArray[ziDuanArray.length -1];
			}
		}
		if(isAsc){
			order = Order.asc(finalProperty);
		}else{
			order = Order.desc(finalProperty);
		}
		return order;
	}
	
	
	/**
	 * 取待办数据集合
	 */
	public static JSONObject getToDoDataset(Element dataEl,JSONObject params,String filterString,String sortString,Integer yongHuDM) throws Exception{
		JSONObject result = new JSONObject();
		try {
			//待办数据的实体类代号
			String shiTiLeiDH= "SYS_LiuChengJS"; 
			ShiTiLei liuChengJS_STL = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			//当前用户
			int start = 0;
			String startString = dataEl.attributeValue("start");
			if(startString!=null){
				start = Integer.parseInt(startString);
			}
			
			int limit = 0;
			String limitString = dataEl.attributeValue("limit");
			if(limitString!=null){
				limit = Integer.parseInt(limitString);
			}
			result.put("start", start);
			result.put("limit", limit);
			
			boolean todoOnly = true;
			String todoOnlyString = dataEl.attributeValue("todoOnly");
			if(todoOnlyString!=null && todoOnlyString.equals("false")){
				todoOnly = false;
			}
			result.put("todoOnly", todoOnly);
			
			//循环检查 本次查询的功能范围
			JSONArray workflowArray = new JSONArray();
			
//			Map<String,Element> workflowElMap = new HashMap<String,Element>();
			String gnsString = "";
			Element workflowsEl = dataEl.element("workflows");
			if(workflowsEl!=null){
				List<?> workflowEls = workflowsEl.elements("workflow");
				for(int i=0;i<workflowEls.size();i++){
					Element workflowEl = (Element)workflowEls.get(i);
					String gndh = workflowEl.attributeValue("name");
					if(gnsString.length() >0){
						gnsString += ",'"+gndh+"'";
					}else{
						gnsString = "'"+gndh+"'";
					}
//					workflowElMap.put(gndh, workflowEls.get(i));
					//---------------------
					JSONObject workflowObj = new JSONObject();
					workflowObj.put("gongNengDH", gndh);
					
					JSONArray propertiesArray = new JSONArray();
					if(workflowEl.element("properties")!=null){
						List<?> workflowPropertyEls = workflowEl.element("properties").elements("property");
						for(int j=0;j<workflowPropertyEls.size();j++){
							Element workflowPropertyEl = (Element)workflowPropertyEls.get(j);
							String propertyName = workflowPropertyEl.attributeValue("todoProperty");
							String freemarkerTemplate = workflowPropertyEl.attributeValue("freemarker");
							
							JSONObject propertyObj= new JSONObject();
							propertyObj.put("todoProperty", propertyName);
							propertyObj.put("freemarkerTemplate", freemarkerTemplate);
							//
							propertiesArray.add(propertyObj);
						}
					}
					workflowObj.put("properties", propertiesArray);
					
					workflowArray.add(workflowObj);
				}
			}
			
			
			if(gnsString.length()>0){
				//先根据流程检视的属性定义 取得json格式的数据
				JSONArray fieldJsonArray = null;
				Element fieldsEl = dataEl.element("properties");
				if(fieldsEl==null){
					fieldJsonArray = JSONArray.fromObject("[{" +
						"name:'"+liuChengJS_STL.getZhuJianLie()+"',fieldType:'int'" +
					"},{" +
						"name:'"+liuChengJS_STL.getXianShiLie()+"',fieldType:'string'" +
					"},{" +
						"name:'"+liuChengJS_STL.getPaiXuLie()+"',fieldType:'string'" +
					"}]");
				}else{
					fieldJsonArray = DatasourceElParser.getFieldsDefByEL(fieldsEl,null,true);
				}
				
				result = getTodoListByHibernate(shiTiLeiDH, start, limit, fieldJsonArray, workflowArray,filterString,sortString, yongHuDM,todoOnly);
			}else{
				result.put("totalCount", 0);
				result.put("data", new JSONArray());
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("errorMsg", e.getMessage());
		}
		return result;
	}
	
	public static JSONObject getTodoListByHibernate(String shiTiLeiDH,int start,int limit,JSONArray fields,JSONArray workflows,String filterString,String sortString,Integer yongHuDM,boolean todoOnly) throws Exception{
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
			//当前用户
			ShiTiLei dataStl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			YongHu yh = (YongHu)sess.load(YongHu.class, yongHuDM);
					
			result.put("start", start);
			result.put("limit", limit);
			result.put("todoOnly", todoOnly);
			
			result.put("fields", fields);
			result.put("workflows", workflows);
			
			
			//取得满足条件的流程检视对象
			int totalCount = 0;
			JSONArray dataJsonArray = new JSONArray();

			Criteria criteria = sess.createCriteria(Class.forName(dataStl.getShiTiLeiClassName()));
			//传递来的过滤条件
			//只使用其中与当前实体类有关的字段（流程检视表）
			List<Query> queriesForLCJS = new ArrayList<Query>();
			List<Query> queriesForWorkflow = new ArrayList<Query>();
			List<Query> queriesAll = QueryUtils.createQueriesByJson(filterString);
			if(queriesAll!=null && filterString.length()>0){
				for(int i=0;i<queriesAll.size();i++){
					Query query = queriesAll.get(i);
					if(query.isUserQuery() || query.isSqlQuery()){
						queriesForLCJS.add(query);
					}else if(dataStl.hasZiDuan(((PropertyQuery)query).getProperty())){
						queriesForLCJS.add(query);
					}else{
						queriesForWorkflow.add(query);
					}
				}
			}
			if(queriesForLCJS.size()>0){
				Criterion c = QueryUtils.createCriterionByQueries(dataStl,queriesForLCJS,yh);
				if(c!=null){
					criteria.add(c);
				}
			}
			
			//循环检查 本次查询的流程功能范围
			String gnsString = "";
			Criterion gnsCriterion = null;
			for(int i=0;i<workflows.size();i++){
				JSONObject workflow = workflows.getJSONObject(i);
				
				if(workflow.containsKey("gongNengDH")){
					String gndh = workflow.getString("gongNengDH");
					if(gndh != null){
						if(queriesForWorkflow.size()>0){
							//有流程查询条件 
							ShiTiLei workflowStl = GongNeng.getGongNengByDH(gndh).getShiTiLei();
							List<Query> queriesForGn = new ArrayList<Query>();
							for(int j=0;j<queriesForWorkflow.size();j++){
								PropertyQuery workflowQuery = (PropertyQuery)queriesForWorkflow.get(j);
								if(workflowStl.hasZiDuan(workflowQuery.getProperty())){
									queriesForGn.add(workflowQuery);
								}
							}
							//当前流程功能定义中有符合的查询条件 
							if(queriesForGn.size()>0){
								DetachedCriteria subselect = DetachedCriteria.forClass(Class.forName(workflowStl.getShiTiLeiClassName()));
								Criterion c2 = QueryUtils.createCriterionByQueries(workflowStl,queriesForGn,yh);
								if(c2!=null){
									subselect.add(c2);
								}
								subselect.setProjection(Projections.id());
								if(gnsCriterion == null){
									gnsCriterion = Restrictions.and(
											Restrictions.eq("gongNengDH", gndh), 
											Property.forName("gongNengObjId").in(subselect)
									);
								}else{
									gnsCriterion = Restrictions.or(gnsCriterion, Restrictions.and(
											Restrictions.eq("gongNengDH", gndh), 
											Property.forName("gongNengObjId").in(subselect)
									));
								}
								
								//有符合的条件 才加入查询
								if(gnsString.length() >0){
									gnsString += ",'"+gndh+"'";
								}else{
									gnsString = "'"+gndh+"'";
								}
							}
						}else{
							//无流程查询条件 直接加入
							if(gnsString.length() >0){
								gnsString += ",'"+gndh+"'";
							}else{
								gnsString = "'"+gndh+"'";
							}
						}
					}
				}
			}
		
			if(gnsString.length()>0){
				if(gnsCriterion!=null){
					criteria.add(gnsCriterion);
				}
				List<?> lcjsList = null;
				
				if(todoOnly){
					//仅代办任务
					criteria.add(Restrictions.sqlRestriction("gongnengdh in ("+gnsString+") " +
							(yh.isSuperUser()?"":("and caozuoren like '%"+yh.getYongHuMC()+" %' ")) +
							"and shifousc = 0 and shifouwc = 0 and shifougdjd = 0"))
							;
				}else{
					//包括已完成的 任务
					criteria.add(Restrictions.sqlRestriction("gongnengdh in ("+gnsString+") " +
							(yh.isSuperUser()?"":("and caozuoren like '%"+yh.getYongHuMC()+" %' ")) +
							"and shifousc = 0 and shifougdjd = 0"))
							;
				}
				
				//传递来的sort参数 
				HashMap<String, String> existsSortAlias = new HashMap<String, String>(); 
				if(sortString!=null && sortString.length()>2){
					JSONArray orderBys = JSONArray.fromObject(sortString); 
					JSONObject orderBy = null; 
					boolean isAsc;
					for(int i=0;i<orderBys.size();i++){
						orderBy = orderBys.getJSONObject(i);
						//操作类型
						isAsc = true;
						if(orderBy.has("dir") && orderBy.getString("dir").equals("DESC")){
							isAsc = false;
						}
						criteria.addOrder(getSortCriterion(orderBy.getString("property"),isAsc,existsSortAlias));
					}
				}else{
					criteria.addOrder(Order.asc("chuangJianRQ"))
						.addOrder(Order.asc("gongNengDH"))
						.addOrder(Order.asc("caoZuoDH"));
				}
				//添加alia
				if(existsSortAlias!=null && existsSortAlias.size()>0){
					Iterator<String> keys = existsSortAlias.keySet().iterator();
					String key = null;
					while(keys.hasNext()){
						key = keys.next();
						criteria.createAlias(key, existsSortAlias.get(key));
					}
				}
				
				if(limit>0){
					criteria.setFirstResult(start);
					criteria.setMaxResults(limit);
					lcjsList = criteria.list();
					
					criteria.setFirstResult(0); 
					criteria.setMaxResults(1); 
					totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
				}else{
					lcjsList = criteria.list();
					totalCount = lcjsList.size();
				}
				//先根据流程检视的属性定义 取得json格式的数据
				JSONArray lcjsFields = new JSONArray();
				for(int li=0;li<fields.size();li++){
					String fName = fields.getJSONObject(li).getString("name");
					if(dataStl.hasZiDuan(fName)){
						lcjsFields.add(fields.getJSONObject(li));
					}
				}
				dataJsonArray = JSONConvertUtils.objectList2JSONArray(dataStl,lcjsList,lcjsFields);
				//循环根据workflow中的属性定义  设置/覆盖其中的信息
				for(int i=0;i<lcjsList.size();i++){
					LiuChengJS lcjs = (LiuChengJS)lcjsList.get(i);
					JSONObject jsonDataObj = dataJsonArray.getJSONObject(i);
					//取得功能对象
					GongNeng busiGn = GongNeng.getGongNengByDH(lcjs.getGongNengDH());
					root.put("gn", busiGn);
					//取得流程关联的业务对象数据
					Object busiObj = sess.createCriteria(busiGn.getShiTiLei().getShiTiLeiClassName()).add(Restrictions.eq("id", lcjs.getGongNengObjId())).uniqueResult();
					if(busiObj != null){
						root.put("data", busiObj);
					}
					
					//查找工作流功能定义
					for(int j=0;j<workflows.size();j++){
						JSONObject workflow = workflows.getJSONObject(j);
						if(workflow.getString("gongNengDH").equals(lcjs.getGongNengDH())){
							JSONArray propertiesArray = workflow.getJSONArray("properties");
							for(int k=0;k<propertiesArray.size();k++){
								
								JSONObject workflowProperty =  propertiesArray.getJSONObject(k);
								String propertyName = workflowProperty.getString("todoProperty");
								String freemarkerTemplate = workflowProperty.getString("freemarkerTemplate");
								//
//								if(params!=null){
//									freemarkerTemplate = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+freemarkerTemplate;
//								}
								String value = null;
								try {
									value = FreemarkerUtils.processTemplate(freemarkerTemplate, root);
								} catch (Exception e) {
									value = "";
//									e.printStackTrace();
								}
								if(value!=null){
									jsonDataObj.put(propertyName, value.trim());
								}
							}
							break;
						}
					}
				}
			}
			result.put("totalCount", totalCount);
			result.put("data", dataJsonArray);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("errorMsg", e.getMessage());
		}
		return result;
	}
	
	public static Result doDelele(String gongNengDH,List<Integer> idList,Integer yongHuDM) {
		Result result = null;
		try {
			Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
			//设置或取消删除标记
			GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
			Class<?> stlClass = Class.forName(gn.getShiTiLei().getShiTiLeiClassName());
			Method setRelaShanChuZTMethod = stlClass.getMethod("setRelaShanChuZT",new Class[]{boolean.class});
			
			for(Integer id:idList){
				
				EntityI entityI = (EntityI)sess.load(stlClass, id);
				if(!gn.getShiTiLei().getShiFouYWB()){
					//删除数据
					sess.delete(entityI);
				}else{
					BusinessI busiI = (BusinessI)entityI;
					setRelaShanChuZTMethod.invoke(entityI, new Object[]{true});
					sess.update(entityI);
					//业务表 且流程功能 且数据与流程已绑定的话 
					//还需要删除流程实例
					if(gn.getShiFouLCGN()!=null  && busiI.getProcessInstanceId()!=null){
						ProcessEngine myProcessEngine = ProcessEngines.getDefaultProcessEngine();
						RuntimeService runtimeService = myProcessEngine.getRuntimeService();
						HistoryService historyService = myProcessEngine.getHistoryService();
						
						HistoricProcessInstanceEntity hisprocessInstance = (HistoricProcessInstanceEntity)historyService.createHistoricProcessInstanceQuery().processInstanceId(busiI.getProcessInstanceId()).singleResult();
						if(hisprocessInstance!=null){
							//删除流程之前 先将流程中的business_Key_清空
							((ServiceImpl)runtimeService).getCommandExecutor().execute(new ClearProcessInstanceBussinessKeyCmd(busiI.getProcessInstanceId()));
							//删除流程
							runtimeService.deleteProcessInstance(busiI.getProcessInstanceId(), "用户删除数据");
						}
						
						//删除流程检视
						@SuppressWarnings("unchecked")
						List<LiuChengJS> lcjss = sess.createCriteria(LiuChengJS.class)
								.add(Restrictions.eq("gongNengDH",gongNengDH))
								.add(Restrictions.eq("gongNengObjId",busiI.pkValue()))
								.list();
						for(LiuChengJS lcjs:lcjss){
							sess.delete(lcjs);
						}
						//删除关联信息
						busiI.setProcessInstanceId(null);
						busiI.setProcessInstanceEnded(false);
						busiI.setProcessInstanceTerminated(false);
						sess.update(busiI);
					}
				}
			}
		}catch (InvocationTargetException e){
			e.printStackTrace();
			result = new JSONMessageResult(e.getTargetException().getMessage());
		}catch (Exception e){
			e.printStackTrace();
			result = new JSONMessageResult(e.getMessage());
		}
		return result;
	}
	
	public static Result doExec(String gongNengDH,String caoZuoDH,List<Integer> idList,Integer yongHuDM) {
		
		Result result = null;
		Session sess = null;
		try {
			if(caoZuoDH.equals("delete")){
				return doDelele(gongNengDH, idList, yongHuDM);
			}
			
			GongNeng gn = GongNeng.getGongNengByDH(gongNengDH);
			if(gn==null){
				return new JSONMessageResult("功能("+gongNengDH+")不存在!");
			}
			
			sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
			
			GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
					.add(Restrictions.eq("gongNeng.id",gn.getGongNengDM()))
					.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
			if(gncz==null){
				return new JSONMessageResult("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
			}
			//检查登录状态 并取得当前用户代码
			
			String fieldsMeta = null;
			YongHu yh = (YongHu)sess.load(YongHu.class, yongHuDM);
			//
			List<EntityI> currentObjList = new ArrayList<EntityI>();
			//检查权限
			if(idList!=null){
				for(Integer id:idList){
					if(!AuthUtils.checkAuth(gongNengDH,caoZuoDH, id, yongHuDM)){
						return new JSONMessageResult("记录("+gongNengDH+"."+caoZuoDH+"."+id+")不存在或用户对此记录没有操作权限！");
					}
					
					//调用原始操作对应的方法取得初始对象
					EntityI entityI = (EntityI)DataUtils.getData(gncz,id,yongHuDM);
					currentObjList.add(entityI);
				}
			}
			//
			Class<?> gnActionClass = null;
			Object gnActionInstance = null;
			try {
				gnActionClass = Class.forName(gncz.getGongNeng().getGongNengClass());
				gnActionInstance = gnActionClass.newInstance();
			} catch (Exception e) {
			}
			String firstCZDH = caoZuoDH.replaceFirst(caoZuoDH.substring(0, 1),caoZuoDH.substring(0, 1).toUpperCase());
			//检查功能action类中 是否实现了before方法
			Method beforeMethod=getBeforeMethod(gnActionClass,"before"+firstCZDH);
			if(beforeMethod!=null){
				Result beforeRet = (Result)beforeMethod.invoke(gnActionInstance, new Object[]{gongNengDH,caoZuoDH,currentObjList,null,yongHuDM});
				if(!beforeRet.isSuccess()){
					return beforeRet;
				}
			}
			//检查功能action类中 是否实现了on方法
			Method onMethod = getOnMethod(gnActionClass,"on"+firstCZDH);
			if(onMethod!=null){
				Result onRet = (Result)onMethod.invoke(gnActionInstance, new Object[]{gongNengDH,caoZuoDH,currentObjList,null,yongHuDM});
				if(!onRet.isSuccess()){
					return onRet;
				}
			}else{
				for(EntityI entityI:currentObjList){
					if(entityI instanceof BusinessI){
						//为业务对象的业务字段赋值
						BusinessI p = (BusinessI)entityI;
						p.setXiuGaiRen(yh.getYongHuMC());
						p.setXiuGaiRQ(Calendar.getInstance().getTime());
					}
					sess.saveOrUpdate(entityI);
				}
			}	
			
			//检查功能action类中 是否实现了after方法
			Method afterMethod=getAfterMethod(gnActionClass,"after"+firstCZDH);
			if(afterMethod!=null){
				Result afterRet = (Result)afterMethod.invoke(gnActionInstance, new Object[]{gongNengDH,caoZuoDH,currentObjList,null,yongHuDM});
				if(!afterRet.isSuccess()){
					return afterRet;
				}
			}
			
			//检查当前功能是否流程功能
			if(gncz.getGongNeng().getShiFouLCGN()!=null && gncz.getGongNeng().getShiFouLCGN()){// && gncz.getDuiXiangXG()
				for(EntityI entityI:currentObjList){
					TaskUtils.completeTask((BusinessI)entityI,null, gncz, yh,false);
				}
			}
			//检查返回字段定义
			if(fieldsMeta==null){
				fieldsMeta = "[{name:'"+gncz.getGongNeng().getShiTiLei().getZhuJianLie()+"'}]";
			}
			//当前处理的数据idList
			List<Integer> retIds = new ArrayList<Integer>();
			for(EntityI currentEntityI:currentObjList){
				retIds.add(currentEntityI.pkValue());
			}
			//
			JSONArray rows = JSONConvertUtils.objectList2JSONArray(gncz.getGongNeng().getShiTiLei(),currentObjList,JSONArray.fromObject(fieldsMeta));
			
			result = new JSONDataResult(rows, rows.size(), 0, 0, null);
			
		
		}catch (InvocationTargetException e){
			e.printStackTrace();
			result = new JSONMessageResult( e.getTargetException().getMessage());
		}catch (Exception e){
			e.printStackTrace();
			result = new JSONMessageResult(e.getMessage());
		}
		return result;
	}
	

	
	private static EntityI getData(GongNengCZ gncz,Integer id,Integer yhdm) throws Exception{
		//返回新增或已存在的实体类对象
		Session sess = HibernateSessionFactory.getSession(HibernateSessionFactory.defaultSessionFactory);
		YongHu yh = null;
		GongNengCZ originalGNCZ = gncz;
		ShiTiLei originalStl = originalGNCZ.getGongNeng().getShiTiLei();
		ShiTiLei realSTL = originalStl;
		GongNengCZ realGNCZ = originalGNCZ;
		//当前用户对象
		if(yhdm!=null){
			yh = (YongHu)sess.load(YongHu.class,yhdm);
		}
		//正常方式 原始对象
		EntityI obj = null;
		if(id!=null){
			obj = (EntityI)sess.load(originalStl.getShiTiLeiClassName(), id);
		}else{
			Class<?> stlClass = Class.forName(originalStl.getShiTiLeiClassName());
			obj = (EntityI)stlClass.newInstance();
		}
		
		try {
			Class<?> gongNengClass = Class.forName(gncz.getGongNeng().getGongNengClass());
			Method getFormObjectMethod = gongNengClass.getMethod("getFormObject",new Class[]{String.class,Integer.class,Integer.class,Object.class,GongNengCZ.class});
			obj = (EntityI)getFormObjectMethod.invoke(gongNengClass.newInstance(), new Object[]{gncz.getCaoZuoDH(),id,yhdm,obj,gncz});
		} catch (Exception e1) {
		}
		//如果是show 且流程功能 设置查看状态
		if(gncz.getCaoZuoDH().equals("show") && gncz.getGongNeng().getShiFouLCGN()){
			//查找对应的流程检视 将当前用户的待办设为已查看
			@SuppressWarnings("unchecked")
			List<LiuChengJS> currentLCJSs = (List<LiuChengJS>)sess.createCriteria(LiuChengJS.class)
					.add(Restrictions.eq("gongNengDH",gncz.getGongNeng().getGongNengDH()))
					.add(Restrictions.eq("gongNengObjId",obj.pkValue()))
					.add(Restrictions.eq("caoZuoDH", gncz.getCaoZuoDH()))
					.add(Restrictions.like("caoZuoRen", gncz.getCaoZuoDH()+" ",MatchMode.ANYWHERE))
					.add(Restrictions.eq("shiFouCK", false))//未查看
					.add(Restrictions.eq("shiFouCL", false))//未处理
					.add(Restrictions.eq("shiFouWC", false))//未完成
					.add(Restrictions.eq("shiFouSC", false))//未删除
					.list();
			for(LiuChengJS currentLCJS:currentLCJSs){
				currentLCJS.setShiFouCK(true);
				sess.update(currentLCJS);
			}
		}
		//检查当前功能操作中 是否有自动赋值的设置
//		for(GongNengCZFZ gnczfz:gncz.getFzs()){
//			if(gnczfz.getFuZhiSJ().getFuZhiSJDM().intValue() == FuZhiSJ.fuZhiSJ_load){
//				if(testFuzhiTJ(obj,gnczfz.getMxs())){
//					//赋值目标（这里只应该对当前对象的直接字段 或关联子集的直接字段赋值 ）
//					String fuZhiMB = gnczfz.getFuZhiMB();
//					//赋值内容
//					String fuZhiNR = gnczfz.getFuZhiMB();
//					
//				}
//			}
//		}
		return obj;
	}
	
	
	
	public static Method getAfterMethod(Class<?> gnActionClass,String methodName) throws Exception{
		Method method=null;
		if(gnActionClass!=null){
			//检查类中是否存在此名称的方法
			boolean nameExists = false;
			Method[] ms = gnActionClass.getMethods();
			for(Method m:ms){
				if(m.getName().endsWith("Save")){
					throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+m.getName()+"方法，改写为after{CaoZuoDH}!例:public MethodResult afterAppend(String,String,List,JSONObject,Integer)");
				}
			}
			for(Method m:ms){
				if(m.getName().equals(methodName)){
					nameExists = true;
					break;
				}
			}
			
			//取得符合条件的方法
			try {
				method = gnActionClass.getMethod(methodName,new Class[]{String.class,String.class,List.class,JSONObject.class,Integer.class});
			} catch (Exception e) {
			}
			if(method==null && nameExists){
				throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+methodName+"方法签名及返回值改写为:public MethodResult "+methodName+"(String,String,List,JSONObject,Integer)");
			}
		}
		return method;
	}
	
	//before方法
	public static Method getBeforeMethod(Class<?> gnActionClass,String methodName) throws Exception{
		Method method=null;
		if(gnActionClass!=null){
			//检查类中是否存在此名称的方法
			boolean nameExists = false;
			Method[] ms = gnActionClass.getMethods();
			for(Method m:ms){
				if(m.getName().endsWith("Save")){
					throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+m.getName()+"方法，改写为before{CaoZuoDH}!例:public MethodResult beforeAppend(String,String,List,JSONObject,Integer)");
				}
			}
			for(Method m:ms){
				if(m.getName().equals(methodName)){
					nameExists = true;
					break;
				}
			}
			
			//取得符合条件的方法
			try {
				method = gnActionClass.getMethod(methodName,new Class[]{String.class,String.class,List.class,JSONObject.class,Integer.class});
			} catch (Exception e) {
			}
			if(method==null && nameExists){
				throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+methodName+"方法签名及返回值改写为:public MethodResult "+methodName+"(String,String,List,JSONObject,Integer)");
			}
		}
		return method;
	}

	//prepare方法
	public static Method getPrepareMethod(Class<?> gnActionClass,String methodName) throws Exception{
		Method method=null;
		if(gnActionClass!=null){
			//检查类中是否存在此名称的方法
			boolean nameExists = false;
			Method[] ms = gnActionClass.getMethods();
			for(Method m:ms){
				if(m.getName().endsWith("Save")){
					throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+m.getName()+"方法，改写为prepare{CaoZuoDH}!例:public MethodResult prepareAppend(String,String,List,JSONObject,Integer)");
				}
			}
			for(Method m:ms){
				if(m.getName().equals(methodName)){
					nameExists = true;
					break;
				}
			}
			
			//取得符合条件的方法
			try {
				method = gnActionClass.getMethod(methodName,new Class[]{String.class,String.class,List.class,JSONObject.class,Integer.class});
			} catch (Exception e) {
			}
			if(method==null && nameExists){
				throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+methodName+"方法签名及返回值改写为:public MethodResult "+methodName+"(String,String,List,JSONObject,Integer)");
			}
		}
		return method;
	}

	//on方法
	public static Method getOnMethod(Class<?> gnActionClass,String methodName) throws Exception{
		Method method=null;
		if(gnActionClass!=null){
			//检查类中是否存在此名称的方法
			boolean nameExists = false;
			Method[] ms = gnActionClass.getMethods();
			for(Method m:ms){
				if(m.getName().endsWith("Save")){
					throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+m.getName()+"方法，改写为on{CaoZuoDH}!例:public MethodResult onAppend(String,String,List,JSONObject,Integer)");
				}
			}
			for(Method m:ms){
				if(m.getName().equals(methodName)){
					nameExists = true;
					break;
				}
			}
			
			//取得符合条件的方法
			try {
				method = gnActionClass.getMethod(methodName,new Class[]{String.class,String.class,List.class,JSONObject.class,Integer.class});
			} catch (Exception e) {
			}
			if(method==null && nameExists){
				throw new Exception("请将功能类"+gnActionClass.getName()+"中的"+methodName+"方法签名及返回值改写为:public MethodResult "+methodName+"(String,String,List,JSONObject,Integer)");
			}
		}
		return method;
	}
	
	public static JSONArray getPkMetaFromSTL(ShiTiLei stl){
		JSONArray stlMeta = new JSONArray();
		JSONObject zdMeta = new JSONObject();
		zdMeta.put("name", stl.getZhuJianLie());
		for(ZiDuan zd :stl.getZds()){
			if("object".equals(zd.getZiDuanLX().getZiDuanLXDH()) || "set".equals(zd.getZiDuanLX().getZiDuanLXDH()) ){
				JSONObject zdMeta1 = new JSONObject();
				zdMeta1.put("name", stl.getZhuJianLie());
				
				JSONArray fields = new JSONArray();
				JSONObject zhuJianLieMeta = new JSONObject();
				zhuJianLieMeta.put("name", zd.getGuanLianSTL().getZhuJianLie());
				fields.add(zhuJianLieMeta);
				
				zdMeta1.put("fields", fields);
				
				stlMeta.add(zdMeta1);
			}
		}
		return stlMeta;
	}
	
//	public static JSONObject setPkFromEntity(ShiTiLei stl,JSONObject jsonObj){
//		if(jsonObj.containsKey("_entity")){
//			EntityI obj = (EntityI)jsonObj.get("_entity");
//			if(obj!=null){
//				jsonObj.put(stl.getZhuJianLie(), obj.pkValue());
//				for(ZiDuan zd :stl.getZds()){
//					if("set".equals(zd.getZiDuanLX().getZiDuanLXDH()) && jsonObj.containsKey(zd.getZiDuanDH())){
//						JSONArray subDataArray = jsonObj.getJSONArray(zd.getZiDuanDH());
//						for(int i=0;i<subDataArray.size();i++){
//							JSONObject subDataObj = subDataArray.getJSONObject(i);
//							setPkFromEntity(zd.getGuanLianSTL(),subDataObj);
//						}
//					}
//				}
//			}
//			jsonObj.remove("_entity");
//		}
//		return jsonObj;
//	}
	
}

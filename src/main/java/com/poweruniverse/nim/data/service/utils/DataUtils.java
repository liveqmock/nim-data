package com.poweruniverse.nim.data.service.utils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.utils.FreemarkerUtils;
import com.poweruniverse.nim.data.entity.GongNeng;
import com.poweruniverse.nim.data.entity.GongNengCZ;
import com.poweruniverse.nim.data.entity.LiuChengJS;
import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.entity.base.EntityI;
import com.poweruniverse.nim.data.pageParser.DatasourceElParser;

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
	public static Object getObjectByGN(String gongNengDH,String caoZuoDH,Integer id,Integer yongHuDM) throws Exception{
		Object obj = null;
		
		Session sess = SystemSessionFactory.getSession();

		//取得数据
		GongNengCZ gncz = (GongNengCZ)sess.createCriteria(GongNengCZ.class)
				.createAlias("gongNeng", "gncz_gn")
				.add(Restrictions.eq("gncz_gn.gongNengDH",gongNengDH))
				.add(Restrictions.eq("caoZuoDH", caoZuoDH)).uniqueResult();
		if(gncz==null){
			throw new Exception("功能操作:"+gongNengDH+"."+caoZuoDH+"不存在！");
		}
		
		YongHu yh = null;
		if(yongHuDM!=null){
			yh = (YongHu)sess.load(YongHu.class, yongHuDM);
		}else if(gncz.getKeYiSQ()){
			throw new Exception("未登录或已超时，请重新登录！");
		}
		
		//用这个openGNDH、openCZDH确定和检查权限
		if(AuthUtils.checkAuth(gongNengDH,caoZuoDH, id, yh)){
			//正常方式 原始对象
			if(gncz.getDuiXiangXG()){
				if(id==null){
					throw new Exception("使用对象相关的功能操作("+gongNengDH+"."+caoZuoDH+")必须提供id！");
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
//					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}else{
			throw new Exception("记录("+gongNengDH+"."+caoZuoDH+"."+id+")不存在或用户没有权限！");
		}
	
		return obj;
	}
	
	/**
	 * 取功能数据对象
	 * 只能取到有权限的数据对象
	 */
	public static Object getObjectBySTL(String shiTiLeiDH,Integer id) throws Exception{
		
		Session sess = SystemSessionFactory.getSession();

		//取得数据
		ShiTiLei stl = (ShiTiLei)sess.createCriteria(ShiTiLei.class)
				.createAlias("shiTiLeiDH", shiTiLeiDH).uniqueResult();
		if(stl==null){
			throw new Exception("实体类:"+shiTiLeiDH+"不存在！");
		}
		
		//用这个openGNDH、openCZDH确定和检查权限

		//正常方式 原始对象
		Object obj = sess.load(stl.getShiTiLeiClassName(), id);
		
		return obj;
	}
	
	/**
	 * 取sql数据对象
	 * 与权限无关
	 */
	public static JSONObject getRowBySQL(String sql,JSONArray fieldJsonArray) throws Exception{
		JSONObject row = new JSONObject();
		if(fieldJsonArray!=null && fieldJsonArray.size()>0){
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try{
	        	conn = SystemSessionFactory.getConnection();
	        	
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
	public static JSONObject getRowsBySQL(String sql,int start,int limit ,JSONArray fieldJsonArray) throws Exception{
		JSONArray rows = new JSONArray();
		int totalCount = 0;
		if(fieldJsonArray!=null && fieldJsonArray.size()>0){
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try{
	        	conn = SystemSessionFactory.getConnection();
	        	
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
		
		JSONObject result = new JSONObject();
		result.put("totalCount", totalCount);
		result.put("rows", rows);
		return result;
	}

		
	public static Map<String,Object> getListByGN(String gndh,String czdh,int start,int limit,String filterString,String sortString,Integer yhdm) 
			throws Exception{
		Session sess = SystemSessionFactory.getSession();
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
		result.put("start", start);
		result.put("limit", limit);
		result.put("totalCount", totalCount);
	
		return result;
	}
	
	
	public static Map<String,Object> getListBySTL(String stldh,int start,int limit,String filterString,String sortString) 
			throws Exception{
		Session sess = SystemSessionFactory.getSession();
		Map<String,Object> result = new HashMap<String,Object>();
		int totalCount = 0;
		//功能实体类
		ShiTiLei stl = (ShiTiLei)sess.createCriteria(ShiTiLei.class)
				.createAlias("shiTiLeiDH", stldh).uniqueResult();
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
	
	/**
	 * 取待办数据集合
	 */
	private static JSONObject getTodoListByHibernate(String shiTiLeiDH,int start,int limit,JSONArray fields,JSONArray workflows,String filterString,String sortString,Integer yongHuDM,boolean todoOnly) throws Exception{
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			Session sess = SystemSessionFactory.getSession();
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
									value = FreemarkerUtils.processTemplate(freemarkerTemplate, root,null);
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
}

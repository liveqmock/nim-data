package com.poweruniverse.nim.data.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;
import com.poweruniverse.nim.data.service.utils.Query;
import com.poweruniverse.nim.data.service.utils.QueryUtils;
import com.poweruniverse.nim.interfaces.entity.BaseEntityI;
import com.poweruniverse.nim.interfaces.entity.ShiTiLeiI;
import com.poweruniverse.nim.message.JsonReturn;

@WebService
public class DataServiceImpl {
	
	
	private static String shiTiLeiClassName = "";
	
	/**
	* 执行sql 
	* @param String sql
	* @return
	*/
	public JsonReturn execute(String sql,int start,int limit,JSONArray fields){
		
		JsonReturn result = new JsonReturn();
		
		if(fields!=null && fields.size()>0){
			return new JsonReturn("请提供fields参数，且不能为空集");
		}
		Session sess = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			int totalCount = 0;
			sess = HibernateSessionFactory.getSession();
			
			Connection conn = HibernateSessionFactory.getConnection();
			
			rs = conn.createStatement().executeQuery("select count(*) rowCount from ("+sql+") ");
			if(rs.next()){
				totalCount = rs.getInt(1);
			}
			
			if(limit >0){
				sql =	"select t2.* from ("+
						"	select t.*,rownum rn  from ( "+sql+") t where rownum <= "+(start+limit)+
								") t2"+
						" where t2.rn > "+start;
			}
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//
			ResultSetMetaData rsmd = rs.getMetaData();
			if(fields.size()==1 && "...".equals(fields.get(0))){
				fields = new JSONArray();
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
					
					fields.add(fieldDefObj);
				}
			}
				
			JSONArray rows = JSONConvertUtils.applyResultset2JSONArray(rs, fields);
	
			result = new JsonReturn();
			result.put("start", start);
			result.put("limit", limit);
			result.put("totalCount", totalCount);
			result.put("data", rows);
		}catch (Exception e) {
			result = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		return result;
	}

	/**
	* 取对象
	* @param gongNengDH
	* @param caoZuoDH
	* @param objectId
	* @return
	*/
	public JsonReturn load(Integer shiTiLeiDM,Integer objectId,JSONArray fields){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			//本次查询的对象
			BaseEntityI obj = (BaseEntityI)sess.load(shiTiLei.getShiTiLeiClassName(), objectId);
			
			JSONObject json = JSONConvertUtils.applyEntity2JSONObject(shiTiLei, obj, fields);
			
			ret = new JsonReturn("data",json);
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
		
	}
	
	/**
	* 查询
	* @param gongNengDH
	* @param caoZuoDH
	* @param fieldsJson
	* @param filterJson
	* @param sortString
	* @return
	*/
	@SuppressWarnings("unchecked")
	public JsonReturn list(Integer shiTiLeiDM,JSONArray filters,JSONArray sorts,int start,int limit,JSONArray fields){
		JsonReturn result = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			//基础查询语法
			Criteria criteria = sess.createCriteria(shiTiLei.getShiTiLeiClassName());
//			
			//传递来的过滤条件
			List<Query> queries = QueryUtils.createQueriesByJson(filters);
			Criterion c = QueryUtils.createCriterionByQueries(shiTiLei,queries);
			if(c!=null){
				criteria.add(c);
			}
			//传递来的sort参数 
			HashMap<String, String> existsSortAlias = new HashMap<String, String>(); 
			if(sorts!=null && sorts.size()>0){
				JSONObject orderBy = null; 
				boolean isAsc;
				for(int i=0;i<sorts.size();i++){
					orderBy = sorts.getJSONObject(i);
					//操作类型
					isAsc = true;
					if(orderBy.has("dir") && orderBy.getString("dir").equals("DESC")){
						isAsc = false;
					}
					criteria.addOrder(QueryUtils.getSortCriterion(orderBy.getString("property"),isAsc,existsSortAlias));
				}
			}else{
				criteria.addOrder(Order.asc(shiTiLei.getPaiXuLie()));
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
			int totalCount = 0;
			List<BaseEntityI> objs =null;
			if(limit>0){
				//加入分页参数
				criteria.setFirstResult(start); 
				criteria.setMaxResults(limit); 
				objs = (List<BaseEntityI>)criteria.list();
				//取结果总数
				criteria.setFirstResult(0); 
				criteria.setMaxResults(1); 
				Object sa = criteria.setProjection(Projections.rowCount())
						.uniqueResult();
				if(sa!=null){
					totalCount = ((Long)sa).intValue();
				}
				
			}else{
				objs = (List<BaseEntityI>)criteria.list();
				totalCount = objs.size();
			}
			
			JSONArray rows = JSONConvertUtils.applyList2JSONArray(shiTiLei, objs, fields);
			
			result = new JsonReturn();
			result.put("start", start);
			result.put("limit", limit);
			result.put("totalCount", totalCount);
			result.put("data", rows);
		}catch (Exception e) {
			result = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return result;
		
	}
	
	
	/**
	* 插入数据
	* @param gongNengDH
	* @param caoZuoDH
	* @param objectId
	* @param data
	* @return
	*/
	private Integer insert(ShiTiLeiI shiTiLei,JSONObject data) throws Exception{

		//数据库链接
		Session sess = HibernateSessionFactory.getSession();
		//本次新增的对象
		BaseEntityI object = (BaseEntityI)Class.forName(shiTiLei.getShiTiLeiClassName()).newInstance();
		
		JSONConvertUtils.applyJSONToObject(shiTiLei,object, data);
		
		sess.save(object);
		
		//返回字符串
		return object.pkValue();
	}
	public JsonReturn insert(Integer shiTiLeiDM,JSONObject data){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			
			Integer id = insert(shiTiLei,data);
			
			JSONObject retData = new JSONObject();
			data.put(shiTiLei.getZhuJianLie(),id);
			
			ret = new JsonReturn("data",retData);
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}	
	
	public JsonReturn insertAll(Integer shiTiLeiDM,JSONArray dataList){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			//本次新增的对象
			JSONArray idList = new JSONArray();
			for(int i = 0;i<dataList.size();i++){
				JSONObject data = dataList.getJSONObject(i);
				Integer id = insert(shiTiLei,data);
				
				JSONObject retData = new JSONObject();
				data.put(shiTiLei.getZhuJianLie(),id);
				idList.add(retData);
			}
			ret = new JsonReturn("data",idList);
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}	
	
	/**
	 * 保存数据变化 
	 * 其中可能有数据的新增、
	 * @param shiTiLeiDM
	 * @param saveData
	 * @return
	 */
	public JsonReturn save(Integer shiTiLeiDM,JSONObject saveData){
		JsonReturn ret = null;
		Session sess = null;
		try {
			JSONArray idList = new JSONArray(); 
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			//本次新增的对象
			if(saveData.containsKey("insert")){
				JSONArray insertData = saveData.getJSONArray("insert");
				for(int i = 0;i<insertData.size();i++){
					JSONObject data = insertData.getJSONObject(i);
					Integer id = insert(shiTiLei,data);
					
					JSONObject retData = new JSONObject();
					data.put(shiTiLei.getZhuJianLie(),id);
					idList.add(retData);
				}
			}
			//本次修改的对象
			if(saveData.containsKey("update")){
				JSONArray updateData = saveData.getJSONArray("update");
				for(int i = 0;i<updateData.size();i++){
					JSONObject data = updateData.getJSONObject(i);
					update(shiTiLei,data);
				}
			}
			//本次删除的对象
			if(saveData.containsKey("delete")){
				JSONArray deleteData = saveData.getJSONArray("delete");
				for(int i = 0;i<deleteData.size();i++){
					JSONObject data = deleteData.getJSONObject(i);
					Integer id = data.getInt(shiTiLei.getZhuJianLie());
					delete(shiTiLei,id);
				}
			}
			ret = new JsonReturn("data",idList);
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}
	
	
	/**
	* 修改数据
	* @param gongNengDH
	* @param caoZuoDH
	* @param objectId
	* @param data
	* @return
	*/
	private void update(ShiTiLeiI shiTiLei,JSONObject data) throws Exception{
		Session sess = HibernateSessionFactory.getSession();
		Integer id = data.getInt(shiTiLei.getZhuJianLie());
		BaseEntityI object = (BaseEntityI)sess.load(shiTiLei.getShiTiLeiClassName(),id);
		JSONConvertUtils.applyJSONToObject(shiTiLei,object, data);
		sess.update(object);
		
	}
	public JsonReturn update(Integer shiTiLeiDM,JSONObject data){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			update(shiTiLei,data);
			
			ret = new JsonReturn();
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}	
	
	public JsonReturn updateAll(Integer shiTiLeiDM,JSONArray dataList){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			//本次新增的对象
			for(int i = 0;i<dataList.size();i++){
				JSONObject data = dataList.getJSONObject(i);
				update(shiTiLei,data);
			}
			ret = new JsonReturn();
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}	
	
	//删除数据
	private void delete(ShiTiLeiI shiTiLei,Integer id){
		Session sess = HibernateSessionFactory.getSession();
		Object object = sess.load(shiTiLei.getShiTiLeiClassName(),id);
		sess.delete(object);
	}
	
	public JsonReturn delete(Integer shiTiLeiDM,Integer id){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			delete(shiTiLei,id);
			
			ret = new JsonReturn();
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}
	public JsonReturn deleteAll(Integer shiTiLeiDM,List<Integer> idList){
		JsonReturn ret = null;
		Session sess = null;
		try {
			//数据库链接
			sess = HibernateSessionFactory.getSession();
			//本次查询的实体类定义
			ShiTiLeiI shiTiLei = (ShiTiLeiI)sess.load(shiTiLeiClassName,shiTiLeiDM);
			//本次新增的对象
			for(int i = 0;i<idList.size();i++){
				delete(shiTiLei,idList.get(i));
			}
			ret = new JsonReturn();
		}catch (Exception e) {
			ret = new JsonReturn(e.getMessage());
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
			e.printStackTrace();
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		//返回字符串
		return ret;
	}

}


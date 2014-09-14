package com.poweruniverse.nim.data.service.utils;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;

import com.poweruniverse.nim.interfaces.entity.BaseEntityI;
import com.poweruniverse.nim.interfaces.entity.ShiTiLeiI;
import com.poweruniverse.nim.interfaces.entity.ZiDuanI;
import com.poweruniverse.nim.interfaces.entity.ZiDuanLXI;

public class JSONConvertUtils {
	private static SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");

	//将客户端修改的值 应用到原始数据对象中
	public static BaseEntityI applyJSONToObject(ShiTiLeiI stl,BaseEntityI obj,JSONObject data) throws Exception{
		if(data==null) return obj;
		//根据
		Iterator<String> properties = data.keys();
		while(properties.hasNext()){
			String propertyName = properties.next();
			if(!propertyName.startsWith("_")){
				Object propertyValue = data.get(propertyName);
				
				if(propertyValue!=null){
					ZiDuanI zd = stl.getZiDuan(propertyName);
					if(zd == null){
						throw new Exception("实体类\""+stl.getShiTiLeiMC()+"\"中不存在字段\""+zd.getZiDuanDH()+"\"！");
					}
					ZiDuanLXI zdlx = zd.getZiDuanLX();
					if(zdlx==null){
						throw new Exception("实体类\""+stl.getShiTiLeiMC()+"\"中字段\""+zd.getZiDuanDH()+"\"的字段类型为空！");
					}
					//
					String ziDuanLXDH = zdlx.getZiDuanLXDH();
					
					if("object".equals(ziDuanLXDH)){
						//对象类型的字段 只存一个有主键值的对象进去就可以了
						ShiTiLeiI substl = zd.getGuanLianSTL();
						BaseEntityI subObj = (BaseEntityI)Class.forName(substl.getShiTiLeiClassName()).newInstance();
						
						Integer substlPkValue = null;
						Object substlPkValueObj = null;
						if(propertyValue instanceof JSONObject){
							substlPkValueObj = ((JSONObject)propertyValue).get(substl.getZhuJianLie());
						}else{
							substlPkValueObj = propertyValue;
						}

						if(substlPkValueObj!=null){
							if(substlPkValueObj instanceof String){
								substlPkValue = Double.valueOf((String)substlPkValueObj).intValue();
							}else if(substlPkValueObj instanceof Double){
								substlPkValue = ((Double)substlPkValueObj).intValue();
							}else if(substlPkValueObj instanceof Integer){
								substlPkValue = (Integer)substlPkValueObj;
							}else{
								throw new Exception("未知的主键值类型\""+substlPkValue+"("+substlPkValue.getClass()+")!");
							}
						}
						
						PropertyUtils.setProperty(subObj,substl.getZhuJianLie(),substlPkValue);
						PropertyUtils.setProperty(obj,propertyName,subObj);
					}else if("set".equals(ziDuanLXDH)){
						//父对象的类中 关于子类集合操作的几个方法
						Class<?> cls = Class.forName(stl.getShiTiLeiClassName());
						Method appendMethod = cls.getMethod("addTo"+propertyName,new Class[]{Object.class,Object.class});
						Method removeMethod = cls.getMethod("removeFrom"+propertyName,new Class[]{Object.class,Object.class});
						Method getMethod = cls.getMethod("get"+propertyName+"ById",new Class[]{Object.class});
						
						ShiTiLeiI substl = zd.getGuanLianSTL();
						BaseEntityI subObj = null;
						if(propertyValue instanceof JSONObject){
							@SuppressWarnings("unchecked")
							JSONArray modified = (JSONArray)((JSONObject)propertyValue).get("modified");
							if(modified!=null && modified.size()>0){
								for(int i =0;i<modified.size();i++){
									JSONObject subObjData = modified.getJSONObject(i);
									Object subPrimaryValue = subObjData.get(substl.getZhuJianLie());
									subObj = (BaseEntityI)getMethod.invoke(obj, new Object[]{subPrimaryValue});
									subObj = applyJSONToObject(substl,subObj,subObjData);
								}
							}
							@SuppressWarnings("unchecked")
							JSONArray inserted = (JSONArray)((JSONObject)propertyValue).get("inserted");
							if(inserted!=null && inserted.size()>0){
								for(int i =0;i<inserted.size();i++){
									JSONObject subObjData = inserted.getJSONObject(i);
									//新增子对象
									try{
										Class<?> subCls = Class.forName(stl.getShiTiLeiClassName());
										Method mtd = subCls.getMethod("new"+propertyName+"ByParent",new Class[]{});
										subObj = (BaseEntityI)mtd.invoke(obj, new Object[]{});
									}catch(Exception e){
										subObj = (BaseEntityI)Class.forName(substl.getShiTiLeiClassName()).newInstance();
										PropertyUtils.setProperty(subObj,zd.getGuanLianFLZD(),obj);
									}
									//填充子对象的内容
									subObj = applyJSONToObject(substl,subObj,subObjData);
									appendMethod.invoke(obj, new Object[]{obj,subObj});
								}
							}
							@SuppressWarnings("unchecked")
							JSONArray deleted = (JSONArray)((JSONObject)propertyValue).get("deleted");
							if(deleted!=null && deleted.size()>0){
								for(int i =0;i<deleted.size();i++){
									Object subPrimaryValue = deleted.getJSONObject(i).get(substl.getZhuJianLie());
									subObj = (BaseEntityI)getMethod.invoke(obj, new Object[]{subPrimaryValue});
									removeMethod.invoke(obj, new Object[]{obj,subObj});
								}
							}
							
						}else if (propertyValue instanceof List){
							@SuppressWarnings("unchecked")
							JSONArray inserted = (JSONArray)propertyValue;
							if(inserted!=null && inserted.size()>0){
								for(int i =0;i<inserted.size();i++){
									//新增子对象
									try{
										Class<?> subCls = Class.forName(stl.getShiTiLeiClassName());
										Method mtd = subCls.getMethod("new"+propertyName+"ByParent",new Class[]{});
										subObj = (BaseEntityI)mtd.invoke(obj, new Object[]{});
									}catch(Exception e){
										subObj = (BaseEntityI)Class.forName(substl.getShiTiLeiClassName()).newInstance();
										PropertyUtils.setProperty(subObj,zd.getGuanLianFLZD(),obj);
									}
									//填充子对象的内容
									subObj = applyJSONToObject(substl,subObj,inserted.getJSONObject(i));
									appendMethod.invoke(obj, new Object[]{obj,subObj});
								}
							}
						}
						
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("date") && (propertyValue instanceof String) ){
						String dateString = ""+propertyValue;
						Date dateValue = null;
						if(dateString.length()==10){
							dateValue = sdf.parse(dateString);
						}else if(dateString.length()==19){
							dateValue = dtf.parse(dateString);
						}else{
							System.out.println("错误的日期数据("+propertyName+")："+dateString+"格式必须为yyyy-MM-dd HH:mm:ss或yyyy-MM-dd");
						}
						PropertyUtils.setProperty(obj,propertyName,dateValue);
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("double") && propertyValue!=null && (propertyValue instanceof String) ){
						Double dateValue = Double.valueOf((String)propertyValue);
						PropertyUtils.setProperty(obj,propertyName,dateValue);
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("double") && propertyValue!=null && (propertyValue instanceof Integer) ){
						Double dateValue = Double.valueOf(propertyValue.toString());
						PropertyUtils.setProperty(obj,propertyName,dateValue);
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("string") && propertyValue!=null && !(propertyValue instanceof String) ){
						PropertyUtils.setProperty(obj,propertyName,propertyValue.toString());
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("int") && propertyValue!=null && (propertyValue instanceof String || propertyValue instanceof Double) ){
						Double doubleValue = Double.valueOf(propertyValue.toString());
						PropertyUtils.setProperty(obj,propertyName,doubleValue.intValue());
					}else{
						try{
							PropertyUtils.setProperty(obj,propertyName,propertyValue);
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
				}else{
					PropertyUtils.setProperty(obj,propertyName,null);
				}
			}
		}
		return obj;
	}
	
	public static JSONObject applyEntity2JSONObject(ShiTiLeiI stl,BaseEntityI obj, JSONArray fields) throws Exception{
		//
		Object value = null;
		String fieldName = null;
		JSONObject field = null;
		
		JSONObject row = new JSONObject();
//		row.setAllowNestedValues(false);
		//主键字段必须加入
		try{
			value = PropertyUtils.getProperty(obj,stl.getZhuJianLie());
		}catch (Exception e){
			value= null;
			e.printStackTrace();
		}
		row.put(stl.getZhuJianLie(), value);
		//其余字段
		for(int i=0;i<fields.size();i++){
			field = fields.getJSONObject(i);
			fieldName = field.getString("name");
			if(!fieldName.equals(stl.getZhuJianLie())){
				value = PropertyUtils.getNestedProperty(obj, fieldName);
//				try{
//					value = PropertyUtils.getNestedProperty(obj, fieldName);
//				}catch (Exception e){
//					value= null;
//					String objectString = "";
//					try{
//						objectString = ""+obj;
//					}catch (Exception eq){
//						System.err.println("从对象("+stl.getShiTiLeiMC()+":"+objectString+")取字段"+fieldName+"的值失败！");
//						System.err.println("		:"+eq.getMessage());
//					}
//				}
					
				if(value!=null){
					ZiDuanI ziDuan = stl.getZiDuan(fieldName);
					if(ziDuan == null){
						throw new Exception("applyEntity2JSONObject：实体类("+stl.getShiTiLeiMC()+")中不存在此字段("+fieldName+")");
					}
					ZiDuanLXI ziDuanlx = ziDuan.getZiDuanLX();
					if(ziDuanlx == null){
						throw new Exception("applyEntity2JSONObject：实体类("+stl.getShiTiLeiMC()+")的字段("+fieldName+")未定义字段类型");
					} 
					
					String ziDuanLXDH = ziDuanlx.getZiDuanLXDH();
					if("object".equals(ziDuanLXDH) || "set".equals(ziDuanLXDH)){
						ShiTiLeiI glstl = ziDuan.getGuanLianSTL();
						
						JSONArray subFields = null;
						if(glstl!=null){
							if(field.has("fields")){
								subFields = field.getJSONArray("fields");
							}else{
								subFields = JSONArray.fromObject("[{name:'"+glstl.getXianShiLie()+"'}]");
							}
						}
						
						if(ziDuanLXDH.equals("object")){
							value = applyEntity2JSONObject(glstl,(BaseEntityI)value,subFields);
						}else {
							value = applyList2JSONArray(glstl,(Collection<BaseEntityI>)value,subFields);
						}
					}else if("date".equals(ziDuanlx.getZiDuanLXDH())){
						value = dtf.format((Date)value);
					}else if("month".equals(ziDuanlx.getZiDuanLXDH())){
						value = smf.format((Date)value);
					}
				}
				row.put(fieldName, value);
			}
		}
		return row;
	}
	
	public static JSONArray applyList2JSONArray(ShiTiLeiI stl,Collection<BaseEntityI> objs, JSONArray fields) throws Exception{
		JSONArray data = new JSONArray();
		for(BaseEntityI obj:objs){
			data.add(applyEntity2JSONObject(stl,obj,fields));
		}
		return data;
	}



	public static JSONArray applyResultset2JSONArray(ResultSet rs,JSONArray fields) throws Exception{
		JSONArray rows = new JSONArray();
		while(rs.next()){
			JSONObject obj = new JSONObject();
			//检查
			for(int i=0;i<fields.size();i++){
				String fieldName = null;
				Object value = null;
				String fieldType = "string";
				
				Object fieldDef = fields.get(i);
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
		return rows;
	}
}

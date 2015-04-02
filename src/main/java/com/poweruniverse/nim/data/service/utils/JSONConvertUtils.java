package com.poweruniverse.nim.data.service.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

import com.poweruniverse.nim.base.utils.NimJSONArray;
import com.poweruniverse.nim.base.utils.NimJSONObject;
import com.poweruniverse.nim.data.entity.sys.ShiTiLei;
import com.poweruniverse.nim.data.entity.sys.ZiDuan;
import com.poweruniverse.nim.data.entity.sys.ZiDuanLX;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;

public class JSONConvertUtils {
	public static SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	public static SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sbf = new SimpleDateFormat("MM-dd");

	
	public static NimJSONArray Entities2JSONArray(ShiTiLei stl,Collection<?> objs,JSONArray fields) throws Exception{
		//
		NimJSONArray rows = new NimJSONArray();
		//
		if(objs!=null && objs.size()>0){
			Iterator<?> objIts = objs.iterator();
			while(objIts.hasNext()){
				NimJSONObject row = Entity2JSONObject(stl, objIts.next(),fields);
				//如果需要功能操作权限信息
				rows.add(row);
			}
		}
		return rows;
	}
	
	public static NimJSONObject Entity2JSONObject(ShiTiLei stl,Object obj, JSONArray fields){
		//
		//
		Object value = null;
		String fieldName = null;
		JSONObject field = null;
		NimJSONObject row = new NimJSONObject();
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
				ZiDuan ziDuan = null;
				ZiDuanLX ziDuanlx =null;
				try{
					value = PropertyUtils.getNestedProperty(obj, fieldName);
				}catch (Exception e){
					value= null;
					e.printStackTrace();
				}
				try{
					ziDuan = stl.getZiDuan(fieldName);
					ziDuanlx = ziDuan.getZiDuanLX();
				}catch (Exception e){
					e.printStackTrace();
				}
				
				if(ziDuanlx == null){
					System.out.println("实体类("+stl.getShiTiLeiMC()+")的字段("+fieldName+")未定义字段类型");
				}else if(value!=null){
					ShiTiLei glstl = ziDuan.getGuanLianSTL();
					
					JSONArray subFields = null;
					if(glstl!=null){
						if(field.has("fields")){
							subFields = field.getJSONArray("fields");
						}else{
							subFields = JSONArray.fromObject("[{name:'"+glstl.getXianShiLie()+"'}]");
						}
					}
					
					if(ZiDuanLX.isObjectType(ziDuanlx.getZiDuanLXDH())){
						value = Entity2JSONObject(glstl,value,subFields);
					}else if(ZiDuanLX.isSetType(ziDuanlx.getZiDuanLXDH())){
						NimJSONArray data = new NimJSONArray();
						Iterator<?> objIts = ((Set<?>)value).iterator();
						while(objIts.hasNext()){
							data.add(Entity2JSONObject(glstl,objIts.next(),subFields));
						}
						value = data;
					}else if(ziDuanlx.getZiDuanLXDM().intValue() == ZiDuanLX.ZiDuanLX_DATE){
						value = dtf.format(value);
//					}else if("null".equals(value)){
//						//NimJSON toString 的时候 会替换为"null"
//						value = "'null'";
					}
				}
				//检查field是否nested (aa.bb.cc)
				if(fieldName.indexOf("\\.")>0){
					List<String> paths = new ArrayList<String>(Arrays.asList(fieldName.split("\\.")));
					NimJSONObject tmpRow = row;
					for(int ii=0;ii<paths.size()-1;ii++){
						if(tmpRow.get(paths.get(ii))==null){
							tmpRow.put(paths.get(ii), new JSONObject());
						}
						tmpRow= tmpRow.getJSONObject(paths.get(ii));
					}
					tmpRow.put(paths.get(paths.size()-1), value);
				}else{
					row.put(fieldName, value);
				}
			}
		}
		return row;
	}
	
	public static void applyJson2XML(Element cfgEl,JSONObject cfgJson){
		for(Object keyObj:cfgJson.keySet()){
			String keyString = keyObj.toString();
			if(!keyString.equals("children") && !keyString.equals("xmlElName") && !keyString.startsWith("_") && !(cfgJson.get(keyString) instanceof JSONNull)){
//				String valueString = cfgJson.getString(keyString);
//				if(valueString.equals("\"null\"")){
//					valueString = "null";
//				}
				cfgEl.addAttribute(keyString, cfgJson.getString(keyString));
			}
		}
		if(cfgJson.containsKey("children")){
			JSONArray children = cfgJson.getJSONArray("children");
			for(int i=0;i<children.size();i++){
				JSONObject childrenJson = children.getJSONObject(i);
				
				Element childrenEl = cfgEl.addElement(childrenJson.getString("xmlElName"));
				
				applyJson2XML(childrenEl,childrenJson);
			}
		}
	}
	
	public static JSONObject applyXML2Json(Element cfgEl,boolean cascade){
		JSONObject cfgJson = new JSONObject();
//		cfgJson.put("component", cfgEl.attributeValue("component"));
		
		for(Object attribute:cfgEl.attributes()){
			DefaultAttribute dAttr = (DefaultAttribute)attribute;
			cfgJson.put(dAttr.getName(), dAttr.getValue());
		}
		//读取type定义
		if(cascade){
			JSONArray childArray = new JSONArray();
			List<Element> childEls = cfgEl.elements();
			for(Element childEl:childEls){
				childArray.add(applyXML2Json(childEl,cascade));
			}
			cfgJson.put("children", childArray);
		}
		return cfgJson;
	}

	//将客户端修改的值 应用到原始数据对象中 同时 记录
	public static EntityI JSON2Entity(ShiTiLei stl,EntityI obj,JSONObject dataObj,Map<JSONObject,EntityI> linkMap1) throws Exception{
		if(dataObj==null) return obj;
		//在
		if(linkMap1!=null){
			linkMap1.put(dataObj, obj);
		}
		//根据
		Iterator<?> properties = dataObj.keys();
		while(properties.hasNext()){
			String propertyName = (String)properties.next();
			if(!propertyName.startsWith("_")){
				ZiDuan zd = null;
				try {
					zd = stl.getZiDuan(propertyName);
				} catch (Exception e1) {
				}
				//不处理提交的数据中 当前实体类定义以外的数据 
				if(zd!=null){
					ZiDuanLX zdlx = zd.getZiDuanLX();
					if(zdlx==null){
						throw new Exception("实体类\""+stl.getShiTiLeiMC()+"\"中字段\""+zd.getZiDuanDH()+"\"的字段类型为空！");
					}
					//
					Object propertyValue = dataObj.get(propertyName);
					if(propertyValue instanceof JSONNull){
						propertyValue = null;
					}
					if(ZiDuanLX.isObjectType(zdlx.getZiDuanLXDH())){
						if(propertyValue!=null ){
							ShiTiLei substl = zd.getGuanLianSTL();
							Object value = Class.forName(substl.getShiTiLeiClassName()).newInstance();
							
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
									throw new Exception("未知的主键值:"+substlPkValue+(substlPkValue==null?"!":("类型("+substlPkValue.getClass()+")!")));
								}
							}
							
							PropertyUtils.setProperty(value,substl.getZhuJianLie(),substlPkValue);
							PropertyUtils.setProperty(obj,propertyName,value);
						}else{
							PropertyUtils.setProperty(obj,propertyName,null);
						}
						
					}else if(ZiDuanLX.isSetType(zdlx.getZiDuanLXDH())){
						//父对象的类中 关于子类集合操作的几个方法
						Class<?> cls = Class.forName(stl.getShiTiLeiClassName());
						Method appendMethod = cls.getMethod("addTo"+propertyName,new Class[]{Object.class,Object.class});
						Method removeMethod = cls.getMethod("removeFrom"+propertyName,new Class[]{Object.class,Object.class});
						Method getMethod = cls.getMethod("get"+propertyName+"ById",new Class[]{Object.class});
						
						ShiTiLei substl = zd.getGuanLianSTL();
						Object subObj = null;
						if(propertyValue instanceof JSONObject){
							@SuppressWarnings("unchecked")
							JSONArray modified = (JSONArray)((JSONObject)propertyValue).get("modified");
							if(modified!=null && modified.size()>0){
								for(int i =0;i<modified.size();i++){
									JSONObject subObjData = modified.getJSONObject(i);
									Object subPrimaryValue = subObjData.get(substl.getZhuJianLie());
									subObj = getMethod.invoke(obj, new Object[]{subPrimaryValue});
									subObj = JSON2Entity(substl,(EntityI)subObj,subObjData,linkMap1);
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
										subObj = mtd.invoke(obj, new Object[]{});
									}catch(Exception e){
										subObj = Class.forName(substl.getShiTiLeiClassName()).newInstance();
										PropertyUtils.setProperty(subObj,zd.getGuanLianFLZD().getZiDuanDH(),obj);
									}
									//填充子对象的内容
									subObj = JSON2Entity(substl,(EntityI)subObj,subObjData,linkMap1);
									appendMethod.invoke(obj, new Object[]{obj,subObj});
								}
							}
							@SuppressWarnings("unchecked")
							JSONArray deleted = (JSONArray)((JSONObject)propertyValue).get("deleted");
							if(deleted!=null && deleted.size()>0){
								for(int i =0;i<deleted.size();i++){
									Object subPrimaryValue = deleted.getJSONObject(i).get(substl.getZhuJianLie());
									subObj = getMethod.invoke(obj, new Object[]{subPrimaryValue});
									removeMethod.invoke(obj, new Object[]{obj,subObj});
								}
							}
							
						}else if (propertyValue instanceof JSONArray){
							@SuppressWarnings("unchecked")
							JSONArray inserted = (JSONArray)propertyValue;
							if(inserted!=null && inserted.size()>0){
								for(int i =0;i<inserted.size();i++){
									//新增子对象
									try{
										Class<?> subCls = Class.forName(stl.getShiTiLeiClassName());
										Method mtd = subCls.getMethod("new"+propertyName+"ByParent",new Class[]{});
										subObj = mtd.invoke(obj, new Object[]{});
									}catch(Exception e){
										subObj = Class.forName(substl.getShiTiLeiClassName()).newInstance();
										PropertyUtils.setProperty(subObj,zd.getGuanLianFLZD().getZiDuanDH(),obj);
									}
									//填充子对象的内容
									subObj = JSON2Entity(substl,(EntityI)subObj,inserted.getJSONObject(i),linkMap1);
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
						}else if(dateString.length() == 21){//langll 处理采购网同步日期 计入毫秒日期
							dateValue = dsf.parse(dateString);
						}else{
							System.out.println("错误的日期数据("+propertyName+")："+dateString+"格式必须为yyyy-MM-dd HH:mm:ss或yyyy-MM-dd");
						}
						PropertyUtils.setProperty(obj,propertyName,dateValue);
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("double") && propertyValue!=null&& !propertyValue.equals("") && (propertyValue instanceof String) ){
						Double dateValue = Double.valueOf((String)propertyValue);
						PropertyUtils.setProperty(obj,propertyName,dateValue);
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("double") && propertyValue!=null && !propertyValue.equals("")&& (propertyValue instanceof Integer) ){
						Double dateValue = Double.valueOf(propertyValue.toString());
						PropertyUtils.setProperty(obj,propertyName,dateValue);
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("string") && propertyValue!=null && !(propertyValue instanceof String) ){
						PropertyUtils.setProperty(obj,propertyName,propertyValue.toString());
					}else if(zdlx.getZiDuanLXDH().equalsIgnoreCase("int") && propertyValue!=null && !propertyValue.equals("")&& (propertyValue instanceof String || propertyValue instanceof Double) ){
						Double doubleValue = Double.valueOf(propertyValue.toString());
						PropertyUtils.setProperty(obj,propertyName,doubleValue.intValue());
					}else{
						try{
							PropertyUtils.setProperty(obj,propertyName,propertyValue);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
		return obj;
	}
}

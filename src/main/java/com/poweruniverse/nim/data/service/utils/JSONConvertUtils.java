package com.poweruniverse.nim.data.service.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

import com.poweruniverse.nim.data.entity.ShiTiLei;
import com.poweruniverse.nim.data.entity.ZiDuan;
import com.poweruniverse.nim.data.entity.ZiDuanLX;

public class JSONConvertUtils {
	private static SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");

	
	public static JSONArray objectList2JSONArray(ShiTiLei stl,Collection<?> objs,JSONArray fields) throws Exception{
		//
		JSONArray rows = new JSONArray();
		//
		if(objs!=null && objs.size()>0){
			Iterator<?> objIts = objs.iterator();
			while(objIts.hasNext()){
				JSONObject row = object2JSONObject(stl, objIts.next(),fields);
				//如果需要功能操作权限信息
				rows.add(row);
			}
		}
		return rows;
	}
	
	public static JSONObject object2JSONObject(ShiTiLei stl,Object obj, JSONArray fields){
		//
		//
		Object value = null;
		String fieldName = null;
		JSONObject field = null;
		JSONObject row = new JSONObject();
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
						value = object2JSONObject(glstl,value,subFields);
					}else if(ZiDuanLX.isSetType(ziDuanlx.getZiDuanLXDH())){
						JSONArray data = new JSONArray();
						Iterator<?> objIts = ((Set<?>)value).iterator();
						while(objIts.hasNext()){
							data.add(object2JSONObject(glstl,objIts.next(),subFields));
						}
						value = data;
					}else if(ziDuanlx.getZiDuanLXDM().intValue() == ZiDuanLX.ZiDuanLX_DATE){
						value = dtf.format(value);
					}
				}
				//检查field是否nested (aa.bb.cc)
				if(fieldName.indexOf("\\.")>0){
					List<String> paths = new ArrayList<String>(Arrays.asList(fieldName.split("\\.")));
					JSONObject tmpRow = row;
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


}

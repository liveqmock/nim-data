package com.poweruniverse.nim.data.pageParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

public class DesignerElParser {

	public static Map<String,JSONObject> parseEl(List<?> defEls) throws Exception{
		Map<String,JSONObject> ret = new HashMap<String,JSONObject>();
		for(int i=0;i<defEls.size();i++){
			Element defEl = (Element)defEls.get(i) ;
			JSONObject defObj = new JSONObject();

			String defName = defEl.attributeValue("name");
			
			for(Object attribute:defEl.attributes()){
				DefaultAttribute dAttr = (DefaultAttribute)attribute;
				defObj.put(dAttr.getName(), dAttr.getValue());
			}
			
			JSONArray propertiesArray = new JSONArray();
			JSONArray extensionsArray = new JSONArray();
			JSONArray eventsArray = new JSONArray();
			JSONArray structureArray = new JSONArray();
			JSONArray dependenciesArray = new JSONArray();
			
			//基础属性
			if(defEl.element("properties")!=null){
				List<Element> propertyDefEls = defEl.element("properties").elements("property");
				for(Element propertyDefEl:propertyDefEls){
					JSONObject propertyDefObj = new JSONObject();
					for(Object attribute:propertyDefEl.attributes()){
						DefaultAttribute dAttr = (DefaultAttribute)attribute;
						propertyDefObj.put(dAttr.getName(), dAttr.getValue());
					}
					propertiesArray.add(propertyDefObj);
				}
				defObj.put("properties", propertiesArray);
			}
			//基础扩展
			if(defEl.element("extensions")!=null){
				List<Element> extensionDefEls = defEl.element("extensions").elements("property");
				for(Element extensionDefEl:extensionDefEls){
					JSONObject extensionDefObj = new JSONObject();
					for(Object attribute:extensionDefEl.attributes()){
						DefaultAttribute dAttr = (DefaultAttribute)attribute;
						extensionDefObj.put(dAttr.getName(), dAttr.getValue());
					}
					extensionsArray.add(extensionDefObj);
				}
				defObj.put("extensions", extensionsArray);
			}
			//基础事件
			if(defEl.element("events")!=null){
				List<Element> eventDefEls = defEl.element("events").elements("event");
				for(Element eventDefEl:eventDefEls){
					JSONObject eventDefObj = new JSONObject();
					//<property code="compnentType" label="控件类型" type="string" allowEmpty="false" info="" default=""/>
					for(Object attribute:eventDefEl.attributes()){
						DefaultAttribute dAttr = (DefaultAttribute)attribute;
						eventDefObj.put(dAttr.getName(), dAttr.getValue());
					}
					eventsArray.add( eventDefObj);
				}
				defObj.put("events", eventsArray);
			}
			//基础结构
			if(defEl.element("structure")!=null){
				List<Element> itemDefEls = defEl.element("structure").elements("item");
				for(Element itemDefEl:itemDefEls){
					JSONObject itemDefObj = new JSONObject();
					//<property code="compnentType" label="控件类型" type="string" allowEmpty="false" info="" default=""/>
					for(Object attribute:itemDefEl.attributes()){
						DefaultAttribute dAttr = (DefaultAttribute)attribute;
						itemDefObj.put(dAttr.getName(), dAttr.getValue());
					}
					structureArray.add(itemDefObj);
				}
				defObj.put("structure", structureArray);
			}
			//依赖的元素
			if(defEl.element("dependencies")!=null){
				List<Element> dependencyEls = defEl.element("dependencies").elements("dependency");
				for(Element dependencyEl:dependencyEls){
					JSONObject dependencyObj = new JSONObject();
					for(Object attribute:dependencyEl.attributes()){
						DefaultAttribute dAttr = (DefaultAttribute)attribute;
						dependencyObj.put(dAttr.getName(), dAttr.getValue());
					}
					dependenciesArray.add(dependencyObj);
				}
				defObj.put("dependencies", dependenciesArray);
			}
			ret.put(defName,defObj);
		}
		return ret;
	}
	

}

package com.poweruniverse.nim.data.pageParser;

import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class TabpageElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static String parseTabpageEl(Element tabEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String tabScriptContent = "";
		
		JSONObject tabObj = JSONConvertUtils.applyXML2Json(tabEl,false);
		JSONObject listenersObj = new JSONObject();
		if(tabObj.has("onRender")){
			listenersObj.put("onRender", tabObj.getString("onRender"));
			tabObj.remove("onRender");
		}
		if(tabObj.has("onSelect")){
			listenersObj.put("onSelect", tabObj.getString("onSelect"));
			tabObj.remove("onSelect");
		}
		tabObj.put("listenerDefs", listenersObj);
		
		String tabVarName = "_tab_"+tabObj.getString("name");
		if("tabSelector".equals(tabEl.attributeValue("component"))){
			tabScriptContent = "//标签页 "+tabObj.getString("label")+" \n" +
				"var "+tabVarName+" = LUI.Tab.createNew(\n" +
				tabObj.toString()+"\n"+
				");\n\n";
		}else if("tabGenerator".equals(tabEl.attributeValue("component"))){
			
		}

		//注册
		if(isIndependent){
			tabScriptContent += "LUI.Page.instance.register('tab',"+tabVarName+");\n";
		}else{
			tabScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('tab',"+tabVarName+");\n";
		}

		return tabScriptContent;
	}
	

}

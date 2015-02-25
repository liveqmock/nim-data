package com.poweruniverse.nim.data.pageParser;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class ImportElParser {

	/**
	 * 导入子页面的解析
	 */
	public static String parseImportEl(Element importEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String importScriptContent = "";
		
		JSONObject importObj = JSONConvertUtils.applyXML2Json(importEl,false);
		
//		String name = importEl.attributeValue("name");
//		String label = importEl.attributeValue("label");
//		String renderto = importEl.attributeValue("renderto");
//		String pageURL = importEl.attributeValue("pageURL");
//		String autoLoad = importEl.attributeValue("autoLoad");
//		if(autoLoad==null){
//			autoLoad = "true";
//		}
		
		//page events
		JSONObject listenersObj = new JSONObject();

		String onLoad = importEl.attributeValue("onLoad");
		listenersObj.put("onLoad",onLoad);
		
		importObj.put("listenerDefs", listenersObj);
		
		JSONArray parameters =  DatasourceElParser.getParametersFromEl(importEl,root,params);
		importObj.put("parameters", parameters);
		
		String importPageVarName = null;
		String importCmpType = importEl.attributeValue("component");
		if("importSubpage".equals(importCmpType)){
			importPageVarName = "_import_page_"+importObj.getString("name");
			importScriptContent = "//导入页面 "+importEl.attributeValue("label")+" \n" +
				"var "+importPageVarName+" = LUI.ImportPage.createNew(\n" +
					importObj.toString()+"\n"+
				");\n\n";
		}
		//注册
		if(isIndependent){
			importScriptContent += "LUI.Page.instance.register('importPage',"+importPageVarName+");\n";
		}else{
			importScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('importPage',"+importPageVarName+");\n";
		}
		return importScriptContent;
	}


}

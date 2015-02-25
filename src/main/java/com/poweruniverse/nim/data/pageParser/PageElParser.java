package com.poweruniverse.nim.data.pageParser;

import net.sf.json.JSONObject;

import org.dom4j.Element;

public class PageElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parsePageEl(Element pageEl,String pageName,String pageUrl,JSONObject params,boolean isIndependent,Integer yongHuDM) throws Exception{
		JSONObject ret = new JSONObject();
		
		if(params==null){
			params = new JSONObject();
		}
		String needsLogin = pageEl.attributeValue("needsLogin");
		
		String pageTitle = pageEl.attributeValue("title");
		//临时代码 将页面标题字段名 从title改为label
		if(pageEl.attributeValue("label")!=null){
			pageTitle = pageEl.attributeValue("label");
		}
		
		JSONObject listenersObj = new JSONObject();
		listenersObj.put("onLoad",pageEl.attributeValue("onLoad"));
		listenersObj.put("onClose",pageEl.attributeValue("onClose"));
		
//		String pageCmpType = pageEl.attributeValue("component");
		if(isIndependent){
			//当页面（无论是page还是subpage）作为独立页面被打开时 执行这里的解析
			ret.put("pageScriptContent", "//当前页面\n" +
					"	LUI.Page.createNew({\n" +
					"	title:'" + pageTitle +"',\n" +
					"	needsLogin:" + "true".equals(needsLogin) +",\n" +
					"	listenerDefs:" +listenersObj.toString()+",\n"+
					"	params:" +params.toString()+"\n"+
				"});\n\n");
			
		}else{
			String width = pageEl.attributeValue("width");
			String height = pageEl.attributeValue("height");
			
//			ret.put("name", name);
//			ret.put("width", width);
//			ret.put("height", height);
			
			ret.put("pageScriptContent", "//当前页面\n" +
					"LUI.Subpage.createNew({\n" +
					"	name:'" + pageName +"',\n" +
					"	title:'" + pageTitle +"',\n" +
					"	pageUrl:'" + pageUrl +"',\n" +
					"	needsLogin:" + "true".equals(needsLogin) +",\n" +
					"	width:'" + width +"',\n" +
					"	height:'" + height +"',\n" +
					"	listenerDefs:" +listenersObj.toString()+",\n"+
					"	params:" +params.toString()+"\n"+
				"});\n\n");
		}
		ret.put("needsLogin", "true".equals(needsLogin));
		return ret;
	}


}

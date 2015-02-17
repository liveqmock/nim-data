package com.poweruniverse.nim.data.pageParser;

import net.sf.json.JSONObject;

import org.dom4j.Element;

public class PageElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parsePageEl(Element pageEl,String pageUrl,JSONObject params,boolean isIndependent,Integer yongHuDM) throws Exception{
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
		
		String pageCmpType = pageEl.attributeValue("component");
		if("page".equals(pageCmpType) || isIndependent){
			ret.put("pageScriptContent", "//当前页面\n" +
					"LUI.Page.createNew({\n" +
					"title:'" + pageTitle +"',\n" +
					"needsLogin:" + "true".equals(needsLogin) +",\n" +
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"params:" +params.toString()+"\n"+
				"});\n\n");
			
		}else if("subpage".equals(pageCmpType)){
			
			String name = pageEl.attributeValue("name");
			String width = pageEl.attributeValue("width");
			String height = pageEl.attributeValue("height");
			
//			ret.put("name", name);
//			ret.put("width", width);
//			ret.put("height", height);
			
			ret.put("pageScriptContent", "//当前页面\n" +
					"var _subpage_"+name.replaceAll("-", "_")+"_widget = LUI.Subpage.createNew({\n" +
					"name:'" + name +"',\n" +
					"title:'" + pageTitle +"',\n" +
					"pageUrl:'" + pageUrl +"',\n" +
					"needsLogin:" + "true".equals(needsLogin) +",\n" +
					"width:'" + width +"',\n" +
					"height:'" + height +"',\n" +
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"params:" +params.toString()+"\n"+
				"});\n\n");
		}
		ret.put("needsLogin", "true".equals(needsLogin));
		return ret;
	}


}

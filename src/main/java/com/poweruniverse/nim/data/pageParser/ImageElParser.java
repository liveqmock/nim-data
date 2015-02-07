package com.poweruniverse.nim.data.pageParser;

import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.base.utils.FreemarkerUtils;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class ImageElParser {

	/**
	 * 导入子页面的解析
	 */
	public static String parseImageEl(Element imageEl,JSONObject params,Map<String, Object> root,Integer yongHuDM) throws Exception{
		String imageScriptContent = "";
		
		JSONObject imageObj = JSONConvertUtils.applyXML2Json(imageEl,false);
		
		//page events
//		JSONObject listenersObj = new JSONObject();
//		String onLoad = imageEl.attributeValue("onLoad");
//		listenersObj.put("onLoad",onLoad);
//		imageObj.put("listenerDefs", listenersObj);
		
		String id= imageObj.getString("id"); 
		//是否动态value
		if(id!=null && (id.indexOf("$")>=0 || id.indexOf("<#") >=0)){
			String parseString = id;
			if(params!=null){
				parseString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+parseString;
			}
			id = FreemarkerUtils.processTemplate(parseString, root,null);
			imageObj.put("id", id);
		}
		
		
		String imageCmpType = imageEl.attributeValue("component");
		if("workflowImg".equals(imageCmpType)){
			imageScriptContent = "//流程图 "+imageEl.attributeValue("label")+" \n" +
				"LUI.WorkflowImg.createNew(\n" +
					imageObj.toString()+"\n"+
				");\n\n";
		}
		return imageScriptContent;
	}


}

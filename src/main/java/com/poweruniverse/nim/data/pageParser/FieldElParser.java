package com.poweruniverse.nim.data.pageParser;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class FieldElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parseFieldEl(Element fieldEl,JSONObject params,Map<String, Object> root,Integer yongHuDM) throws Exception{
		String fieldsPreScript = "";

		JSONObject fieldObj = JSONConvertUtils.applyXML2Json(fieldEl,false);
		
		//处理字段的数据源定义
		Element fieldDatasetEl = fieldEl.element("dataset");
		if(fieldDatasetEl!=null){
			JSONObject fieldDatasetResult = DatasourceElParser.parseDatasetEl(fieldDatasetEl, params, root, yongHuDM);
			fieldsPreScript += fieldDatasetResult.getString("dataScriptContent");
			fieldsPreScript += fieldDatasetResult.getString("dataLoadContent");
			
			fieldObj.put("datasourceName", fieldDatasetResult.getString("datasourceName"));
		}
		//处理字段的子表格定义
		Element fieldGridEl = fieldEl.element("grid");
		if(fieldGridEl!=null){
			JSONObject fieldGridResult = GridElParser.parseGridEl(fieldGridEl, params, root, yongHuDM);
			 
			fieldsPreScript += fieldGridResult.getString("gridScriptContent");
			
			fieldObj.put("gridName", fieldGridResult.getString("gridName"));
		}
		//处理字段的子树定义
		Element fieldTreeEl = fieldEl.element("tree");
		if(fieldTreeEl!=null){
			JSONObject fieldTreeResult = TreeElParser.parseTreeEl(fieldTreeEl, params, root, yongHuDM);
			
			fieldsPreScript += fieldTreeResult.getString("treeScriptContent");
			fieldObj.put("treeName", fieldTreeResult.getString("treeName"));
		}
		//处理字段的按钮定义
		Element filedButtonsEl = fieldEl.element("buttons");
		if(filedButtonsEl != null){
			JSONArray filedButtonArray = new JSONArray();
			List<Element> filedButtonEls = filedButtonsEl.elements();
			for(Element filedButtonEl : filedButtonEls){
				JSONObject filedButtonObj = JSONConvertUtils.applyXML2Json(filedButtonEl,true);
				filedButtonArray.add(filedButtonObj);
			}
			fieldObj.put("buttons", filedButtonArray);
		}
		
	
		
		JSONObject ret = new JSONObject();
		ret.put("field", fieldObj);
		ret.put("fieldPreScript", fieldsPreScript);
		return ret;
	}
	

}

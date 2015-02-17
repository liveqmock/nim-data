package com.poweruniverse.nim.data.pageParser;

import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class TreeElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parseTreeEl(Element treeEl,JSONObject params,Map<String, Object> root,Integer yongHuDM) throws Exception{
		String treeScriptContent = "";
		String dataLoadContent = "";
		
		JSONObject treeCfgObj = JSONConvertUtils.applyXML2Json(treeEl,false);
		//取得其中的监听定义
		JSONObject listenersObj = new JSONObject();
		for(Object keyName:treeCfgObj.keySet()){
			if(((String)keyName).startsWith("on")){
				String listener = treeCfgObj.getString((String)keyName);
				listenersObj.put(keyName, listener);
			}
		}
		treeCfgObj.put("listenerDefs", listenersObj);
		//删除监听
		for(Object listenerName:listenersObj.keySet()){
			treeCfgObj.remove(listenerName);
		}
		
		
		//检查datasource设置
		Element datasourceEl = treeEl.element("dataset");
		if(datasourceEl!=null){
			
			JSONObject fieldDatasetResult = DatasourceElParser.parseDatasetEl(datasourceEl, params, root, yongHuDM);
			treeScriptContent += fieldDatasetResult.getString("dataScriptContent");
			dataLoadContent += fieldDatasetResult.getString("dataLoadContent");
			
			treeCfgObj.put("datasourceName", fieldDatasetResult.getString("datasourceName"));
		}
		
		//创建树
		if("tree".equals(treeEl.attributeValue("component"))){
			treeScriptContent+="\n//创建树"+treeCfgObj.getString("label")+":"+treeCfgObj.getString("name")+"对象\n";
			treeScriptContent+="var _tree_"+treeCfgObj.getString("name")+" = LUI.Tree.DatasourceTree.createNew(\n" +
					treeCfgObj+"\n"+
			");\n";
			//注册
			treeScriptContent += "LUI.Page.instance.register('tree',_tree_"+treeCfgObj.getString("name")+");\n";
		}else if("subTree".equals(treeEl.attributeValue("component"))){
			treeScriptContent+="\n//创建树"+treeCfgObj.getString("label")+":"+treeCfgObj.getString("name")+"对象\n";
			treeScriptContent+="var _subTree_"+treeCfgObj.getString("name")+" = LUI.Tree.SubTree.createNew(\n" +
					treeCfgObj+"\n"+
			");\n";
			//注册
			treeScriptContent += "LUI.Page.instance.register('tree',_subTree_"+treeCfgObj.getString("name")+");\n";
		}
	
		JSONObject ret = new JSONObject();
		ret.put("treeScriptContent", treeScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("treeName", treeCfgObj.getString("name"));
		return ret;
	}

}

package com.poweruniverse.nim.data.pageParser;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class GridElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parseGridEl(Element gridEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String gridScriptContent = "";
		String dataLoadContent = "";
		 
		JSONObject gridCfgObj = JSONConvertUtils.applyXML2Json(gridEl,false);
		//取得其中的监听定义
		JSONObject listenersObj = new JSONObject();
		for(Object keyName:gridCfgObj.keySet()){
			if(((String)keyName).startsWith("on")){
				String listener = gridCfgObj.getString((String)keyName);
				listenersObj.put(keyName, listener);
			}
		}
		//历史原因 兼容老的定义
		if(listenersObj.containsKey("onRender")){
			listenersObj.put("onGridRendered", listenersObj.getString("onRender"));
		}
		
		gridCfgObj.put("listenerDefs", listenersObj);
		//删除监听
		for(Object listenerName:listenersObj.keySet()){
			gridCfgObj.remove(listenerName);
		}
		
//		String name = gridEl.attributeValue("name");
//		String label = gridEl.attributeValue("label");
//		String autoLoad= gridEl.attributeValue("autoLoad"); 
		
		//检查datasource设置
		Element datasourceEl = gridEl.element("dataset");
		if(datasourceEl!=null){
			
			JSONObject fieldDatasetResult = DatasourceElParser.parseDatasetEl(datasourceEl, params, root, yongHuDM,isIndependent, pageName);
			gridScriptContent += fieldDatasetResult.getString("dataScriptContent");
			dataLoadContent += fieldDatasetResult.getString("dataLoadContent");
			
			gridCfgObj.put("datasourceName", fieldDatasetResult.getString("datasourceName"));
		}
		
		String gridVarName = null;
		
		if("displayGrid".equals(gridEl.attributeValue("component"))){
			//检查colums设置
			JSONArray colArray = new JSONArray();
			Element columnsEl = gridEl.element("columns");
			if(columnsEl!=null){
				List<Element> columnEls = columnsEl.elements("column");
				if(columnEls!=null){
					for(Element columnEl : columnEls){
						
						JSONObject columnCfgObj = JSONConvertUtils.applyXML2Json(columnEl,false);
						//临时代码：老页面中可能有遗留的 表列中renderTemplate属性为空的情况 
						if(!columnCfgObj.containsKey("renderTemplate")){
							columnCfgObj.put("renderTemplate", "{{"+columnCfgObj.getString("name")+"}}");
						}
						colArray.add(columnCfgObj);
					}
				}
			}
			gridCfgObj.put("columns", colArray);
			
			gridScriptContent+="\n//生成表格"+gridCfgObj.getString("label")+":"+gridCfgObj.getString("name")+"的显示\n";
												
			gridVarName = "_griddisplay_"+gridCfgObj.getString("name");
			gridScriptContent+="var "+gridVarName+" = LUI.Grid.createNew(\n" +
					gridCfgObj+"\n"+
			");\n";
		}else if("treeDisplayGrid".equals(gridEl.attributeValue("component"))){
			//
			Element treeEl = gridEl.element("tree");
			JSONObject treeObj = JSONConvertUtils.applyXML2Json(treeEl,false);
			gridCfgObj.put("tree", treeObj);
			
			//检查colums设置
			JSONArray colArray = new JSONArray();
			Element columnsEl = gridEl.element("columns");
			if(columnsEl!=null){
				List<Element> columnEls = columnsEl.elements("column");
				if(columnEls!=null){
					for(Element columnEl : columnEls){
						
						JSONObject columnCfgObj = JSONConvertUtils.applyXML2Json(columnEl,false);
						if(!columnCfgObj.containsKey("renderTemplate")){
							columnCfgObj.put("renderTemplate", "{{"+columnCfgObj.getString("name")+"}}");
						}
						colArray.add(columnCfgObj);
					}
				}
			}
			gridCfgObj.put("columns", colArray);
			
			gridScriptContent+="\n//生成树状表格"+gridCfgObj.getString("label")+":"+gridCfgObj.getString("name")+"的显示\n";
												
			gridVarName = "_treegrid_"+gridCfgObj.getString("name");
			gridScriptContent+="var "+gridVarName+" = LUI.Grid.TreeGrid.createNew(\n" +
					gridCfgObj+"\n"+
			");\n";
		
		}else if("treeEditGrid".equals(gridEl.attributeValue("component"))){
		}else if("operateGrid".equals(gridEl.attributeValue("component"))){
		}else if("editGrid".equals(gridEl.attributeValue("component"))){
			//检查colums设置
			JSONArray colArray = new JSONArray();
			Element columnsEl = gridEl.element("columns");
			if(columnsEl!=null){
				List<Element> columnEls = columnsEl.elements("column");
				if(columnEls!=null){
					for(Element columnEl : columnEls){
						JSONObject columnCfgObj = FieldElParser.parseFieldEl(columnEl, params, root, yongHuDM,isIndependent,pageName);
						
						String columnPreScript = columnCfgObj.getString("fieldPreScript");
						if(columnPreScript!=null && columnPreScript.length()>0){
							gridScriptContent += columnPreScript;
						}
						
						colArray.add(columnCfgObj.getJSONObject("field"));
					}
				}
			}
			gridCfgObj.put("columns", colArray);
			gridScriptContent+="\n//生成表格"+gridCfgObj.getString("label")+":"+gridCfgObj.getString("name")+"的编辑\n";
			
			gridVarName = "_grid_edit_"+gridCfgObj.getString("name");
			gridScriptContent+="var "+gridVarName+" = LUI.EditGrid.createNew(\n" +
					gridCfgObj+"\n"+
			");\n";
		}else if("subGrid".equals(gridEl.attributeValue("component"))){
			//检查colums设置
			JSONArray colArray = new JSONArray();
			Element columnsEl = gridEl.element("columns");
			if(columnsEl!=null){
				List<Element> columnEls = columnsEl.elements("column");
				if(columnEls!=null){
					for(Element columnEl : columnEls){
						JSONObject columnCfgObj = FieldElParser.parseFieldEl(columnEl, params, root, yongHuDM,isIndependent,pageName);
						
						String columnPreScript = columnCfgObj.getString("fieldPreScript");
						if(columnPreScript!=null && columnPreScript.length()>0){
							gridScriptContent += columnPreScript;
						}
						
						colArray.add(columnCfgObj.getJSONObject("field"));
					}
				}
			}
			gridCfgObj.put("columns", colArray);
			
			gridScriptContent+="\n//生成子表格"+gridCfgObj.getString("label")+":"+gridCfgObj.getString("name")+"的显示\n";
									
			gridVarName = "_sub_grid_"+gridCfgObj.getString("name");
			gridScriptContent+="var "+gridVarName+" = LUI.SubGrid.createNew(\n" +
					gridCfgObj+"\n"+
			");\n";
		}
		
		//注册
		if(isIndependent){
			gridScriptContent += "LUI.Page.instance.register('"+gridEl.attributeValue("component")+"',"+gridVarName+");\n";
		}else{
			gridScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('"+gridEl.attributeValue("component")+"',"+gridVarName+");\n";
		}

		JSONObject ret = new JSONObject();
		ret.put("gridScriptContent", gridScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("gridName", gridCfgObj.getString("name"));
		return ret;
	}

}

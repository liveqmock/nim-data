package com.poweruniverse.nim.data.pageParser;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.base.utils.FreemarkerUtils;
import com.poweruniverse.nim.data.entity.system.ShiTiLei;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class FormElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parseFormEl(Element formEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String formScriptContent = "";
		String dataLoadContent = "";

		String label = formEl.attributeValue("label");
		String name = formEl.attributeValue("name");
		
		String formVarName = null;
		if("searchToPage".equals(formEl.attributeValue("component"))){
			//查询 结果显示到新页面
			String autoRender = formEl.attributeValue("autoRender"); 
			if(autoRender==null){
				autoRender = "false";
			}
			String renderto = formEl.attributeValue("renderto");
			String renderType = formEl.attributeValue("renderType");
			String target = formEl.attributeValue("target");
			
			//searchToGrid events
			JSONObject listenersObj = new JSONObject();
			listenersObj.put("beforeSearch",formEl.attributeValue("beforeSearch"));
			
			//检查buttons设置
			JSONArray buttonArray = new JSONArray();
			Element formButtonsEl = formEl.element("buttons");
			if(formButtonsEl!=null){
				List<Element> formButtonEls = formButtonsEl.elements("button");
				if(formButtonEls!=null && formButtonEls.size()>0){
					for(Element formButtonEl : formButtonEls){
						JSONObject buttonObj = JSONConvertUtils.applyXML2Json(formButtonEl,true);
						buttonArray.add(buttonObj);
					}
				}
			}
			
			//检查filters设置
			JSONArray filterArray = new JSONArray();
			Element filtersEl = formEl.element("filters");
			if(filtersEl!=null){
				List<Element> filterEls = filtersEl.elements("filter");
				if(filterEls!=null && filterEls.size()>0){
					for(Element filterEl : filterEls){
						JSONObject filterObj = JSONConvertUtils.applyXML2Json(filterEl,true);
						filterArray.add(filterObj);
					}
				}
			}
			
			formScriptContent+="\n//生成查询表单("+label+":"+name+")\n";
			
			formVarName = "_searchToPage_"+name;
			formScriptContent+="var "+formVarName+" = LUI.SearchPageForm.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"renderto:'" +renderto+"',\n"+
					"autoRender:" +autoRender+",\n"+
					"renderType:'" +renderType+"',\n"+
					"target:'" +target+"',\n"+
					"filters:" +filterArray.toString()+",\n"+
					"buttons:" +buttonArray.toString()+",\n"+
					"listenerDefs:" +listenersObj.toString()+"\n"+
			"});\n";
			
		}else if("dataDisplayForm".equals(formEl.attributeValue("component"))){
			
			String renderto = formEl.attributeValue("renderto");
			String renderType = formEl.attributeValue("renderType");
			if(renderType==null){
				renderType = "rela";
			}
			String autoLoad= formEl.attributeValue("autoLoad"); 
			String autoRender= formEl.attributeValue("autoRender"); 
			if(autoRender==null){
				autoRender = autoLoad;
			}
			
			//保存表单时 所使用的参数
			String xiTongDH= formEl.attributeValue("xiTongDH"); 
			String gongNengDH= formEl.attributeValue("gongNengDH"); 
			String caoZuoDH= formEl.attributeValue("caoZuoDH");
			
			//searchToGrid events
			JSONObject listenersObj = new JSONObject();
			listenersObj.put("onLoad",formEl.attributeValue("onLoad"));
			listenersObj.put("onRender",formEl.attributeValue("onRender"));
			listenersObj.put("onSubmit",formEl.attributeValue("onSubmit"));
			
			String fieldsPreScript = "";
			JSONArray fieldArray = new JSONArray();
			Element fieldsEl = formEl.element("fields");
			if(fieldsEl!=null){
				List<Element> fieldEls = fieldsEl.elements("field");
				
				for(Element fieldEl : fieldEls){
					
					JSONObject fieldCfgObj = FieldElParser.parseFieldEl(fieldEl, params, root, yongHuDM,isIndependent,pageName);
					//兼容老的设计器代码
					JSONObject field = fieldCfgObj.getJSONObject("field");
					if("complexField".equals(field.getString("component")) || "simpleField".equals(field.getString("component"))){
						field.put("component", field.getString("fieldType")+"Display");
					}
					
					String fieldPreScript = fieldCfgObj.getString("fieldPreScript");
					if(fieldPreScript!=null && fieldPreScript.length()>0){
						fieldsPreScript += fieldPreScript;
					}
					
					fieldArray.add(field);
				}
			}
			
			JSONArray buttonArray = new JSONArray();
			Element buttonsEl = formEl.element("buttons");
			if(buttonsEl!=null){
				List<Element> buttonEls = buttonsEl.elements();
				for(Element buttonEl : buttonEls){
					JSONObject buttonObj = JSONConvertUtils.applyXML2Json(buttonEl,true);
					buttonArray.add(buttonObj);
				}
			}
			
			formScriptContent+="\n//生成单表编辑表单("+label+":"+name+")\n";
			//检查datasource设置
			String datasourceType = formEl.attributeValue("datasourceType");
			String datasourceName = formEl.attributeValue("datasourceName");
			
			Element datasourceEl = formEl.element("record");
			if(datasourceEl!=null){
				JSONObject _dataRecordResult = DatasourceElParser.parseDataRecordEl(datasourceEl, params, root, yongHuDM,isIndependent,pageName);
				formScriptContent += _dataRecordResult.getString("dataScriptContent");
				dataLoadContent += _dataRecordResult.getString("dataLoadContent");
				
				datasourceName =  _dataRecordResult.getString("datasourceName");
			}
			
			if(fieldsPreScript.length()>0){
				formScriptContent += fieldsPreScript;
			}
			formVarName = "_dataDisplayForm_"+name;
			formScriptContent+="var "+formVarName+" = LUI.DisplayForm.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					(xiTongDH!=null?("xiTongDH:'" +xiTongDH+"',\n"):"")+
					(gongNengDH!=null?("gongNengDH:'" +gongNengDH+"',\n"):"")+
					(caoZuoDH!=null?("caoZuoDH:'" +caoZuoDH+"',\n"):"")+
					"datasourceType:'" +datasourceType+"',\n"+
					"datasourceName:'" +datasourceName+"',\n"+
					"renderto:'" +renderto+"',\n"+
					"renderType:'" +renderType+"',\n"+
					"autoRender:" +autoRender+",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"fields:" +fieldArray.toString()+",\n"+
					"buttons:" +buttonArray.toString()+",\n"+
					"listenerDefs:" +listenersObj.toString()+"\n"+
			"});\n";
			///////////////////////////////////////////////////////////
		}else if("workflowForm".equals(formEl.attributeValue("component"))){
			
			String renderto = formEl.attributeValue("renderto");
			String renderType = formEl.attributeValue("renderType");
			if(renderType==null){
				renderType = "rela";
			}
			String autoLoad= formEl.attributeValue("autoLoad"); 
			String hideNullValue= formEl.attributeValue("hideNullValue"); 
			String autoRender= formEl.attributeValue("autoRender"); 
			if(autoRender==null){
				autoRender = "true";
			}
			
			//取流程检视 所使用的参数
			String xiTongDH= formEl.attributeValue("xiTongDH"); 
			String gongNengDH= formEl.attributeValue("gongNengDH"); 
			String gongNengObjId= formEl.attributeValue("gongNengObjId");
			//是否动态gongNengObjId
			if(gongNengObjId!=null && (gongNengObjId.indexOf("$")>=0 || gongNengObjId.indexOf("<#") >=0)){
				String parseString = gongNengObjId;
				if(params!=null){
					parseString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+parseString;
				}
				gongNengObjId = FreemarkerUtils.processTemplate(parseString, root);
			}
			
			//searchToGrid events
			JSONObject listenersObj = new JSONObject();
			listenersObj.put("onLoad",formEl.attributeValue("onLoad"));
			listenersObj.put("onRender",formEl.attributeValue("onRender"));
			
			String fieldsPreScript = "";
			JSONArray fieldArray = new JSONArray();
			Element fieldsEl = formEl.element("fields");
			if(fieldsEl!=null){
				List<Element> fieldEls = fieldsEl.elements("field");
				
				for(Element fieldEl : fieldEls){
					
					JSONObject fieldCfgObj = FieldElParser.parseFieldEl(fieldEl, params, root, yongHuDM,isIndependent,pageName);
					
					String fieldPreScript = fieldCfgObj.getString("fieldPreScript");
					if(fieldPreScript!=null && fieldPreScript.length()>0){
						fieldsPreScript += fieldPreScript;
					}
					
					fieldArray.add(fieldCfgObj.get("field"));
				}
			}
			
			//流程检视子表单
			Element formsEl = formEl.element("forms");
			JSONArray formsArray = new JSONArray();
			if(formsEl!=null){
				List<Element> subFormEls = formsEl.elements("form");
				for(Element subFormEl : subFormEls){
					JSONObject subFormObj = JSONConvertUtils.applyXML2Json(subFormEl,false);
					
					Element subFormFieldsEl = subFormEl.element("fields");
					JSONArray subFormFieldsArray = new JSONArray();
					if(subFormFieldsEl!=null){
						List<Element> subFormFieldEls = subFormFieldsEl.elements("field");
						for(Element subFormFieldEl : subFormFieldEls){
							JSONObject subFormFieldObj = JSONConvertUtils.applyXML2Json(subFormFieldEl,false);
							subFormFieldsArray.add(subFormFieldObj);
						}
					}
					subFormObj.put("fields", subFormFieldsArray);
					
					formsArray.add(subFormObj);
				}
			}
			formScriptContent+="\n//生成流程检视表单("+label+":"+name+")\n";
			//datasource设置
			JSONObject lcDatasourceRet = createLiuChengJSDatasource(name,label,gongNengDH,gongNengObjId,"true".equals(autoLoad),yongHuDM);
			String datasourceName = lcDatasourceRet.getString("datasourceName");
			formScriptContent += lcDatasourceRet.getString("dataScriptContent");
			dataLoadContent += lcDatasourceRet.getString("dataLoadContent");
			
			if(fieldsPreScript.length()>0){
				formScriptContent += fieldsPreScript;
			}
			formVarName = "_workflowForm_"+name;
			formScriptContent+="var "+formVarName+" = LUI.WorkflowForm.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					(xiTongDH!=null?("xiTongDH:'" +xiTongDH+"',\n"):"")+
					(gongNengDH!=null?("gongNengDH:'" +gongNengDH+"',\n"):"")+
					(gongNengObjId!=null?("gongNengObjId:'" +gongNengObjId+"',\n"):"")+
					"datasourceType:'private',\n"+
					"datasourceName:'" +datasourceName+"',\n"+
					"hideNullValue:'" +hideNullValue+"',\n"+
					"renderto:'" +renderto+"',\n"+
					"renderType:'" +renderType+"',\n"+
					"autoRender:" +autoRender+",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"forms:" +formsArray.toString()+",\n"+
					"fields:" +fieldArray.toString()+",\n"+
					"listenerDefs:" +listenersObj.toString()+"\n"+
			"});\n";
			///////////////////////////////////////////////////////////
		}else if("searchToGrid".equals(formEl.attributeValue("component"))){
			//查询 结果显示到新页面
			String autoRender = formEl.attributeValue("autoRender"); 
			if(autoRender==null){
				autoRender = "false";
			}
			String renderto = formEl.attributeValue("renderto");
			String renderType = formEl.attributeValue("renderType");
			String datasourceName = formEl.attributeValue("datasourceName");
			String autoSearch= formEl.attributeValue("autoSearch"); 
			if(autoSearch==null){
				autoSearch = "false";
			}
			//searchToGrid events
			JSONObject listenersObj = new JSONObject();
			listenersObj.put("beforeSearch",formEl.attributeValue("beforeSearch"));

			//检查buttons设置
			JSONArray buttonArray = new JSONArray();
			Element formButtonsEl = formEl.element("buttons");
			if(formButtonsEl!=null){
				List<Element> formButtonEls = formButtonsEl.elements("button");
				if(formButtonEls!=null && formButtonEls.size()>0){
					for(Element formButtonEl : formButtonEls){
						JSONObject buttonObj = JSONConvertUtils.applyXML2Json(formButtonEl,true);
						buttonArray.add(buttonObj);
					}
				}
			}
			
			//检查filters设置
			JSONArray filterArray = new JSONArray();
			Element filtersEl = formEl.element("filters");
			if(filtersEl!=null){
				List<Element> filterEls = filtersEl.elements("filter");
				if(filterEls!=null && filterEls.size()>0){
					for(Element filterEl : filterEls){
						JSONObject filterObj = JSONConvertUtils.applyXML2Json(filterEl,true);
						filterArray.add(filterObj);
					}
				}
			}
			
			formScriptContent+="\n//生成查询表单("+label+":"+name+")\n";
			formVarName = "_searchToGrid_"+name;
			formScriptContent+="var "+formVarName+" = LUI.SearchDatasourceForm.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"datasourceName:'" +datasourceName+"',\n"+
					"renderto:'" +renderto+"',\n"+
					"renderType:'" +renderType+"',\n"+
					"autoSearch:" +autoSearch+",\n"+
					"autoRender:" +autoRender+",\n"+
					"filters:" +filterArray.toString()+",\n"+
					"buttons:" +buttonArray.toString()+",\n"+
					"listenerDefs:" +listenersObj.toString()+"\n"+
			"});\n";
			if("true".equals(autoSearch)){
				formScriptContent+="_searchToGrid_"+name+".submit();\n";
			}
		}else if("singleEditForm".equals(formEl.attributeValue("component"))){
			
			String renderto = formEl.attributeValue("renderto");
			String renderType = formEl.attributeValue("renderType");
			String autoLoad= formEl.attributeValue("autoLoad"); 
			String autoRender= formEl.attributeValue("autoRender"); 
			
			
			//保存表单时 所使用的参数
			String xiTongDH= formEl.attributeValue("xiTongDH"); 
			String gongNengDH= formEl.attributeValue("gongNengDH"); 
			String caoZuoDH= formEl.attributeValue("caoZuoDH");
			
			//searchToGrid events
			JSONObject listenersObj = new JSONObject();
			listenersObj.put("onLoad",formEl.attributeValue("onLoad"));
			listenersObj.put("onRender",formEl.attributeValue("onRender"));
			listenersObj.put("onSubmit",formEl.attributeValue("onSubmit"));
			
			String fieldsPreScript = "";
			JSONArray fieldArray = new JSONArray();
			Element fieldsEl = formEl.element("fields");
			if(fieldsEl!=null){
				List<Element> fieldEls = fieldsEl.elements("field");
				
				for(Element fieldEl : fieldEls){
					
					JSONObject fieldCfgObj = FieldElParser.parseFieldEl(fieldEl, params, root, yongHuDM,isIndependent,pageName);
					
					String fieldPreScript = fieldCfgObj.getString("fieldPreScript");
					if(fieldPreScript!=null && fieldPreScript.length()>0){
						fieldsPreScript += fieldPreScript;
					}
					
					JSONObject fieldDef = fieldCfgObj.getJSONObject("field");
					if(fieldDef.containsKey("onChange")){
						JSONObject fieldListenersObj = new JSONObject();
						fieldListenersObj.put("onChange",fieldDef.getString("onChange"));
						fieldDef.put("listenerDefs", fieldListenersObj);
						
						fieldDef.remove("onChange");
					}
					
					fieldArray.add(fieldDef);
				}
			}
			
			JSONArray buttonArray = new JSONArray();
			Element buttonsEl = formEl.element("buttons");
			if(buttonsEl!=null){
				List<Element> buttonEls = buttonsEl.elements();
				for(Element buttonEl : buttonEls){
					JSONObject buttonObj = JSONConvertUtils.applyXML2Json(buttonEl,true);
					buttonArray.add(buttonObj);
				}
			}
			
			formScriptContent+="\n//生成单表编辑表单("+label+":"+name+")\n";
			//检查datasource设置
			String datasourceType = formEl.attributeValue("datasourceType");
			String datasourceName = formEl.attributeValue("datasourceName");
			
			Element datasourceEl = formEl.element("record");
			if(datasourceEl!=null){
				JSONObject _dataRecordResult = DatasourceElParser.parseDataRecordEl(datasourceEl, params, root, yongHuDM,isIndependent,pageName);
				formScriptContent += _dataRecordResult.getString("dataScriptContent");
				dataLoadContent += _dataRecordResult.getString("dataLoadContent");
				
				datasourceName =  _dataRecordResult.getString("datasourceName");
			}
			
			if(fieldsPreScript.length()>0){
				formScriptContent += fieldsPreScript;
			}
			formVarName = "_singleeditform_"+name;
			formScriptContent+="var "+formVarName+" = LUI.Form.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					(xiTongDH!=null?("xiTongDH:'" +xiTongDH+"',\n"):"")+
					(gongNengDH!=null?("gongNengDH:'" +gongNengDH+"',\n"):"")+
					(caoZuoDH!=null?("caoZuoDH:'" +caoZuoDH+"',\n"):"")+
					"datasourceType:'" +datasourceType+"',\n"+
					"datasourceName:'" +datasourceName+"',\n"+
					"renderto:'" +renderto+"',\n"+
					"renderType:'" +renderType+"',\n"+
					"autoRender:" +autoRender+",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"fields:" +fieldArray.toString()+",\n"+
					"buttons:" +buttonArray.toString()+",\n"+
					"listenerDefs:" +listenersObj.toString()+"\n"+
			"});\n";
		}
		
		//注册
		if(isIndependent){
			formScriptContent += "LUI.Page.instance.register('form',"+formVarName+");\n";
		}else{
			formScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('form',"+formVarName+");\n";
		}
		
		JSONObject ret = new JSONObject();
		ret.put("formScriptContent", formScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("formName", name);
		return ret;
	}
	
	
	public static JSONObject createLiuChengJSDatasource(String formName,String formLabel,String gongNengDH,String gongnengObjId,boolean autoLoad,Integer yhdm) throws Exception{
		String dataScriptContent = "";
		String dataLoadContent = "";
		
		String xiTongDH= "hdzg"; 
		String shiTiLeiDH= "SYS_LiuChengJS"; 
		
		JSONArray filters = JSONArray.fromObject("[{"+
//			"property:'shiFouWC',operator:'=',value:true"+ 
//		"},{"+ 
			"property:'shiFouGDJD',operator:'=',value:false"+ 
		"},{"+ 
			"property:'shiFouSC',operator:'=',value:false"+ 
		"},{"+ 
			"property:'gongNengDH',operator:'=',value:'"+gongNengDH+"'"+ 
		"},{"+ 
			"property:'gongNengObjId',operator:'=',value:"+gongnengObjId+""+ 
		"}]");
		
		
		JSONArray sorts = JSONArray.fromObject("[{"+
			"property:'chuangJianRQ',dir:'ASC'"+ 
		"}]");
		
		//取得json格式的数据
		ShiTiLei dataStl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
		if(dataStl==null){
			throw new Exception("实体类("+shiTiLeiDH+")不存在！");
		}

		JSONArray fieldJsonArray = JSONArray.fromObject("[{" +
			"name:'liuChengJSDM',fieldType:'int'" +
		"},{" +
			"name:'caoZuoRen',fieldType:'string'" +
		"},{" +
			"name:'caoZuoDH',fieldType:'string'" +
		"},{" +
			"name:'caoZuoMC',fieldType:'string'" +
		"},{" +
			"name:'caoZuoXX',fieldType:'string'" +
		"},{" +
			"name:'chuangJianRQ',fieldType:'date'" +
		"},{" +
			"name:'shiFouWC',fieldType:'boolean'" +
		"},{" +
			"name:'wanChengRQ',fieldType:'date'" +
		"},{" +
			"name:'bls',fields:[{"+
				"name:'liuChengJSBLDM',fieldType:'int'" +
			"},{" +
				"name:'liuChengJSBLDH',fieldType:'string'" +
			"},{" +
				"name:'liuChengJSBLMC',fieldType:'string'" +
			"},{" +
				"name:'liuChengJSBLZ',fieldType:'string'" +
			"},{" +
				"name:'liuChengJSBLZXS',fieldType:'string'" +
			"},{" +
				"name:'ziDuanLX',fields:[{"+
					"name:'ziDuanLXDH',fieldType:'string'" +
				"},{" +
					"name:'ziDuanLXMC',fieldType:'string'" +
				"}]" +
			"}]" +
		"}]");
		
		String varName = formName+"_workflow_datasource";
		//将数据转换为json格式 添加到页面中
		dataScriptContent += "\n//流程检视 数据集（"+formLabel+":"+formName+"）\n";
		dataScriptContent += "var "+varName+" = LUI.Datasource.stlDataset.createNew({\n" +
				"name:'" +formName+"_datasource',\n"+
				"label:'" +formLabel+"数据源',\n"+
				"xiTongDH:'" +xiTongDH+"',\n"+
				"shiTiLeiDH:'" +shiTiLeiDH+"',\n"+
				"primaryFieldName:'" +dataStl.getZhuJianLie()+"',\n"+
				"autoLoad:"+autoLoad+",\n"+
				"start:0,\n"+
				"limit:0,\n"+
				"fields:" +fieldJsonArray.toString()+",\n"+
				"filters:" +filters+",\n"+
				"sorts:" +sorts+"\n"+
		"});\n";
		if(autoLoad){
			//取得数据对象(用老的程序方法)
			Map<String,Object> result = DataUtils.getListBySTL(shiTiLeiDH, 0, 0, filters.toString(), sorts.toString());
			List<?> objs = (List<?>)result.get("objs");
			int totalCount = (Integer)result.get("totalCount");

			JSONArray jsonData = JSONConvertUtils.objectList2JSONArray(dataStl,objs,fieldJsonArray);
			
			dataScriptContent += varName+"_init_data = {\n" +
					"start:0,\n"+
					"limit:0,\n"+
					"totalCount:" +totalCount+",\n"+
					"rows:" +jsonData.toString()+"\n"+
			"};\n";
			dataLoadContent += varName+".loadData("+varName+"_init_data);\n\n";
			
		}
		//注册
		dataScriptContent += "LUI.Page.instance.register('dataset',"+varName+");\n";
		
		JSONObject ret = new JSONObject();
		ret.put("dataScriptContent", dataScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("datasourceName", formName+"_datasource");
		return ret;
	}

}

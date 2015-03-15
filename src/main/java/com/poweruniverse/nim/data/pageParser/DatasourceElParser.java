package com.poweruniverse.nim.data.pageParser;

import java.sql.Clob;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.poweruniverse.nim.base.bean.BaseJavaDatasource;
import com.poweruniverse.nim.base.utils.FreemarkerUtils;
import com.poweruniverse.nim.data.entity.sys.GongNeng;
import com.poweruniverse.nim.data.entity.sys.ShiTiLei;
import com.poweruniverse.nim.data.entity.sys.ZiDuan;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
import com.poweruniverse.nim.data.service.utils.DataUtils;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class DatasourceElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static JSONObject parseDatasetEl(Element datasetEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String dataScriptContent = "";
		String dataLoadContent = "";
		//数据源名称
		String label= datasetEl.attributeValue("label"); 
		String name= datasetEl.attributeValue("name"); 
		String varName= "_dataset_"+datasetEl.attributeValue("name"); 
//		if(realDsName!=null){
//			name = realDsName;
//		}
		JSONObject listenersObj = new JSONObject();
		listenersObj.put("onLoad",datasetEl.attributeValue("onLoad"));
		listenersObj.put("onSave",datasetEl.attributeValue("onSave"));
		listenersObj.put("onSubmit",datasetEl.attributeValue("onSubmit"));
		listenersObj.put("onChange",datasetEl.attributeValue("onChange"));
		
		String autoLoad= datasetEl.attributeValue("autoLoad"); 
		if("javaDataset".equals(datasetEl.attributeValue("component"))){
			//java类型的数据源
			String className = datasetEl.attributeValue("className"); 
			String primaryFieldName = datasetEl.attributeValue("primaryFieldName"); 
			
			int start = 0;
			String startString = datasetEl.attributeValue("start");
			if(startString!=null){
				start = Integer.parseInt(startString);
			}
			
			int limit = 0;
			String limitString = datasetEl.attributeValue("limit");
			if(limitString!=null){
				limit = Integer.parseInt(limitString);
			}
			
			//toDoDataset events
			
			
			JSONArray parameters =  getParametersFromEl(datasetEl,root,params);
			JSONObject parametersObject = new JSONObject();
			for(int i=0;i<parameters.size();i++){
				JSONObject p = parameters.getJSONObject(i);
				parametersObject.put(p.get("name"), p.get("value"));
			}
			
			Element fieldsEl = datasetEl.element("properties");
			JSONArray fieldJsonArray = null;
			if(fieldsEl==null){
				fieldJsonArray = JSONArray.fromObject("[]");
			}else{
				fieldJsonArray = getFieldsDefByEL(fieldsEl,null,true);
			}
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//Java数据集（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.javaDataset.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"className:'" +className+"',\n"+
					"primaryFieldName:'" +primaryFieldName+"',\n"+
					"start:" +start+",\n"+
					"limit:" +limit+",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"fields:" +fieldJsonArray+",\n"+
					"parameters:" +parametersObject+"\n"+
			"});\n";
			if("true".equals(autoLoad)){
				int totalCount = 0;
				//取得数据对象(用老的程序方法)
				JSONObject jsonData = null;
				try {
					BaseJavaDatasource javaDS = (BaseJavaDatasource)Class.forName(className).newInstance();
					jsonData = javaDS.getData(null,parametersObject,start,limit);
				} catch (Exception e) {
					jsonData = new JSONObject();
					jsonData.put("totalCount", 0);
					jsonData.put("start", start);
					jsonData.put("limit", limit);
					jsonData.put("rows", new JSONArray());
					e.printStackTrace();
				}
				
				dataScriptContent += varName+"_init_data = \n" +
						jsonData.toString()+"\n"+
				";\n";
				dataLoadContent += varName+".loadData("+varName+"_init_data);\n\n";
				
				//
				root.put(name+"_totalCount", totalCount);
				root.put(name, jsonData);
			}
			
		}else if("sqlDataset".equals(datasetEl.attributeValue("component"))){
			String xiTongDH= datasetEl.attributeValue("xiTongDH"); 
			
			String sqlString= datasetEl.attributeValue("sql"); 
			if(sqlString.indexOf("<#")>=0 || sqlString.indexOf("${")>=0 ){
				if(params!=null){
					sqlString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+sqlString;
				}
				sqlString = FreemarkerUtils.processTemplate(sqlString, root);
			}
			sqlString = sqlString.replaceAll("\\\\n", " ");
			
			int start = 0;
			String startString = datasetEl.attributeValue("start");
			if(startString!=null){
				start = Integer.parseInt(startString);
			}
			
			int limit = 0;
			String limitString = datasetEl.attributeValue("limit");
			if(limitString!=null){
				limit = Integer.parseInt(limitString);
			}
			
			//onLoad events
//			JSONObject listenersObj = new JSONObject();
//			listenersObj.put("onLoad",datasetEl.attributeValue("onLoad"));
			

			JSONArray fieldJsonArray = null;
			Element fieldsEl = datasetEl.element("properties");
			if(fieldsEl==null){
				fieldJsonArray = JSONArray.fromObject("[]");
			}else{
				fieldJsonArray = getFieldsDefByEL(fieldsEl,null,true);
			}
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//SQL 数据集（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.sqlDataset.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"xiTongDH:'" +xiTongDH+"',\n"+
					"sql:\"" +sqlString+"\",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"start:" +start+",\n"+
					"limit:" +limit+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"fields:" +fieldJsonArray.toString()+"\n"+
			"});\n";
			if("true".equals(autoLoad)){
				Map<String,Object> dataResult = DataUtils.getListBySQL(sqlString,start,limit,fieldJsonArray);
				JSONArray rows =  (JSONArray)dataResult.get("objs");
				int totalCount = (Integer)dataResult.get("totalCount");
				
				dataScriptContent += varName+"_init_data = {\n" +
						"start:" +start+",\n"+
						"limit:" +limit+",\n"+
						"totalCount:" +totalCount+",\n"+
						"rows:" +rows+"\n"+
				"};\n";
				dataLoadContent += varName+".loadData("+varName+"_init_data);\n\n";
				
				root.put(name, rows);
			}
		}else if("todoDataset".equals(datasetEl.attributeValue("component"))){
			//待办的数据集
			String xiTongDH= datasetEl.attributeValue("xiTongDH"); 
			String shiTiLeiDH= datasetEl.attributeValue("shiTiLeiDH"); 
			
			//toDoDataset events
//			JSONObject listenersObj = new JSONObject();
//			listenersObj.put("onLoad",datasetEl.attributeValue("onLoad"));
			
			JSONArray filters = getFiltersFromEl(datasetEl,root,params);
			JSONArray sorts = getSortsFromEl(datasetEl,root,params);
			
			JSONObject result = DataUtils.getToDoDataset(datasetEl, params,filters.toString(), sorts.toString(),yongHuDM);
			if(result.getBoolean("success")){
				JSONArray objJsonArray = result.getJSONArray("data");
				int totalCount = result.getInt("totalCount");
				int start = result.getInt("start");
				int limit = result.getInt("limit");
				boolean todoOnly = result.getBoolean("todoOnly");
				String fieldJsonString = result.getString("fields");
				String workflowJsonString = result.getString("workflows");
				//送到客户端
				dataScriptContent += "\n//待办 数据集（"+label+":"+name+"）\n";
				dataScriptContent += "var "+varName+" = LUI.Datasource.TodoDataset.createNew({\n" +
						"name:'" +name+"',\n"+
						"label:'" +label+"',\n"+
						"xiTongDH:'" +xiTongDH+"',\n"+
						"shiTiLeiDH:'" +shiTiLeiDH+"',\n"+
						"autoLoad:" +autoLoad+",\n"+
						"fields:" +fieldJsonString.toString()+",\n"+
						"workflows:" +workflowJsonString.toString()+",\n"+
						"listenerDefs:" +listenersObj.toString()+",\n"+
						"start:" +start+",\n"+
						"limit:" +limit+",\n"+
						"filters:" +filters+",\n"+
						"sorts:" +sorts+",\n"+
						"todoOnly:" +todoOnly+"\n"+
				"});\n";
				if("true".equals(autoLoad)){
					dataScriptContent += varName+"_init_data = {\n" +
							"start:" +start+",\n"+
							"limit:" +limit+",\n"+
							"totalCount:" +totalCount+",\n"+
							"rows:" +objJsonArray.toString()+"\n"+
					"};\n";
					dataLoadContent += varName+".loadData("+varName+"_init_data);\n\n";
				}
			}else{
				throw new Exception(result.getString("errorMsg"));
			}
		}else if("stlDataset".equals(datasetEl.attributeValue("component"))){

			String xiTongDH= datasetEl.attributeValue("xiTongDH"); 
			String shiTiLeiDH= datasetEl.attributeValue("shiTiLeiDH"); 
			
			int start = 0;
			String startString = datasetEl.attributeValue("start");
			if(startString!=null){
				start = Integer.parseInt(startString);
			}
			
			int limit = 0;
			String limitString = datasetEl.attributeValue("limit");
			if(limitString!=null){
				limit = Integer.parseInt(limitString);
			}
			
			JSONArray filters = getFiltersFromEl(datasetEl,root,params);
			JSONArray sorts = getSortsFromEl(datasetEl,root,params);
			
			//toDoDataset events
//			JSONObject listenersObj = new JSONObject();
//			listenersObj.put("onLoad",datasetEl.attributeValue("onLoad"));
			
			//取得json格式的数据
			ShiTiLei dataStl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			if(dataStl==null){
				throw new Exception("实体类("+shiTiLeiDH+")不存在！");
			}

			JSONArray fieldJsonArray = null;
			Element fieldsEl = datasetEl.element("properties");
			if(fieldsEl==null){
				fieldJsonArray = JSONArray.fromObject("[{" +
					"name:'"+dataStl.getZhuJianLie()+"',fieldType:'int'" +
				"},{" +
					"name:'"+dataStl.getXianShiLie()+"',fieldType:'string'" +
				"},{" +
					"name:'"+dataStl.getPaiXuLie()+"',fieldType:'string'" +
				"}]");
			}else{
				fieldJsonArray = getFieldsDefByEL(fieldsEl,dataStl,false);
			}
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//实体类 数据集（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.stlDataset.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"xiTongDH:'" +xiTongDH+"',\n"+
					"shiTiLeiDH:'" +shiTiLeiDH+"',\n"+
					"primaryFieldName:'" +dataStl.getZhuJianLie()+"',\n"+
					"autoLoad:" +autoLoad+",\n"+
					"start:" +start+",\n"+
					"limit:" +limit+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"fields:" +fieldJsonArray.toString()+",\n"+
					"filters:" +filters+",\n"+
					"sorts:" +sorts+"\n"+
			"});\n";
			if("true".equals(autoLoad)){
				//取得数据对象(用老的程序方法)
				Map<String,Object> result = DataUtils.getListBySTL(shiTiLeiDH, start, limit, filters.toString(), sorts.toString());
				
//				ModelData result = com.poweruniverse.nim.system.utils.DataUtils.getStlDataset(datasetEl, params, root,yongHuDM);
				List<?> objs = (List<?>)result.get("objs");
				int totalCount = (Integer)result.get("totalCount");

				JSONArray jsonData = JSONConvertUtils.objectList2JSONArray(dataStl,objs,fieldJsonArray);
				dataScriptContent += varName+"_init_data = {\n" +
						"start:" +start+",\n"+
						"limit:" +limit+",\n"+
						"totalCount:" +totalCount+",\n"+
						"rows:" +jsonData.toString()+"\n"+
				"};\n";
				dataLoadContent += varName+".loadData("+varName+"_init_data);\n\n";
				
				//
				root.put(name+"_totalCount", totalCount);
				root.put(name+"_start", start);
				root.put(name+"_limit", limit);
				root.put(name, objs);
			}
		}else if("gnDataset".equals(datasetEl.attributeValue("component"))){
			String xiTongDH= datasetEl.attributeValue("xiTongDH"); 
			String gongNengDH= datasetEl.attributeValue("gongNengDH"); 
			String caoZuoDH= datasetEl.attributeValue("caoZuoDH"); 
			
			//gnDataset events
//			JSONObject listenersObj = new JSONObject();
//			listenersObj.put("onLoad",datasetEl.attributeValue("onLoad"));
			
			int start = 0;
			String startString = datasetEl.attributeValue("start");
			if(startString!=null){
				start = Integer.parseInt(startString);
			}
			
			int limit = 0;
			String limitString = datasetEl.attributeValue("limit");
			if(limitString!=null){
				limit = Integer.parseInt(limitString);
			}
			
			JSONArray filters = getFiltersFromEl(datasetEl,root,params);
			JSONArray sorts = getSortsFromEl(datasetEl,root,params);
			
			//取得json格式的数据
			GongNeng dataGn = GongNeng.getGongNengByDH(gongNengDH);
			if(dataGn==null){
				throw new Exception("功能("+gongNengDH+")不存在！");
			}else{
				JSONArray fieldJsonArray = new JSONArray();
				Element fieldsEl = datasetEl.element("properties");
				if(fieldsEl==null){
					fieldJsonArray = JSONArray.fromObject("[{" +
						"name:'"+dataGn.getShiTiLei().getZhuJianLie()+"',fieldType:'int'" +
					"},{" +
						"name:'"+dataGn.getShiTiLei().getXianShiLie()+"',fieldType:'string'" +
					"},{" +
						"name:'"+dataGn.getShiTiLei().getPaiXuLie()+"',fieldType:'string'" +
					"}]");
				}else{
					fieldJsonArray = getFieldsDefByEL(fieldsEl,dataGn.getShiTiLei(),false);
				}
				
				//将数据转换为json格式 添加到页面中
				dataScriptContent += "\n//功能 数据集（"+label+":"+name+"）\n";
				dataScriptContent += "var "+varName+" = LUI.Datasource.gnDataset.createNew({\n" +
						"name:'" +name+"',\n"+
						"label:'" +label+"',\n"+
						"xiTongDH:'" +xiTongDH+"',\n"+
						"gongNengDH:'" +gongNengDH+"',\n"+
						"caoZuoDH:'" +caoZuoDH+"',\n"+
						"primaryFieldName:'" +dataGn.getShiTiLei().getZhuJianLie()+"',\n"+
						"autoLoad:" +autoLoad+",\n"+
						"start:" +start+",\n"+
						"limit:" +limit+",\n"+
						"listenerDefs:" +listenersObj.toString()+",\n"+
						"fields:" +fieldJsonArray.toString()+",\n"+
						"filters:" +filters+",\n"+
						"sorts:" +sorts+"\n"+
				"});\n";
				if("true".equals(autoLoad)){
					Map<String,Object> listResult = DataUtils.getListByGN(gongNengDH,caoZuoDH,start,limit,filters.toString(),sorts.toString(),yongHuDM);
					List<EntityI> objs =  (List<EntityI>)listResult.get("objs");
					Integer totalCount = (Integer)listResult.get("totalCount");
					
					JSONArray rows = JSONConvertUtils.objectList2JSONArray(dataGn.getShiTiLei(), objs, fieldJsonArray);
					
					dataScriptContent += "var "+varName+"_init_data = {\n" +
							"start:" +start+",\n"+
							"limit:" +limit+",\n"+
							"totalCount:" +totalCount+",\n"+
							"rows:" +rows.toString()+"\n"+
					"};\n";
					dataLoadContent += varName+".loadData("+varName+"_init_data);\n\n";
					
					//
					root.put(name+"_totalCount",totalCount);
					root.put(name+"_start", start);
					root.put(name+"_limit", limit);
					root.put(name, objs);
				}
			}
		}
	
		//注册
		if(isIndependent){
			dataScriptContent += "LUI.Page.instance.register('dataset',"+varName+");\n";
		}else{
			dataScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('dataset',"+varName+");\n";
		}
		
		JSONObject ret = new JSONObject();
		ret.put("dataScriptContent", dataScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("datasourceName", name);
		return ret;
	}
	
	/**
	 * 记录类型数据源的解析
	 */
	public static JSONObject parseDataRecordEl(Element recordEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String dataScriptContent = "";
		String dataLoadContent = "";
		
		String name= recordEl.attributeValue("name"); 
		String varName= "_dataRecord_"+recordEl.attributeValue("name");

		String label= recordEl.attributeValue("label"); 
		String xiTongDH= recordEl.attributeValue("xiTongDH"); 
		String autoLoad= recordEl.attributeValue("autoLoad"); 
		
		//toDoDataset events
		JSONObject listenersObj = new JSONObject();
		listenersObj.put("onLoad",recordEl.attributeValue("onLoad"));
		listenersObj.put("onSave",recordEl.attributeValue("onSave"));
		listenersObj.put("onSubmit",recordEl.attributeValue("onSubmit"));
		listenersObj.put("onChange",recordEl.attributeValue("onChange"));
		
		if("sqlRecord".equals(recordEl.attributeValue("component"))){
			
			String sqlString= recordEl.attributeValue("sql"); 
			
			if(sqlString.indexOf("<#")>=0 || sqlString.indexOf("${")>=0 ){
				if(params!=null){
					sqlString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+sqlString;
				}
				try {
					sqlString = FreemarkerUtils.processTemplate(sqlString, root);
				} catch (Exception e) {
					//出错的情况下 不autoload
					autoLoad = "false";
				}
			}
			sqlString = sqlString.replaceAll("\\\\n", " ");
			
			JSONArray fieldJsonArray = null;
			Element fieldsEl = recordEl.element("properties");
			if(fieldsEl==null){
				fieldJsonArray = JSONArray.fromObject("[]");
			}else{
				fieldJsonArray = getFieldsDefByEL(fieldsEl,null,true);
			}
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//SQL 数据对象（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.sqlDataRecord.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"xiTongDH:'" +xiTongDH+"',\n"+
					"sql:\"" +sqlString+"\",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"fields:" +fieldJsonArray.toString()+"\n"+
			"});\n";
			if("true".equals(autoLoad)){
				JSONObject obj = DataUtils.getObjectBySQL(sqlString,fieldJsonArray);
				dataScriptContent += ""+varName+"_init_data = {\n" +
						"start:0,"+
						"limit:1,"+
						"totalCount:"+(obj.keySet().size()>0?1:0)+","+
						"rows:[" +obj.toString()+"]\n"+
				"};\n";
				dataLoadContent += ""+varName+".loadData("+varName+"_init_data);\n\n";
				
				root.put(name, obj);
			}
		}else if("gnRecord".equals(recordEl.attributeValue("component"))){
			
			String gongNengDH= recordEl.attributeValue("gongNengDH"); 
			String caoZuoDH= recordEl.attributeValue("caoZuoDH"); 
			String idString= recordEl.attributeValue("id"); 
			
			Integer id = null;
			if(idString!=null && "true".equals(autoLoad)){
				try {
					if(idString.indexOf("<#")>=0 || idString.indexOf("${")>=0){
						if(params!=null){
							idString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+idString;
						}
						idString = FreemarkerUtils.processTemplate(idString, root);
					}
					idString = idString.replaceAll("\\\\n", "");
					id = Integer.valueOf(idString);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			GongNeng dataGn = GongNeng.getGongNengByDH(gongNengDH);
			if(dataGn==null){
				throw new Exception("功能("+gongNengDH+")不存在！");
			}
			
			JSONArray fieldJsonArray = null;
			Element fieldsEl = recordEl.element("properties");
			if(fieldsEl==null){
				fieldJsonArray = JSONArray.fromObject("[{" +
					"name:'"+dataGn.getShiTiLei().getZhuJianLie()+"',fieldType:'int'" +
				"},{" +
					"name:'"+dataGn.getShiTiLei().getXianShiLie()+"',fieldType:'string'" +
				"},{" +
					"name:'"+dataGn.getShiTiLei().getPaiXuLie()+"',fieldType:'string'" +
				"}]");
			}else{
				fieldJsonArray = getFieldsDefByEL(fieldsEl,dataGn.getShiTiLei(),false);
			}
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//功能 数据对象（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.gnDataRecord.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"xiTongDH:'" +xiTongDH+"',\n"+
					"gongNengDH:'" +gongNengDH+"',\n"+
					"caoZuoDH:'" +caoZuoDH+"',\n"+
					"primaryFieldName:'" +dataGn.getShiTiLei().getZhuJianLie()+"',\n"+
					(idString==null?"dataId:null":("dataId:'"+idString+"'"))+",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"fields:" +fieldJsonArray.toString()+"\n"+
			"});\n";
			if("true".equals(autoLoad)){
				Object obj = DataUtils.getObjectByGNCZ(gongNengDH,caoZuoDH,id,yongHuDM);
				if(obj!=null){
					JSONObject jsonData = JSONConvertUtils.object2JSONObject(dataGn.getShiTiLei(),obj,fieldJsonArray);
					dataScriptContent += ""+varName+"_init_data = {\n" +
							"start:0,"+
							"limit:1,"+
							"totalCount:1,"+
							"rows:[" +jsonData.toString()+"]\n"+
					"};\n";
				}else{
					dataScriptContent += ""+varName+"_init_data = {\n" +
							"start:0,"+
							"limit:1,"+
							"totalCount:0,"+
							"rows:[]\n"+
					"};\n";
				}
				dataLoadContent += ""+varName+".loadData("+varName+"_init_data);\n\n";
				//
				root.put(name, obj);
			}
			
		}else if("stlRecord".equals(recordEl.attributeValue("component"))){
			String shiTiLeiDH= recordEl.attributeValue("shiTiLeiDH"); 
			
			ShiTiLei dataStl = ShiTiLei.getShiTiLeiByDH(shiTiLeiDH);
			if(dataStl==null){
				throw new Exception("实体类("+shiTiLeiDH+")不存在！");
			}
			
//			String id= recordEl.attributeValue("id"); 
//			if(id.indexOf("<#")>=0 || id.indexOf("${")>=0 ){
//				if(params!=null){
//					id = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+id;
//				}
//				id = FreemarkerUtils.processTemplate(id, root,null);
//			}
//			id = id.replaceAll("\\\\n", " ");
			
			String idString= recordEl.attributeValue("id"); 
			Integer id = null;
			if(idString!=null && "true".equals(autoLoad)){
				try {
					if(idString.indexOf("<#")>=0 || idString.indexOf("${")>=0){
						if(params!=null){
							idString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+idString;
						}
						idString = FreemarkerUtils.processTemplate(idString, root);
					}
					idString = idString.replaceAll("\\\\n", "");
					id = Integer.valueOf(idString);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			JSONArray fieldJsonArray = null;
			Element fieldsEl = recordEl.element("properties");
			if(fieldsEl==null){
				fieldJsonArray = JSONArray.fromObject("[{" +
					"name:'"+dataStl.getZhuJianLie()+"',fieldType:'int'" +
				"},{" +
					"name:'"+dataStl.getXianShiLie()+"',fieldType:'string'" +
				"},{" +
					"name:'"+dataStl.getPaiXuLie()+"',fieldType:'string'" +
				"}]");
			}else{
				fieldJsonArray = getFieldsDefByEL(fieldsEl,dataStl,false);
			}
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//实体类 数据对象（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.stlDataRecord.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"xiTongDH:'" +xiTongDH+"',\n"+
					"shiTiLeiDH:'" +shiTiLeiDH+"',\n"+
					"primaryFieldName:'" +dataStl.getZhuJianLie()+"',\n"+
					(idString==null?"dataId:null":("dataId:'"+idString+"'"))+",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
					"fields:" +fieldJsonArray.toString()+"\n"+
			"});\n";
			if("true".equals(autoLoad)){
				Object obj = DataUtils.getObjectBySTL(shiTiLeiDH,Integer.valueOf(id));
				if(obj!=null){
					JSONObject jsonData = JSONConvertUtils.object2JSONObject(dataStl,obj,fieldJsonArray);
					dataScriptContent += ""+varName+"_init_data = {\n" +
							"start:0,"+
							"limit:1,"+
							"totalCount:1,"+
							"rows:[" +jsonData.toString()+"]\n"+
					"};\n";
				}else{
					dataScriptContent += ""+varName+"_init_data = {\n" +
							"start:0,"+
							"limit:1,"+
							"totalCount:0,"+
							"rows:[]\n"+
					"};\n";
				}
				dataLoadContent += ""+varName+".loadData("+varName+"_init_data);\n\n";
				root.put(name, obj);
			}
		}
		
		//注册
		if(isIndependent){
			dataScriptContent += "LUI.Page.instance.register('record',"+varName+");\n";
		}else{
			dataScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('record',"+varName+");\n";
		}

		JSONObject ret = new JSONObject();
		ret.put("dataScriptContent", dataScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("datasourceName", name);
		return ret;
	}
	
	
	/**
	 * 变量类型数据源的解析
	 */
	public static JSONObject parseDataVariableEl(Element variableEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String dataScriptContent = "";
		String dataLoadContent = "";
		

		String name= variableEl.attributeValue("name"); 
		String varName = "_dataVariable_"+variableEl.attributeValue("name"); 
		String label= variableEl.attributeValue("label"); 
		String xiTongDH= variableEl.attributeValue("xiTongDH"); 
		String autoLoad= variableEl.attributeValue("autoLoad"); 
		if(autoLoad == null){
			autoLoad = "true";
		}
		String showThousand= variableEl.attributeValue("showThousand");
		
		String renderto = variableEl.attributeValue("renderto");
		String renderTemplate = variableEl.attributeValue("renderTemplate");
		if(renderTemplate == null){
			renderTemplate = "{{"+name+"}}";
		}
		
		//toDoDataset events
		JSONObject listenersObj = new JSONObject();
		listenersObj.put("onLoad",variableEl.attributeValue("onLoad"));
		
		if("sqlVariable".equals(variableEl.attributeValue("component"))){
			String sqlString = variableEl.attributeValue("sql");
			if(sqlString.indexOf("<#")>=0 || sqlString.indexOf("${")>=0 ){
				if(params!=null){
					sqlString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+sqlString;
				}
				sqlString = FreemarkerUtils.processTemplate(sqlString, root);
			}
			sqlString = sqlString.replaceAll("\\\\n", " ");
			
			//将数据转换为json格式 添加到页面中
			dataScriptContent += "\n//SQL 数据变量（"+label+":"+name+"）\n";
			dataScriptContent += "var "+varName+" = LUI.Datasource.sqlDataVariable.createNew({\n" +
					"name:'" +name+"',\n"+
					"label:'" +label+"',\n"+
					"xiTongDH:'" +xiTongDH+"',\n"+
					"showThousand:'" +showThousand+"',\n"+
					"renderto:'" +renderto+"',\n"+
					"renderTemplate:'" +renderTemplate+"',\n"+
					"sql:\"" +sqlString+"\",\n"+
					"autoLoad:" +autoLoad+",\n"+
					"listenerDefs:" +listenersObj.toString()+",\n"+
			"});\n";
			if("true".equals(autoLoad)){
				Object dataVariable_value = HibernateSessionFactory.getSession().createSQLQuery(sqlString).uniqueResult();
				if (dataVariable_value != null && dataVariable_value instanceof Clob) {
                	Clob clob = (Clob)dataVariable_value;
                	dataVariable_value = clob.getSubString(1, (int) clob.length());
				}
				if(dataVariable_value != null && dataVariable_value instanceof String){
					String oldString = (String)dataVariable_value;
					oldString = oldString.replaceAll("\\\\", "\\\\\\\\");
					oldString = oldString.replaceAll("\r", "");
					oldString = oldString.replaceAll("\n", "");
					oldString = oldString.replaceAll("'", "\\\\'");
					
                	dataVariable_value = oldString.replaceAll("\"", "\\\\\"");
				}
				root.put(name, dataVariable_value);
				
				dataScriptContent += "//自动加载的数据变量值\n";
				if(dataVariable_value==null){
					dataScriptContent += "var "+name+" = null;\n";
				}else{
					dataScriptContent += "var "+name+" = '"+dataVariable_value+"';\n";
				}
				dataLoadContent += ""+varName+".loadData("+name+");\n\n";
			}
		}else if("stlVariable".equals(variableEl.attributeValue("component"))){
		}else if("gnVariable".equals(variableEl.attributeValue("component"))){
		}
		//注册
		if(isIndependent){
			dataScriptContent += "LUI.Page.instance.register('variable',"+varName+");\n";
		}else{
			dataScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('variable',"+varName+");\n";
		}
		
		JSONObject ret = new JSONObject();
		ret.put("dataScriptContent", dataScriptContent);
		ret.put("dataLoadContent", dataLoadContent);
		ret.put("datasourceName", name);
		return ret;
	}
	
	
	public static JSONArray getFiltersFromEl(Element dataEl,Map<String, Object> root,JSONObject params ) throws Exception{
		JSONArray filterJsonArray = new JSONArray();
		Element filtersEl = dataEl.element("filters");
		if(filtersEl!=null){
			List<?> filterEls = filtersEl.elements("filter");
			for(int index=0;index<filterEls.size();index++){
				Element filterEl = (Element)filterEls.get(index);
				if("propertyFilter".equals(filterEl.attributeValue("component"))){
					String property= filterEl.attributeValue("property"); 
					String assist= filterEl.attributeValue("assist"); 
					String operator= filterEl.attributeValue("operator"); 
					String value= filterEl.attributeValue("value"); 
					//是否动态value
					if(value!=null && (value.indexOf("$")>=0 || value.indexOf("<#") >=0)){
						String parseString = value;
						if(params!=null){
							parseString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+parseString;
						}
						value = FreemarkerUtils.processTemplate(parseString, root);
					}
					
					JSONObject filterObj = new JSONObject();
					filterObj.put("property", property);
					filterObj.put("assist", assist);
					filterObj.put("operator", operator);
					filterObj.put("value", value);
					
					filterJsonArray.add(filterObj);
				}else if("sqlFilter".equals(filterEl.attributeValue("component"))){
					String sql= filterEl.attributeValue("sql"); 
					//是否动态sql
					if(sql!=null && (sql.indexOf("$")>=0 || sql.indexOf("<#") >=0)){
						String parseString = sql;
						if(params!=null){
							parseString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+parseString;
						}
						sql = FreemarkerUtils.processTemplate(parseString, root);
					}
					JSONObject filterObj = new JSONObject();
					filterObj.put("operator", "sql");
					filterObj.put("sql", sql);
					
					filterJsonArray.add(filterObj);
				}
			}
		}
		return filterJsonArray;
	}
	
	
	public static JSONArray getSortsFromEl(Element dataEl,Map<String, Object> root,JSONObject params ) throws Exception{
		JSONArray sortJsonArray = new JSONArray();
		Element sortsEl = dataEl.element("sorts");
		if(sortsEl!=null){
			List<?> sortEls = sortsEl.elements("sort");
			for(int i=0;i<sortEls.size();i++){
				Element sortEl = (Element)sortEls.get(i);
				String property= sortEl.attributeValue("property"); 
				String dir= sortEl.attributeValue("dir"); 
				
				JSONObject sortObj = new JSONObject();
				sortObj.put("property", property);
				sortObj.put("dir", dir);
				
				sortJsonArray.add(sortObj);
			}
		}
		return sortJsonArray;
	}
	
	
	public static JSONArray getFieldsDefByEL(Element el,ShiTiLei stl,boolean allowPropertyNotExists) throws Exception{
		JSONArray fieldArray  = new JSONArray();
		boolean hasZhuJianLie = false;
//		if(stl!=null){
//			hasZhuJianLie = true;
//		}
		List<?> subEls = el.elements("property");
		for(int index=0;index<subEls.size();index++){
			Element subEl = (Element)subEls.get(index);
			String fieldName = subEl.attributeValue("name");
			JSONObject fieldObj  = new JSONObject();
			fieldObj.put("name",fieldName );
			fieldObj.put("fieldType", subEl.attributeValue("fieldType"));
			
//			Element subFieldsEl = subEl.element("properties");
//			if(subFieldsEl!=null){
//				ShiTiLei subStl = null;
//				if(stl!=null && stl.hasZiDuan(subEl.attributeValue("name"))){
//					ZiDuan zd = stl.getZiDuan(subEl.attributeValue("name"));
//					subStl = zd.getGuanLianSTL();
//					
//					JSONObject metaObj  = new JSONObject();
//					metaObj.put("primaryFieldName", subStl.getZhuJianLie());
//					fieldObj.put("meta",metaObj);
//				}else if(stl!=null && !stl.hasZiDuan(subEl.attributeValue("name")) && !allowPropertyNotExists){
//					throw new Exception("实体类("+stl.getShiTiLeiMC()+")中不存在此字段("+subEl.attributeValue("name")+")!");
//				}
//				fieldObj.put("fields",getFieldsDefByEL(subFieldsEl,subStl,allowPropertyNotExists));
//			}
			
			ZiDuan zd = null;
			if(stl!=null && stl.hasZiDuan(fieldName)){
				zd = stl.getZiDuan(fieldName);
				//检查是否主键列
				if(fieldName.equals(stl.getZhuJianLie())){
					hasZhuJianLie = true;
				}
			}
			//检查是否有关联实体类字段定义
			Element subFieldsEl = subEl.element("properties");
			if(subFieldsEl!=null){
				//对象/集合类型的字段
				ShiTiLei subStl = null;
				if(zd!=null){
					subStl = zd.getGuanLianSTL();
					
					JSONObject metaObj  = new JSONObject();
					metaObj.put("primaryFieldName", subStl.getZhuJianLie());
					fieldObj.put("meta",metaObj);
				}else if(stl!=null && !stl.hasZiDuan(subEl.attributeValue("name")) && !allowPropertyNotExists){
					throw new Exception("实体类("+stl.getShiTiLeiMC()+")中不存在此字段("+subEl.attributeValue("name")+")!");
				}
				fieldObj.put("fields",getFieldsDefByEL(subFieldsEl,subStl,allowPropertyNotExists));
			}else if(zd!=null && zd.getZiDuanCD()!=null && zd.getZiDuanCD()>0){
				//简单字段需要记录长度
				fieldObj.put("fieldLength",zd.getZiDuanCD());
			}
			
			fieldArray.add(fieldObj);
		}
		//必须包含主键列
		if(stl!=null && !hasZhuJianLie){
			JSONObject fieldObj  = new JSONObject();
			fieldObj.put("name",stl.getZhuJianLie() );
			fieldObj.put("fieldType", "int");
			fieldArray.add(fieldObj);
		}
		return fieldArray;
	}
	
	public static JSONArray getParametersFromEl(Element dataEl,Map<String, Object> root,JSONObject params ) throws Exception{
		JSONArray parameterJsonArray = new JSONArray();
		Element parametersEl = dataEl.element("parameters");
		if(parametersEl!=null){
			List<?> parameterEls = parametersEl.elements("parameter");
			for(int i=0;i<parameterEls.size();i++){
				Element parameterEl = (Element)parameterEls.get(i);
					
				String parameterType= parameterEl.attributeValue("parameterType"); 
				String name= parameterEl.attributeValue("name"); 
				
				
				JSONObject parameterObj = new JSONObject();
				parameterObj.put("parameterType", parameterType);
				parameterObj.put("name", name);
				
				
				String value= parameterEl.attributeValue("value"); 
				//是否动态value
				if(value!=null && (value.indexOf("$")>=0 || value.indexOf("<#") >=0)){
					String parseString = value;
					if(params!=null){
						parseString = "<#assign _paramsString>"+params.toString()+"</#assign><#assign params = _paramsString?eval />"+parseString;
					}
					value = FreemarkerUtils.processTemplate(parseString, root);
				}
				parameterObj.put("value", value);
				
				parameterJsonArray.add(parameterObj);
			}
		}
		return parameterJsonArray;
	}

}

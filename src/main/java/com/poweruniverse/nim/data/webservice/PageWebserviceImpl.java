package com.poweruniverse.nim.data.webservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;

import com.poweruniverse.nim.base.description.Application;
import com.poweruniverse.nim.base.message.JSONMessageResult;
import com.poweruniverse.nim.base.utils.FreemarkerUtils;
import com.poweruniverse.nim.base.webservice.BasePlateformWebservice;
import com.poweruniverse.nim.data.entity.YongHu;
import com.poweruniverse.nim.data.pageParser.ActionElParser;
import com.poweruniverse.nim.data.pageParser.DatasourceElParser;
import com.poweruniverse.nim.data.pageParser.FormElParser;
import com.poweruniverse.nim.data.pageParser.GridElParser;
import com.poweruniverse.nim.data.pageParser.ImageElParser;
import com.poweruniverse.nim.data.pageParser.ImportElParser;
import com.poweruniverse.nim.data.pageParser.PageElParser;
import com.poweruniverse.nim.data.pageParser.TabpageElParser;
import com.poweruniverse.nim.data.pageParser.TreeElParser;
import com.poweruniverse.nim.data.service.utils.SystemSessionFactory;

/**
 * 
 * @author Administrator
 *
 */
@WebService
public class PageWebserviceImpl extends BasePlateformWebservice {
	@Resource
	private WebServiceContext wsContext;
	
	/**
	 * 根据当前用户以及url参数
	 * 解析传递来的xml文件(string)和html文件(string) 
	 * @return
	 */
	public JSONMessageResult analyse(
			@WebParam(name="cfgContent") String cfgContent,
			@WebParam(name="htmlContent") String htmlContent,
			@WebParam(name="params") String params){
		JSONMessageResult msg = new JSONMessageResult();
		Session sess = null;
		try {
			Integer yongHuDM = this.getYongHuDM(wsContext,false);
			msg.put("isLogged", !(yongHuDM==null));
			//检查pageUrl 是否合法(无.. js后缀)

			sess = SystemSessionFactory.getSession();
			
			Map<String, Object> root = new HashMap<String, Object>();
			//加入用户信息
			if(yongHuDM!=null){
				root.put("yongHu", sess.load(YongHu.class, yongHuDM));
			}
			
			JSONObject jsonParams = JSONObject.fromObject(params);
					
			Application app = Application.getInstance();
			String dataScriptContent = "\n//默认页面标题\n"+
					"document.title= '"+app.getTitle()+"';";
			//读取页面xml文件定义
			if(cfgContent!=null){
				SAXReader reader = new SAXReader();
				Document doc = reader.read(cfgContent);
				Element cfgEl = doc.getRootElement();
				
				//处理page元素
				JSONObject pageResult = PageElParser.parsePageEl(cfgEl,jsonParams);
				//确定此页面是否需要登陆后才能访问
				if(pageResult.getBoolean("needsLogin") && yongHuDM==null){
					msg.setSuccess(false);
					msg.put("needsLogin", true);//此页面是否需要登录
					return msg;
				}else{
					Iterator<?> keysIt = pageResult.keys();
					while(keysIt.hasNext()){
						String key = (String)keysIt.next(); 
						if(key.equals("pageScriptContent")){
							dataScriptContent += pageResult.getString("pageScriptContent");
						}else{
							msg.put(key, pageResult.get(key));
						}
					}
				}

				String dataLoadContent = "//为自动加载的数据源 load数据\n";
				//处理variable数据源
				List<?> variableEls = cfgEl.elements("variable");
				for(int i=0;i<variableEls.size();i++){
					Element variableEl = (Element)variableEls.get(i);
					JSONObject datasetResult = DatasourceElParser.parseDataVariableEl(variableEl, jsonParams, root, yongHuDM);
					dataScriptContent += datasetResult.getString("dataScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
				}
				//处理record数据源
				List<?> recordEls = cfgEl.elements("record");
				for(int i=0;i<recordEls.size();i++){
					Element recordEl = (Element)recordEls.get(i);
					JSONObject datasetResult = DatasourceElParser.parseDataRecordEl(recordEl, jsonParams, root, yongHuDM);
					dataScriptContent += datasetResult.getString("dataScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				//处理dataset数据源
				List<?> datasetEls = cfgEl.elements("dataset");
				for(int i=0;i<datasetEls.size();i++){
					Element datasetEl = (Element)datasetEls.get(i);
					JSONObject datasetResult = DatasourceElParser.parseDatasetEl(datasetEl, jsonParams, root, yongHuDM);
					dataScriptContent += datasetResult.getString("dataScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				
				//处理 grid
				List<?> gridEls = cfgEl.elements("grid");
				for(int i=0;i<gridEls.size();i++){
					Element gridEl = (Element)gridEls.get(i);
					JSONObject datasetResult = GridElParser.parseGridEl(gridEl, jsonParams, root, yongHuDM);
					dataScriptContent += datasetResult.getString("gridScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				
				//处理 form
				List<?> formEls = cfgEl.elements("form");
				for(int i=0;i<formEls.size();i++){
					Element formEl = (Element)formEls.get(i);
					JSONObject datasetResult = FormElParser.parseFormEl(formEl, jsonParams, root, yongHuDM);
					dataScriptContent += datasetResult.getString("formScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}

				//处理 tree
				List<?> treeEls = cfgEl.elements("tree");
				for(int i=0;i<treeEls.size();i++){
					Element treeEl = (Element)treeEls.get(i);
					JSONObject datasetResult = TreeElParser.parseTreeEl(treeEl, jsonParams, root, yongHuDM);
					dataScriptContent += datasetResult.getString("treeScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				
				
				//处理 tab
				List<?> tabEls = cfgEl.elements("tab");
				for(int i=0;i<tabEls.size();i++){
					Element tabEl = (Element)tabEls.get(i);
					dataScriptContent += TabpageElParser.parseTabpageEl(tabEl, jsonParams, root, yongHuDM);
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
							
				
				//处理 action
				List<?> actionEls = cfgEl.elements("action");
				for(int i=0;i<actionEls.size();i++){
					Element actionEl = (Element)actionEls.get(i);
					dataScriptContent += ActionElParser.parseActionEl(actionEl, jsonParams, root, yongHuDM);
				}
				
				//处理import
				List<?> importEls = cfgEl.elements("import");
				for(int i=0;i<importEls.size();i++){
					Element importEl = (Element)importEls.get(i);
					dataScriptContent += ImportElParser.parseImportEl(importEl, jsonParams, root, yongHuDM);
				}

				//处理image
				List<?> imageEls = cfgEl.elements("image");
				for(int i=0;i<imageEls.size();i++){
					Element imageEl = (Element)imageEls.get(i);
					dataScriptContent += ImageElParser.parseImageEl(imageEl, jsonParams, root, yongHuDM);
				}
				//所有数据源 自动加载数据的代码 添加到程序定义的结尾
				dataScriptContent += dataLoadContent;
				
				//页面加载完成
//				if(cfgEl.attributeValue("onLoad")!=null){
//					dataScriptContent+="\n//页面全部加载完成\n";
//					dataScriptContent+="LUI.Page.instance.();\n";
//				}
				//关闭mask的代码
				dataScriptContent+="\n//关闭mask\n";
				dataScriptContent+="$('#_pageContent').unmask();\n";
			
			}else{
				//不存在xml文件 此页面不需要登录
				msg.put("needsLogin", false);
			}
			//原始文本信息
			if(htmlContent.indexOf("$")>=0 || htmlContent.indexOf("<#") >=0){
				String parseString = htmlContent;
				if(params!=null){
					parseString = "<#assign _paramsString>"+params+"</#assign><#assign params = _paramsString?eval />"+parseString;
				}
				htmlContent = FreemarkerUtils.processTemplate(parseString,root,null);
			}
			//可能存在的 根据配置文件 生成的脚本
			msg.put("content", htmlContent);
			if(dataScriptContent!=null){
				msg.put("script", dataScriptContent);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			msg = new JSONMessageResult(e.getMessage());
			msg.put("needsLogin", false);
		}
		return msg;
	}
	
	

	

}

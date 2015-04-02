package com.poweruniverse.nim.data.webservice;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;

import com.poweruniverse.nim.base.bean.UserInfo;
import com.poweruniverse.nim.base.description.Application;
import com.poweruniverse.nim.base.message.StringResult;
import com.poweruniverse.nim.base.webservice.AbstractWebservice;
import com.poweruniverse.nim.data.entity.sys.YongHu;
import com.poweruniverse.nim.data.pageParser.ActionElParser;
import com.poweruniverse.nim.data.pageParser.DatasourceElParser;
import com.poweruniverse.nim.data.pageParser.FormElParser;
import com.poweruniverse.nim.data.pageParser.GridElParser;
import com.poweruniverse.nim.data.pageParser.ImageElParser;
import com.poweruniverse.nim.data.pageParser.ImportElParser;
import com.poweruniverse.nim.data.pageParser.PageElParser;
import com.poweruniverse.nim.data.pageParser.TabpageElParser;
import com.poweruniverse.nim.data.pageParser.TreeElParser;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;

/**
 * 
 * @author Administrator
 *
 */
@WebService
public class AnalyseWebserviceImpl extends AbstractWebservice {
	@Resource
	private WebServiceContext wsContext;
	protected static Logger logger = null;

	public AnalyseWebserviceImpl(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
		if(logger==null){
			logger = Logger.getLogger("nim-data.analyse");
		}
	}

	/**
	 * 根据当前用户以及url参数
	 * 解析传递来的xml文件(string)和html文件(string) 
	 * @return
	 */
	public StringResult analyse(
			@WebParam(name="pageName") String pageName,
			@WebParam(name="pageUrl") String pageUrl,
			@WebParam(name="pageContent") String pageContent,
			@WebParam(name="isIndependent") boolean isIndependent,
			@WebParam(name="params") String params){
		StringResult msg = null;
		Session sess = null;
		logger.debug("请求解析页面："+pageUrl+" 参数："+params);
		try {
			Integer yongHuDM = this.getYongHuDM(wsContext,false);
			//检查pageUrl 是否合法(无.. js后缀)
			
			sess = HibernateSessionFactory.getSession();
			
			Map<String, Object> root = new HashMap<String, Object>();
			//加入用户信息
			if(yongHuDM!=null){
				root.put("yongHu", sess.load(YongHu.class, yongHuDM));
			}
			
			JSONObject jsonParams = null;
			if(params!=null){
				jsonParams = JSONObject.fromObject(params);
			}
					
			Application app = Application.getInstance();
			String dataScriptContent = "";
			if(isIndependent){
				dataScriptContent = "\n//默认页面标题\n"+
								    "document.title= '"+app.getTitle()+"';";
			}
					
			//读取页面xml文件定义
			if(pageContent!=null){
				SAXReader reader = new SAXReader();
				reader.setEncoding("utf-8");
				Document doc = reader.read(new ByteArrayInputStream(pageContent.getBytes("UTF-8")));
				Element cfgEl = doc.getRootElement();
				
				
				//处理page元素
				JSONObject pageResult = PageElParser.parsePageEl(cfgEl,pageName,pageUrl,jsonParams,isIndependent,yongHuDM);
				dataScriptContent += pageResult.getString("pageScriptContent");
				boolean needsLogin = pageResult.getBoolean("needsLogin");
				if(needsLogin && yongHuDM==null){
					dataScriptContent += "\n//页面需要登录且未登录 跳转至登录页面\n"+
							"LUI.Page.instance.redirect('"+app.getLoginPage()+"');";
					return new StringResult(dataScriptContent);
				}

				String dataLoadContent = "";
				//处理variable数据源
				List<?> variableEls = cfgEl.elements("variable");
				for(int i=0;i<variableEls.size();i++){
					Element variableEl = (Element)variableEls.get(i);
					JSONObject datasetResult = DatasourceElParser.parseDataVariableEl(variableEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					dataScriptContent += datasetResult.getString("dataScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
				}
				//处理record数据源
				List<?> recordEls = cfgEl.elements("record");
				for(int i=0;i<recordEls.size();i++){
					Element recordEl = (Element)recordEls.get(i);
					JSONObject datasetResult = DatasourceElParser.parseDataRecordEl(recordEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					dataScriptContent += datasetResult.getString("dataScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				//处理dataset数据源
				List<?> datasetEls = cfgEl.elements("dataset");
				for(int i=0;i<datasetEls.size();i++){
					Element datasetEl = (Element)datasetEls.get(i);
					JSONObject datasetResult = DatasourceElParser.parseDatasetEl(datasetEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					dataScriptContent += datasetResult.getString("dataScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				
				//处理 grid
				List<?> gridEls = cfgEl.elements("grid");
				for(int i=0;i<gridEls.size();i++){
					Element gridEl = (Element)gridEls.get(i);
					JSONObject datasetResult = GridElParser.parseGridEl(gridEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					dataScriptContent += datasetResult.getString("gridScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				
				//处理 form
				List<?> formEls = cfgEl.elements("form");
				for(int i=0;i<formEls.size();i++){
					Element formEl = (Element)formEls.get(i);
					JSONObject datasetResult = FormElParser.parseFormEl(formEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					dataScriptContent += datasetResult.getString("formScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}

				//处理 tree
				List<?> treeEls = cfgEl.elements("tree");
				for(int i=0;i<treeEls.size();i++){
					Element treeEl = (Element)treeEls.get(i);
					JSONObject datasetResult = TreeElParser.parseTreeEl(treeEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					dataScriptContent += datasetResult.getString("treeScriptContent");
					dataLoadContent += datasetResult.getString("dataLoadContent");
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
				
				
				//处理 tab
				List<?> tabEls = cfgEl.elements("tab");
				for(int i=0;i<tabEls.size();i++){
					Element tabEl = (Element)tabEls.get(i);
					dataScriptContent += TabpageElParser.parseTabpageEl(tabEl, jsonParams, root, yongHuDM,isIndependent,pageName);
					
//					dataScriptContent += "\n alert('dataScriptContent:"+dataScriptContent.length()+"');\n";
//					dataScriptContent += "\n alert('dataLoadContent:"+dataLoadContent.length()+"');\n";

				}
							
				
				//处理 action
				List<?> actionEls = cfgEl.elements("action");
				for(int i=0;i<actionEls.size();i++){
					Element actionEl = (Element)actionEls.get(i);
					dataScriptContent += ActionElParser.parseActionEl(actionEl, jsonParams, root, yongHuDM,isIndependent,pageName);
				}
				
				//处理import
				List<?> importEls = cfgEl.elements("import");
				for(int i=0;i<importEls.size();i++){
					Element importEl = (Element)importEls.get(i);
					dataScriptContent += ImportElParser.parseImportEl(importEl, jsonParams, root, yongHuDM,isIndependent,pageName);
				}

				//处理image
				List<?> imageEls = cfgEl.elements("image");
				for(int i=0;i<imageEls.size();i++){
					Element imageEl = (Element)imageEls.get(i);
					dataScriptContent += ImageElParser.parseImageEl(imageEl, jsonParams, root, yongHuDM,isIndependent,pageName);
				}
				//所有数据源 自动加载数据的代码 添加到程序定义的结尾
				if(dataLoadContent.length()>0){
					dataScriptContent += "//为自动加载的数据源 load数据\n"+dataLoadContent;
				}
				
				
				//页面加载完成(name为空的是独立页面)
				dataScriptContent+="\n//页面加载完成\n";
				if(isIndependent){
					dataScriptContent+="LUI.Page.instance.load();\n";
				}else{
					dataScriptContent+="LUI.Subpage.getInstance('"+pageName+"').load();\n";
				}
				
				logger.debug("解析页面："+pageUrl+" ...完成");
			}else{
				//不存在xml文件 此页面不需要登录
				dataScriptContent+= "//默认配置信息（无配置文件）\n" +
						"LUI.Page.createNew({\n" +
						"	title:'" + app.getTitle() +"',\n" +
						"	needsLogin:false,\n" +
						"	listenerDefs:{},\n"+
						"	params:" +params+"\n"+
						"});\n\n";
			}
			msg = new StringResult(dataScriptContent);
			
		}catch (Exception e) {
			logger.error("解析页面："+pageUrl+" ...失败",e);
			e.printStackTrace();
			msg = new StringResult("alert('解析pageUrl失败："+e.getMessage()+"！');");
		}
		return msg;
	}

}

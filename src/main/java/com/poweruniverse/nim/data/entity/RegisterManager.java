package com.poweruniverse.nim.data.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.poweruniverse.nim.base.description.Application;
import com.poweruniverse.nim.base.utils.NimJSONObject;
import com.poweruniverse.nim.data.entity.sys.CaoZuoLB;
import com.poweruniverse.nim.data.entity.sys.GongNeng;
import com.poweruniverse.nim.data.entity.sys.GongNengCZ;
import com.poweruniverse.nim.data.entity.sys.ShiTiLei;
import com.poweruniverse.nim.data.entity.sys.XiTong;
import com.poweruniverse.nim.data.entity.sys.ZiDuan;
import com.poweruniverse.nim.data.entity.sys.ZiDuanLX;
import com.poweruniverse.nim.data.service.utils.HibernateSessionFactory;
import com.poweruniverse.nim.data.service.utils.JSONConvertUtils;

public class RegisterManager {
	private static SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //日期格式化
	//检查是否同步
	//根据hbm文件中的时间戳 检查java文件 是否需要生成新的版本
	public static boolean checkRegisterSyn(String contextPath){
		Session sess = null;
		boolean ret = true;
		try {
			sess = HibernateSessionFactory.getSession();
			

			SAXReader reader = new SAXReader();
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			
			JSONObject sessionConfig =  HibernateSessionFactory.getConfiguration();
			JSONArray xiTongConfigs = sessionConfig.getJSONArray("xiTongs");
			for(int k=0;k<xiTongConfigs.size();k++){
				JSONObject xiTongConfig = xiTongConfigs.getJSONObject(k);
				String xiTongName = xiTongConfig.getString("name");
				String mappingFileName = contextPath+"WEB-INF/register."+xiTongName+".xml";
				File mappingFile = new File(mappingFileName);
				if(!mappingFile.exists()){
					System.err.println("------------------------------------------------------");
					System.err.println("系统("+xiTongName+")的功能映射文件("+mappingFileName+")不存在，忽略相关功能的检查");
					System.err.println("------------------------------------------------------");
					
					//通过复制 创建sys的功能配置文件 
					if("sys".equals(xiTongName)){
						InputStream ins = RegisterManager.class.getResourceAsStream("/register.sys.xml");
						OutputStream os = new FileOutputStream(mappingFile);
						int bytesRead = 0;
						byte[] buffer = new byte[8192];
						while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
							os.write(buffer, 0, bytesRead);
						}
						os.close();
						ins.close();
					}
					continue;
				}
				Document configurationDoc = reader.read(new FileInputStream(mappingFile));
				String entityPackage = xiTongConfig.getString("entityPackage");
				String entitySrcPath = xiTongConfig.getString("srcPath");
				String entityClassesPath = xiTongConfig.getString("classesPath");
				if(entitySrcPath.indexOf("{contextPath}") >= 0){
					String contextPath2 = contextPath;
					if(contextPath.indexOf("src\\main\\webapp/")>0){
						contextPath2 = contextPath.substring(0,contextPath.indexOf("src\\main\\webapp/"));
					}
					entitySrcPath = entitySrcPath.replace("{contextPath}", contextPath2);
				}
				
				if(entityClassesPath.indexOf("{contextPath}") >= 0){
					entityClassesPath = entityClassesPath.replace("{contextPath}", contextPath);
				}
				
				Map<String, JSONObject> resourceDefineMap = new HashMap<String, JSONObject>();
				
				@SuppressWarnings("unchecked")
				List<Element> gongNengEls = (List<Element>)configurationDoc.getRootElement().elements("gongNeng");
				for(int i=0;i<gongNengEls.size();i++){
					Element gongNengEl = gongNengEls.get(i) ;
					String resourceName = gongNengEl.attributeValue("resource");//com/poweruniverse/nim/data/gn/sys/XiTong.hbm.xml
					String entityName = resourceName.substring(0,resourceName.indexOf("."));//com/poweruniverse/nim/data/gn/sys/XiTong
					
					if(!resourceName.startsWith(entityPackage.replaceAll("\\.", "/"))){
						System.err.println("映射文件("+mappingFileName+")第"+i+"行的映射信息错误,功能位置必须与application.cfg.xml文件中系统"+xiTongName+"的entityPackage参数("+entityPackage+")保持一致");
						return false;
					}
					
					//读取定义文件(定义文件可能在两个地方 系统功能的定义在jar中 应用程序功能的定义在classes目录中)
					String defJsonString = null;
					File defJsonFile = new File(entityClassesPath+entityName+".def.json");//从执行路径读取定义文件
					if(!defJsonFile.exists()){
						//根据资源路径 检查是否存在定义文件
						InputStream ri = EntityManager.class.getResourceAsStream("/"+entityName+".def.json");
						if(ri!=null){
							defJsonString = IOUtils.toString(ri, "utf8");
						}
					}else{
						defJsonString = FileUtils.readFileToString(defJsonFile, "utf8");
					}
					//解析定义文件
					JSONObject defJson = null;
					if(defJsonString==null){
						System.err.println("功能("+entityName+")的定义文件不存在,忽略java文件和hbm文件的版本检查");
						continue;
					}else{
						try {
							defJson = JSONObject.fromObject(defJsonString);
							resourceDefineMap.put(resourceName, defJson);
						} catch (Exception e) {
							System.err.println("功能("+entityName+")的定义文件不是有效的json对象,忽略java文件和hbm文件的版本检查");
							continue;
						}
					}
				}
				
				//检查是否数据库中 是否缺少功能
				for(int i=0;i<gongNengEls.size();i++){
					Element gongNengEl = gongNengEls.get(i) ;
					String resourceName = gongNengEl.attributeValue("resource");//hbm/sys/XiTong.hbm.xml
					//读取定义文件中的版本信息,创建不存在的功能
					JSONObject defJson = resourceDefineMap.get(resourceName);
					if(defJson !=null ){
						try {
							//取功能信息
							GongNeng gn = (GongNeng)sess.createCriteria(GongNeng.class).add(Restrictions.eq("gongNengDH", defJson.getString("gongNengDH"))).uniqueResult();
							if(gn == null){
								//不存在 创建之
								gn = new GongNeng();
								gn.setGongNengDH(defJson.getString("gongNengDH"));
								gn.setGongNengMC(defJson.getString("gongNengMC"));
								System.out.println("<===添加功能："+gn.getGongNengDH()+"("+gn.getGongNengMC()+")");
								sess.save(gn);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				//再循环检查是否数据库中 是否功能的版本早于定义文件中的版本号
				for(int i=0;i<gongNengEls.size();i++){
					Element gongNengEl = gongNengEls.get(i) ;
					String resourceName = gongNengEl.attributeValue("resource");//hbm/sys/XiTong.hbm.xml
					
					//读取定义文件中的版本信息,与数据库中功能的版本进行比较
					JSONObject defJson = resourceDefineMap.get(resourceName);
					if(defJson!=null ){
						Date defineVersion = dateFm.parse(defJson.getString("gongNengBB"));
						//取功能信息
						boolean needsUpdate = false;
						GongNeng gn = (GongNeng)sess.createCriteria(GongNeng.class).add(Restrictions.eq("gongNengDH", defJson.getString("gongNengDH"))).uniqueResult();
						if(gn.getGongNengBB() == null){
							needsUpdate = true;
						}else{
							Date gnVersion = dateFm.parse(gn.getGongNengBB());
							if(gnVersion == null || defineVersion.after(gnVersion) ){
								needsUpdate = true;
							}else{
								System.out.println("功能("+resourceName+")的数据库版本为"+gn.getGongNengBB()+"不早于于定义版本"+dateFm.format(defineVersion)+",忽略！");
							}
						}
						if(needsUpdate){
							System.out.println("功能("+resourceName+")的数据库版本为"+gn.getGongNengBB()+"早于定义版本"+dateFm.format(defineVersion)+",需要更新！");
							applyDef2Obj(gn, defJson);
							sess.update(gn);
						}
					}else{
						System.err.println("功能("+resourceName+")的定义文件不存在,忽略数据库中功能的版本检查");
					}
				}
			}
		} catch (Exception e) {
			ret = false;
			e.printStackTrace();
			if(sess!=null){
				HibernateSessionFactory.closeSession(false);
			}
		}finally{
			if(sess!=null){
				HibernateSessionFactory.closeSession(true);
			}
		}
		return ret;
	}
	
	/**
	 * 根据数据库中的记录 创建功能定义文件 记录功能及字段的信息以及版本
	 */
	public static String createGongNengDefine(GongNeng entityObj,Date versionDate,JSONObject xiTongConfig) throws Exception{
		
		String versionString = null;
		try {
			versionString = dateFm.format(versionDate);
			
			String contextPath = Application.getInstance().getContextPath();
			if(contextPath.indexOf("src\\main\\webapp/")>0){
				contextPath = contextPath.substring(0,contextPath.indexOf("src\\main\\webapp/"));
			}	
			String fileSrcPath = xiTongConfig.getString("srcPath");
			if(fileSrcPath.indexOf("{contextPath}") >= 0){
				fileSrcPath = fileSrcPath.replace("{contextPath}", contextPath);
			}
			
			String entityPackage = xiTongConfig.getString("entityPackage");
			String defineFileName = entityPackage.replaceAll("\\.","/")+"/gn/"+entityObj.getXiTong().getXiTongDH()+"/"+entityObj.getGongNengDH()+".def.json";
			
			//功能本身的定义
			ShiTiLei stl = (ShiTiLei)HibernateSessionFactory.getSession().createCriteria(ShiTiLei.class).add(Restrictions.eq("shiTiLeiDH", "SYS_GongNeng")).uniqueResult();//功能-功能
			//字段定义 
			InputStream fieldsJsonStream = RegisterManager.class.getResourceAsStream("gongNeng.fields");
			String fieldsString = IOUtils.toString(fieldsJsonStream);
			JSONArray fieldsArray = JSONArray.fromObject(fieldsString);

			//目标文件、目录
			File defineFile = new File(fileSrcPath+defineFileName);
			File definePathFile = defineFile.getParentFile();
			if(!definePathFile.exists()){
				definePathFile.mkdirs();
			}
			//生成目标文件
			NimJSONObject jsonData = JSONConvertUtils.Entity2JSONObject(stl, entityObj, fieldsArray);
			jsonData.put("gongNengBB", versionString);
			//将文件写入
			FileUtils.writeStringToFile(defineFile, jsonData.toString());
			//将当前文件链接添加到register.{xiTongDH}.xml文件中
			synchronized(RegisterManager.class){
				System.out.println("...............................处理:"+entityObj+"的注册信息");
				
				String registerFileName = Application.getInstance().getContextPath()+ "WEB-INF/register."+entityObj.getXiTong().getXiTongDH()+".xml";
				File registerFile = new File(registerFileName);
				
				Document doc = null;
				Element root = null;
				if(!registerFile.exists()){
					doc = DocumentHelper.createDocument(); 
					root = doc.addElement("gongNengs");//添加文档根 
				}else{
					SAXReader reader = new SAXReader();
					reader.setEncoding("utf-8");
					reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
					doc = reader.read(registerFile);
					root = doc.getRootElement();
				}
				
				//检查并增加hibernate.cfg.xml中 是否有此功能的关联
				boolean hasMapping = false;
				@SuppressWarnings("unchecked")
				Iterator<Element> mappingEls = root.elements("gongNeng").iterator();
				Element mappingEl = null;
				while (mappingEls.hasNext()) {
					mappingEl = mappingEls.next();
					if (defineFileName.equals(mappingEl.attributeValue("resource"))) {
						hasMapping = true;
						break;
					}
				}
				if (!hasMapping) {
					System.out.println("...............................添加"+defineFileName);
					mappingEl = root.addElement("gongNeng");
					mappingEl.addAttribute("resource", defineFileName);
					//
					XMLWriter output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(registerFile), "utf-8"),OutputFormat.createPrettyPrint());
					output.write(doc);
					output.close();
				}
				System.out.println("...............................处理完成:"+entityObj);
			}
		} catch (HibernateException e) {
			versionString = null;
			e.printStackTrace();
		} catch (IOException e) {
			versionString = null;
			e.printStackTrace();
		} catch (Exception e) {
			versionString = null;
			e.printStackTrace();
		}
		return versionString;
	}
	
	//将定义信息中的功能信息 更新到功能对象中
	public static Object applyDef2Obj(Object obj,JSONObject dataObject) throws Exception{
		if(dataObject==null) return obj;
		
		Session sess = HibernateSessionFactory.getSession();
		
		Iterator<?> properties = dataObject.keys();
		while(properties.hasNext()){
			String propertyName = properties.next().toString();
			Object propertyValue = dataObject.get(propertyName);
			if(propertyName.equals("gongNengDM") || propertyName.equals("gongNengCZDM") ){
				continue;
			}
			if(propertyValue instanceof JSONObject){
				//对象类型 根据代号字段 取得关联对象
				Object propertyObj = null;
				if(propertyName.equals("xiTong")){
					String xiTongDH = ((JSONObject)propertyValue).getString("xiTongDH");
					propertyObj = sess.createCriteria(XiTong.class).add(Restrictions.eq("xiTongDH", xiTongDH)).uniqueResult();
					if(propertyObj == null){
						String xiTongMC = ((JSONObject)propertyValue).getString("xiTongMC");
						XiTong xiTong = new XiTong();
						xiTong.setXiTongDH(xiTongDH);
						xiTong.setXiTongMC(xiTongMC);
						
						sess.save(xiTong);
						propertyObj = xiTong;
					}
				}else if(propertyName.equals("caoZuoLB")){
					String caoZuoLBMC = ((JSONObject)propertyValue).getString("caoZuoLBMC");
					propertyObj = sess.createCriteria(CaoZuoLB.class).add(Restrictions.eq("caoZuoLBMC", caoZuoLBMC)).uniqueResult();
					if(propertyObj == null){
						String caoZuoLBSM = ((JSONObject)propertyValue).getString("caoZuoLBSM");
						CaoZuoLB caoZuoLB = new CaoZuoLB();
						caoZuoLB.setCaoZuoLBMC(caoZuoLBMC);
						caoZuoLB.setCaoZuoLBSM(caoZuoLBSM);
						
						sess.save(caoZuoLB);
						propertyObj = caoZuoLB;
					}
				}else if(propertyName.equals("shiTiLei")){
					String shiTiLeiDH = ((JSONObject)propertyValue).getString("shiTiLeiDH");
					propertyObj = sess.createCriteria(ShiTiLei.class).add(Restrictions.eq("shiTiLeiDH", shiTiLeiDH)).uniqueResult();
					if(propertyObj == null){
						throw new Exception("在更新功能对象时，发现未定义的实体类："+shiTiLeiDH);
					}
				}else{
					throw new Exception("在更新功能对象时，发现未定义处理方式的对象类型字段："+propertyName);
				}
				PropertyUtils.setProperty(obj,propertyName,propertyObj);
			}else if(propertyValue instanceof JSONArray){
				//集合类型
				if(propertyName.equals("czs")){
					//删除原有字段
					GongNeng gn = (GongNeng)obj;
					JSONArray czArray = (JSONArray)propertyValue;
					List<GongNengCZ> tobeDeleted = new ArrayList<GongNengCZ>();
					for(GongNengCZ cz :gn.getCzs()){
						boolean isCzExists = false;
						for(int i=0;i<czArray.size();i++){
							if(czArray.getJSONObject(i).getString("caoZuoDH").equals(cz.getCaoZuoDH())){
								isCzExists = true;
								break;
							}
						}
						if(!isCzExists){
							System.out.println("===>删除操作："+gn.getGongNengDH()+"."+cz.getCaoZuoDH()+"("+gn.getGongNengMC()+"."+cz.getCaoZuoMC()+")");
							tobeDeleted.add(cz);
						}
					}
					gn.getCzs().removeAll(tobeDeleted);
					//添加新增的字段（更新存在的字段）
					for(int i=0;i<czArray.size();i++){
						String caoZuoDH = czArray.getJSONObject(i).getString("caoZuoDH");
						if(gn.hasCaoZuo(caoZuoDH)){
							GongNengCZ cz = gn.getCaoZuoByDH(caoZuoDH);
							applyDef2Obj(cz, czArray.getJSONObject(i));
						}else{
							GongNengCZ cz = new GongNengCZ();
							applyDef2Obj(cz, czArray.getJSONObject(i));
							cz.setGongNeng(gn);
							gn.getCzs().add(cz);
							System.out.println("<===添加字段："+gn.getGongNengDH()+"."+cz.getCaoZuoDH()+"("+gn.getGongNengMC()+"."+cz.getCaoZuoMC()+")");
						}
					}
//				}else if (propertyName.equals("shiTiLeiLX")){
//					String shiTiLeiLXDH = ((JSONObject)propertyValue).getString("shiTiLeiLXDH");
//					propertyObj = sess.createCriteria(ShiTiLeiLX.class).add(Restrictions.eq("shiTiLeiLXDH", shiTiLeiLXDH)).uniqueResult();
				}else{
					throw new Exception("在更新功能对象时，发现未定义处理方式的对象类型字段："+propertyName);
				}
			}else{
				PropertyUtils.setProperty(obj,propertyName,propertyValue);
			}
		}
		return obj;
	}
	
	
}

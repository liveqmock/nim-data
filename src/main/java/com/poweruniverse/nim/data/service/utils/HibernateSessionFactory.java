package com.poweruniverse.nim.data.service.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;

import com.poweruniverse.nim.base.description.Application;

public class HibernateSessionFactory{
	
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static Configuration sessionConfiguration;
    private static SessionFactory sessionFactory;
    private static JSONObject configuration = null;
    
    private static Set<String> baseEntityMappings = new HashSet<String>();
    
    public static boolean initial(File cfgFile,JSONObject sessConfig) {
    	boolean isOk = true;
		try {
			configuration = sessConfig;
			
		    System.out.println("building SessionFactory ..."+cfgFile.getPath());
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/XiTong.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/GongNeng.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/GongNengLB.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/GongNengCZ.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/CaoZuoLB.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/ShiTiLei.hbm.xml");
		    
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/ShiTiLeiLX.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/ZiDuan.hbm.xml");
		    baseEntityMappings.add("com/poweruniverse/nim/data/hbm/system/ZiDuanLX.hbm.xml");
		    
		    sessionConfiguration = new Configuration ().configure(cfgFile);
		    for(String resourceName:baseEntityMappings){
		    	try {
					sessionConfiguration.addResource(resourceName);
				} catch (Exception e) {
					 System.err.println("添加映射资源("+resourceName+")失败");
					 isOk = false;
					e.printStackTrace();
				}
		    	if(!isOk){
					break;
				}
		    }
			System.out.println("sessionFactory   builded");
	    } catch (Exception e) {
	    	isOk = false;
		    System.err.println("%%%% Error Creating SessionFactory  ("+cfgFile.getPath()+")%%%%");
		    e.printStackTrace();
	    }
	    return isOk;
    }
    
    public static boolean loadMappings() throws Exception{
    	JSONArray xiTongConfigs = configuration.getJSONArray("xiTongs");
    	for(int i=0;i<xiTongConfigs.size();i++){
			JSONObject xiTongConfig = xiTongConfigs.getJSONObject(i);
			String mappingFileName = Application.getInstance().getContextPath()+ "WEB-INF/mapping."+xiTongConfig.getString("name")+".xml";
			
			SAXReader reader = new SAXReader();
			File mappingFile = new File(mappingFileName);
			if(mappingFile.exists()){
				Document doc = reader.read(mappingFile);
				
				Iterator<Element> mappingEls = doc.getRootElement().elements("mapping").iterator();
				Element mappingEl = null;
				while (mappingEls.hasNext()) {
					mappingEl = mappingEls.next();
					String resourceName = mappingEl.attributeValue("resource");
					if(!baseEntityMappings.contains(resourceName)){
						sessionConfiguration.addResource(resourceName);
					}
				}
			}else{
				System.err.println("---------------------------------------");
				System.err.println("系统"+xiTongConfig.getString("name")+"的映射文件("+mappingFileName+")不存在,hibernate没有加载相关的实体类映射！");
				System.err.println("---------------------------------------");
			}
		}
    	if(sessionFactory!=null){
    		sessionFactory.close();
    		sessionFactory = null;
    	}
    	return true;
    }
    
    public static JSONObject getConfiguration(){
    	return configuration;
    }

    public static Configuration getSessionConfiguration(){
    	return sessionConfiguration;
    }

    public static void addMapping(String resourceName){
    	sessionConfiguration.addResource(resourceName);
    }

    private static SessionFactory getSessionFactory(){
    	if(sessionFactory==null){
    		sessionFactory = sessionConfiguration.buildSessionFactory() ;
    	}
    	return sessionFactory;
    }
    
    public static void close(){
    	if(sessionFactory!=null){
    		sessionFactory.close();;
    	}
    }
    
    public static Connection getConnection(){
    	Connection conn = null;
    	try {
			ConnectionProvider cp =((SessionFactoryImplementor)getSessionFactory()).getConnectionProvider();  
			conn = cp.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 return conn;
    }
    
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
		   session = getSessionFactory().openSession();
		   threadLocal.set(session);
		   System.out.println("Session opened ..."+session.hashCode());
		}
		if (!session.getTransaction().isActive()) {
			 session.beginTransaction();
			 System.out.println("Session beginTransaction ..."+session.hashCode());
        }
//		System.out.println("Session geted ..."+session.hashCode());
        return session;
    }
    
    public static void closeSession(boolean commit) throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        if (session != null) {
        	int ret = session.hashCode();
        	if (session.getTransaction().isActive()) {
        		if(commit){
        			try {
						session.getTransaction().commit();
						System.out.println("Session commited ..."+ret);
					} catch (Exception e) {
						e.printStackTrace();
						session.getTransaction().rollback();
						System.out.println("Session commit failure...roolbacked"+ret);
						throw new HibernateException(e.getMessage());
					}
        		}else{
        			session.getTransaction().rollback();
                	System.out.println("Session rollbacked ..."+ret);
        		}
            }
            if (session.isOpen()) {
            	session.close();
            	System.out.println("Session closed ..."+ret);
            }
        }
    }

}

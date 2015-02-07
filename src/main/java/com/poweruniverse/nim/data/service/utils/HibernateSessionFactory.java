package com.poweruniverse.nim.data.service.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;

public class HibernateSessionFactory{
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static Map<String,SessionFactory> sessionFactoryMap = new HashMap<String,SessionFactory>();
    
    public static SessionFactory createSessionFactory(String factoryName,File cfgFile) {
    	SessionFactory cSessionFactory = sessionFactoryMap.get(factoryName);
    	if(cSessionFactory==null){
    		try {
    		    System.out.println("building SessionFactory for "+factoryName+"...");
    		    cSessionFactory = new Configuration ().configure(cfgFile).buildSessionFactory() ;
    			System.out.println("sessionFactory "+factoryName+" builded");
    	    } catch (Exception e) {
    		     System.out.println("%%%% Error Creating SessionFactory "+factoryName+" ("+cfgFile.getPath()+")%%%%");
    		     e.printStackTrace();
    	    }
    	}
	    return cSessionFactory;
    }
    
    public static Connection getConnection(String factoryName){
    	Connection conn = null;
    	try {
			ConnectionProvider cp =((SessionFactoryImplementor)sessionFactoryMap.get(factoryName)).getConnectionProvider();  
			conn = cp.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 return conn;
    }
    
    public static Session getSession(String factoryName) throws HibernateException {
        Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
		   session = sessionFactory.openSession();
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

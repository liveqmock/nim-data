package com.poweruniverse.nim.data.service.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;

public class HibernateSessionFactory{
    private static String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory;
    
    private static SessionFactory getSessionFactory() {
    	if(sessionFactory==null){
    		try {
    		    System.out.println("building SessionFactory...");
    		    sessionFactory = new Configuration ().configure(CONFIG_FILE_LOCATION).buildSessionFactory() ;
    			System.out.println("sessionFactory build");
    	    } catch (Exception e) {
    		     System.out.println("%%%% Error Creating SessionFactory %%%%");
    		     e.printStackTrace();
    	    }
    	}
	    return sessionFactory;
    }
    
    public static Connection getConnection(){
    	Connection conn = null;
    	try {
			ConnectionProvider cp =((SessionFactoryImplementor)sessionFactory).getConnectionProvider();  
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

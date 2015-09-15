package com.nms.util.db;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {
	private static SessionFactory sessionFactory= null;
/*	static {
		try {
		// Create the SessionFactory from hibernate.cfg.xml
		sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
*/

	public void setSessionFactory(SessionFactory newSessionFactory) {
		sessionFactory=newSessionFactory;
	}
	
	public static Session getSession(){
			return sessionFactory.openSession();
	}


}
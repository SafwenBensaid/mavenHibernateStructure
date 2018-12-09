package com.jcg.hibernate.maven;

import java.util.Date;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AppMain {

	static User userObj;
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration
		// File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	public static boolean insert(User user) {
		System.out.println(".......Insert into database.......\n");
		boolean result = true;
		try {
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();
			sessionObj.save(userObj);
			System.out.println("\n.......Record Saved Successfully To The Database.......\n");
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch (HibernateException exception) {
			System.out.println("Problem creating session factory");
			exception.printStackTrace();
			result = false;
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
				result = false;
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		userObj = new User();
		userObj.setUserid(1);
		userObj.setUsername("Editor " + 1);
		userObj.setCreatedBy("Administrator");
		userObj.setCreatedDate(new Date());
		insert(userObj);
			}
}
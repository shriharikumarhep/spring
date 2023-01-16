package com.org.onehome.repository.database;


import java.util.HashMap;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;




public class DBConnectionUtil {

	
	public static Session getCurrentSessionFromConfig() {
		  // SessionFactory in Hibernate 5 example
		  Configuration config = new Configuration();
		  config.configure();
		  // local SessionFactory bean created
		  SessionFactory sessionFactory = config.buildSessionFactory();
		  Session session = sessionFactory.getCurrentSession();
		  return session;
		}
	public static Session getCurrentSession() {
		  // Hibernate 5.4 SessionFactory example without XML
		  Map<String, Object> settings = new HashMap<String, Object>();
		  settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
		  settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
		  settings.put("hibernate.connection.url", 
		    "jdbc:mysql://localhost:3306/onehome");
		  settings.put("hibernate.connection.username", "root");
		  settings.put("hibernate.connection.password", "Admin@123");
		  settings.put("hibernate.current_session_context_class", "thread");
		  settings.put("hibernate.show_sql", "true");
		  settings.put("hibernate.format_sql", "true");

		  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		                                    .applySettings(settings).build();

		  MetadataSources metadataSources = new MetadataSources(serviceRegistry);
		  // metadataSources.addAnnotatedClass(Player.class);
		  Metadata metadata = metadataSources.buildMetadata();

		  // here we build the SessionFactory (Hibernate 5.4)
		  SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		  Session session = sessionFactory.getCurrentSession();
		  return session;
		}
	public static SessionFactory getCurrentSessionFromJPA() {
		  // JPA and Hibernate SessionFactory example
		  EntityManagerFactory emf = 
		      Persistence.createEntityManagerFactory("jpa-tutorial");
		  EntityManager entityManager = emf.createEntityManager();
		  // Get the Hibernate Session from the EntityManager in JPA
		  Session session = entityManager.unwrap(org.hibernate.Session.class);
		  SessionFactory factory = session.getSessionFactory();
		  return factory;
		}

}

package com.pofigist.hibraLibra2.model;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.pofigist.hibraLibra2.entities.*;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/hibra_libra?serverTimezone=UTC");
                settings.put(Environment.USER, "mysql");
                settings.put(Environment.PASS, "mysql");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Library.class);
                configuration.addAnnotatedClass(Bookcase.class);
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Reader.class);
                configuration.addAnnotatedClass(TakenBook.class);
                configuration.addAnnotatedClass(BookOnShelf.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
 
}

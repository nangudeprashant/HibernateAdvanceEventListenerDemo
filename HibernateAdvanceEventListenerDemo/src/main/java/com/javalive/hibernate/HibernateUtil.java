package com.javalive.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.javalive.entity.Book;

/**
 * @author javalive.com 
 *         Interceptor’s methods such as onSave(), onLoad() etc.
 *         are invoked by Hibernate default event listeners. For example, the
 *         Interceptor#onLoad() is called by the DefaultPreLoadEventListener.
 * 
 *         Hibernate native event system is more flexible then interceptor. It
 *         allows us to implement custom event listeners to listen the
 *         PreLoadEvent, PostLoadEvent, PostInsertEvent and more.
 *
 */
public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				BootstrapServiceRegistry bootstrapRegistry = new BootstrapServiceRegistryBuilder()
						.applyIntegrator(new EventListenerIntegrator()).build();

				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder(bootstrapRegistry);

				Map<String, Object> settings = new HashMap<>();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/test1?useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "root");
				// settings.put(Environment.HBM2DDL_AUTO, "update");

				registryBuilder.applySettings(settings);

				registry = registryBuilder.build();

				MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Book.class);

				Metadata metadata = sources.getMetadataBuilder().build();

				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}

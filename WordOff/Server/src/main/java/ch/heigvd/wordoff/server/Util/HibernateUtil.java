package heiggen.server.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Utilities for Hibernate
 *
 * From: http://docs.jboss.org/hibernate/orm/5.2/quickstart/html_single/#hibernate-gsg-tutorial-basic-config
 */
class HibernateUtil {
    private static final SessionFactory sessionFactory = setUp();

    /**
     * Set up the factory
     *
     * @return a SessionFactory to use from Hibernate
     */
    private static SessionFactory setUp() {
        // A SessionFactory is set up once for an application!
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Tearn down the factory
     */
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     * Get the factory like we would a Singleton
     *
     * @return the "singleton" instance
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

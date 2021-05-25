package weather.config;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ConfigureHibernateMethod {
    public SessionFactory GetSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        configuration.buildSessionFactory();
        return configuration.buildSessionFactory();
    }

    public Session GetSession() {
        SessionFactory sessionFactory = GetSessionFactory();
        return sessionFactory.openSession();
    }
}

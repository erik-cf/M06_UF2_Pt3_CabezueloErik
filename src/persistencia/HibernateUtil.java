package persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/*
 * Aquesta classe gestiona la SessionFactory, la inicialitza amb
 * el fitxer de configuraci� d'Hibernate
 */
public class HibernateUtil {

	// Inicialitzem la sessionFactory
	private static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();;
	
	/*
	 * M�tode que retorna la SessionFactory
	 */
	public static SessionFactory getSessionFactory() {				
		return sessionFactory;
	}
	
	/*
	 * M�tode que tanca la sessionFactory
	 */
	public static void closeFactory() {
		sessionFactory.close();
	}
	
}

package services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import bo.Parametres;

public class ParametresService {

	private final static Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
	private final static StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	private final static SessionFactory sessionFactory = cfg.configure().buildSessionFactory(serviceRegistry);

	public static boolean saveParametres(Parametres param) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(param!=null){
			try {
				session.save(param);
				tx.commit();
				session.close();
				return true;
			} catch (Exception e) {
				tx.rollback();
				session.close();
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public static boolean updateParametres(Parametres param) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(param!=null){
			try {
				session.update(param);
				tx.commit();
				session.close();
				return true;
			} catch (Exception e) {
				tx.rollback();
				session.close();
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}



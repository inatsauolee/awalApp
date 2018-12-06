package services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import bo.Groupe;

public class GroupService {

	private final static Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
	private final static StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	private final static SessionFactory sessionFactory = cfg.configure().buildSessionFactory(serviceRegistry);

	public static boolean saveGroup(Groupe group) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(group!=null){
			try {
				session.save(group);
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

	public static Groupe getGroupById(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Groupe u =(Groupe) session.get(Groupe.class, id);
			tx.commit();
			session.close();
			return u;
		} catch (Exception e) {
			tx.rollback();
			session.close();
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Groupe> getAllGroups() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q =session.createQuery("from Groupe order by date");
			List<Groupe> list =(List<Groupe>) q.list();
			tx.commit();
			session.close();
			return list;
		} catch (Exception e) {
			tx.rollback();
			session.close();
			e.printStackTrace();
		}
		return null;
	}
}



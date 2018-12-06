package services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import bo.Contact;

public class UserService {

	private final static Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
	private final static StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	private final static SessionFactory sessionFactory = cfg.configure().buildSessionFactory(serviceRegistry);

	public static boolean saveUser(Contact user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(user!=null){
			try {
				session.saveOrUpdate(user);
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

	public static Contact getUserById(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Contact u =(Contact) session.get(Contact.class, id);
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
	public static List<Contact> getAllContacts() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q =session.createQuery("from Contact order by date");
			List<Contact> list =(List<Contact>) q.list();
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

	public static Contact getUser(String uname, String pass) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q =session.createQuery("from Contact where username =:uname and password=:pass order by date");
			q.setParameter("uname", uname);
			q.setParameter("pass", pass);
			Contact user =(Contact) q.uniqueResult();;
			tx.commit();
			session.close();
			return user;
		} catch (Exception e) {
			tx.rollback();
			session.close();
			e.printStackTrace();
		}
		return null;
	}

	public static Contact getUserByUsername(String uname) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q =session.createQuery("from Contact where username =:uname order by date");
			q.setParameter("uname", uname);
			Contact user =(Contact) q.uniqueResult();;
			tx.commit();
			session.close();
			return user;
		} catch (Exception e) {
			tx.rollback();
			session.close();
			e.printStackTrace();
		}
		return null;
	}

	public static boolean removeUser(Contact user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(user!=null){
			try {
				session.delete(user);
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

	public static boolean updateUser(Contact user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(user!=null){
			try {
				session.update(user);
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



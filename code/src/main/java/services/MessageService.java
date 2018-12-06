package services;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import bo.Message;
import crypting.Cesar;
import crypting.RSA;
import crypting.XOR;

public class MessageService {

	private final static Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
	private final static StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	private final static SessionFactory sessionFactory = cfg.configure().buildSessionFactory(serviceRegistry);

	public static boolean sendMessage(Message message) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(message!=null){
			try {
				message=cryptMessage(message);
				session.save(message);
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

	@SuppressWarnings("unchecked")
	public static List<Message> readMessagesOneDerection(int emet, int dest) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q =session.createQuery("from Message where idEmet=:emet and idDest=:dest order by date");
			q.setParameter("emet", emet);
			q.setParameter("dest", dest);
			List<Message> lm =(List<Message>) q.list();
			tx.commit();
			session.close();
			return lm;
		} catch (Exception e) {
			tx.rollback();
			session.close();
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Message> readMessages(int user1, int user2, boolean priv) {
		List<Message> lm = null;
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q;
			if(priv) {
				q = session.createQuery("from Message where (priv=:priv) and ((idEmet=:emet and idDest=:dest) or (idEmet=:dest and idDest=:emet)) order by date");
				q.setParameter("priv", priv);
				q.setParameter("emet", user1);
				q.setParameter("dest", user2);
			}
			else {
				q = session.createQuery("from Message where (priv=:priv) and (idDest=:dest) order by date");
				q.setParameter("priv", priv);
				q.setParameter("dest", user2);
			}

			lm =(List<Message>) q.list();
			if (!tx.wasCommitted()) tx.commit();
			session.close();
		} catch (Exception e) {
			tx.rollback();
			session.close();
			e.printStackTrace();
		}
		if(lm.size()>0){
			for (int j = 0; j < lm.size(); j++) {
				lm.set(j, decryptMessage(lm.get(j)));
			}
		}
		return lm;
	}

	public static Message cryptMessage(Message m){

		//Cryptage selon le type de parametre

		//RSA
		if(m.getCryptingType().equals("RSA")){
			RSA rsa = new RSA(2048);
			byte[] bytes = rsa.chiffrer(m.getText().getBytes());
			m.setText(Base64.getEncoder().encodeToString(bytes));
			m.setKey(rsa.getN().toString());
			m.setCryptingKey2(rsa.getE().toString());
			m.setCryptingKey3(rsa.getD().toString());
			return m;
		}

		//XOR
		if(m.getCryptingType().equals("XOR")){
			byte[] input = null;
			try {
				input = m.getText().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte [] keyb=m.getKey().getBytes();
			m.setText(XOR.encode_using_xor(input,keyb));
			return m;
		}

		//Cesar
		else if(m.getCryptingType().equals("CESAR")){
			m.setText(Cesar.crypter(m.getText(), 3));
			m.setKey("3");
			return m;
		}
		
		//NONE
		else if(m.getCryptingType().equals("NONE")){
			return m;
		}

		else return m;
	}

	public static Message decryptMessage(Message m){

		//Cryptage selon le type de parametre

		//RSA
		if(m.getCryptingType().equals("RSA")){
			RSA rsa = new RSA(2048);
			byte[] b= Base64.getDecoder().decode(m.getText());
			rsa.setN(m.getKey());
			rsa.setE(m.getCryptingKey2());
			rsa.setD(m.getCryptingKey3());
			m.setText(new String(rsa.dechiffrer(b)));
			return m;
		}
		//XOR
		if(m.getCryptingType().equals("XOR")){
			byte [] keyb=m.getKey().getBytes();
			m.setText(XOR.decode_operation(m.getText(),keyb));
			return m;
		}
		//Cesar
		else if(m.getCryptingType().equals("CESAR")){
			m.setText(Cesar.decrypter(m.getText(), Integer.parseInt(m.getKey())));
			return m;
		}
		
		//NONE
		else if(m.getCryptingType().equals("NONE")){
			return m;
		}

		else return m;
	}



}



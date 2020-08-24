package client;

import org.hibernate.Session;

import util.HibernateUtil;
import entity.Message;


public class HelloWorldClient {
	public static void main(String[] args) {
		
				addMessage();
			//	getAndUpdateMessage();
	
	}

	private static void getAndUpdateMessage() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
      
		Message message = session.get(Message.class, 2L);
		message.setText("Modified");
		//Message message = new Message( "Hello World with Hibernate & JPA Annotations" );
      
		//session.save(message);    
      
		session.getTransaction().commit();
		session.close();
	}

	private static void addMessage() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
      
		Message message = new Message( "Hello World with Hibernate & JPA Annotations" );
      
		session.save(message);    
      
		session.getTransaction().commit();
		session.close();
	}
}


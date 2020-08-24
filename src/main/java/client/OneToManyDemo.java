package client;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Guide;
import entity.Student;
import util.HibernateUtil;

public class OneToManyDemo {
public static void main(String[] args) {

	Session session = HibernateUtil.getSessionFactory().openSession();
	Transaction txn = session.getTransaction();
	try {
		txn.begin();

//		Guide guide1 = new Guide("2000MO10789", "test1", 1000);
//		Guide guide2 = new Guide("2000IM10901", "test2", 2000);
//
//		Student student1 = new Student("2014JT50123", "stud1", guide1);
//		Student student2 = new Student("2014AL50456", "stud2", guide1);
//
//		guide1.getStudents().add(student1);
//		guide1.getStudents().add(student2);
//
//		session.persist(guide1);
//		session.persist(guide2);

		//Updating inverse end
		/*
    	Guide guide = (Guide) session.get(Guide.class, 20L);        	
    	Student student = (Student) session.get(Student.class, 19L);          	
    	guide.getStudents().add(student);
			*/

		//Updating owner
		/*
    	Guide guide = (Guide) session.get(Guide.class, 2L);        	
    	Student student = (Student) session.get(Student.class, 2L);          	
    	student.setGuide(guide);
			*/        	

		//Updating inverse end (after adding addStudent(Student) in Guide entity)
		/*
    	Guide guide = (Guide) session.get(Guide.class, 2L);        	
    	Student student = (Student) session.get(Student.class, 1L);          	
    	guide.addStudent(student);
    	*/

		txn.commit();
	}	catch(Exception e) {
			if(txn != null) { txn.rollback(); }
			e.printStackTrace();
	}	finally {
			if(session != null) { session.close(); }
	}

}
}

package com.Sanket.hibder;

import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class App 
{
    public static void main( String[] args )
    {
//        Student s=new Student(1,"Sanket",35265);
    	
        Configuration con=new Configuration().configure().addAnnotatedClass(Student.class);
        SessionFactory sf=con.buildSessionFactory();
        Session session= sf.openSession();
        session.beginTransaction();
        
//        Random r=new Random();
//        for(int i=1;i<=50;i++) {
//        	Student s=new Student();
//        	s.setId(i);
//        	s.setName("Name"+i);
//        	s.setMarks(r.nextInt(100));
//        	session.save(s);
//        }

//        Query q=session.createQuery("from Student where marks > 50");
//        List<Student> st= q.list();     
//        for(Student s:st) {
//        	s.show();
//        }
        Query q=session.createQuery("from Student where id=10");
        Student st=(Student) q.uniqueResult();     
        st.show();
      
        		
        session.getTransaction().commit();
        session.close();
        
        
    }
}

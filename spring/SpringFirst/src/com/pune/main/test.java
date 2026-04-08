package com.pune.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pune.Student;
import com.pune.employee;

public class test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/pune/configure.xml");
		employee e1 = (employee)context.getBean("emp1");
		e1.display();
		
		Student s1 = (Student)context.getBean("s1");
		s1.display();
	}
}

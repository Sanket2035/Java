package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	StudentRepo sr;
	
	public void registration(Student s) {
		sr.save(s);
	}
	
	public List<Student> getAllStudents(){
		return sr.findAll();
	}
}

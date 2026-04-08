package com.sanket.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDAO {
	@Autowired
	StudentRepo sr;

	public boolean registration(Student s) {
		sr.save(s);
		return true;
	}

	public List<Student> getAllStudents() {
		return sr.findAll();
	}

	public void delete(Long id) {
		if (sr.existsById(id)) {
			sr.deleteById(id);
		} else {
			System.out.println("Id Does Not Exist!");
		}
	}

	public Student login(String email, String password) {
		List<Student> students = sr.findAll();
		for (Student student : students) {
			if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
				return student;
			}
		}
		return null;
	}

	public Student update(Student s) {
		if (sr.existsById(s.getId())) {
			return sr.save(s);
		} else {
			throw new RuntimeException("Student Not Found!");
		}
	}

	public Student getStudentById(Long id) {
		return sr.getById(id);
	}

}

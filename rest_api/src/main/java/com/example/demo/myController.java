package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class myController {

	@Autowired
	StudentService ss;
	
	@GetMapping("/")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tested ok");
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Student s){
		try {
			ss.registration(s);
			return ResponseEntity.status(HttpStatus.CREATED).body("Student Registered");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Student Not Registered");
		}
	}
	
	@GetMapping("/viewstudents")
	public ResponseEntity<List<Student>> viewStudents(){
		List<Student> data = ss.getAllStudents();
		return ResponseEntity.ok(data);
	}
}

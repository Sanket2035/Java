package com.Demo.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.Demo.Models.Student;

public class StudentDAO {
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int register(Student s) {
		String sql="insert into student_model(name,email,pass,address) values('"+s.getName()+"','"+s.getEmail()+"','"+s.getPassword()+"','"+s.getAddress()+"')";
		return template.update(sql);
	}
	
	public List<Student> getAllStudents(){
		String sql = "select * from student_model order by id desc";
		
		return template.query(sql, (rs,numberofrows)->{
			Student s = new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setEmail(rs.getString("email"));
			s.setPassword(rs.getString("pass"));
			s.setAddress(rs.getString("address"));			
			return s;
		});
	}
	
	public void delete(int id) {
		String sql = "delete from student_model where id = ?";
		template.update(sql,id);
	}

	public Student getStudent(int id) {
		String sql = "select * from student_model where id = ?";
		return template.queryForObject(sql,(rs,numberofrows)->{
			Student s = new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setEmail(rs.getString("email"));
			s.setPassword(rs.getString("pass"));
			s.setAddress(rs.getString("address"));			
			return s;
		},id);
	}

	public void update(Student s) {
		String sql = "update student_model set name = ?,email = ?,pass = ?,address = ? where id = ?";
		template.update(sql,s.getName(),s.getEmail(),s.getPassword(),s.getAddress(),s.getId());
	}
}

package com.Demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Demo.Models.Student;
import com.Demo.dao.StudentDAO;

@Controller
public class controller {
//	@PostMapping("/add")
//	public String addition(@RequestParam int num1,@RequestParam int num2, Model m) {
//		int result= num1+num2;
//		m.addAttribute("result",result);
//		return "welcome";
//	}
	
	@Autowired
	StudentDAO sdao;
	
	@PostMapping("/register")
	public String register(@ModelAttribute Student s) {
		sdao.register(s);
		return "redirect:/studentsdata";
	}
	
	@GetMapping("/studentsdata")
	public String welcomePage(Model m) {
		List<Student> list = sdao.getAllStudents();
		m.addAttribute("list",list);
		return "studentsdata";
	}
	
	@GetMapping("/deletestudent/{id}")
	public String deletestudent(@PathVariable int id) {
		sdao.delete(id);
		return "redirect:/studentsdata";
	}
	
	@GetMapping("/editprofile/{id}")
	public String editprofile(@PathVariable int id,Model m) {
		Student s = sdao.getStudent(id);
		m.addAttribute("s",s);
		return "edit";
	}
	
	@PostMapping("/update")
	public String updatestudent(@ModelAttribute Student s) {
		sdao.update(s);
		return "redirect:/studentsdata";
	}
}

package com.sanket.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class myController {
	@Autowired
	StudentDAO sdao;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/about")
	public String aboutpage() {
		return "about";
	}

	@GetMapping("/viewdata")
	public String viewdata(Model m) {
		List<Student> list = sdao.getAllStudents();
		m.addAttribute("list", list);
		return "viewdata";
	}

	@GetMapping("/registerpage")
	public String registerpage() {
		return "register";
	}

	@GetMapping("/loginpage")
	public String loginpage() {
		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model m, HttpSession session) {
		Student student = (Student) session.getAttribute("user");
		m.addAttribute("user", student);
		return "dashboard";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute Student s) {
		boolean result = sdao.registration(s);
		if (result) {
			System.out.println("Registration Successfully..");
			return "redirect:/viewdata";
		} else {
			System.out.println("Registration fail");
			return "redirect:/";
		}
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model m) {
		Student student = sdao.login(email, password);
		if (student != null) {
			session.setAttribute("user", student);
			m.addAttribute("user", student);
			System.out.println("Login Successfully..");
			return "redirect:/dashboard";
		} else {
			System.out.println("Login fail");
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Student s) {
		Student result = sdao.update(s);
		if (result != null) {
			System.out.println("Update Successfull..");
			return "redirect:/viewdata";
		} else {
			System.out.println("Update fail");
			return "redirect:/viewdata";
		}
	}

	@GetMapping("/deletestudent/{id}")
	public String deletestudent(@PathVariable Long id) {
		sdao.delete(id);
		return "redirect:/viewdata";
	}

	@GetMapping("/edit/{id}")
	public String editstudent(@PathVariable Long id, Model m) {
		Student student = sdao.getStudentById(id);
		m.addAttribute("s", student);
		return "edit";
	}

}

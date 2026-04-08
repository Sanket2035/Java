package com.csts.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/adminLogin")
	public String adminLogin() {
		return "adminLogin";
	}
}

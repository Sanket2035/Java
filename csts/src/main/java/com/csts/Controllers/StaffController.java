package com.csts.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffController {

	@GetMapping("/customerRegister")
	public String customerRegister() {
		return "customerRegister";
	}

	@GetMapping("/customerLogin")
	public String customerLogin() {
		return "customerLogin";
	}
}

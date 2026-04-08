package com.csts.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

	@GetMapping("/staffLogin")
	public String staffLogin() {
		return "staffLogin";
	}
}

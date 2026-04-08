package com.sanket.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class myController {
	
	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/about")
	public String aboutpage() {
		return "about";
	}
	
}

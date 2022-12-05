package com.org.onehome.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
public class LoginController {

	@GetMapping("/")
	public String loginPage()
	{
		return "loginpage";
	}
}

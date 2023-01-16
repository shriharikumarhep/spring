package com.org.onehome.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@GetMapping("/")
	public String loginPage()
	{
		return "You will acheive all the greatness in life..Good Luck !";
	}
}

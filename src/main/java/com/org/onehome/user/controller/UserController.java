package com.org.onehome.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.org.onehome.CommonResponse;
import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path="/all")
	  public @ResponseBody Iterable<UserModel> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return userRepository.findAll();
	  }
	
	@PostMapping(path="/create",consumes = "application/json",produces = "application/json")
	  public @ResponseBody ResponseEntity create(@RequestBody UserModel model) {
	    // This returns a JSON or XML with the users
		CommonResponse result = new CommonResponse();
		try {
			result.message = "Added Succesfully";
			result.data = userRepository.save(model);
			result.statusCode = 200;
		}catch (Exception e) {
			
			e.printStackTrace();
			result.message = "Added Failed !"+e.getLocalizedMessage();
			result.data = null;
			result.statusCode = 300;
		}
		
		 return new ResponseEntity<>(
			      "Your age is " , 
			      HttpStatus.OK);
	}
}

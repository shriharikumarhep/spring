package com.org.onehome.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	UserRepository userRepository;
	

	@GetMapping(path = "/all")
	public @ResponseBody CollectionModel<UserModel> getAllUsers() {
		// This returns a JSON or XML with the users
		return CollectionModel.of(userRepository.findAll());
	}
	

}

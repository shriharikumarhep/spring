package com.org.onehome.user.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.onehome.CommonResponse;
import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRepresentationModel assembler;
	
	@GetMapping(path="/all")
	  public @ResponseBody Iterable<UserModel> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return CollectionModel.of(userRepository.findAll());
	  }

	@PostMapping(path="/create",consumes = "application/json",produces = "application/json")
	  public @ResponseBody CommonResponse create(@RequestBody UserModel model) {
	    // This returns a JSON or XML with the users
		CommonResponse result = new CommonResponse();
		try {
			result.status = true;
			result.message = "Added Succesfully";
			result.data = userRepository.save(model);
			result.statusCode = 200;
		}catch (Exception e) {			
			e.printStackTrace();
			result.status = false;
			result.message = "Added Failed !"+e.getLocalizedMessage();
			result.data = null;
			result.statusCode = 300;
		}
		return result;
	    
	  }
	@GetMapping(path="/{id}",consumes = "*/*",produces = "application/json")
	  public @ResponseBody CommonResponse getUserById(@PathVariable("id") Long id) {
	    // This returns a JSON or XML with the users
		CommonResponse result = new CommonResponse();
		try {
			UserModel user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
			 Link link=WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users");
			Link link2=WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
			ArrayList<Link> list= new ArrayList<Link>();
			list.add(link2);
			list.add(link);
			result.data =  assembler.toModel(user);//EntityModel.of(user).add(list);
			 
			result.status = true;
			result.message = "Added Succesfully";
			result.statusCode = 200;
		}catch (Exception e) {			
			e.printStackTrace();
			result.status = false;
			result.message = "Added Failed !"+e.getLocalizedMessage();
			result.data = null;
			result.statusCode = 302;
		}
		return result;
	    
	  }
	 @DeleteMapping("delete/{id}")
	  CommonResponse deleteEmployee(@PathVariable Long id) {

		CommonResponse result = new CommonResponse();
		try {
			userRepository.deleteById(id);
			result.data = null;				
			result.status = true;
			result.message = "Deleted Succesfully";
			result.statusCode = 200;
		}catch (Exception e) {			
			e.printStackTrace();
			result.status = false;
			result.message = "Delete Failed !"+e.getLocalizedMessage();
			result.data = null;
			result.statusCode = 300;
		}
		return result;
	  }

}

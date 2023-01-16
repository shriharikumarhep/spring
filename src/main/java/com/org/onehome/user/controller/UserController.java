package com.org.onehome.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.org.onehome.CommonResponse;
import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;
import com.org.onehome.security.SecurityUtil;

import net.minidev.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRepresentationModel assembler;

	@GetMapping(path = "/all")
	public @ResponseBody CollectionModel<UserModel> getAllUsers() {
		// This returns a JSON or XML with the users
		return CollectionModel.of(userRepository.findAll());
	}
	@RequestMapping("/dashboard")
	public ModelAndView dashboard(Model model) {
		System.out.println("-----Calling dashboard"+SecurityUtil.getInstance().isAuthenticated());
		ModelAndView mv = new ModelAndView("index.html");
		mv.addObject("userlist", userRepository.findAll());
		 if (!SecurityUtil.getInstance().isAuthenticated()) {
		        mv =  new ModelAndView("login");
		    }else
		    {
		    	mv.addObject("userlist", userRepository.findAll());
		    }
		 
		 return mv;
		//return "index.html";
	}
	@GetMapping(path="/create")
	public ModelAndView loginPage(Model model) {
		System.out.println("-----Calling create user");
		ModelAndView mv = new ModelAndView("create_user.html");
		 
		 return mv;
		//return "index.html";
	}
	@GetMapping(path="/edit")
	public ModelAndView editUser(Model model,@RequestParam Map<String, String> req) {
		System.out.println("-----Calling edit user"+model+"----"+req);
		ModelAndView mv = new ModelAndView("edit_user.html");
		try {
			long id = (long) model.getAttribute("id"); 
		UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		mv.addObject("user", user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 return mv;
		//return "index.html";
	}

	@PostMapping(path = "/createuser", consumes = {"application/x-www-form-urlencoded","application/json"}, produces = "application/json")
	public @ResponseBody CommonResponse create( @RequestParam Map<String, String> model) {
		// This returns a JSON or XML with the users
		System.out.println("-----Calling create REST user"+model.toString());
		CommonResponse result = new CommonResponse();
		try {
			UserModel user = new UserModel();
			user.name = model.get("username");
			user.password = model.get("password");
			result.status = true;
			result.message = "Added Succesfully";
			result.data = userRepository.save(user);
			result.statusCode = 200;
		} catch (Exception e) {
			e.printStackTrace();
			result.status = false;
			result.message = "Added Failed !" + e.getLocalizedMessage();
			result.data = null;
			result.statusCode = 300;
		}
		return result;

	}
	
	@RequestMapping(path="register")
	public String register()
	{
		return "register.html";
	}

	@GetMapping(path = "/{id}", consumes = "*/*", produces = "application/json")
	public @ResponseBody CommonResponse getUserById(@PathVariable("id") Long id) {
		// This returns a JSON or XML with the users
		CommonResponse result = new CommonResponse();
		try {
			UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
			Link link = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users");
			Link link2 = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
			ArrayList<Link> list = new ArrayList<Link>();
			list.add(link2);
			list.add(link);
			result.data = assembler.toModel(user);// EntityModel.of(user).add(list);
			result.status = true;
			result.message = "Added Succesfully";
			result.statusCode = 200;
		} catch (Exception e) {
			e.printStackTrace();
			result.status = false;
			result.message = "Added Failed !" + e.getLocalizedMessage();
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
		} catch (Exception e) {
			e.printStackTrace();
			result.status = false;
			result.message = "Delete Failed !" + e.getLocalizedMessage();
			result.data = null;
			result.statusCode = 300;
		}
		return result;
	}
	

	@RequestMapping("resttemp/{id}")
	public String normalRestTemplate(@PathVariable Long id)
	{
		RestTemplate resttemplate = new RestTemplate();
		URI uri;
		ResponseEntity<String> output = null;
		try {
			uri = new URI("https://jsonplaceholder.typicode.com/todos/1");
			 output = resttemplate.getForEntity(uri,String.class);
			System.out.println(output);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		
		return "";
	}

}

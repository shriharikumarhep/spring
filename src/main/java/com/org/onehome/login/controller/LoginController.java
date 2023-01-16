package com.org.onehome.login.controller;

import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;
import com.org.onehome.security.AppSecurityConfig;
import com.org.onehome.security.SecurityUtil;
import com.org.onehome.security.jwt.JwtGeneratorImpl;
import com.org.onehome.security.jwt.JwtGeneratorInterface;
import com.org.onehome.user.controller.UserController;
import com.org.onehome.user.controller.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtGeneratorImpl jwtGeneratorImpl;
	
	/*
	 * @RequestMapping("/") public ModelAndView index(Model model) {
	 * System.out.println("-----Calling / on path");
	 * if(SecurityUtil.getInstance().isAuthenticated()) {
	 * 
	 * return new UserController().dashboard(model); }else { return new
	 * ModelAndView("forward:/login"); }
	 * 
	 * }
	 */
	
	@RequestMapping("/generaldash")
	public ModelAndView loginPage(Model model) {
		System.out.println("-----Calling dashboard");
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
	@RequestMapping("/")
	public String login(Model model) {
		System.out.println("-----Calling login page..2"+model.toString());
		return "loginpage.html";
	}
	@RequestMapping("/loginJwt")
	public ResponseEntity<?> login(@RequestBody UserModel user) {
		System.out.println("-----Calling login page..2"+user.toString());
		try {
		      if(user.name == null || user.password == null) {
		    	  System.out.println("eer 1");
		      throw new UserNotFoundException("UserName or Password is Empty");		      
		      }
		      UserModel userData = userRepository.getUserByNameAndPassword(user.getUserName(), user.getPassword());
		      if(userData == null){
		    	  System.out.println("eer 2");
		         throw new UserNotFoundException("UserName or Password is Invalid");
		      }
		      System.out.println("eer 3");
		      return new ResponseEntity<>(jwtGeneratorImpl.generateToken(user), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		
		
	}

	/*
	 * @RequestMapping("/login-error.html") public String loginError(Model model) {
	 * model.addAttribute("loginError", true); return "login.html"; }
	 */
	@RequestMapping("/logout")
	public String validate(Model model) {
		System.out.println("-----Calling login page..3");
		return "logout-success.html";
	}

	@PostMapping("/dashboard")
	public ModelAndView login(Model model, HttpServletRequest request, HttpServletResponse respone) {
		System.out.println("--dashboard");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println("Params ==" + request.getParameter("username"));
		System.out.println("Params ==" + request.getParameter("password"));
		ModelAndView mv = new ModelAndView("/loginpage.html");
		/*
		 * if(username.equals("admin") && password.equals("admin")) {
		 */

		System.out.println("Userlist" + userRepository.count());
		mv = new ModelAndView("dash");
		/* } */	
		mv.addObject("userlist", userRepository.findAll());

		// return mv;
		// return "dash.jsp";
		System.out.println("MV="+mv.getViewName());

		return mv;
	}
}

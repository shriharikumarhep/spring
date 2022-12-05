package com.org.onehome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class OneHomeApplication implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(OneHomeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * System.out.println("Application Started.."); UserModel model = new
		 * UserModel(); model.name = "test 2"; model.password = "test";
		 * 
		 * var result = userRepository.save(model); if(result!=null) {
		 * System.out.println("Username =="+result.toString()); result.toString(); }else
		 * { System.out.println("failed"); }
		 */
	}

}

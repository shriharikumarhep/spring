package com.org.onehome;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.netflix.discovery.EurekaClient;
import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;
import com.org.onehome.model.Authority;
import com.org.onehome.model.Role;
import com.org.onehome.repository.AuthRepository;
import com.org.onehome.repository.RoleRepository;
import com.org.onehome.repository.database.DBConnectionUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.org.onehome")
@EnableWebMvc
@Transactional
public class OneHomeApplication extends SpringBootServletInitializer implements CommandLineRunner {
		
	 
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AuthRepository authRepository;

	public static void main(String[] args) {
		
		SpringApplication.run(OneHomeApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OneHomeApplication.class);
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
		UserModel newUser = new UserModel();
		newUser.setName("user1");
		newUser.setPassword("user1");
		newUser.setRole(roleRepository.getById(1L));
		
	
	
		Role role  = roleRepository.getById(1L);
		
		
		ArrayList authorities = new  ArrayList();
		Authority write = new Authority();
		write.setName("CREATE");
		write.setId(1L);
		write.setRole(role);
		
		
		DBConnectionUtil.getCurrentSession().beginTransaction();
		
		
		authRepository.saveAll(authorities);
		DBConnectionUtil.getCurrentSession().close();
		
	
	}

}

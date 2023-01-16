package com.org.onehome.login.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.org.onehome.login.model.UserModel;


@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	@Test
	public void test() {


		UserModel model = new UserModel();
		model.name = "test";
		model.password = "test";
		
		var result = userRepository.save(model);
		if(result!=null)
		{
			result.toString();
		}else
		{
			System.out.println("failed");
		}
	}

}

package com.org.onehome.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.org.onehome.login.model.UserModel;

public class FeignClients {
	
	
	@FeignClient("${spring.application.name}")
    public interface UserClient {

        @GetMapping("/test/user/{id}")
        ResponseEntity<UserModel> getUser(@PathVariable("id") String id);

    }

}

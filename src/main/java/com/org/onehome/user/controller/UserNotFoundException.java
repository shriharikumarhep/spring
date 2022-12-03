package com.org.onehome.user.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class UserNotFoundException extends RuntimeException {
	
    UserNotFoundException(Long id)
    {
    	super("Could not find employee " + id);
    	
    }
    
    
}

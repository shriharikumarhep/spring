package com.org.onehome.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;
import com.org.onehome.user.controller.UserNotFoundException;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository repository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 
		UserModel user = repository.findByName(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("User not found !");
		}
		
		return new UserPrincipal(user);
	}

}

package com.org.onehome.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.org.onehome.login.model.UserModel;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	private UserModel user;
	
	
	
	public UserPrincipal(UserModel user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<SimpleGrantedAuthority> currentRole = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		System.out.println("ROLE = "+user.role);
		if( ( user.getRole().getName()).equals("ADMIN"))
		{
			currentRole  = Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
			
			
		}else if( ( user.getRole().getName()).equals("USER"))
		{
			currentRole  = Collections.singleton(new SimpleGrantedAuthority("USER"));
			
		}
		System.out.println("ROLE = "+currentRole);
		return currentRole;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.name;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

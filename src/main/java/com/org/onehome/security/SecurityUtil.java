package com.org.onehome.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	static SecurityUtil securityUtil = null;
	
	public static SecurityUtil getInstance() {
		if(securityUtil==null)
		{
			securityUtil = new SecurityUtil();
		}
		return securityUtil;
	}
	public boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}

}

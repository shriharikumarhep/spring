package com.org.onehome.security.jwt;

import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@Component
public class JwtFilterConfig {

	/*
	 * @Bean public FilterRegistrationBean someFilterRegistration() {
	 * 
	 * System.out.println("Filter Bean"); FilterRegistrationBean registration = new
	 * FilterRegistrationBean(); registration.setFilter( new JwtFilter());
	 * registration.addUrlPatterns("/api*");
	 * registration.addInitParameter("paramName", "paramValue");
	 * registration.setName("someFilter"); registration.setOrder(1); return
	 * registration; }
	 */
	
	@Component("restAuthenticationEntryPoint")
	public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			   response.setContentType("application/json");
		        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");

			
		}
	}
	

	
}

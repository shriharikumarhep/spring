package com.org.onehome.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.org.onehome.login.model.UserModel;
import com.org.onehome.login.repository.UserRepository;
import com.org.onehome.security.MyUserDetailService;
import com.org.onehome.security.UserPrincipal;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class JwtFilter extends OncePerRequestFilter {
	
	
	  @Override protected boolean shouldNotFilter(final HttpServletRequest request)
	  throws ServletException { final String path = request.getRequestURI();
	  Set<String> skipUrls = new
	  HashSet<>(Arrays.asList("/customError","/","/loginJwt*","/api*"));
	  AntPathMatcher pathMatcher = new AntPathMatcher(); return
	  skipUrls.stream().anyMatch(p -> pathMatcher.match(p,
	  request.getRequestURI()));
	  
	  }
	 
	 

	@Autowired
	private JwtGeneratorImpl jwtUtil;

	@Autowired
	private MyUserDetailService userDetailsService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException, JWTDecodeException {
		//
		final String authHeader = request.getHeader("Authorization");
		if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			final String jwt = authHeader.substring(7);
			if (jwt == null || jwt.isBlank()) {
				System.out.println("AUTH TOKEN IS EMPTY");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
			} else {
				try {

					final String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
					final UserDetails user = userDetailsService.loadUserByUsername(email);
					System.out.println("User == " + email);
					final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,
							user.getPassword(), user.getAuthorities());
					System.out.println("authToken == " + authToken);
					if (SecurityContextHolder.getContext().getAuthentication() == null) {

						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						System.out.println("authToken == " + authToken);
						SecurityContextHolder.getContext().setAuthentication(authToken);
						
					}
				} catch (Exception exc) {
					System.out.println("Filtering ....Exception  --"+response.isCommitted()+"---" + exc.toString());
					SecurityContextHolder.clearContext();
					// response.sendRedirect("/customError");
					 response.reset();
					 response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT Token");
					 //response.sendRedirect("/customError");
					 return;
					 
				}
			}
		}
		System.out.println("Filtering ...." + request.getRequestURI());
		if(SecurityContextHolder.getContext().getAuthentication()!=null)
		{
			System.out.println("Filtering ....1" + SecurityContextHolder.getContext().getAuthentication());
			if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
			{
				UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				System.out.println("Filtering ....2" + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
				filterChain.doFilter(request, response);
			}
		
	}	
		else
		{
			response.sendRedirect("/");
			//filterChain.doFilter(request, response);
			
		}
		

	}
	

}

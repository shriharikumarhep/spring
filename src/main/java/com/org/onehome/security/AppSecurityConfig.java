package com.org.onehome.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.org.onehome.security.jwt.JwtAuthEntryPoint;
import com.org.onehome.security.jwt.JwtFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity

public class AppSecurityConfig  {
	
	@Autowired
	MyUserDetailService userDetailsService;
	
	@Autowired
	JwtFilter filter;
	
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;
	
	@Bean("DaoMysqlAdapter")
	public DaoAuthenticationProvider authenication()
	{
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		authenticationProvider.setUserDetailsService(userDetailsService);		
		return authenticationProvider;
		
	}


	@Bean public UserDetailsService userDetailsService(PasswordEncoder
			passwordEncoder) { UserDetails user =
			User.withUsername("user").password(passwordEncoder.encode("password")).roles(
					"USER").build();

			UserDetails admin =
					User.withUsername("admin").password(passwordEncoder.encode("admin")).roles(
							"USER", "ADMIN") 
					.build();

			return new InMemoryUserDetailsManager(user, admin); }

		
		
		
		/*
		 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return (web) ->
		 * web.ignoring().requestMatchers("/api*", "/login*","/"); }
		 */ 
		  
		  @Bean
		  public SecurityFilterChain filterChain(HttpSecurity http) throws
		  Exception {
			  System.out.println("Spring Security");
			  http
			  .csrf()
			  .disable()
			  .cors()
			  .disable()
			    .authorizeHttpRequests().requestMatchers("/","/login*").permitAll()
			    .and().authorizeHttpRequests().requestMatchers("/admin").hasAuthority("ADMIN")
						 .anyRequest() .authenticated().and()						 			
				.formLogin((form) -> form
					.loginPage("/").defaultSuccessUrl("/user/dashboard",true)
					.permitAll()
					
				)
			
				
				.logout((logout) -> logout
						.invalidateHttpSession(true)
						 .clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/")
						.permitAll());
			  
			/*	.sessionManagement()				
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);*/
                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		    //---------
                http
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler
						/*
						 * (request, response, ex) -> { response.sendError(
						 * HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage() ); }
						 */
                );
                http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
                    AccessDeniedHandler defaultAccessDeniedHandler = new AccessDeniedHandlerImpl();
                    defaultAccessDeniedHandler.handle(request, response, accessDeniedException);
                });
                
                
		  
		  return http.build(); 
		  }
		 

	@Bean 
	public PasswordEncoder passwordEncoder() { PasswordEncoder encoder =
			PasswordEncoderFactories.createDelegatingPasswordEncoder(); return encoder; 
			}
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

	 
}

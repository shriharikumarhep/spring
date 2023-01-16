package com.org.onehome.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	  private Long id;
	  
	  @NotEmpty(message = "User name should be empty")
	  private String username;
	    
	
	  @NotEmpty(message = "Password should be empty")
	  private String password;
	
}

package com.org.onehome.security.jwt;
import java.util.Map;

import com.org.onehome.login.model.UserModel;


public interface JwtGeneratorInterface {
	Map<String, String> generateToken(UserModel user);
	
}

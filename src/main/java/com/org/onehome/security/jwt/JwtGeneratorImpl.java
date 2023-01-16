package com.org.onehome.security.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.org.onehome.login.model.UserModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtGeneratorImpl implements JwtGeneratorInterface{

	 @Value("${jwt.secret}")
	  private String secret;
	 
	 @Value("${app.jwt.message}")
	 private String message;
	 
	@Override
	public Map<String, String> generateToken(UserModel user) {
		/*
		 * // TODO Auto-generated method stub String jwtToken = Jwts.builder()
		 * .setSubject("USER_DETAILS") .claim("username",user.name) .setIssuedAt(new
		 * Date()) .signWith(SignatureAlgorithm.HS256, secret)
		 * .setIssuer("com.org.onehome") .setExpiration(new Date()) .compact();
		 */
		 	
		      String token = null;
		   try {
			 token =  JWT.create()
			    .withSubject("User Details")
			    .withClaim("email", user.name)
			    .withIssuedAt(new Date())
			    .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
			    .sign(Algorithm.HMAC256(secret));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JWTCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		    
		    Map<String, String> jwtTokenGen = new HashMap<>();
		    jwtTokenGen.put("token", token);
		    jwtTokenGen.put("message", message);
		    
		    System.out.println(token.toString());
		    
		   // System.out.println("---------------"+validateTokenAndRetrieveSubject(jwtToken));
		    return jwtTokenGen;
							
	}
	 public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
	        JWTVerifier verifier = null;
			try {
				verifier = JWT.require(Algorithm.HMAC256(secret))
				        .withSubject("User Details")
				        .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
				        .build();
			} catch (IllegalArgumentException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        DecodedJWT jwt = verifier.verify(token);
	        return jwt.getClaim("email").asString();
	    }
	
}

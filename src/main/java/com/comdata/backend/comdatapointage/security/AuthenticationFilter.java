package com.comdata.backend.comdatapointage.security;

import com.comdata.backend.comdatapointage.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		
		try {
			
			LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
			
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getMatricule(), creds.getPassword() , new ArrayList<>()));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

		org.springframework.security.core.userdetails.
				User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
		System.out.println("username : " + user.getUsername() );
		System.out.println("password : " + user.getPassword() );
		for (GrantedAuthority a : user.getAuthorities()) {
			System.out.println("role : " + a.getAuthority());
		}
		String jwt = Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
				.claim("roles", user.getAuthorities())
				.compact();
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				"{\"" + SecurityConstants.HEADER_STRING + "\":\"" + SecurityConstants.TOKEN_PREFIX+jwt + "\"}"
		);

	}
}

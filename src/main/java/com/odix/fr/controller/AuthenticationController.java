package com.odix.fr.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odix.fr.security.AuthenticationManagerIpml;
import com.odix.fr.security.JwtRequestModel;
import com.odix.fr.security.JwtResponseModel;
import com.odix.fr.security.JwtTokenUtil;
import com.odix.fr.security.UserDetailsServiceImpl;


@CrossOrigin(origins = "http://35.208.199.180:30000")
@RestController
@RequestMapping("/api/gateway/authentication-controller")
public class AuthenticationController {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private AuthenticationManagerIpml authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	// Authentifier un utilisateur via le formulaire de Login
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestModel authenticationRequest) throws Exception {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		logger.warn("JWT Token has been created");

		return ResponseEntity.ok(new JwtResponseModel(token));
	}
	
	// Récupérer le Token quand un utilisateur réinitialise son Password et qu'il va être forwardé directement sans besoin d'authentification
	@PostMapping("/authenticateByResetPassword")
	public ResponseEntity<?> createAuthenticationTokenWhenResetPassword(@RequestBody JwtRequestModel authenticationRequest) throws Exception {
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		logger.warn("JWT Token has been created");

		return ResponseEntity.ok(new JwtResponseModel(token));
	}
	
	// Récupérer le Token quand un utilisateur vient de se créer via Formulaire HomePage, on le redirige directement sans besoin d'authentification
	@PostMapping("/authenticateNewCreatedUser")
	public ResponseEntity<?> createAuthenticationTokenWhenNewUserIsCreated(@RequestBody JwtRequestModel authenticationRequest) throws Exception {
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		logger.warn("JWT Token has been created");

		return ResponseEntity.ok(new JwtResponseModel(token));
	}
}


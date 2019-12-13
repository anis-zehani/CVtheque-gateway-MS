package com.odix.fr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationManagerIpml  implements AuthenticationManager{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	//Seul point d'entrée pour Authentification
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		Authentication authenticationResult = authenticationManager.authenticate(authentication);
		
		return authenticationResult;
	}
}

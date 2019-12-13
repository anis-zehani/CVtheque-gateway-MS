package com.odix.fr.security;

import java.io.Serializable;

public class JwtResponseModel implements Serializable {

	private static final long serialVersionUID = -3464859428302712078L;
	
	private final String jwttoken;

	public JwtResponseModel(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}

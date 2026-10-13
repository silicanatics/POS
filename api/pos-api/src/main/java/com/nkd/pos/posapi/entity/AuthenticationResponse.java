package com.nkd.pos.posapi.entity;

public class AuthenticationResponse {

	private String token;

	public AuthenticationResponse(String token) {
		   this.token = token;
	}

	public AuthenticationResponse() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [token=" + token + "]";
	}
	
	
	
}

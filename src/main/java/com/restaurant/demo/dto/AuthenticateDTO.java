package com.restaurant.demo.dto;

public class AuthenticateDTO {

	private String token;

	public AuthenticateDTO() {
		super();
	}

	public AuthenticateDTO(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}

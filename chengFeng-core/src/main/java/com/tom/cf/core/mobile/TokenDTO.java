package com.tom.cf.core.mobile;


import com.tom.cf.core.utils.IdGen;

public class TokenDTO {
	private String token;
	
	public TokenDTO(){
		this.token =IdGen.uuid();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "token: " + token;
	}
}

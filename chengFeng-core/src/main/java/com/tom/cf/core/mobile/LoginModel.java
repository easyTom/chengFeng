package com.tom.cf.core.mobile;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class LoginModel {
	
	@NotBlank
	private String account;
	@NotBlank
	private String password;
	@NotBlank
	private String device;
	
	private String appKey;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		/*byte [] pwd = new byte[]{};
		try {
			pwd = java.util.Base64.getDecoder().decode(password);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new String(pwd);*/
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

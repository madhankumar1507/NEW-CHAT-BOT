package com.example.demo.Beans;

import com.example.demo.Entities.AuthenticationEntity;

public class RegisterRequest {
       private String status;
       private AuthenticationEntity authenticationEntity;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AuthenticationEntity getAuthenticationEntity() {
		return authenticationEntity;
	}
	public void setAuthenticationEntity(AuthenticationEntity authenticationEntity) {
		this.authenticationEntity = authenticationEntity;
	}
}

package com.example.demo.Services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Constants.Constants;
import com.example.demo.Beans.ForgotPass;
import com.example.demo.Beans.LoginRequest;
import com.example.demo.Beans.OTPCheck;
import com.example.demo.Beans.RegisterRequest;
import com.example.demo.Entities.AuthenticationEntity;
import com.example.demo.Entities.EmailEntity;
import com.example.demo.Repositories.LoginDetailsRepo;
import com.example.demo.Services.EmailService;

@Service
public class LoginDetailsServices {
	@Autowired
	LoginDetailsRepo loginDetailaRepo;
	@Autowired(required = false)
	Constants Constants;
	@Autowired
	EmailService emailService;
	
	//Validation of details entered by user
	public String getLoginDetails(LoginRequest request) {
		String response = "";
		AuthenticationEntity authenticationDetails = loginDetailaRepo.findByEmailIdAndIsActiveTrue(request.getEmailId());
		if(authenticationDetails!=null) {
			if(authenticationDetails.getPassword().equals(request.getPassword())) {
				response = Constants.YES;
			}
			else {
				response = Constants.NO;
			}
		}
		else {
			response = Constants.NO;
		}
		return response;
	}
	
	//To save Registered details in DB
	public RegisterRequest saveRegestrationDetails(AuthenticationEntity request) {
		RegisterRequest registrationDetails = new RegisterRequest();
		AuthenticationEntity authenticationDetails = loginDetailaRepo.findByEmailIdAndIsActiveTrue(request.getEmailId());
		if(authenticationDetails==null) {
			request.setChecker("0");
			request.setMobileNumber("1");
			loginDetailaRepo.save(request);
			registrationDetails.setAuthenticationEntity(request);
			registrationDetails.setStatus("Registered Successfully");
			return registrationDetails;
			}
		else {
			registrationDetails.setStatus("Aleready Registered");
			return registrationDetails;
		}
		
	}
	public String forgotPasswordCheck(ForgotPass request) {
		AuthenticationEntity authenticationDetails = loginDetailaRepo.findByEmailIdAndIsActiveTrue(request.getEmailId());
		if(null!=authenticationDetails) {
			long min = 100000;  
			long max = 999999;   
			long otp = (int)(Math.random()*(max-min+1)+min); 
			authenticationDetails.setChecker(Long.toString(otp));
			loginDetailaRepo.save(authenticationDetails);
			EmailEntity emailDetails = new EmailEntity();
			emailDetails.setMsgBody(Long.toString(otp));
			emailDetails.setSubject("OTP for resetting password");
			emailDetails.setRecipient(request.getEmailId());
			String response = emailService.sendSimpleMail(emailDetails);
			return response;
			}
		else {
			return "Not an User";
		}
	}
	public String otpCheck(OTPCheck request) {
		String response = "";
		AuthenticationEntity authenticationDetails = loginDetailaRepo.findByEmailIdAndIsActiveTrue(request.getEamilId());
		if(authenticationDetails!=null) {
			if(authenticationDetails.getChecker().equals(request.getOtp())) {
				response = Constants.YES;
			}
			else {
				response = Constants.NO;
			}
		}
		else {
			response = Constants.NO;
		}
		return response;
	}
	
	public String saveNewPassword(OTPCheck request) {
		AuthenticationEntity authenticationDetails = loginDetailaRepo.findByEmailIdAndIsActiveTrue(request.getEamilId());
		if(authenticationDetails!=null) {
			if(null!=request.getPassword())
			authenticationDetails.setPassword(request.getPassword());
			return "Passwor updated Successfully";
		}
		else {
			return "Something wrong in saving password";
		}
	}
}


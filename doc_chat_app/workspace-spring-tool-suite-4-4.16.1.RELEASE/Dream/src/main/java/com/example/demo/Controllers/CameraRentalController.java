package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Beans.RentalProductList;
import com.example.demo.Beans.RentalRegistrationBean;
import com.example.demo.Beans.RentalRegistrationResponse;
import com.example.demo.Beans.cameraDetailsBean;
import com.example.demo.Entities.CameraEntity;
import com.example.demo.Entities.RentalCameraDetailsEntity;
import com.example.demo.Services.RentalRegistrationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class CameraRentalController {
	
	@Autowired
	RentalRegistrationService rentalRegistrationService;
	
	@PostMapping(value = "/rentalRegistration", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RentalRegistrationResponse rentalRegistration(@RequestBody RentalRegistrationBean request) {
		RentalRegistrationResponse response = rentalRegistrationService.saveRentalDetails(request);
		return response;
	}
	@PostMapping(value = "/saveRentalProducts", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody cameraDetailsBean saveRentalProducts(@RequestBody RentalCameraDetailsEntity request) {
		cameraDetailsBean response = rentalRegistrationService.saveRentalCameratDetails(request);
		return response;
	}
	@PostMapping(value = "/getRentalProductsList", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<RentalProductList> getRentalProductsList(@RequestBody String request) {
		List<RentalProductList> response = rentalRegistrationService.getRentalProductsList(request);
		return response;
	}
	@PostMapping(value = "/getCamSpecs", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RentalCameraDetailsEntity getCamSpecs(@RequestBody String request) {
		RentalCameraDetailsEntity response = rentalRegistrationService.getCameraDetails(request);
		return response;
	}
}

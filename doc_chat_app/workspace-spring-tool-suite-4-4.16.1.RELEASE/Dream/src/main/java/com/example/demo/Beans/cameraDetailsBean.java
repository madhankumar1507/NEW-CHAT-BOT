package com.example.demo.Beans;

import com.example.demo.Entities.RentalCameraDetailsEntity;

public class cameraDetailsBean {
	private String Status;
	private RentalCameraDetailsEntity rentalCameraDetailsEntity;
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public RentalCameraDetailsEntity getRentalCameraDetailsEntity() {
		return rentalCameraDetailsEntity;
	}
	public void setRentalCameraDetailsEntity(RentalCameraDetailsEntity rentalCameraDetailsEntity) {
		this.rentalCameraDetailsEntity = rentalCameraDetailsEntity;
	}
}

package com.example.demo.Beans;

import com.example.demo.Entities.CameraEntity;

public class RentalRegistrationResponse {
    private String Status;
    private CameraEntity cameraEntity;
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public CameraEntity getCameraEntity() {
		return cameraEntity;
	}
	public void setCameraEntity(CameraEntity cameraEntity) {
		this.cameraEntity = cameraEntity;
	}
}

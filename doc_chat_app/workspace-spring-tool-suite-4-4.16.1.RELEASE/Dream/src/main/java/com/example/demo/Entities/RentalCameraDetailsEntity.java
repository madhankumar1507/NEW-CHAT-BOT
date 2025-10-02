package com.example.demo.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="tb_Rental_Product_Details")
public class RentalCameraDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name="rentalCode")
	private String rentalCode;
	@Column(name="cameraPhoto")
	private String cameraPhoto;
	@Column(name="productName")
	private String productName;
	@Column(name="productNumber")
	private String productNumber;
	@Column(name="rentPerDay")
	private String rentPerDay;
	@Column(name="lenseType")
	private String lenseType;
	@Column(name="modelNumber")
	private String modelNumber;
	@Column(name="formFactor")
	private String formFactor;
	@Column(name="effectiveStillResolution")
	private String effectiveStillResolution;
	@Column(name="specialFeature")
	private String specialFeature;
	@Column(name="opticalZoom")
	private String opticalZoom;
	@Column(name="screenSize")
	private String screenSize;
	@Column(name="photoSensorSize")
	private String photoSensorSize;
	@Column(name="isActive")
	private boolean isActive;
	public RentalCameraDetailsEntity() {
		
	}
	public RentalCameraDetailsEntity(String rentalCode, String cameraPhoto, String productName, String productNumber,
			String rentPerDay, String lenseType, String modelNumber, String formFactor,
			String effectiveStillResolution, String specialFeature, String opticalZoom, String screenSize,
			String photoSensorSize, boolean isActive) {
		super();
		this.rentalCode = rentalCode;
		this.cameraPhoto = cameraPhoto;
		this.productName = productName;
		this.productNumber = productNumber;
		this.rentPerDay = rentPerDay;
		this.lenseType = lenseType;
		this.modelNumber = modelNumber;
		this.formFactor = formFactor;
		this.effectiveStillResolution = effectiveStillResolution;
		this.specialFeature = specialFeature;
		this.opticalZoom = opticalZoom;
		this.screenSize = screenSize;
		this.photoSensorSize = photoSensorSize;
		this.isActive = isActive;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRentalCode() {
		return rentalCode;
	}
	public void setRentalCode(String rentalCode) {
		this.rentalCode = rentalCode;
	}
	public String getCameraPhoto() {
		return cameraPhoto;
	}
	public void setCameraPhoto(String cameraPhoto) {
		this.cameraPhoto = cameraPhoto;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public String getRentPerDay() {
		return rentPerDay;
	}
	public void setRentPerDay(String rentPerDay) {
		this.rentPerDay = rentPerDay;
	}
	public String getLenseType() {
		return lenseType;
	}
	public void setLenaseType(String lenseType) {
		this.lenseType = lenseType;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getFormFactor() {
		return formFactor;
	}
	public void setFormFactor(String formFactor) {
		this.formFactor = formFactor;
	}
	public String getEffectiveStillResolution() {
		return effectiveStillResolution;
	}
	public void setEffectiveStillResolution(String effectiveStillResolution) {
		this.effectiveStillResolution = effectiveStillResolution;
	}
	public String getSpecialFeature() {
		return specialFeature;
	}
	public void setSpecialFeature(String specialFeature) {
		this.specialFeature = specialFeature;
	}
	public String getOpticalZoom() {
		return opticalZoom;
	}
	public void setOpticalZoom(String opticalZoom) {
		this.opticalZoom = opticalZoom;
	}
	public String getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	public String getPhotoSensorSize() {
		return photoSensorSize;
	}
	public void setPhotoSensorSize(String photoSensorSize) {
		this.photoSensorSize = photoSensorSize;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}

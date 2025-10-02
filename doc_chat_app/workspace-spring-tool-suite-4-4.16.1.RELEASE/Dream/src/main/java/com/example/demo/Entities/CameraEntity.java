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
@Entity(name="tb_Camera_Rental_Data")
public class CameraEntity {
	public CameraEntity(Long id, String city, String rentalName, String rating, String contact, String address,
			String rentalCode, String rentalEmail, String registeredDate) {
		super();
		this.id = id;
		this.city = city;
		this.rentalName = rentalName;
		this.rating = rating;
		this.contact = contact;
		this.address = address;
		this.rentalCode = rentalCode;
		this.rentalEmail = rentalEmail;
		this.registeredDate = registeredDate;
	}
	public CameraEntity() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRentalName() {
		return rentalName;
	}
	public void setRentalName(String rentalName) {
		this.rentalName = rentalName;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRentalCode() {
		return rentalCode;
	}
	public void setRentalCode(String rentalCode) {
		this.rentalCode = rentalCode;
	}
	public String getRentalEmail() {
		return rentalEmail;
	}
	public void setRentalEmail(String rentalEmail) {
		this.rentalEmail = rentalEmail;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name="city")
	private String city;
	@Column(name="rentalName")
	private String rentalName;
	@Column(name="rating")
	private String rating;
	@Column(name="contact")
	private String contact;
	@Column (name="address")
	private String address;
	@Column(name="rentalCode")
	private String rentalCode;
	@Column(name="rentalEmail")
	private String rentalEmail;
	@Column(name="registeredDate")
	private String registeredDate;
	@Column(name="isActive")
	private boolean isActive=true;
	public CameraEntity(boolean isActive) {
		super();
		this.isActive = isActive;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}

package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.CameraEntity;

public interface RentalRegestrationRepo extends JpaRepository<CameraEntity, Long>{
	CameraEntity findByRentalEmailAndIsActiveTrue(String rentalCode);
	List<CameraEntity> findByCityAndIsActiveTrue(String city);
}

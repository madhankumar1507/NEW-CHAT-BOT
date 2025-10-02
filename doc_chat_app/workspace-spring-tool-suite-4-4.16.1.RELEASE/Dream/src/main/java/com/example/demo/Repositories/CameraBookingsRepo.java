package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.CameraBookingsEntity;

public interface CameraBookingsRepo extends JpaRepository<CameraBookingsEntity, Long> {

}

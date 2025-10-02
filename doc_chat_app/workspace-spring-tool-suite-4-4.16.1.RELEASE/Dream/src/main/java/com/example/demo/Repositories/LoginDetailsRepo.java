package com.example.demo.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.AuthenticationEntity;

public interface LoginDetailsRepo extends JpaRepository<AuthenticationEntity, Long>{
	
	AuthenticationEntity findByEmailIdAndIsActiveTrue(String emailId);
}

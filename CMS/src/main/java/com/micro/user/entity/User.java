package com.micro.user.entity;

import java.util.UUID;

import com.micro.user.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;

import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

 @Entity
@Table(name = "users")
 @Data
	public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private UUID id;


	    private String username;

	    private String password;

	    @Enumerated(EnumType.STRING)
	    private Role role;

	    // getters and setters
	}



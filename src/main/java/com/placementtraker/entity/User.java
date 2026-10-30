package com.placementtraker.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class User {
	
	private Integer id;
	private String name;
	private String email;
	private Long phoneNumber;
	private String branch;
	private String course;
	private double cgpa;
	private Integer graduationYear;
	private String password;
	
	private enum UserRole{
		ADMIN,STUDENT
	}
	
	private String rep;
	
	
}

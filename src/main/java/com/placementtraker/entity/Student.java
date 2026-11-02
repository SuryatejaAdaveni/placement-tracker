package com.placementtraker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "std_personal_details")
@NoArgsConstructor
public class Student{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name is Required")
	@Size(min = 3, max = 100, message = "Name should be 3-100 charecters between")
	@Column(nullable = false)
	private String name;
	
	@NotBlank(message = "Mail is required")
	@Email(message = "Enter a valid email address")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotNull(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$",
			message = "Phone contains exactly 10 digits")
	@Column(nullable = false, unique = true)
	private Long phone;
	
	@NotBlank(message = "Password is Required")
	@Size(min = 8, max = 72, message = "Password contains 8-72 charecters ")
	@Column(nullable = false)
	private String password;
	
	@NotBlank(message = "Country is Required")
	@Size(min = 2, max = 72, message = "Country contains 8-72 charecters ")
	@Column(nullable = false)
	private String country;
	
	@NotBlank(message = "State is Required")
	@Size(min = 2, max = 72, message = "State contains 8-72 charecters ")
	@Column(nullable = false)
	private String state;
	
	@NotBlank(message = "District is Required")
	@Size(min = 2, max = 72, message = "District contains 8-72 charecters ")
	@Column(nullable = false)
	private String district;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="std_acdemic_id", unique = true)
	private StudentAcademicDetails studentAcademicDetails;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name ="std_mock_rating", unique = true)
	private MockRating mockRating;
	
}
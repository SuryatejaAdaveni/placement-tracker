package com.placementtraker.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	
	@NotNull(message = "Student Id is required")
	@Column(unique = true, nullable = false, updatable = false)
	private String studentId;
	
	
	@NotBlank(message = "Name is Required")
	@Size(min = 3, max = 100, message = "Name should be 3-100 charecters between")
	@Column(nullable = false)
	private String name;
	
	@NotBlank(message = "Mail is required")
	@Email(message = "Enter a valid email address")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$",
			message = "Phone contains exactly 10 digits")
	@Column(nullable = false, unique = true)
	private String phone;
	
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
	
	@OneToMany(mappedBy = "student",
			fetch = FetchType.LAZY)
	@JoinColumn(name ="std_mock_rating", unique = true)
	private List<MockRating> mockRating = new ArrayList<>();
	
	@Lob
	@Column(name = "resume_file")
	private byte[] resumeFile;
	
	@PrePersist
	public void generateStudentId() {
		if(studentId==null||studentId.isBlank()) {
			studentId="STU"+java.util.UUID.randomUUID()
			.toString()
			.replace("-", "")
			.substring(0, 8)
			.toUpperCase();
		}
	}
	
	
}
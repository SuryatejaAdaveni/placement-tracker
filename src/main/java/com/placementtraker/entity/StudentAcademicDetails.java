package com.placementtraker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;	
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "std_academics")
@NoArgsConstructor
public class StudentAcademicDetails {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotNull(message = "CGPA is required")
	@DecimalMin(value="0.0", message = "CGPA should greater than 0")
	@DecimalMax(value = "10.0", message = "CGPA should lesser than 10")
	@Column(nullable = false)
	private Double cgpa;
	
	
	@NotBlank(message = "College name is required")
	@Column(nullable = false)
	private String collegeName;
	
	@NotBlank(message = "Location is required")
	@Column(nullable = false)
	private String location;
	
	@NotNull(message = "Backlogs is required")
	@Column(nullable = false)
	@Min(value = 0, message = "Backlogs must not be lesser than 0")
	private Integer backlogs;
	
	@NotNull(message = "Course is required")
	@Column(nullable = false)
	@DecimalMax(value = "100", message = "percentage must not be greater than 100")
	@DecimalMin(value = "0.0", message = "percentage must not be lesser than 0.0")
	private Double tenthPercentage;
	
	@NotNull(message = "Course is required")
	@Column(nullable = false)
	@DecimalMax(value = "100", message = "percentage must not be greater than 100")
	@DecimalMin(value = "0.0", message = "percentage must not be lesser than 0.0")
	private Double twelthPrecentage;
	
	@NotBlank(message = "Course is required")
	@Column(nullable = false)
	private String course;
	
	
	@NotBlank(message = "Branch is required")
	@Column(nullable = false)
	private String branch;
	
	@NotNull(message = "Graduation Year is Required")
	@Min(value = 2020, message = "Invalid graduation year")
	@Max(value = 2027, message = "Invalid graduation year")
	@Column(nullable = false)
	private Integer graduationYear;
	
	@OneToOne(mappedBy = "studentAcademicDetails")
	private Student student;
}
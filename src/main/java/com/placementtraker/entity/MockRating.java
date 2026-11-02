package com.placementtraker.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MockRating {
	
	@NotNull
	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@NotBlank(message = "specifying the technology is required")
	@Column(name = "tech_name",nullable = false)
	private String technology;
	
	@DecimalMax(value = "10.0", message = "Mock rating should not be greater than 10")
	@DecimalMin(value = "0.0", message = "Mock rating should not be lesser than 0")
	@Column(nullable = false)
	private Double rating;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;
}

package com.placementtraker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "JOB")
@Setter
@Getter
public class Job {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Job id is Required")
	@Column(nullable = false, unique = true, updatable = false)
	private String jobId;
	
	@NotBlank(message = "Job role is required")
	@Column(nullable = false)
	private String jobRole;
	
	@NotBlank(message = "Company name is required")
	@Column(nullable = false)
	private String companyName;
	
	@NotNull
	@Column(nullable = false, precision = 12, scale = 2)
	@DecimalMin(value = "0.0")
	private BigDecimal salary;
	
	@NotBlank(message= "Job Descrpition should be minimum 50 charecters")
	@Min(value = 50, message = "Job Description must contains charecters between 50-5000")
	@Column(nullable = false, length = 5000)
	private String jobDescription;
	
	@NotBlank(message = "Additional Criteria's are required")
	@Column(nullable = false, length = 2000)
	private String criteria;
	
	@ElementCollection
	@CollectionTable(name = "job_elgible_branches",
						joinColumns = @JoinColumn(name="job_id"))
	@NotBlank(message = "specifying the branches is required")
	@Column(name = "branch_name",nullable = false)
	private Set<String> eligibleBranches = new HashSet<>();
	
	@NotNull(message = "application date is required")
	@FutureOrPresent(message = "Deadline cannot be in the past")
	@Column(nullable=false)
	private LocalDate applicationDate;

	@OneToMany(mappedBy = "job")
	private List<Application>applications=new ArrayList<>();
	
	@PrePersist
	public void generateJobId() {
		if(jobId==null||jobId.isBlank()) {
			jobId = "JOB"+java.util.UUID.randomUUID()
			.toString()
			.replace("-", "")
			.substring(0, 8)
			.toUpperCase();
		}
	}
	
}
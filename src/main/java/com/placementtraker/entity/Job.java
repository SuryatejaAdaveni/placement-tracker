package com.placementtraker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Job {
	
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String jobId;
	
	@NotBlank(message = "Job role is required")
	private String jobRole;
	
	@NotNull
	private Integer salary;
	
	@NotBlank(message= "Job Descrpition should be minimum 50 charecters")
	@Min(value = 50)
	private String jobDescription;
	
	@NotNull
	private String criteria;
	
	@NotBlank(message = "specifying the branches is required")
	private String branches;
	
}
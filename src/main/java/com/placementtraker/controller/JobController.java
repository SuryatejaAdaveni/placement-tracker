package com.placementtraker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placementtraker.entity.Job;
import com.placementtraker.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/drives")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {
	private final JobService jservice;

	public JobController(JobService jservice) {
		super();
		this.jservice = jservice;
	}

	@GetMapping
	public ResponseEntity<List<Job>> getAllJobs() {
		List<Job> jobs = jservice.getAllJobs();
		return ResponseEntity.ok(jobs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable String id) {
		return ResponseEntity.ok(jservice.getJobById(id));
	}

	@PostMapping("/add")
	public ResponseEntity<String> addJob(@Valid @RequestBody Job job) {
		jservice.addJob(job);
		return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateJobById(@PathVariable String id,  @Valid @RequestBody Job job) {
		jservice.updateJobById(id, job);
		return new ResponseEntity<>("Job updated successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable String id) {
		jservice.deleteJobById(id);
		return new ResponseEntity<>("Job added successfully", HttpStatus.ACCEPTED);
	}
}

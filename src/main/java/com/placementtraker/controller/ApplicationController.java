package com.placementtraker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placementtraker.entity.Application;
import com.placementtraker.entity.ApplicationStatus;
import com.placementtraker.entity.ForwardingStatus;
import com.placementtraker.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
	private final ApplicationService aservice;

	public ApplicationController(ApplicationService aservice) {
		super();
		this.aservice = aservice;
	}

	@GetMapping
	public ResponseEntity<List<Application>> getAllApplications() {
		return ResponseEntity.ok(aservice.getAllApplications());
	}

	@PostMapping("/addApplication")
	public ResponseEntity<Application> applyForJob(@RequestParam String studentId, @RequestParam String jobId) {

		Application application = aservice.addApplication(studentId, jobId);

		return new ResponseEntity<>(application, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateApplication(@PathVariable String id, Application application) {
		aservice.updateApplicationById(id, application);
		return new ResponseEntity<>("Application updated successfully", HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public Application getApplicationById(@PathVariable String id) {
		return aservice.getApplicationById(id);
	}

	// Student views their applications
	@GetMapping("/student/{studentId}")
	public ResponseEntity<List<Application>> getApplicationsByStudent(@PathVariable String studentId) {
		return ResponseEntity.ok(aservice.getApplicationsByStudent(studentId));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteApplicationById(@PathVariable String id) {
		aservice.deleteApplicationById(id);
		return new ResponseEntity<>("Application deleted successfully", HttpStatus.ACCEPTED);
	}

	// Admin updates application status
	@PutMapping("/{id}/status")
	public ResponseEntity<Application> updateApplicationStatus(@PathVariable String id,
			@RequestParam(required = false) ApplicationStatus applicationStatus,
			@RequestParam(required = false) ForwardingStatus forwardingStatus) {

		return ResponseEntity.ok(aservice.updateApplicationStatus(id, applicationStatus, forwardingStatus));
	}

}

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
import org.springframework.web.bind.annotation.RestController;

import com.placementtraker.entity.Application;
import com.placementtraker.service.ApplicationService;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
	private final ApplicationService aservice;

	public ApplicationController(ApplicationService aservice) {
		super();
		this.aservice = aservice;
	}
	
	@GetMapping("/applications")
	public List<Application> getAllApplications(){
		return aservice.getAllApplications();
	}
	@PostMapping("/addApplication")
	public ResponseEntity<String> addApplication(@RequestBody Application application){
		aservice.addApplication(application);
		return new ResponseEntity<>("Application added Successfully", HttpStatus.CREATED);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateApplication(@PathVariable String id, Application application){
		aservice.updateApplicationById(id, application);
		return new ResponseEntity<>("Application updated successfully", HttpStatus.CREATED);
	}
	@GetMapping("/application/{id}")
	public Application getApplicationById(@PathVariable String id) {
		return aservice.getApplicationById(id);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteApplicationById(@PathVariable String id){
		aservice.deleteApplicationById(id);
		return new ResponseEntity<>("Application deleted successfully", HttpStatus.ACCEPTED);
	}
	
}

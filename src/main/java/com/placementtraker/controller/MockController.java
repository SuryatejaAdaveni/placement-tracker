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

import com.placementtraker.entity.MockRating;
import com.placementtraker.service.MockRatingService;

@RestController
@RequestMapping("/api/mock")
public class MockController {
	private final MockRatingService mservice;

	public MockController(MockRatingService mservice) {
		super();
		this.mservice = mservice;
	}
	@GetMapping("/mockRatings")
	public List<MockRating>getAllMocks(){
		return mservice.getAllMockRatings();
	}
	@GetMapping("/mockRating/{id}")
	public MockRating getMockRatingById(@PathVariable Integer id) {
		return mservice.getMockRatingById(id);
	}
	@PostMapping("/addMock")
	public ResponseEntity<String> addMock(@RequestBody MockRating mockRating){
		mservice.addMockRating(mockRating);
		return new ResponseEntity<>("Mock rating added Successfully", HttpStatus.CREATED);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateMockById(@PathVariable Integer id, @RequestBody MockRating mockRating){
		mservice.updateMockRatingById(id, mockRating);
		return new ResponseEntity<>("Mock updated successfully", HttpStatus.CREATED);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteMockById(@PathVariable Integer id){
		mservice.deleteMockRatingById(id);
		return new ResponseEntity<>("Mock deleted successfully", HttpStatus.ACCEPTED);
	}
}

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

import com.placementtraker.entity.MockRating;
import com.placementtraker.service.MockRatingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mockrating")
@CrossOrigin(origins = "http://localhost:5173")
public class MockController {
	private final MockRatingService mservice;

	public MockController(MockRatingService mservice) {
		super();
		this.mservice = mservice;
	}

	@GetMapping
	public ResponseEntity<List<MockRating>> getAllMockRatings() {

		return ResponseEntity.ok(mservice.getAllMockRatings());
	}

	@GetMapping("/{id}")
	public MockRating getMockRatingById(@PathVariable Integer id) {
		return mservice.getMockRatingById(id);
	}

	@PostMapping("/add/{studentId}")
	public ResponseEntity<String> addMock(@Valid @RequestBody MockRating mockRating, @PathVariable String studentId) {
		mservice.addMockRating(studentId, mockRating);
		return new ResponseEntity<>("MockRating Saved Successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/student/{studentId}")
    public ResponseEntity<List<MockRating>> getMockRatingsByStudent(
            @PathVariable String studentId) {

        return ResponseEntity.ok(
                mservice.getMockRatingsByStudent(studentId));
    }


	@PutMapping("/{id}")
	public ResponseEntity<String> updateMockById(@PathVariable Integer id, @RequestBody MockRating mockRating) {
		mservice.updateMockRatingById(id, mockRating);
		return new ResponseEntity<>("Mock updated successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMockById(@PathVariable Integer id) {
		mservice.deleteMockRatingById(id);
		return new ResponseEntity<>("Mock deleted successfully", HttpStatus.ACCEPTED);
	}
}

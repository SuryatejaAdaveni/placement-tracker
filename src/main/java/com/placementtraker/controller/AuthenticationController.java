package com.placementtraker.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.placementtraker.dto.LoginRequest;
import com.placementtraker.dto.LoginResponse;
import com.placementtraker.entity.Student;
import com.placementtraker.service.StudentService;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

	private final StudentService sservice;
	

	public AuthenticationController(StudentService sservice) {
		this.sservice = sservice;
	}

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Student> registerStudent
	(
	@RequestPart("student") Student student,
	@RequestPart("resume") MultipartFile resume,
	@RequestPart(value = "profilePic", required = false) MultipartFile profilePic
	) throws IOException {

		Student savedStudent = sservice.registerStudent(
		        student, resume, profilePic
		    );

		    return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		try {
			LoginResponse response = sservice.login(request);

			return ResponseEntity.ok(response);

		} catch (RuntimeException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

}

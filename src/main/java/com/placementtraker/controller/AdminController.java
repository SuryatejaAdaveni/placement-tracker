package com.placementtraker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.placementtraker.dto.StudentResponse;
import com.placementtraker.entity.Student;
import com.placementtraker.service.StudentService;

@RestController
@RequestMapping("/api/admin/managestudents")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

	private final StudentService sservice;

	public AdminController(StudentService sservice) {
		this.sservice = sservice;
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {

		return ResponseEntity.ok(sservice.getAllStudents());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable String id) {

		return ResponseEntity.ok(sservice.getStudentById(id));
	}

	@PutMapping("/verify/{id}")
	public ResponseEntity<String> verifyStudent(@PathVariable String id) {

		sservice.verifyStudent(id);

		return ResponseEntity.ok("Student verified successfully");
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable String id) {

		sservice.deleteStudentById(id);

		return ResponseEntity.ok("Student deleted successfully");
	}
}

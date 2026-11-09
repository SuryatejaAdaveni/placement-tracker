package com.placementtraker.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.placementtraker.entity.Student;
import com.placementtraker.service.StudentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

	private final StudentService sservice;
	private final ObjectMapper objectMapper;

	public StudentController(StudentService sservice, ObjectMapper objectMapper) {
	    this.sservice = sservice;
	    this.objectMapper = objectMapper;
	}

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> registerStudent(@RequestPart("student") String studentJson,
			@RequestPart("resume") MultipartFile resume) throws IOException {

		Student student = objectMapper.readValue(studentJson, Student.class);

		sservice.registerStudent(student, resume);

		return ResponseEntity.status(HttpStatus.CREATED).body("Student Registered Successfully");
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {

		return ResponseEntity.ok(sservice.getAllStudents());
	}

	@PostMapping("/add")
	public ResponseEntity<String> addingStudent(@Valid @RequestBody Student student) {
		sservice.addStudent(student);
		return new ResponseEntity<>("Student Added Successfully", HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable String id) {
		return sservice.getStudentById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudentById(@PathVariable String id, @RequestBody Student studnet) {
		sservice.updateStudentById(id, studnet);
		return new ResponseEntity<>("Student updated successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable String id) {
		sservice.deleteStudentById(id);
		return new ResponseEntity<>("Student Deleted Successfully", HttpStatus.ACCEPTED);
	}
}

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

import com.placementtraker.entity.Student;
import com.placementtraker.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	private final StudentService sservice;
	
	public StudentController(StudentService sservice){
		this.sservice = sservice;
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addingStudent(@Valid @RequestBody Student student){
		sservice.addStudent(student);
		return new ResponseEntity<>("Student Added Successfully",HttpStatus.CREATED);
	}
	@GetMapping("/viewStudents")
	public List<Student> getAllStudents(){
		return sservice.getAllStudents();
	}
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable String id){
		return sservice.getStudentById(id);
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudentById(@PathVariable String id, @RequestBody Student studnet) {
		sservice.updateStudentById(id, studnet);
		return new ResponseEntity<>("Student updated successfully",HttpStatus.CREATED);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable String id){
		sservice.deleteStudentById(id);
		return new ResponseEntity<>("Student Deleted Successfully",HttpStatus.ACCEPTED);
	}
}


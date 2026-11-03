package com.placementtraker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.placementtraker.entity.Student;
import com.placementtraker.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/student")
public class StudentController {
	
	@Autowired
	StudentService sservice;
	
	@PostMapping("/add")
	public ResponseEntity<String> addingStudent(@Valid @RequestBody Student student){
		sservice.addStudent(student);
		return new ResponseEntity<>("Student Added Successfully",HttpStatus.CREATED);
	}
}


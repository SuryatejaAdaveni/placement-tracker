package com.placementtraker.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.placementtraker.dto.LoginRequest;
import com.placementtraker.dto.LoginResponse;
import com.placementtraker.entity.Student;

public interface StudentService {

	Student addStudent(Student student);

	Student updateStudentById(String id, Student currentStudent);

	List<Student> getAllStudents();

	Student getStudentById(String id);

	void deleteStudentById(String id);

	void verifyStudent(String id);

	Student registerStudent(Student student, MultipartFile resume);

	LoginResponse login(LoginRequest request);

}

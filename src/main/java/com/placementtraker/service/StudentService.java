package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.Student;

public interface StudentService {

	Student addStudent(Student student);
	Student updateStudentById(String id, Student currentStudent);
	List<Student> getAllStudents();
	Student getStudentById(String id);
	void deleteStudentById(String id);
}

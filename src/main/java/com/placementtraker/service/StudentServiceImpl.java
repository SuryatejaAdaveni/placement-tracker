package com.placementtraker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementtraker.entity.Student;
import com.placementtraker.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepo srepo;

	@Override
	public Student addStudent(Student student) {
		return srepo.save(student);
	}

}

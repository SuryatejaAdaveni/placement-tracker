package com.placementtraker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementtraker.entity.Student;
import com.placementtraker.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {
	
	private final StudentRepo srepo;

	public StudentServiceImpl(StudentRepo srepo) {
		super();
		this.srepo = srepo;
	}

	@Override
	public Student addStudent(Student student) {
		return srepo.save(student);
	}

	@Override
	public Student updateStudentById(String id, Student currentStudent) {
		Student updatedStudent = srepo.findById(id).orElse(null);
		if(updatedStudent!=null) {
			updatedStudent.setId(currentStudent.getId());
			updatedStudent.setName(currentStudent.getName());
			updatedStudent.setPhone(currentStudent.getPhone());
			updatedStudent.setEmail(currentStudent.getEmail());
			updatedStudent.setStudentId(currentStudent.getStudentId());
			updatedStudent.setCountry(currentStudent.getCountry());
			updatedStudent.setState(currentStudent.getState());
			updatedStudent.setDistrict(currentStudent.getDistrict());
			updatedStudent.setResumeFile(currentStudent.getResumeFile());
			updatedStudent.setPassword(currentStudent.getPassword());
			return srepo.save(updatedStudent);
		}
		return null;
		
	}

	@Override
	public List<Student> getAllStudents() {
		return srepo.findAll();
	}

	@Override
	public Student getStudentById(String id) {
		return srepo.findById(id).orElse(null);
	}

	@Override
	public void deleteStudentById(String id) {
		srepo.deleteById(id);
		
	}

}

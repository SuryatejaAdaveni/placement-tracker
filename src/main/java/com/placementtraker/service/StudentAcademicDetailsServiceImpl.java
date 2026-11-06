package com.placementtraker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.placementtraker.entity.StudentAcademicDetails;
import com.placementtraker.repo.StudentAcademicDetailsRepo;

@Service
public class StudentAcademicDetailsServiceImpl implements StudentAcademicDetailsService{
	
	private final StudentAcademicDetailsRepo srepo;
	public StudentAcademicDetailsServiceImpl(StudentAcademicDetailsRepo srepo) {
		this.srepo = srepo;
	}
	
	
	@Override
	public StudentAcademicDetails addStudentDetails(StudentAcademicDetails studentAcademicDetails) {
		return srepo.save(studentAcademicDetails);
	}

	@Override
	public StudentAcademicDetails updateStudentDetailsById(Long id, StudentAcademicDetails currentStudentDetails) {
		StudentAcademicDetails updatedstudentDetails= srepo.findById(id).orElse(null);
		if(updatedstudentDetails!=null) {
			updatedstudentDetails.setId(currentStudentDetails.getId());
			updatedstudentDetails.setCollegeName(currentStudentDetails.getCollegeName());
			updatedstudentDetails.setBacklogs(currentStudentDetails.getBacklogs());
			updatedstudentDetails.setBranch(currentStudentDetails.getBranch());
			updatedstudentDetails.setCgpa(currentStudentDetails.getCgpa());
			updatedstudentDetails.setCourse(currentStudentDetails.getCourse());
			updatedstudentDetails.setGraduationYear(currentStudentDetails.getGraduationYear());
			updatedstudentDetails.setLocation(currentStudentDetails.getLocation());
			updatedstudentDetails.setTenthPercentage(currentStudentDetails.getTenthPercentage());
			updatedstudentDetails.setTwelthPrecentage(currentStudentDetails.getTwelthPrecentage());
			return srepo.save(updatedstudentDetails);
		}
		return null;
	}
	

	@Override
	public List<StudentAcademicDetails> getAllStudentDetails() {
		return srepo.findAll();
	}

	@Override
	public StudentAcademicDetails getStudentDetailsById(Long id) {
		return srepo.findById(id).orElse(null);
	}

	@Override
	public void deleteStudentById(Long id) {
		srepo.deleteById(id);
	}

}

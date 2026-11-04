package com.placementtraker.service;

import java.util.List;


import com.placementtraker.entity.StudentAcademicDetails;

public interface StudentAcademicDetailsService  {
	StudentAcademicDetails addStudentDetails(StudentAcademicDetails studentAcademicDetails);
	StudentAcademicDetails updateStudentDetailsById(Long id, StudentAcademicDetails studentDetails);
	List<StudentAcademicDetails> getAllStudentDetails();
	StudentAcademicDetails getStudentDetailsById(Long id);
	void deleteStudentById(Long id);
}

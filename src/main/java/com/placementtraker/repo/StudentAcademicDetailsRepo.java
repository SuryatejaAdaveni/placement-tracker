package com.placementtraker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.StudentAcademicDetails;

public interface StudentAcademicDetailsRepo extends JpaRepository<StudentAcademicDetails, Long> {

}

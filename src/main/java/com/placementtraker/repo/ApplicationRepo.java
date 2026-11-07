package com.placementtraker.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Application;

public interface ApplicationRepo extends JpaRepository<Application, String>{

	List<Application> findByStudent_StudentId(Integer studentId);

}

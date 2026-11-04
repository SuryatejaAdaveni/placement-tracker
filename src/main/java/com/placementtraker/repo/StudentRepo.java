package com.placementtraker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Student;

public interface StudentRepo extends JpaRepository<Student, String> {

}

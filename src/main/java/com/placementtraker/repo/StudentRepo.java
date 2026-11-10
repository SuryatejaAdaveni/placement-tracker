package com.placementtraker.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Student;

public interface StudentRepo extends JpaRepository<Student, String> {
	List<Student> findByRole(String role);
	 Optional<Student> findByEmail(String email);
}


package com.placementtraker.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

    List<Student> findByRole(String role);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);

    void deleteByStudentId(String studentId);
}

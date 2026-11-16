
package com.placementtraker.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Application;

public interface ApplicationRepo
        extends JpaRepository<Application, Long> {

    List<Application> findByStudent_StudentId(String studentId);

    Optional<Application> findByApplicationId(String applicationId);

    boolean existsByJob_IdAndStudent_Id(
            Long jobId,
            Long studentId);
}

package com.placementtraker.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Job;

public interface JobRepo extends JpaRepository<Job, Long> {
	Optional<Job> findByJobId(String jobId);
}

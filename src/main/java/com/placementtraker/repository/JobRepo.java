package com.placementtraker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Job;

public interface JobRepo extends JpaRepository<Job, Integer>{

}

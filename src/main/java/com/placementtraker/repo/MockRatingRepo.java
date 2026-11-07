package com.placementtraker.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.MockRating;

public interface MockRatingRepo extends JpaRepository<MockRating, Integer> {
	List<MockRating> findByStudent_Id(String studentId);
}

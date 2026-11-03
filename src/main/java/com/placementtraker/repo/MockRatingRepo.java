package com.placementtraker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.MockRating;

public interface MockRatingRepo extends JpaRepository<MockRating, Integer> {

}

package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.MockRating;

public interface MockRatingService {
	MockRating addMockRating(String studentId, MockRating mockRating);
	MockRating updateMockRatingById(Integer ratingId, MockRating updatedRating);
	List<MockRating> getAllMockRatings();
	List<MockRating> getMockRatingsByStudent(String studentId);
	MockRating getMockRatingById(Integer id);
	void deleteMockRatingById(Integer id);
	
	

	



}

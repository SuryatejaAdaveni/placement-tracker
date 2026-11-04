package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.MockRating;

public interface MockRatingService {
	MockRating addMockRating(MockRating mock);
	MockRating updateMockRatingById(Integer id, MockRating mock);
	List<MockRating> getAllMockRatings();
	MockRating getMockRatingById(Integer id);
	void deleteMockRatingById(Integer id);
}

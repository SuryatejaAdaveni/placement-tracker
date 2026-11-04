package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.MockRating;
import com.placementtraker.repo.MockRatingRepo;

public class MockRatingServiceImpl implements MockRatingService {
	
	private final MockRatingRepo mrepo;
	
	public MockRatingServiceImpl(MockRatingRepo mrepo) {
		this.mrepo=mrepo;
	}

	@Override
	public MockRating addMockRating(MockRating mock) {
		return mrepo.save(mock);
	}

	@Override
	public MockRating updateMockRatingById(Integer id, MockRating currentMock) {
		
		MockRating updatedMock= mrepo.findById(id).orElse(null);
		if(updatedMock!=null) {
			updatedMock.setId(currentMock.getId());
			updatedMock.setRating(currentMock.getRating());
			updatedMock.setTechnology(currentMock.getTechnology());
			return mrepo.save(updatedMock);
		}
		return null;
	}

	@Override
	public List<MockRating> getAllMockRatings() {
		return mrepo.findAll();
	}

	@Override
	public MockRating getMockRatingById(Integer id) {
		return mrepo.findById(id).orElse(null);
	}

	@Override
	public void deleteMockRatingById(Integer id) {
		mrepo.deleteById(id);
	}

}

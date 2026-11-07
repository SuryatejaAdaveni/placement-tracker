package com.placementtraker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.placementtraker.entity.MockRating;
import com.placementtraker.entity.Student;
import com.placementtraker.repo.MockRatingRepo;
import com.placementtraker.repo.StudentRepo;

@Service
public class MockRatingServiceImpl implements MockRatingService {

	private final StudentRepo srepo;
	private final MockRatingRepo mrepo;

	public MockRatingServiceImpl(StudentRepo srepo, MockRatingRepo mrepo) {
		super();
		this.srepo = srepo;
		this.mrepo = mrepo;
	}

	@Override
	public MockRating addMockRating(String studentId, MockRating mockRating) {
		// TODO Auto-generated method stub

		Student student = srepo.findById(studentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

		mockRating.setStudent(student);

		return mrepo.save(mockRating);

	}
	
	
	
	@Override
	public List<MockRating> getAllMockRatings() {
		return mrepo.findAll();
	}
	
	@Override
	public List<MockRating> getMockRatingsByStudent(String studentId) {
		if (!srepo.existsById(studentId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
		}
		return mrepo.findByStudent_Id(studentId);
	}
	

	@Override
	public MockRating updateMockRatingById(Integer ratingId, MockRating updatedRating) {
		MockRating existingRating = mrepo.findById(ratingId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mock rating not found"));
		
		existingRating.setTechnology(updatedRating.getTechnology());
        existingRating.setRating(updatedRating.getRating());
		
		return mrepo.save(existingRating);
	}
	
		

	@Override
	public MockRating getMockRatingById(Integer id) {
		return mrepo.findById(id).orElse(null);
	}

	@Override
	public void deleteMockRatingById(Integer ratingId) {
		MockRating existingRating = mrepo.findById(ratingId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mock rating not found"));
		mrepo.delete(existingRating);
	}

	
}

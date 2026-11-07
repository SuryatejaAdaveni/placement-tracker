package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.Job;

public interface JobService {
	Job addJob(Job job);
	Job updateJobById(String id, Job currentJob);
	List<Job> getAllJobs();

	Job getJobById(String id);

	void deleteJobById(String id);
}

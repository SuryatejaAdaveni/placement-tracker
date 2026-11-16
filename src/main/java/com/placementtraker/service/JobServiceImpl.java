package com.placementtraker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.placementtraker.entity.Job;
import com.placementtraker.repo.JobRepo;

@Service
public class JobServiceImpl implements JobService {
	
	private final JobRepo jrepo;
	private final NotificationService nservice;
	
	public JobServiceImpl(JobRepo jrepo,NotificationService nservice) {
		this.jrepo= jrepo;
		this.nservice = nservice;
	}

	@Override
	public Job addJob(Job job) {
		Job savedJob = jrepo.save(job);

		nservice.addDriveNotification(savedJob);

	    return savedJob;
	}

	@Override
	public Job updateJobById(String id, Job currentJob) {
		Job updatedJob = jrepo.findById(id).orElse(null);
		
		if(updatedJob!=null) {
			updatedJob.setCompanyName(currentJob.getCompanyName());
			updatedJob.setApplicationDate(currentJob.getApplicationDate());
			updatedJob.setEligibleBranches(currentJob.getEligibleBranches());
			updatedJob.setCriteria(currentJob.getCriteria());
			updatedJob.setJobDescription(currentJob.getJobDescription());
			updatedJob.setJobRole(currentJob.getJobRole());
			updatedJob.setSalary(currentJob.getSalary());
			return jrepo.save(updatedJob);
		}
		return null;
	}

	@Override
	public List<Job> getAllJobs() {
		return jrepo.findAll();
	}

	@Override
	public Job getJobById(String id) {
		return jrepo.findById(id).orElseThrow();
	}

	@Override
	public void deleteJobById(String id) {
		jrepo.deleteById(id);
	}

}

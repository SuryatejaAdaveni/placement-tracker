package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.Job;
import com.placementtraker.repo.JobRepo;

public class JobServiceImpl implements JobService {
	
	private final JobRepo jrepo;
	
	public JobServiceImpl(JobRepo jrepo) {
		this.jrepo= jrepo;
	}

	@Override
	public Job addJob(Job job) {
		return jrepo.save(job);
	}

	@Override
	public Job updateJobById(String id, Job currentJob) {
		Job updatedJob = jrepo.findById(id).orElse(null);
		
		if(updatedJob!=null) {
			updatedJob.setJobId(currentJob.getJobId());
			updatedJob.setId(currentJob.getId());
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

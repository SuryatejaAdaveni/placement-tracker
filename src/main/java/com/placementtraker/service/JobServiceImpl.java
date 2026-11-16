
package com.placementtraker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.placementtraker.entity.Job;
import com.placementtraker.repo.JobRepo;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepo jrepo;

    public JobServiceImpl(JobRepo jrepo) {
        this.jrepo = jrepo;
    }

    @Override
    public Job addJob(Job job) {
        return jrepo.save(job);
    }

    @Override
    public Job updateJobById(String jobId, Job currentJob) {

        Job existingJob = jrepo.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job not found"));

        existingJob.setCompanyName(currentJob.getCompanyName());
        existingJob.setApplicationDate(currentJob.getApplicationDate());
        existingJob.setEligibleBranches(currentJob.getEligibleBranches());
        existingJob.setCriteria(currentJob.getCriteria());
        existingJob.setJobDescription(currentJob.getJobDescription());
        existingJob.setJobRole(currentJob.getJobRole());
        existingJob.setSalary(currentJob.getSalary());

        return jrepo.save(existingJob);
    }

    @Override
    public List<Job> getAllJobs() {
        return jrepo.findAll();
    }

    @Override
    public Job getJobById(String jobId) {
        return jrepo.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job not found"));
    }

    @Override
    public void deleteJobById(String jobId) {

        Job job = jrepo.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job not found"));

        jrepo.delete(job);
    }
}

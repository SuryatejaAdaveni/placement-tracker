
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
    private final NotificationService nservice;

    public JobServiceImpl(
            JobRepo jrepo,
            NotificationService nservice) {

        this.jrepo = jrepo;
        this.nservice = nservice;
    }

    // Admin creates a new placement drive
    @Override
    public Job addJob(Job job) {

        Job savedJob = jrepo.save(job);

        // Notify students about the new drive
        nservice.addDriveNotification(savedJob);

        return savedJob;
    }

    // Admin updates a placement drive
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

    // Get all placement drives
    @Override
    public List<Job> getAllJobs() {
        return jrepo.findAll();
    }

    // Get a placement drive using its business ID
    @Override
    public Job getJobById(String jobId) {

        return jrepo.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job not found"));
    }

    // Delete a placement drive using its business ID
    @Override
    public void deleteJobById(String jobId) {

        Job job = jrepo.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job not found"));

        jrepo.delete(job);
    }
}

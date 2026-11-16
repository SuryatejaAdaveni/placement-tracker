
package com.placementtraker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.placementtraker.entity.Application;
import com.placementtraker.entity.ApplicationStatus;
import com.placementtraker.entity.ForwardingStatus;
import com.placementtraker.entity.Job;
import com.placementtraker.entity.Student;
import com.placementtraker.repo.ApplicationRepo;
import com.placementtraker.repo.JobRepo;
import com.placementtraker.repo.StudentRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo arepo;
    private final StudentRepo srepo;
    private final JobRepo jrepo;

    public ApplicationServiceImpl(
            ApplicationRepo arepo,
            StudentRepo srepo,
            JobRepo jrepo) {

        this.arepo = arepo;
        this.srepo = srepo;
        this.jrepo = jrepo;
    }

    // Student applies for a job
    @Override
    public Application addApplication(String studentId, String jobId) {

        Student student = srepo.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        Job job = jrepo.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Placement drive not found"));

        // Prevent duplicate applications
        boolean alreadyApplied =
                arepo.existsByJob_IdAndStudent_Id(job.getId(), student.getId());

        if (alreadyApplied) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You have already applied for this job");
        }

        Application application = new Application();
        application.setStudent(student);
        application.setJob(job);

        return arepo.save(application);
    }

    // Admin gets all applications
    @Override
    public List<Application> getAllApplications() {
        return arepo.findAll();
    }

    // Find application using its business ID
    @Override
    public Application getApplicationById(String applicationId) {

        return arepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));
    }

    // Update application
    @Override
    public Application updateApplicationById(
            String applicationId,
            Application request) {

        Application existing = arepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));

        if (request.getApplicationStatus() != null) {
            existing.setApplicationStatus(request.getApplicationStatus());
        }

        if (request.getForwardingStatus() != null) {
            existing.setForwardingStatus(request.getForwardingStatus());
        }

        return arepo.save(existing);
    }

    // Delete application using business ID
    @Override
    public void deleteApplicationById(String applicationId) {

        Application application = arepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));

        arepo.delete(application);
    }

    // Get applications belonging to a student
    @Override
    public List<Application> getApplicationsByStudent(String studentId) {

    	if (!srepo.existsByStudentId(studentId)) {
    	    throw new ResponseStatusException(
    	            HttpStatus.NOT_FOUND,
    	            "Student not found");
    	}

        return arepo.findByStudent_StudentId(studentId);
    }

    // Admin updates application and forwarding statuses
    @Override
    public Application updateApplicationStatus(
            String applicationId,
            ApplicationStatus applicationStatus,
            ForwardingStatus forwardingStatus) {

        Application application = arepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Application not found"));

        if (applicationStatus != null) {
            application.setApplicationStatus(applicationStatus);
        }

        if (forwardingStatus != null) {
            application.setForwardingStatus(forwardingStatus);
        }

        return arepo.save(application);
    }
}

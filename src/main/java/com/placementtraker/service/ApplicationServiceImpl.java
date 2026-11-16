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
	private final NotificationService nservice;

	public ApplicationServiceImpl(ApplicationRepo arepo, StudentRepo srepo, JobRepo jrepo,NotificationService nservice) {

		this.arepo = arepo;
		this.srepo = srepo;
		this.jrepo = jrepo;
		this.nservice = nservice;
	}

//  student adding and application for the job
	@Override
	public Application addApplication(String studentId, String jobId) {

		Student student = srepo.findById(studentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

		Job job = jrepo.findById(jobId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Placement drive not found"));

		

		Application application = new Application();

		application.setStudent(student);
		application.setJob(job);

		Application savedApplication = arepo.save(application);

		nservice.addStudentApplicationNotification(savedApplication);

		nservice.addStudentApplicationNotificationForStudent(savedApplication);

		return savedApplication;
	}

	@Override
    @Transactional
    public List<Application> getAllApplications() {

        return arepo.findAll();
    }

	@Override
	public Application getApplicationById(String Id) {
		return arepo.findById(Id).orElse(null);
	}

	@Override
	public Application updateApplicationById(String Id, Application application) {

		Application updatedApplication = arepo.findById(Id).orElse(null);
		if (updatedApplication != null) {
			updatedApplication.setApplicationId(application.getApplicationId());
			updatedApplication.setApplicationStatus(application.getApplicationStatus());
			updatedApplication.setForwardingStatus(application.getForwardingStatus());
			return arepo.save(application);
		}
		return null;

	}

	@Override
	public void deleteApplicationById(String id) {
		arepo.deleteById(id);
	}

	@Override
	@Transactional
	public List<Application> getApplicationsByStudent(String studentId) {

//        if (!srepo.findById(studentId).orElse(null)) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Student not found"
//            );
//        }

		return arepo.findByStudent_StudentId(studentId);
	}

	// Admin updates application and forwarding statuses
	@Override
	public Application updateApplicationStatus(String id, ApplicationStatus applicationStatus,
			ForwardingStatus forwardingStatus) {

		Application application = arepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

		if (applicationStatus != null) {
			application.setApplicationStatus(applicationStatus);
		}

		if (forwardingStatus != null) {
			application.setForwardingStatus(forwardingStatus);
		}
		
		Application updatedApplication = arepo.save(application);

		nservice.addApplicationStatusNotification(updatedApplication);

		return updatedApplication;
	}
}

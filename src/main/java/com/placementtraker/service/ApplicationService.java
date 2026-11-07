package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.Application;
import com.placementtraker.entity.ApplicationStatus;
import com.placementtraker.entity.ForwardingStatus;

public interface ApplicationService {
	Application addApplication(String studentId, String jobId);
	List<Application> getAllApplications();
	Application getApplicationById(String Id);
	Application updateApplicationById(String Id, Application application);
	void deleteApplicationById(String id);
	Application updateApplicationStatus(String id, ApplicationStatus applicationStatus,
			ForwardingStatus forwardingStatus);
	
	



	List<Application> getApplicationsByStudent(String studentId);


}

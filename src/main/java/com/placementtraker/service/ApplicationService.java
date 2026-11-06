package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.Application;

public interface ApplicationService {
	Application addApplication(Application application);
	List<Application> getAllApplications();
	Application getApplicationById(String Id);
	Application updateApplicationById(String Id, Application application);
	void deleteApplicationById(String id);
}

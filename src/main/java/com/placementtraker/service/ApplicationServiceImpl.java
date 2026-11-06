package com.placementtraker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.placementtraker.entity.Application;
import com.placementtraker.repo.ApplicationRepo;

@Service
public class ApplicationServiceImpl implements ApplicationService{

	private final ApplicationRepo arepo;
	
	public ApplicationServiceImpl(ApplicationRepo arepo) {
		super();
		this.arepo = arepo;
	}

	@Override
	public Application addApplication(Application application) {
		
		return arepo.save(application);
	}

	@Override
	public List<Application> getAllApplications() {
		return arepo.findAll();
	}

	@Override
	public Application getApplicationById(String Id) {
		return arepo.findById(Id).orElse(null);
	}

	@Override
	public Application updateApplicationById(String Id, Application application) {
		
		Application updatedApplication=arepo.findById(Id).orElse(null);
		if(updatedApplication!=null) {
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

}

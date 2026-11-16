package com.placementtraker.service;

import java.util.List;

import com.placementtraker.entity.Application;
import com.placementtraker.entity.Job;
import com.placementtraker.entity.Notification;
import com.placementtraker.entity.Student;

public interface NotificationService {

//	 Student Notification services
	void addDriveNotification(Job job);

	void addApplicationStatusNotification(Application application);

	void addStudentApplicationNotificationForStudent(
	        Application application);


	List<Notification> getNotificationsByStudent(String studentId);
	Notification updateNotificationView(Long Id, String studentId);

//	 Admin Notification services

	void addStudentRegistrationNotification(Student student);

	void addStudentApplicationNotification(Application application);

	List<Notification> getAllAdminNotifications();
	Notification updateAdminNotificationView(Long id);

//	 Both Notification services


	void deleteNotificationById(Long id);

}

package com.placementtraker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.placementtraker.entity.Application;
import com.placementtraker.entity.Job;
import com.placementtraker.entity.Notification;
import com.placementtraker.entity.NotificationType;
import com.placementtraker.entity.Role;
import com.placementtraker.entity.Student;
import com.placementtraker.repo.NotificationRepo;
import com.placementtraker.repo.StudentRepo;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepo nrepo;
	private final StudentRepo srepo;

	public NotificationServiceImpl(NotificationRepo nrepo, StudentRepo srepo) {
		this.nrepo = nrepo;
		this.srepo = srepo;
	}

	
//	Student
	@Override
	public void addDriveNotification(Job job) {

	    List<Student> students = srepo.findByRole("STUDENT");

	    saveNotifications(
	        students,
	        Role.STUDENT,
	        "New Placement Drive",
	        job.getCompanyName()
	            + " announced a new drive for "
	            + job.getJobRole(),
	        NotificationType.DRIVE
	    );
	}

	@Override
	public void addApplicationStatusNotification(Application application) {

	    Student student = application.getStudent();

	    Notification notification = new Notification();

	    notification.setTitle("Application Status Updated");
	    notification.setMessage(
	        "Your application for "
	            + application.getJob().getCompanyName()
	            + " has been updated to "
	            + application.getApplicationStatus()
	    );

	    notification.setRole(Role.STUDENT);
	    notification.setStudent(student);
	    notification.setNotificationType(NotificationType.STATUS_UPDATE);
	    notification.setViewed(false);

	    nrepo.save(notification);
	}

	@Override
	public void addStudentApplicationNotificationForStudent(
	        Application application) {

	    Student student = application.getStudent();

	    Notification notification = new Notification();

	    notification.setTitle("Application Submitted");
	    notification.setMessage(
	        "You successfully applied for "
	            + application.getJob().getCompanyName()
	            + " - "
	            + application.getJob().getJobRole()
	    );

	    notification.setRole(Role.STUDENT);
	    notification.setStudent(student);
	    notification.setNotificationType(NotificationType.APPLICATION);
	    notification.setViewed(false);

	    nrepo.save(notification);
	}

	@Override
	public Notification updateNotificationView(Long id, String studentId) {
		Notification notification = nrepo.findByIdAndStudent_StudentId(id, studentId)
				.orElseThrow(() -> new RuntimeException("Notification Not Found"));
		notification.setViewed(true);
		return nrepo.save(notification);
	}	

	

	@Override
	public List<Notification> getNotificationsByStudent(String studentId) {
	
		return nrepo.findByStudent_StudentId(studentId);
	}
	
	
	
	
//	All Admin functionalities

	@Override
	public List<Notification> getAllAdminNotifications() {
		// TODO Auto-generated method stub
		return nrepo.findByRole(Role.ADMIN);
	}

	@Override
	public void addStudentRegistrationNotification(Student student) {
		 List<Student> admins = srepo.findByRole("ADMIN");

		    saveNotifications(
		        admins,
		        Role.ADMIN,
		        "New Student Registration",
		        student.getName() + " has registered on the placement portal.",
		        NotificationType.REGISTRATION
		    );
		
	}

	@Override
	public void addStudentApplicationNotification(Application application) {

	    List<Student> admins = srepo.findByRole("ADMIN");

	    saveNotifications(
	        admins,
	        Role.ADMIN,
	        "New Application Received",
	        application.getStudent().getName()
	            + " applied for "
	            + application.getJob().getCompanyName()
	            + " - "
	            + application.getJob().getJobRole(),
	        NotificationType.APPLICATION
	    );
	}
	
	

	@Override
	public Notification updateAdminNotificationView(Long id) {
		Notification notification = nrepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification Not Found"));
		notification.setViewed(true);
		return nrepo.save(notification);
	}

//	Both
	
	@Override
	public void deleteNotificationById(Long id) {
		nrepo.deleteById(id);
	}

	
	

	private void saveNotifications(
	        List<Student> recipients,
	        Role role,
	        String title,
	        String message,
	        NotificationType type) {

	    List<Notification> notifications = new ArrayList<>();

	    for (Student recipient : recipients) {
	        Notification notification = new Notification();

	        notification.setTitle(title);
	        notification.setMessage(message);
	        notification.setRole(role);
	        notification.setStudent(recipient);
	        notification.setNotificationType(type);
	        notification.setViewed(false);
	        notifications.add(notification);
	    }

	    nrepo.saveAll(notifications);
	}


	
	
}

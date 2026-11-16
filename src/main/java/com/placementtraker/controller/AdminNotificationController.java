package com.placementtraker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placementtraker.entity.Notification;
import com.placementtraker.service.NotificationService;

@RestController
@RequestMapping("/api/admin/notifications")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminNotificationController {

	private final NotificationService nservice;

	public AdminNotificationController(NotificationService nservice) {
		this.nservice = nservice;
	}

	@GetMapping
	public List<Notification> getAllAdminNotifications() {
		return nservice.getAllAdminNotifications();
	}

	@PutMapping("/updateview/{id}")
	public ResponseEntity<String> statusChange(@PathVariable Long id, @RequestParam String studentId) {

		nservice.updateNotificationView(id, studentId);

		return new ResponseEntity<>("Notification Updated as View", HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
		nservice.deleteNotificationById(id);
		return new ResponseEntity<>("Notification Deleted Successfully",HttpStatus.ACCEPTED);
	}
}

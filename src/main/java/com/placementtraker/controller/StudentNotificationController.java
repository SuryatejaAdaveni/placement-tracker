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
@RequestMapping("/api/student/notifications")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentNotificationController {

	private final NotificationService nservice;

	public StudentNotificationController(NotificationService nservice) {
		this.nservice = nservice;
	}

//	@GetMapping
//	public List<Notification> getAllNotifications() {
//		return nservice.getAllNotifications();
//	}

	@PutMapping("/updateview/{id}")
	public ResponseEntity<String> statusChange(@PathVariable Long id, @RequestParam String studentId) {

		nservice.updateNotificationView(id, studentId);

		return new ResponseEntity<>("Notification Updated as View", HttpStatus.ACCEPTED);
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<List<Notification>> getAllNotificationsByStudent(@PathVariable String studentId) {

		return ResponseEntity.ok(nservice.getNotificationsByStudent(studentId));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
		System.out.println("DELETE NOTIFICATION ENDPOINT HIT: " + id);
		nservice.deleteNotificationById(id);
		return new ResponseEntity<>("Notification Deleted Successfully",HttpStatus.ACCEPTED);
	}
}

package com.placementtraker.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Notification;
import com.placementtraker.entity.Role;

public interface NotificationRepo  extends JpaRepository<Notification, Long>{
	List<Notification> findByRole(Role role);
	
	Optional<Notification> findByIdAndStudent_StudentId(
		    Long id,
		    String studentId
		);
	
	List<Notification> findByStudent_StudentId(String studentId);
}

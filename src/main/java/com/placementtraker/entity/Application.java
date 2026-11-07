package com.placementtraker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "applications", uniqueConstraints = { @UniqueConstraint(columnNames = { "job_id", "student_id" }) })
public class Application {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Id
	@Column(nullable = false, unique = true, updatable = false)
	private String applicationId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "job_id", nullable = false)
	private Job job;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(nullable = false, updatable = false)
	private LocalDateTime appliedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ForwardingStatus forwardingStatus = ForwardingStatus.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ApplicationStatus applicationStatus = ApplicationStatus.APPLIED;

	@PrePersist
	public void generateApplicationId() {
		if (applicationId == null || applicationId.isBlank()) {
			applicationId = "APP"
					+ java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
		}
		if (appliedAt == null) {
			appliedAt = LocalDateTime.now();
		}
	}
}

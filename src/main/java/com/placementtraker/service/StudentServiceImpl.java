package com.placementtraker.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.placementtraker.dto.LoginRequest;
import com.placementtraker.dto.LoginResponse;
import com.placementtraker.entity.Role;
import com.placementtraker.entity.Student;
import com.placementtraker.repo.StudentRepo;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepo srepo;
	private final CloudinaryService cloudinaryService;
	private final NotificationService nservice;
	public StudentServiceImpl(StudentRepo studentRepo, CloudinaryService cloudinaryService,NotificationService nservice) {

		this.srepo = studentRepo;
		this.nservice = nservice;
		this.cloudinaryService = cloudinaryService;
	}

	@Override
	public Student addStudent(Student student) {
		return srepo.save(student);
	}

	@Override
	public Student updateStudentById(String id, Student currentStudent) {
		Student updatedStudent = srepo.findById(id).orElse(null);
		if (updatedStudent != null) {
			updatedStudent.setName(currentStudent.getName());
			updatedStudent.setPhone(currentStudent.getPhone());
			updatedStudent.setEmail(currentStudent.getEmail());
			updatedStudent.setStudentId(currentStudent.getStudentId());
			updatedStudent.setCountry(currentStudent.getCountry());
			updatedStudent.setState(currentStudent.getState());
			updatedStudent.setDistrict(currentStudent.getDistrict());
//			updatedStudent.setResumeFile(currentStudent.getResumeFile());
			updatedStudent.setPassword(currentStudent.getPassword());
			updatedStudent.setRole("STUDENT");
			return srepo.save(updatedStudent);
		}
		return null;
	}

	@Override
	public List<Student> getAllStudents() {
		return srepo.findByRole("STUDENT");
	}

	@Override
	public Student getStudentById(String id) {
		return srepo.findById(id).orElse(null);
	}

	@Override
	public void deleteStudentById(String id) {
		srepo.deleteById(id);

	}

	@Override
	public void verifyStudent(String id) {
		// TODO Auto-generated method stub
		Student student = srepo.findById(id).orElse(null);

		student.setVerified(true);
		srepo.save(student);
	}

	@Override
	public LoginResponse login(LoginRequest request) {

		Student student = srepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid Email"));
		
		if (!student.getPassword().equals(request.getPassword())) {
		    throw new RuntimeException("Invalid Password");
		}

		return new LoginResponse(student.getStudentId(), student.getName(), student.getEmail(), student.getRole());
	}

	@Override
	@Transactional
	public Student registerStudent(
	        Student student,
	        MultipartFile resume,
	        MultipartFile profilePic) {

	    try {
	        if (resume == null || resume.isEmpty()) {
	            throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST, "Resume is required"
	            );
	        }

	        if (!"application/pdf".equalsIgnoreCase(resume.getContentType())) {
	            throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST, "Only PDF resumes are allowed"
	            );
	        }

	        // Upload resume to Cloudinary
	        Map resumeResult = cloudinaryService.uploadResume(resume);

	        student.setResumeUrl((String) resumeResult.get("secure_url"));
	        student.setResumePublicId((String) resumeResult.get("public_id"));

	        // Upload profile picture if provided
	        if (profilePic != null && !profilePic.isEmpty()) {

	            Map profileResult = cloudinaryService.uploadImage(profilePic);

	            student.setProfilePicUrl((String) profileResult.get("secure_url"));
	            student.setProfilePicPublicId((String) profileResult.get("public_id"));
	        }

	        Student savedStudent = srepo.save(student);
	        
	        nservice.addStudentRegistrationNotification(savedStudent);

	        return savedStudent;

	    } catch (IOException e) {
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR,
	            "Unable to upload files",
	            e
	        );
	    }
	}
}

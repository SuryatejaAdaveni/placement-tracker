
package com.placementtraker.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.placementtraker.dto.LoginRequest;
import com.placementtraker.dto.LoginResponse;
import com.placementtraker.entity.Student;
import com.placementtraker.repo.StudentRepo;
import com.placementtraker.security.CustomUserDetails;
import com.placementtraker.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepo srepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;
    private final NotificationService nservice;

    public StudentServiceImpl(
            StudentRepo srepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CloudinaryService cloudinaryService,
            NotificationService nservice) {

        this.srepo = srepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.cloudinaryService = cloudinaryService;
        this.nservice = nservice;
    }

    @Override
    public Student addStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("STUDENT");
        student.setVerified(false);

        return srepo.save(student);
    }

    @Override
    public Student updateStudentById(String studentId, Student currentStudent) {

        Student existing = srepo.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        existing.setName(currentStudent.getName());
        existing.setPhone(currentStudent.getPhone());
        existing.setEmail(currentStudent.getEmail());
        existing.setCountry(currentStudent.getCountry());
        existing.setState(currentStudent.getState());
        existing.setDistrict(currentStudent.getDistrict());

        // Do not allow normal profile updates to change:
        // studentId, password, role, or verification status.

        return srepo.save(existing);
    }

    @Override
    public List<Student> getAllStudents() {
        return srepo.findByRole("STUDENT");
    }

    @Override
    public Student getStudentById(String studentId) {
        return srepo.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));
    }

    @Override
    @Transactional
    public void deleteStudentById(String studentId) {

        Student student = srepo.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        // JPA cascade rules on Student relationships apply here.
        srepo.delete(student);
    }

    @Override
    public void verifyStudent(String studentId) {

        Student student = srepo.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        student.setVerified(true);
        srepo.save(student);
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
                        HttpStatus.BAD_REQUEST, "Resume is required");
            }

            if (!"application/pdf".equalsIgnoreCase(resume.getContentType())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Only PDF resumes are allowed");
            }

            // Upload resume to Cloudinary.
            Map resumeResult = cloudinaryService.uploadResume(resume);

            student.setResumeUrl((String) resumeResult.get("secure_url"));
            student.setResumePublicId((String) resumeResult.get("public_id"));

            // Upload profile picture only when one is provided.
            if (profilePic != null && !profilePic.isEmpty()) {

                Map profileResult = cloudinaryService.uploadImage(profilePic);

                student.setProfilePicUrl((String) profileResult.get("secure_url"));
                student.setProfilePicPublicId((String) profileResult.get("public_id"));
            }

            // Always assign the role and verification status on the server.
            student.setRole("STUDENT");
            student.setVerified(false);
            student.setPassword(passwordEncoder.encode(student.getPassword()));

            Student savedStudent = srepo.save(student);

            // Notify admins about the new student registration.
            nservice.addStudentRegistrationNotification(savedStudent);

            return savedStudent;

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to upload files",
                    e);
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()));

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Student student = userDetails.getStudent();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getRole(),
                token);
    }
}


package com.placementtraker.service;

import java.io.IOException;
import java.util.List;

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

    public StudentServiceImpl(
            StudentRepo srepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.srepo = srepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

        // Only replace resume if a new one was supplied
        if (currentStudent.getResumeFile() != null
                && currentStudent.getResumeFile().length > 0) {
            existing.setResumeFile(currentStudent.getResumeFile());
        }

        // Do not update studentId, role or verified from request.
        // Password changes should use a separate, validated method.

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
    public void deleteStudentById(String studentId) {

        Student student = srepo.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

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
    public Student registerStudent(Student student, MultipartFile resume) {

        try {
            if (resume == null || resume.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Resume is required");
            }

            if (!"application/pdf".equalsIgnoreCase(resume.getContentType())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Only PDF resumes are allowed");
            }

            student.setResumeFile(resume.getBytes());
            student.setRole("STUDENT");
            student.setVerified(false);
            student.setPassword(passwordEncoder.encode(student.getPassword()));

            return srepo.save(student);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to process resume", e);
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

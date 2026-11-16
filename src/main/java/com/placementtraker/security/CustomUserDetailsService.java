package com.placementtraker.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.placementtraker.entity.Student;
import com.placementtraker.repo.StudentRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final StudentRepo studentRepo;
	
	public CustomUserDetailsService(StudentRepo studentRepo) {
		super();
		this.studentRepo = studentRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Student student = studentRepo.findByEmail(email)
							.orElseThrow(()-> 
							new UsernameNotFoundException("Student not found with email : "+email));
		return new CustomUserDetails(student);
	}

}

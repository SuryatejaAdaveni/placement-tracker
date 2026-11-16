package com.placementtraker.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.placementtraker.entity.Student;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails{

	private Student student;
	public CustomUserDetails(Student student) {
		super();
		this.student = student;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = student.getRole();
		
		if(!role.startsWith("ROLE_")) {
			role = "ROLE_"+role;
		}
		return Collections.singletonList( new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return student.getPassword();
	}

	@Override
	public String getUsername() {
		return student.getRole();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return student.isVerified();
	}

}

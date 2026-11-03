package com.placementtraker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementtraker.entity.Application;

public interface ApplicationRepo extends JpaRepository<	Application, Integer>{

}

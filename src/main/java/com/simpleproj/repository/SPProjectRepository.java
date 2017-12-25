package com.simpleproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleproj.model.SPProject;

@Repository
public interface SPProjectRepository extends JpaRepository<SPProject, Long>{
	public SPProject findProjectByName(String name);
}

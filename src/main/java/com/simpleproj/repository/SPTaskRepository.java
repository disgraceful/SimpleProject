package com.simpleproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleproj.model.SPTask;

@Repository
public interface SPTaskRepository extends JpaRepository<SPTask, Long>{

	public SPTask findTaskByName(String name);
}

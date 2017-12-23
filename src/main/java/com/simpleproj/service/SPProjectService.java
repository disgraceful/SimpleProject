package com.simpleproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPTask;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPUserRepository;

@Service
public class SPProjectService {

	@Autowired
	private SPProjectRepository projRepo;

	@Autowired
	private SPUserRepository userRepo;

	@Transactional
	public SPProject getProjectById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return projRepo.findOne(id);
	}

	@Transactional
	public List<SPProject> getProjects() {
		return projRepo.findAll();
	}

	@Transactional
	public SPProject createProject(SPProject project) {
		if (project == null) {
			throw new IllegalArgumentException();
		}
		return projRepo.saveAndFlush(project);
	}

	@Transactional
	public void deleteProject(SPProject project) {
		if (project == null) {
			throw new IllegalArgumentException();
		}
		projRepo.delete(project);
	}

	@Transactional
	public void deleteProject(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		projRepo.delete(id);
	}

	@Transactional
	public List<SPProject> getProjectsByUserId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return userRepo.getOne(id).getProjects();
	}

	@Transactional
	public void addTask(long projId, SPTask task) {
		if (projId < 1 || projId > Long.MAX_VALUE || task == null) {
			throw new IllegalArgumentException();
		}
		SPProject proj = projRepo.findOne(projId);
		proj.addTask(task);
		projRepo.save(proj);
	}

	@Transactional
	public void deleteTask(long projId, SPTask task) {
		if (projId < 1 || projId > Long.MAX_VALUE || task == null) {
			throw new IllegalArgumentException();
		}
		SPProject proj = projRepo.findOne(projId);
		proj.deleteTask(task);
		projRepo.save(proj);
	}
}

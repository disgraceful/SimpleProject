package com.simpleproj.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.dto.SPProjectDTO;
import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPTask;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPUserRepository;

@Service
public class SPProjectService {

	private final SPProjectRepository projRepo;
	private final SPUserRepository userRepo;
	private final ModelMapper modelMapper;

	@Autowired
	public SPProjectService(SPProjectRepository projRepo, SPUserRepository userRepo, ModelMapper modelMapper){
		this.projRepo = projRepo;
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
	}
	
	@Transactional
	public SPProjectDTO getProjectById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return map(projRepo.findOne(id));
	}

	@Transactional
	public List<SPProjectDTO> getProjects() {
		return projRepo.findAll().stream()
				.map(e->map(e))
				.collect(Collectors.toList());
	}

	@Transactional
	public SPProjectDTO createProject(SPProject project) {
		if (project == null) {
			throw new IllegalArgumentException();
		}
		return map(projRepo.saveAndFlush(project));
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
	public List<SPProjectDTO> getProjectsByUserId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return userRepo.getOne(id).getProjects().stream()
				.map(e->map(e))
				.collect(Collectors.toList());
	}

	@Transactional
	public void addTask(long projId, SPTask task) {
		if (projId < 1 || projId > Long.MAX_VALUE || task == null) {
			throw new IllegalArgumentException();
		}
		SPProject proj = projRepo.findOne(projId);
		proj.addTask(task);
		task.setProject(proj);
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
	
	private SPProjectDTO map(SPProject project) {
		SPProjectDTO projectDTO = modelMapper.map(project, SPProjectDTO.class);
		projectDTO.setTaskAmount(project.getTasks().size());
		projectDTO.setUserId(project.getUser().getId());
		return projectDTO;
	}
}

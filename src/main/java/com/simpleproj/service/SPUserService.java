package com.simpleproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPUserRepository;

@Service
public class SPUserService {

	private final SPUserRepository userRepo;
	
	@Autowired
	public SPUserService(SPUserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional
	public SPUser getUserById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return userRepo.findOne(id);
	}

	@Transactional
	public List<SPUser> getUsers() {
		return userRepo.findAll();
	}

	@Transactional
	public SPUser getUserByLogin(String login) {
		if (login.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		return userRepo.findByLogin(login);
	}

	@Transactional
	public SPUser createUser(String login, String password) {
		if (login.trim().isEmpty() || password.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		SPUser user = new SPUser(login, password);
		SPProject project1 = new SPProject("Important tasks");
		SPProject project2 = new SPProject("University");
		SPProject project3 = new SPProject("Personal");
		
		addProject(user.getId(), project1);
		addProject(user.getId(), project2);
		addProject(user.getId(), project3);
		return userRepo.saveAndFlush(user);
	}

	@Transactional
	public void deleteUser(SPUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		userRepo.delete(user);
	}

	@Transactional
	public void deleteUser(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		userRepo.delete(id);
	}

	@Transactional
	public void addProject(long userId, SPProject project) {
		if (userId < 1 || userId > Long.MAX_VALUE || project == null) {
			throw new IllegalArgumentException();
		}
		SPUser user = userRepo.findOne(userId);
		user.addProject(project);
		project.setUser(user);
		userRepo.save(user);
	}

	@Transactional
	public void deleteProject(long userId, SPProject project) {
		if (userId < 1 || userId > Long.MAX_VALUE || project == null) {
			throw new IllegalArgumentException();
		}
		SPUser user = userRepo.findOne(userId);
		user.deleteProject(project);
		userRepo.save(user);
	}

}

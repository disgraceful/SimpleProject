package com.simpleproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPTask;
import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPUserRepository;

@Service
public class SPUserService {

	@Autowired
	private SPUserRepository userRepo;

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
		user.addProject(new SPProject("Important tasks"));
		user.addProject(new SPProject("University"));
		user.addProject(new SPProject("Personal"));
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

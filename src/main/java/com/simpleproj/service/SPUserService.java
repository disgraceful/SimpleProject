package com.simpleproj.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.dto.SPUserDTO;
import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPUserRepository;
import com.simpleproj.web.requestmodel.SPUserRegisterRequestModel;

@Service
public class SPUserService {

	private final SPUserRepository userRepo;
	private final ModelMapper modelMapper;
	private static final Logger LOG = LoggerFactory.getLogger(SPUserService.class);


	@Autowired
	public SPUserService(SPUserRepository userRepo, ModelMapper modelMapper) {
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public SPUserDTO getUserById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return modelMapper.map(userRepo.findOne(id), SPUserDTO.class);
	}

	@Transactional
	public List<SPUserDTO> getUsers() {
		return userRepo.findAll().stream()
				.map(e->modelMapper.map(e, SPUserDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public SPUserDTO getUserByLogin(String login) {
		if (login.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		return modelMapper.map(userRepo.findByLogin(login),SPUserDTO.class);
	}

	@Transactional
	public SPUserDTO createUser(SPUserRegisterRequestModel model) {
		if (!validateRegisterModel(model)) {
			throw new IllegalArgumentException();
		}
		SPUser user = new SPUser(model.getLogin(), model.getPassword());
		userRepo.save(user);
		addProject(user.getId(), new SPProject("Important tasks"));
		addProject(user.getId(), new SPProject("University"));
		addProject(user.getId(), new SPProject("Personal"));
		return modelMapper.map(userRepo.saveAndFlush(user),SPUserDTO.class);
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

	private boolean validateRegisterModel(SPUserRegisterRequestModel model) {
		return model != null && !model.getLogin().trim().isEmpty() && !model.getPassword().trim().isEmpty()
				&& model.getPassword().trim().equals(model.getConfirmPassword().trim());
	}

}

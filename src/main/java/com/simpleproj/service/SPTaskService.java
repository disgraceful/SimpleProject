package com.simpleproj.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.model.SPTask;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPTaskRepository;
import com.simpleproj.repository.SPUserRepository;

@Service
public class SPTaskService {

	@Autowired
	private SPProjectRepository projRepo;

	@Autowired
	private SPUserRepository userRepo;

	@Autowired
	private SPTaskRepository taskRepo;

	@Transactional
	public SPTask getTaskById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return taskRepo.findOne(id);
	}
	
	@Transactional
	public SPTask getTaskByName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		return taskRepo.findTaskByName(name);
	}

	@Transactional
	public List<SPTask> getTasks() {
		return taskRepo.findAll();
	}

	@Transactional
	public SPTask createTask(String name, Calendar date) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.setTime(date.getTime());
			cal.getTime();
			return taskRepo.save(new SPTask(name, cal));
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

	}

	@Transactional
	public void deleteTask(SPTask project) {
		if (project == null) {
			throw new IllegalArgumentException();
		}
		taskRepo.delete(project);
	}

	@Transactional
	public void deleteTask(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		taskRepo.delete(id);
	}

	@Transactional
	public List<SPTask> getTasksByUserId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return userRepo.getOne(id).getTasks();
	}

	@Transactional
	public List<SPTask> getTasksByProjectId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return projRepo.getOne(id).getTasks();
	}

	@Transactional
	public void completeTask(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		SPTask task = taskRepo.findOne(id);
		task.setCompleted(true);
		taskRepo.save(task);
	}

}

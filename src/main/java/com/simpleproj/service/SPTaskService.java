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
	private final SPProjectRepository projRepo;
	private final SPUserRepository userRepo;
	private final SPTaskRepository taskRepo;

	@Autowired
	public SPTaskService(SPProjectRepository projRepo, SPUserRepository userRepo, SPTaskRepository taskRepo) {
		this.projRepo = projRepo;
		this.userRepo = userRepo;
		this.taskRepo = taskRepo;
	}

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
		if (name.trim().isEmpty() || date == null) {
			throw new IllegalArgumentException();
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.setTime(date.getTime());
			cal.getTime();
			SPTask task = new SPTask(name, date);
			return taskRepo.save(task);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

	}

	@Transactional
	public void deleteTask(SPTask task) {
		if (task == null) {
			throw new IllegalArgumentException();
		}
		taskRepo.delete(task);
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
		return userRepo.findOne(id).getTasks();
	}

	@Transactional
	public List<SPTask> getTasksByProjectId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return projRepo.findOne(id).getTasks();
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

package com.simpleproj.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleproj.dto.SPTaskDTO;
import com.simpleproj.model.SPTask;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPTaskRepository;
import com.simpleproj.repository.SPUserRepository;
import com.simpleproj.utils.SPDateUtils;

@Service
public class SPTaskService {
	private final SPProjectRepository projRepo;
	private final SPUserRepository userRepo;
	private final SPTaskRepository taskRepo;
	private final ModelMapper modelMapper;

	@Autowired
	public SPTaskService(SPProjectRepository projRepo, SPUserRepository userRepo, SPTaskRepository taskRepo,
			ModelMapper modelMapper) {
		this.projRepo = projRepo;
		this.userRepo = userRepo;
		this.taskRepo = taskRepo;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public SPTaskDTO getTaskById(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return map(taskRepo.findOne(id));
	}

	@Transactional
	public SPTaskDTO getTaskByName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		return map(taskRepo.findTaskByName(name));
	}

	@Transactional
	public List<SPTaskDTO> getTasks() {
		return taskRepo.findAll().stream().map(e -> map(e)).collect(Collectors.toList());
	}

	@Transactional
	public List<SPTaskDTO> getTasksForWeek() {
		return taskRepo.findAll().stream().filter(e -> SPDateUtils.isWithinDaysFuture(e.getStartDate(), 6))
				.map(e -> map(e)).collect(Collectors.toList());
	}

	@Transactional
	public List<SPTaskDTO> getTasksForToday() {
		return taskRepo.findAll().stream().filter(e -> SPDateUtils.isToday(e.getStartDate())).map(e -> map(e))
				.collect(Collectors.toList());
	}

	@Transactional
	public SPTaskDTO createTask(String name, Calendar date, long projId, long userId) {
		if (name.trim().isEmpty() || date == null) {
			throw new IllegalArgumentException();
		}
		SPTask task = new SPTask(name, date);
		task.setProject(projRepo.findOne(projId));
		task.setUser(userRepo.findOne(userId));
		return map(taskRepo.save(task));
		

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
	public List<SPTaskDTO> getTasksByUserId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return userRepo.findOne(id).getTasks().stream().map(e -> map(e)).collect(Collectors.toList());
	}

	@Transactional
	public List<SPTaskDTO> getTasksByProjectId(long id) {
		if (id < 1 || id > Long.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		return projRepo.findOne(id).getTasks().stream().map(e -> map(e)).collect(Collectors.toList());
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

	private SPTaskDTO map(SPTask task) {
		SPTaskDTO taskDTO = modelMapper.map(task, SPTaskDTO.class);
		taskDTO.setConvertedDate(task.getTaskTime());
		taskDTO.setProjId(task.getProject().getId());
		taskDTO.setUserId(task.getUser().getId());
		return taskDTO;
	}

}

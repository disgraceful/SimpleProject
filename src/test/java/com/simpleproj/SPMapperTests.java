package com.simpleproj;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.simpleproj.dto.SPProjectDTO;
import com.simpleproj.dto.SPTaskDTO;
import com.simpleproj.dto.SPUserDTO;
import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPTask;
import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPTaskRepository;
import com.simpleproj.repository.SPUserRepository;
import com.simpleproj.service.SPProjectService;
import com.simpleproj.service.SPTaskService;
import com.simpleproj.service.SPUserService;
import com.simpleproj.web.requestmodel.SPUserRegisterRequestModel;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SPMapperTests {
	@Autowired
	private SPUserRepository userRepo;
	
	@Autowired
	private SPProjectRepository projRepo;
	
	@Autowired
	private SPTaskRepository taskRepo;
	
	private ModelMapper mapper;

	private SPUserService userService;
	private SPProjectService projectService;
	private SPTaskService taskService;

	@Before
	public void setUp() {
		mapper = new ModelMapper();
		userService = new SPUserService(userRepo, mapper);
		projectService = new SPProjectService(projRepo, userRepo, mapper);
		taskService = new SPTaskService(projRepo,userRepo,taskRepo,mapper);

	}

	@Test
	public void testUserMapping() {
		SPUserRegisterRequestModel user = new SPUserRegisterRequestModel();
		user.setLogin("login");
		user.setPassword("password");
		user.setConfirmPassword("password");

		SPUserDTO userDTO = userService.createUser(user);

		assertEquals(userDTO.getLogin(), user.getLogin());
		assertEquals(userDTO.getPassword(), user.getPassword());
	}
	
	@Test
	public void testProjectMapping() {
		SPUser user = new SPUser("login","password");
		userRepo.save(user);
		SPProject project = new SPProject("name");
		project.setUser(user);
		project.addTask(new SPTask("name1",null));
		project.addTask(new SPTask("name1",null));
		project.addTask(new SPTask("name1",null));
		
		SPProjectDTO projectDTO= projectService.createProject(project);
		assertEquals(projectDTO.getId(), project.getId());
		assertEquals(projectDTO.getName(), project.getName());
		assertEquals(projectDTO.getTaskAmount(), project.getTasks().size());
		assertEquals(projectDTO.getUserId(),project.getUser().getId());		
	}
	
	@Test
	public void testTaskMapping() throws ParseException {
		SPTask task = new SPTask("task",Calendar.getInstance());
		SPUser user = new SPUser("login","password");
		userRepo.save(user);
		SPProject project = new SPProject("name");
		projRepo.save(project);
		projectService.addTask(project.getId(), task);	
		task.setUser(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		SPTaskDTO taskDTO = taskService.createTask(task.getName(), task.getStartDate(),project.getId(),user.getId());
		assertEquals(taskDTO.getDate(),dateFormat.format(task.getTaskTime()));
		assertEquals(taskDTO.getProjId(),task.getProject().getId());
		assertEquals(taskDTO.getUserId(),task.getUser().getId());
	}

}

package com.simpleproj;

import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPTask;
import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPTaskRepository;
import com.simpleproj.repository.SPUserRepository;
import com.simpleproj.service.SPProjectService;
import com.simpleproj.service.SPUserService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SPDALTests {
	@Autowired
	private SPTaskRepository taskRepo;
	
	@Autowired
	private SPUserRepository userRepo;
	
	@Autowired
	private SPProjectRepository projRepo;

	private SPProjectService projectService;
	private SPUserService userService;

	private SPTask task;
	private SPUser user;
	private SPProject project;

	@Before
	public void setUp() {
		taskRepo.deleteAll();
		taskRepo.deleteAllInBatch();

		task = new SPTask("Do stuff", new GregorianCalendar(2017, 11, 20));
		user = new SPUser("login", "password");
		project = new SPProject("proj");
	//	projectService = new SPProjectService(projRepo,userRepo);
	//	userService = new SPUserService(userRepo);
	}

	@Test
	public void testCreate() {
		long userId = 5;
		System.out.println("id " + taskRepo.save(task));
		SPTask found = taskRepo.findOne(userId);
		Assert.assertEquals(found.getName(), task.getName());
	}

//	@Test
//	public void testRelationship() {
// 		SPUser test = userService.createUser(user.getLogin(), user.getPassword());
//		project = projectService.createProject(project);
//		test = userService.getUserById(2);
//		userService.addProject(test.getId(), project);
//		test = userService.getUserById(2);
//		System.out.println(test.getProjects().size());
//		project = projectService.getProjectById(project.getId());
//		System.out.println(project.getUser());
//
//	}
}

package com.simpleproj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.simpleproj.model.SPProject;
import com.simpleproj.model.SPTask;
import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPTaskRepository;
import com.simpleproj.repository.SPUserRepository;
import com.simpleproj.service.SPTaskService;

@RunWith(MockitoJUnitRunner.class)
public class SPTaskServiceTests {

	@Mock
	private SPTaskRepository taskRepo;
	@Mock
	private SPProjectRepository projRepo;
	@Mock
	private SPUserRepository userRepo;

	@Mock
	private SPTask task;

	@Mock
	private SPProject project;
	@Mock
	private SPUser user;

	private SPTaskService taskService;

	private long taskId;
	private long projId;
	private long userId;
	private Calendar calendar;

	@Before
	public void setUp() {
		taskService = new SPTaskService(projRepo, userRepo, taskRepo);
		taskId = 1;
		projId = 1;
		userId = 1;

		calendar = Calendar.getInstance();
		when(task.getId()).thenReturn(taskId);
		when(task.getName()).thenReturn("Simple Task Name");
		when(task.getStartDate()).thenReturn(calendar);
		when(taskRepo.findOne(taskId)).thenReturn(task);
		when(taskRepo.findTaskByName("Simple Task Name")).thenReturn(task);
		doNothing().when(taskRepo).delete(taskId);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(taskRepo).delete(any(SPTask.class));

		when(taskRepo.save(any(SPTask.class))).thenReturn(task);
		doThrow(new RuntimeException()).when(taskRepo).delete(task);
		when(taskRepo.save(any(SPTask.class))).thenReturn(task);
		when(taskRepo.findAll()).thenReturn(new ArrayList<SPTask>() {
			{
				add(task);
			}
		});
		when(task.isCompleted()).thenReturn(true);
		when(projRepo.findOne(projId)).thenReturn(project);
		when(project.getTasks()).thenReturn(new ArrayList<SPTask>() {
			{
				add(task);
			}
		});
		when(project.getId()).thenReturn(projId);
		when(userRepo.findOne(userId)).thenReturn(user);
		when(user.getTasks()).thenReturn(new ArrayList<SPTask>() {
			{
				add(task);
			}
		});
		when(user.getId()).thenReturn(userId);
	}

	@Test
	public void testCompleted() {
		taskService.completeTask(taskId);
		verify(taskRepo).findOne(taskId);
		verify(task).setCompleted(true);
		verify(taskRepo).save(task);
	}

	@Test
	public void testTaskByProject() {
		taskService.getTasksByProjectId(projId);
		verify(projRepo).findOne(projId);
		verify(project).getTasks();
		SPTask test = project.getTasks().get(0);
		assertEquals(task, test);

	}

	@Test
	public void testTaskByUser() {
		taskService.getTasksByUserId(userId);
		verify(userRepo).findOne(userId);
		verify(user).getTasks();
		SPTask test = user.getTasks().get(0);
		assertEquals(task, test);
	}

	@Test
	public void testGetTaskById() {
		SPTask test = taskService.getTaskById(taskId);
		assertEquals(test, task);
		assertEquals(test.getId(), task.getId());
		assertEquals(test.getName(), task.getName());
		assertEquals(test.getStartDate(), task.getStartDate());
		verify(taskRepo).findOne(userId);

	}

	@Test
	public void testGetTaskByName() {
		String testName = "Simple Task Name";
		SPTask test1 = taskService.getTaskByName(testName);
		assertEquals(test1, task);
		assertEquals(test1.getId(), task.getId());
		assertEquals(test1.getName(), task.getName());
		assertEquals(test1.getStartDate(), task.getStartDate());
		verify(taskRepo).findTaskByName(testName);
	}

	@Test
	public void testGetAllTasks() {
		List<SPTask> tasks = taskService.getTasks();
		assertEquals(tasks.get(0), task);
		assertEquals(tasks.get(0).getId(), task.getId());
		assertEquals(tasks.get(0).getName(), task.getName());
		assertEquals(tasks.get(0).getStartDate(), task.getStartDate());
		verify(taskRepo).findAll();
	}

	@Test
	public void testCreateTask() {
		String testName = "Simple Task Name";
		SPTask test = taskService.createTask(testName, calendar);
		assertNotNull(test);
		assertEquals(test, task);
		assertEquals(test.getName(), testName);
		assertEquals(test.getStartDate(), calendar);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTaskInvalid() {
		String testNameInvalid = "";
		SPTask test = taskService.createTask(testNameInvalid, calendar);
		assertNotNull(test);
		assertEquals(test, task);
		assertEquals(test.getName(), testNameInvalid);
		assertEquals(test.getStartDate(), calendar);
		verify(taskRepo).save(task);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTaskInvalid() {
		SPTask test = taskService.getTaskById(-1);
		verify(taskRepo).findOne(userId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTaskInvalid2() {
		SPTask test = taskService.getTaskById(Long.MAX_VALUE + 1);
		verify(taskRepo).findOne(userId);
	}

	@Test
	public void testDeleteId() {
		taskService.deleteTask(taskId);
		verify(taskRepo).delete(taskId);
	}
}

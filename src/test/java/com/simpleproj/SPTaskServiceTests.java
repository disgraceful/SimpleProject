package com.simpleproj;

import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.simpleproj.model.SPTask;
import com.simpleproj.repository.SPTaskRepository;
import com.simpleproj.service.SPTaskService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SPTaskServiceTests {
	    @Autowired
	    private SPTaskService taskService;
	 
	    @MockBean
	    private SPTaskRepository taskRepo;
	    
	    @Before
	    public void setUp() {
	    	SPTask task = new SPTask("Do stuff",new GregorianCalendar(2017, 11, 20));
	    	Mockito.when(taskRepo.findTaskByName(task.getName()))
	        .thenReturn(task); 
	    }
	    
		@Test
		public void testFind() {
			SPTask task = new SPTask("Do stuff", new GregorianCalendar(2017, 11, 20));
			long userId = 4;
			
			SPTask found = taskRepo.findOne(userId);
			Assert.assertEquals(found.getName(), task.getName());
		}

		@Test
		public void testCreate() {
			SPTask task = new SPTask("Do stuff", new GregorianCalendar(2017, 11, 20));
			long userId = 5;

			System.out.println("id " + taskRepo.save(task));
			SPTask found = taskRepo.findOne(userId);

			Assert.assertEquals(found.getName(), task.getName());
		}
}

package com.simpleproj;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.simpleproj.repository.SPProjectRepository;
import com.simpleproj.repository.SPUserRepository;

@RunWith(SpringRunner.class)
public class SPUserServiceTests {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private SPUserRepository userRepo;
	  	
 
}

package com.simpleproj;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.simpleproj.model.SPUser;
import com.simpleproj.repository.SPUserRepository;
import com.simpleproj.service.SPUserService;

@RunWith(MockitoJUnitRunner.class)
public class SPUserServiceTests {

	@Mock
	private SPUserRepository userRepo;

	@Mock
	private SPUser user;

	private SPUserService userService;

	private long userId;

	@Before
	public void setUp() {
		userService = new SPUserService(userRepo);
		userId = 1;

		when(user.getId()).thenReturn(userId);
		when(user.getLogin()).thenReturn("Login");
		when(user.getPassword()).thenReturn("Password");
		when(userRepo.findOne(userId)).thenReturn(user);
		when(userRepo.findAll()).thenReturn(new ArrayList<SPUser>() {
			{
				add(user);
			}
		});
		when(userRepo.findByLogin("Login")).thenReturn(user);
		when(userRepo.saveAndFlush(any(SPUser.class))).thenReturn(user);
	}

	@Test
	public void testGetUserById() {
		SPUser test = userService.getUserById(userId);
		assertEquals(test, user);
		assertEquals(test.getId(), user.getId());
		assertEquals(test.getLogin(), user.getLogin());
		assertEquals(test.getPassword(), user.getPassword());
		verify(userRepo).findOne(userId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUserByInvalidId() {
		SPUser test = userService.getUserById(-10);
		verify(userRepo).findOne(userId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUserByInvalidId2() {
		SPUser test = userService.getUserById(Long.MAX_VALUE + 1);
		verify(userRepo).findOne(userId);
	}

	@Test
	public void testGetAllUsers() {
		List<SPUser> test = userService.getUsers();
		assertEquals(test.get(0), user);
		assertEquals(test.get(0).getId(), user.getId());
		assertEquals(test.get(0).getLogin(), user.getLogin());
		assertEquals(test.get(0).getPassword(), user.getPassword());
		verify(userRepo).findAll();
	}

	@Test
	public void testGetUserByLogin() {
		SPUser test = userService.getUserByLogin("Login");
		assertEquals(test, user);
		assertEquals(test.getId(), user.getId());
		assertEquals(test.getLogin(), user.getLogin());
		assertEquals(test.getPassword(), user.getPassword());
		verify(userRepo).findByLogin("Login");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetUserByInvalidLogin() {
		SPUser test = userService.getUserByLogin("");
		assertEquals(test, user);
		assertEquals(test.getId(), user.getId());
		assertEquals(test.getLogin(), user.getLogin());
		assertEquals(test.getPassword(), user.getPassword());
		verify(userRepo).findByLogin("Login");
	}
	
	@Test
	public void testCreateUser() {
		SPUser test = userService.createUser("Login", "Password");
		assertEquals(test, user);
		assertEquals(test.getId(), user.getId());
		assertEquals(test.getLogin(), user.getLogin());
		assertEquals(test.getPassword(), user.getPassword());	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInvalidUser() {
		SPUser test = userService.createUser("Login", "");
		assertEquals(test, user);
		assertEquals(test.getId(), user.getId());
		assertEquals(test.getLogin(), user.getLogin());
		assertEquals(test.getPassword(), user.getPassword());	
	}
}

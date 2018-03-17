package com.fsd.workoutportal.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.validateMockitoUsage;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fsd.workoutportal.dao.UserDAO;
import com.fsd.workoutportal.model.User;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@MockBean
	private UserDAO dao;
	
	@Autowired
	private UserService userSvc;
		
	@TestConfiguration
	static class Config{
		
		@Bean
		public UserService getSvc() {
			return new UserServiceImpl();
		}
		
	}
	
	/*@Before
	public void setup() {
		workoutDao = Mockito.mock(WorkoutDAO.class);
		
	}*/
	
	@Test
	public void saveUserTest() throws Exception {
		log.info("Start: saveUserTest");
		User u = new User();
		u.setName("U1");
		u.setPassword("pass");
		
		Mockito.when(dao.save(Mockito.any(User.class))).thenReturn(u);
		
		User user = userSvc.addUser(u);
		assertNotNull(user);
		
		assertThat(user, is(u));
		
		log.info("Exit: saveUserTest");
	}
	
	@Test
	public void loginTest() {
		log.info("Start: loginTest");
		
		User u = new User();
		u.setName("U1");
		u.setPassword("pass");
		
		Mockito.when(dao.findByName(anyString())).thenReturn(u);
		
		User user = userSvc.login(u);
		
		assertNotNull(user);
		assertThat(user, is(u));
		
		user = userSvc.login(null);
		assertNull(user);
		
		log.info("Exit: loginTest");
	}
	
	
	@After
	public void validate() {
		validateMockitoUsage();
	}

}

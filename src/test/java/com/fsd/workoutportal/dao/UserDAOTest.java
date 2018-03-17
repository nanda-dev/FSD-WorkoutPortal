package com.fsd.workoutportal.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.fsd.workoutportal.model.User;
import com.fsd.workoutportal.model.Workout;
import com.fsd.workoutportal.util.UnitTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDAOTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private UserDAO dao;
	
	@Test
	public void saveAndFindUserByNameTest() {
		log.info("Start: saveAndFindUserByNameTest");
		
		Workout w = new Workout();
		w.setTitle("W1");
		w.setUserId(1L);
		w.setCalsBurnt(10d);
		w.setUnit(UnitTime.HOURS);
		
		User u = new User();
		u.setName("U1");
		u.setPassword("pass");
		
		em.persistAndFlush(u);
		
		User user = dao.findByName("U1");
		
		assertNotNull(user);
		Assert.assertThat(user.getName(), is("U1"));		
		
		log.info("Exit: saveAndFindUserByNameTest");
		
	}	

}

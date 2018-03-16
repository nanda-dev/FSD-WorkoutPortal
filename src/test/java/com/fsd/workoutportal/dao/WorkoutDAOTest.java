package com.fsd.workoutportal.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.fsd.workoutportal.model.Workout;
import com.fsd.workoutportal.util.UnitTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkoutDAOTest {
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private WorkoutDAO dao;
	
	@Test
	public void saveWorkoutTest() {
		log.info("Start: saveWorkoutTest");
		
		Workout w = new Workout();
		w.setTitle("W1");
		w.setUserId(1L);
		w.setCalsBurnt(10d);
		w.setUnit(UnitTime.HOURS);
		
		Workout w2 = em.persistAndFlush(w);
		//Assert.assertThat(w2.getTitle(), is(w.getTitle()));
		
		List<Workout> workouts = dao.findByUserId(1L);
		
		assertNotNull(workouts);
		assertThat(workouts.size(), is(1));		
		
		log.info("Exit: saveWorkoutTest");
		
	}
	

}

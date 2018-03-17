package com.fsd.workoutportal.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

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
	public void saveAndFindWorkoutByUserIdTest() {
		log.info("Start: saveAndFindWorkoutByUserIdTest");
		
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
		
		log.info("Exit: saveAndFindWorkoutByUserIdTest");
		
	}
	
	@Test
	public void findWorkoutIdByUserIdTest() throws Exception {
		log.info("Start: findWorkoutIdByUserIdTest");
		
		Workout w = new Workout();
		w.setTitle("W1");
		w.setUserId(1L);
		w.setCalsBurnt(10d);
		w.setUnit(UnitTime.HOURS);
		
		em.persistAndFlush(w);
		
		List<Long> workoutIds = dao.findWorkoutIdByUserId(1L);
		
		assertNotNull(workoutIds);
		assertThat(workoutIds.size(), is(1));
		
		log.info("Exit: findWorkoutIdByUserIdTest");
		
	}
	

}

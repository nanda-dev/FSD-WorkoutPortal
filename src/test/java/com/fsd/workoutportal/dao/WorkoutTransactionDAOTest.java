package com.fsd.workoutportal.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.fsd.workoutportal.model.WorkoutTransaction;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkoutTransactionDAOTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private WorkoutTransactionDAO dao;
	
	@Test
	public void saveAndFindByWorkoutIdTest() {
		log.info("Start: saveAndFindByWorkoutIdTest");
		Long workoutId = 1L;
		
		WorkoutTransaction wt = new WorkoutTransaction();
		wt.setWorkoutId(workoutId);
		
		LocalDateTime start = LocalDateTime.now().minusHours(1);
		LocalDateTime end = LocalDateTime.now();
		
		wt.setStartTime(start);
		wt.setEndTime(end);
		wt.setDuration(Duration.between(start, end));
		wt.setCalsBurnt(10d);
		
		em.persistAndFlush(wt);
		
		List<WorkoutTransaction> txns = dao.findByWorkoutId(workoutId);
		
		assertNotNull(txns);
		assertThat(txns.size(), is(1));		
		
		log.info("Exit: saveAndFindByWorkoutIdTest");
		
	}
	
	@Test
	public void findByStartTimeBetweenAndWorkoutIdInTest() {
		log.info("Start: findByStartTimeBetweenAndWorkoutIdInTest");
		Long workoutId = 1L;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		WorkoutTransaction wt = new WorkoutTransaction();
		wt.setWorkoutId(workoutId);
		
		LocalDateTime start = LocalDateTime.parse("2018-03-10 10:00:00", formatter);
		LocalDateTime end = LocalDateTime.parse("2018-03-10 11:00:00", formatter);
		
		wt.setStartTime(start);
		wt.setEndTime(end);
		wt.setDuration(Duration.between(start, end));
		wt.setCalsBurnt(10d);
		
		em.persistAndFlush(wt);
		
		List<WorkoutTransaction> txns = dao.findByStartTimeBetweenAndWorkoutIdIn(
				LocalDateTime.parse("2018-03-01 00:00:00", formatter), 
				LocalDateTime.parse("2018-03-15 23:59:59", formatter),
				Arrays.asList(workoutId));
		
		assertNotNull(txns);
		assertThat(txns.size(), is(1));		
		
		log.info("Exit: findByStartTimeBetweenAndWorkoutIdInTest");
		
	}
	

}

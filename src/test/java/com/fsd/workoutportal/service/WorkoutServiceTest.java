package com.fsd.workoutportal.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.validateMockitoUsage;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fsd.workoutportal.dao.WorkoutDAO;
import com.fsd.workoutportal.model.Workout;

@RunWith(SpringRunner.class)
public class WorkoutServiceTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@MockBean
	private WorkoutDAO workoutDaoMock;
	
	@Autowired
	private WorkoutService workoutService;
		
	@TestConfiguration
	static class Config{
		
		@Bean
		public WorkoutService getSvc() {
			return new WorkoutServiceImpl();
		}
		
	}
	
	/*@Before
	public void setup() {
		workoutDao = Mockito.mock(WorkoutDAO.class);
		
	}*/
	
	@Test(expected = Exception.class)
	public void saveBlankWorkoutTest() throws Exception {
		log.info("Start: saveBlankWorkoutTest");
		
		List<Workout> workouts = workoutService.addWorkout(new Workout());
		
		log.info("Exit: saveBlankWorkoutTest");
	}
	
	@Test
	public void getWorkoutsTest() throws Exception {
		log.info("Start: getWorkoutsTest");
		Workout w = new Workout();
		Mockito.when(workoutDaoMock.findByUserId(Mockito.anyLong())).thenReturn(Arrays.asList(w));
		List<Workout> workouts = workoutService.getWorkoutsOfUser(2L);
		assertThat(workouts, hasSize(greaterThan(0)));
		log.info("Exit: getWorkoutsTest");
	}
	
	@After
	public void validate() {
		validateMockitoUsage();
	}

}

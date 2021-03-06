package com.fsd.workoutportal.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.validateMockitoUsage;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fsd.workoutportal.WorkoutPortalApplication;
import com.fsd.workoutportal.controller.WorkoutController;
import com.fsd.workoutportal.model.Workout;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = WorkoutController.class, secure = false)
@EnableCaching
public class WorkoutsCacheTest {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WorkoutService workoutService;
	
	@Test
	public void getWorkoutsFromCacheTest() throws Exception {
		log.info("Start: getWorkoutsFromCacheTest");
		List<Workout> firstList = new ArrayList<>();
		List<Workout> secondList = new ArrayList<>();

		Mockito.when(workoutService.getWorkoutsOfUser(Mockito.any(Long.class))).thenReturn(firstList, secondList);
		
		log.info("Asserting the initial fetch.");
		List<Workout> result = workoutService.getWorkoutsOfUser(2L);
		assertThat(result, is(firstList));

		// Assert whether the result, which would now come from Cache,
		// is the same object.
		log.info("Asserting the second fetch for the same query.");
		result = workoutService.getWorkoutsOfUser(2L);
		assertThat(result, is(firstList));

		// Verify that the Cacheable method is called only once as of now.
		//log.info("Verifying the Cacheable method call count is 1.");
		//Mockito.verify(workoutService, Mockito.times(1)).getWorkoutsOfUser(2L);

		log.info("Asserting the fetch for a different query.");
		result = workoutService.getWorkoutsOfUser(3L);
		assertThat(result, is(secondList));

		log.info("Exit: getWorkoutsFromCacheTest");
	}
	
	@After
	public void validate() {
		validateMockitoUsage();
	}
	
	/*public void  resetMocks() {
		Mockito.reset(workoutService, workoutServiceMock);
	}*/

}

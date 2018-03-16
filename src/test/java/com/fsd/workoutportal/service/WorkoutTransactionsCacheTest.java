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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fsd.workoutportal.controller.WorkoutTransactionController;
import com.fsd.workoutportal.model.WorkoutTransaction;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = WorkoutTransactionController.class, secure = false)
@EnableCaching
public class WorkoutTransactionsCacheTest {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WorkoutService workoutService;
	
	@MockBean
	private WorkoutTransactionService txnService;
	
	@Test
	public void getWorkoutTranscationsFromCacheTest() throws Exception {
		log.info("Start: getWorkoutTranscationsFromCacheTest");
		List<WorkoutTransaction> firstList = new ArrayList<>();
		List<WorkoutTransaction> secondList = new ArrayList<>();

		Mockito.when(txnService.getWorkoutTransactions(Mockito.any(Long.class))).thenReturn(firstList, secondList);
		
		log.info("Asserting the initial fetch.");
		List<WorkoutTransaction> result = txnService.getWorkoutTransactions(2L);
		assertThat(result, is(firstList));

		// Assert whether the result, which would now come from Cache,
		// is the same object.
		log.info("Asserting the second fetch for the same query.");
		result = txnService.getWorkoutTransactions(2L);
		assertThat(result, is(firstList));

		log.info("Asserting the fetch for a different query.");
		result = txnService.getWorkoutTransactions(3L);
		assertThat(result, is(secondList));

		log.info("Exit: getWorkoutTranscationsFromCacheTest");
	}
	
	@After
	public void validate() {
		validateMockitoUsage();
	}


}

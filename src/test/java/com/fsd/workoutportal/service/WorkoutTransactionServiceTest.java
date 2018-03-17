package com.fsd.workoutportal.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.validateMockitoUsage;

import java.time.LocalDateTime;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.fsd.workoutportal.dao.WorkoutDAO;
import com.fsd.workoutportal.dao.WorkoutTransactionDAO;
import com.fsd.workoutportal.model.WorkoutTransaction;

@RunWith(SpringRunner.class)
public class WorkoutTransactionServiceTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@MockBean
	private WorkoutDAO wkDao;
	
	@MockBean
	private WorkoutTransactionDAO txnDao;
	
	@Autowired
	private WorkoutTransactionService txnService;
		
	@TestConfiguration
	static class Config{
		
		@Bean
		public WorkoutTransactionService getSvc() {
			return new WorkoutTransactionServiceImpl();
		}
		
	}
	
	@Test(expected = Exception.class)
	public void saveBlankWorkoutTransactionTest() throws Exception {
		log.info("Start: saveBlankWorkoutTransactionTest");
		
		List<WorkoutTransaction> txns = txnService.addWorkoutTransaction(new WorkoutTransaction());
		
		log.info("Exit: saveBlankWorkoutTransactionTest");
	}	
	
	
	@After
	public void validate() {
		validateMockitoUsage();
	}

}

package com.fsd.workoutportal.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.workoutportal.model.WorkoutTransaction;
import com.fsd.workoutportal.service.WorkoutTransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WorkoutTransactionController.class, secure = false)
public class WorkoutTransactionControllerTest {
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WorkoutTransactionService txnService;
	
	@Test
	public void addWorkoutTransactionTest() throws Exception {
		log.info("Start: addWorkoutTransactionTest");
		WorkoutTransaction txn = new WorkoutTransaction();
		txn.setId(1L);
		txn.setWorkoutId(1L);
		txn.setStartTime(LocalDateTime.now());
		txn.setEndTime(LocalDateTime.now());
		txn.setDuration(Duration.between(txn.getStartTime(), txn.getEndTime()));
		txn.setCalsBurnt(10d);
		
		Mockito.when(txnService.addWorkoutTransaction(Mockito.any(WorkoutTransaction.class))).thenReturn(Arrays.asList(txn));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/wktxn")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"workoutId\": 1,\"startTime\": \"2018-03-01 10:00:00\",\"endTime\": \"2018-03-01 11:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		log.info("Exit: addWorkoutTransactionTest");
	}
	
	@Test
	public void getWorkoutTransactionsTest() throws Exception {
		log.info("Exit: getWorkoutTransactionsTest");
		WorkoutTransaction t1 = new WorkoutTransaction();
		t1.setId(1L);
		t1.setWorkoutId(1L);
		t1.setStartTime(LocalDateTime.now());
		t1.setEndTime(LocalDateTime.now());
		t1.setDuration(Duration.between(t1.getStartTime(), t1.getEndTime()));
		t1.setCalsBurnt(10d);
		WorkoutTransaction t2 = new WorkoutTransaction();
		t2.setId(2L);
		t2.setWorkoutId(1L);
		t2.setStartTime(LocalDateTime.now());
		t2.setEndTime(LocalDateTime.now());
		t2.setDuration(Duration.between(t2.getStartTime(), t2.getEndTime()));
		t2.setCalsBurnt(20d);
		
		List<WorkoutTransaction> txns = Arrays.asList(new WorkoutTransaction[] {t1, t2});
		Mockito.when(txnService.getWorkoutTransactions(1L)).thenReturn(txns);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/wktxn/1")
									.accept(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("$", hasSize(greaterThan(0)))).andDo(print());	
		
		log.info("Exit: getWorkoutTransactionsTest");
	}

}

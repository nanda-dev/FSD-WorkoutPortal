package com.fsd.workoutportal.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.workoutportal.model.WorkoutTransaction;
import com.fsd.workoutportal.service.WorkoutService;
import com.fsd.workoutportal.service.WorkoutTransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WorkoutTransactionController.class, secure = false)
public class WorkoutTransactionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WorkoutTransactionService txnService;
	
	@Test
	public void getWorkoutTransactionsTest() throws Exception {
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
	}

}

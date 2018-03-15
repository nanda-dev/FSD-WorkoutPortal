package com.fsd.workoutportal.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.workoutportal.model.Workout;
import com.fsd.workoutportal.service.WorkoutService;
import com.fsd.workoutportal.util.UnitTime;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WorkoutController.class, secure = false)
public class WorkoutControllerTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MockMvc mockMvc;	
	
	@MockBean
	private WorkoutService workoutService;
	
	@Test
	public void getWorkoutsOfUserTest() throws Exception {
		log.info("Test #1 Running...");
		Workout w1 = new Workout();
		w1.setId(1L);
		w1.setTitle("W1");
		w1.setCalsBurnt(10d);
		w1.setUnit(UnitTime.HOURS);
		w1.setUserId(2L);
		Workout w2 = new Workout();
		w2.setId(2L);
		w2.setTitle("W2");
		w2.setCalsBurnt(20d);
		w2.setUnit(UnitTime.HOURS);
		w2.setUserId(2L);
		
		Workout[] w = {w1, w2};		
		Mockito.when(workoutService.getWorkoutsOfUser(2L)).thenReturn(Arrays.asList(w));
		MvcResult result;
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/workout/2")
									.accept(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("$", hasSize(greaterThan(0)))).andDo(print());
		//System.out.println(result.getResponse().getContentAsString());
		log.info("Test #1 Passed.");		
	}	

}

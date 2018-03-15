package com.fsd.workoutportal.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
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
	public void createWorkoutTest() throws Exception {
		log.info("Start: createWorkoutTest");
		Workout workout = new Workout();
		workout.setId(1L);
		workout.setTitle("W1");
		workout.setUnit(UnitTime.HOURS);
		workout.setCalsBurnt(10d);
		workout.setUserId(1L);
		
		Mockito.when(workoutService.addWorkout(Mockito.any(Workout.class))).thenReturn(Arrays.asList(workout));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/workout")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"title\": \"testworkout\",\"unit\": \"HOURS\",\"calsBurnt\": 10,\"userId\": 1}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		log.info("Exit: createWorkoutTest");
	}
	
	@Test
	public void getWorkoutsOfUserTest() throws Exception {
		log.info("Start: getWorkoutsOfUserTest");
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
		//MvcResult result;
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/workout/2")
									.accept(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("$", hasSize(greaterThan(0)))).andDo(print());
		
		log.info("Exit: getWorkoutsOfUserTest");	
	}	

}

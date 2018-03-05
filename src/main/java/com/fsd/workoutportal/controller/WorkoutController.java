package com.fsd.workoutportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.workoutportal.dto.ApiResponse;
import com.fsd.workoutportal.model.Workout;
import com.fsd.workoutportal.service.WorkoutService;
import com.fsd.workoutportal.util.Constants;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WorkoutService workoutService;
	
	@PostMapping
	public ApiResponse addWorkout(@RequestBody Workout workout) {
		logger.info("Add Workout");
		try {
			workoutService.addWorkout(workout);
			logger.info("Workout added to database");
			return new ApiResponse(Constants.API_STATUS_SUCCESS, null);
		} catch(Exception e) {
			logger.error("Error while adding Workout: ", e);
			return new ApiResponse(Constants.API_STATUS_ERROR, e.getMessage());
		}
		
	}
	
	@GetMapping("/{userId}")
	public ApiResponse getWorkoutsOfUser(@PathVariable Long userId) {
		return new ApiResponse(Constants.API_STATUS_SUCCESS, null);
	}

}

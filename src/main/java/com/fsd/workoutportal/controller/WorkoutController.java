package com.fsd.workoutportal.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class WorkoutController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WorkoutService workoutService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> addWorkout(@RequestBody Workout workout) {
		logger.info("Add Workout.");
		try {
			workoutService.addWorkout(workout);
			logger.info("Workout added to database");
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(Constants.API_STATUS_SUCCESS, null));			
		} catch(Exception e) {
			logger.error("Error while adding Workout: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(Constants.API_STATUS_ERROR, e.getMessage()));
		}
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Workout>> getWorkoutsOfUser(@PathVariable Long userId) {
		logger.info("Get Workouts of User: {}", userId);
		List<Workout> workouts = new ArrayList<>();
		try {			
			workouts = workoutService.getWorkoutsOfUser(userId);
			logger.info("Returning {} Workouts to App.", (workouts != null ? workouts.size() : 0));
			return ResponseEntity.status(HttpStatus.CREATED).body(workouts);
		} catch(Exception e) {
			logger.error("Error while fetching Workouts: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(workouts);
		}		
		
	}

}

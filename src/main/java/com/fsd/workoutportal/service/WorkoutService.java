package com.fsd.workoutportal.service;

import java.util.List;

import com.fsd.workoutportal.model.Workout;

public interface WorkoutService {
	
	public List<Workout> addWorkout(Workout workout) throws Exception;
	
	public List<Workout> getWorkoutsOfUser(Long userId);

}

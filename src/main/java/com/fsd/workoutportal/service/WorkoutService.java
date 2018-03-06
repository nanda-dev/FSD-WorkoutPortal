package com.fsd.workoutportal.service;

import java.util.List;

import com.fsd.workoutportal.model.Workout;

public interface WorkoutService {
	
	public void addWorkout(Workout workout);
	
	public List<Workout> getWorkoutsOfUser(Long userId);

}

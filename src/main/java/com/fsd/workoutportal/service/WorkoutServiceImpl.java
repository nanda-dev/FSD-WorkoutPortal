package com.fsd.workoutportal.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsd.workoutportal.dao.WorkoutDAO;
import com.fsd.workoutportal.model.Workout;

@Service
public class WorkoutServiceImpl implements WorkoutService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WorkoutDAO workoutDao;

	@Override
	@Transactional
	public void addWorkout(Workout workout) {
		workoutDao.save(workout);
	}

	@Override
	public List<Workout> getWorkoutsOfUser(Long userId) {
		return workoutDao.findByUserId(userId);
	}

}

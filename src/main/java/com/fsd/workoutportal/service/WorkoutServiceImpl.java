package com.fsd.workoutportal.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	@CachePut(value = "userWorkoutsCache", key = "#result[0].userId", unless = "#result == null")
	public List<Workout> addWorkout(Workout workout) throws Exception {
		List<Workout> workouts = null;
		if(workout != null && workout.getUserId() != null) {
			logger.info("Saving workout of user {} to database.", workout.getUserId());
			Workout wk = workoutDao.save(workout);
			logger.info("Workout saved.");
			if(wk != null) {
				workouts = this.getWorkoutsOfUser(wk.getUserId());
				logger.info("Workout added to cache. Entries in cache: {}", workouts.size());
				return workouts;
			}			
		}		
		throw new Exception("Invalid input for adding Workout.");		
	}

	@Override
	@Cacheable(value = "userWorkoutsCache", key = "#userId")
	public List<Workout> getWorkoutsOfUser(Long userId) {
		logger.info("Fetching workouts of user {} from database.", userId);
		return workoutDao.findByUserId(userId);
	}

}

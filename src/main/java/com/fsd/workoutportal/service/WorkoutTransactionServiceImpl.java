package com.fsd.workoutportal.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.workoutportal.dao.WorkoutDAO;
import com.fsd.workoutportal.dao.WorkoutTransactionDAO;
import com.fsd.workoutportal.model.Workout;
import com.fsd.workoutportal.model.WorkoutTransaction;
import com.fsd.workoutportal.util.UnitTime;

@Service
public class WorkoutTransactionServiceImpl implements WorkoutTransactionService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WorkoutTransactionDAO wtxnDao;
	
	@Autowired
	private WorkoutDAO workoutDao;

	@Override
	public void addWorkoutTransaction(WorkoutTransaction wTxn) {
		if(wTxn != null && wTxn.getStartTime() != null && wTxn.getEndTime() != null) {	
			logger.info("Calculate duration...");
			
			Duration duration = Duration.between(wTxn.getStartTime(), wTxn.getEndTime());
			
			logger.info("Duration in seconds: {}", 
					TimeUnit.MILLISECONDS.toSeconds(duration.toMillis()));
			
			//Calculate the Calories Burnt
			Double calsBurnt = getCalsBurntByWorkoutForDuration(wTxn.getWorkoutId(), duration);
			
			logger.info("Calories Burnt: {}", calsBurnt);
			
			wTxn.setCalsBurnt(calsBurnt);
			wTxn.setDuration(duration);
			
			wtxnDao.save(wTxn);
			logger.info("Txn saved");
		}		

	}
	
	@Override
	public List<WorkoutTransaction> getWorkoutTransactions(Long workoutId) {
		logger.info("Fetching transaction list from DB...");
		List<WorkoutTransaction> txns = wtxnDao.findByWorkoutId(workoutId);
		if(txns != null) {
			logger.info("{} transactions fetched from database", txns.size());
		}
		return txns;
	}
	
	@Override
	public List<WorkoutTransaction> getWorkoutTransactionReport(WorkoutTransaction txn) throws Exception {
		if(txn != null) {
			logger.info("Fetching transaction list from DB...");
			logger.info("Start Time: {}", txn.getStartTime());
			logger.info("End Time: {}", txn.getEndTime());
			List<WorkoutTransaction> txns = wtxnDao.findByStartTimeBetween(txn.getStartTime(), txn.getEndTime());
			logger.info("{} transactions fetched from database", (txns != null ? txns.size() : 0));
			return txns;
		} else {
			throw new Exception("Invalid input");
		}		
	}
	
	private Double getCalsBurntByWorkoutForDuration(Long workoutId, Duration duration) {
		//Fetch Workout using workoutId
		Workout workout = workoutDao.findOne(workoutId);
		
		if(workout != null) {
			logger.info("Workout {} fetched from db.", workoutId);
			Double workoutCals = workout.getCalsBurnt();
			Double multiplierUnit = getWorkoutUnitFromDuration(workout.getUnit(), duration);
			Double calsBurnt = workoutCals * multiplierUnit;
			
			logger.info("workoutCals = {}, "
					+ "multiplierUnit = {}, "
					+ "calsBurnt = {}", 
					workoutCals, 
					multiplierUnit, 
					calsBurnt);
			
			return calsBurnt;
		}
		else {
			logger.info("Workout {} not found in db.", workoutId);
			return 0d;
		}
		
	}
	
	private Double getWorkoutUnitFromDuration(UnitTime workoutUnit, Duration duration) {
		//Convert the millisecond/second duration 
		//to the required time unit for easy calc.
		if(workoutUnit == UnitTime.HOURS) {
			logger.info("WorkoutUnit is HOURS");
			return (TimeUnit.MILLISECONDS.toSeconds(duration.toMillis()) / 3600d);
		}
		else if(workoutUnit == UnitTime.MINUTES) {
			logger.info("WorkoutUnit is MINUTES");
			return (TimeUnit.MILLISECONDS.toSeconds(duration.toMillis()) / 60d);
		}
		else{		
			logger.info("WorkoutUnit is SECONDS");
			return (double) (TimeUnit.MILLISECONDS.toSeconds(duration.toMillis()));
		}		
	}

}

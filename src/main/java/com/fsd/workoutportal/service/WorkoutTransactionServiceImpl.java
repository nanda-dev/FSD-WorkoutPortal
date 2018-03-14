package com.fsd.workoutportal.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	@CachePut(value = "txnCache", key = "#result[0].workoutId", unless = "#result == null")
	public List<WorkoutTransaction> addWorkoutTransaction(WorkoutTransaction wTxn) throws Exception {
		WorkoutTransaction txn = null;
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
			
			txn = wtxnDao.save(wTxn);
			logger.info("Txn saved");
			if(txn != null) {
				logger.info("Txn for workout {} will be added to cache.", txn.getWorkoutId());
				List<WorkoutTransaction> cachedList = this.getWorkoutTransactions(txn.getWorkoutId());
				
				if(cachedList == null) {
					logger.info("Cache is null/empty.");
					//cachedList = new ArrayList<>();
				}					
				
				logger.info("Txns in Cache for workout {} before addition: {}", 
						txn.getWorkoutId(), 
						(cachedList != null ? cachedList.size() : 0));
				
				//Saved txn is getting added to Cache.
				//Hence adding it here, will give a duplicate entry in response.
				//cachedList.add(txn);
				
				logger.info("Txns in Cache for workout {} after addition: {}", 
						txn.getWorkoutId(), 
						(cachedList != null ? cachedList.size() : 0));
				
				return cachedList;
				
			}
		}		
		
		throw new Exception("Invalid input for adding Workout Transaction.");			

	}
	
	@Override
	@Cacheable(value = "txnCache", key = "#workoutId", unless = "#result == null")
	public List<WorkoutTransaction> getWorkoutTransactions(Long workoutId) {
		logger.info("Fetching transaction list from DB...");
		List<WorkoutTransaction> txns = wtxnDao.findByWorkoutId(workoutId);
		if(txns != null) {
			//If there are no records fetched, dao is returning a non-null, 0 sized list.
			//Could be the reason why the cache is always having a value.
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
		Workout workout = this.getWorkoutById(workoutId);
		
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
	
	@Cacheable(value = "workoutCache", key = "#workoutId")
	private Workout getWorkoutById(Long workoutId) {
		logger.info("Fetching workout {} from db. Will be cached.", workoutId);
		return workoutDao.findOne(workoutId);
	}

}

package com.fsd.workoutportal.service;

import java.util.List;

import com.fsd.workoutportal.model.WorkoutTransaction;

public interface WorkoutTransactionService {
	
	public List<WorkoutTransaction> addWorkoutTransaction(WorkoutTransaction wTxn) throws Exception;	
	public List<WorkoutTransaction> getWorkoutTransactions(Long workoutId);
	public List<WorkoutTransaction> getWorkoutTransactionReport(Long userId, WorkoutTransaction txn) throws Exception;

}

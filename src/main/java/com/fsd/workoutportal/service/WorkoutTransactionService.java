package com.fsd.workoutportal.service;

import java.util.List;

import com.fsd.workoutportal.model.WorkoutTransaction;

public interface WorkoutTransactionService {
	
	public void addWorkoutTransaction(WorkoutTransaction wTxn);	
	public List<WorkoutTransaction> getWorkoutTransactions(Long workoutId);
	public List<WorkoutTransaction> getWorkoutTransactionReport(WorkoutTransaction txn) throws Exception;

}

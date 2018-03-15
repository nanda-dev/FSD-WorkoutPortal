package com.fsd.workoutportal.controller;

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
import com.fsd.workoutportal.model.WorkoutTransaction;
import com.fsd.workoutportal.service.WorkoutTransactionService;
import com.fsd.workoutportal.util.Constants;

@RestController
@RequestMapping("/api/wktxn")
@CrossOrigin
public class WorkoutTransactionController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WorkoutTransactionService txnService;
	
	@PostMapping
	public ApiResponse addNewTransaction(@RequestBody WorkoutTransaction wTxn) {
		logger.info("Add Workout Transaction");
		try {
			txnService.addWorkoutTransaction(wTxn);
			logger.info("Workout Transaction added to database");
			return new ApiResponse(Constants.API_STATUS_SUCCESS, null);
		} catch (Exception e) {
			logger.error("Error while adding Workout Transaction: {}", e);
			return new ApiResponse(Constants.API_STATUS_ERROR, e.getMessage());
		}		
	}
	
	@GetMapping("/{workoutId}")
	public ResponseEntity<Object> getWorkoutTransactions(@PathVariable Long workoutId){
		logger.info("Get Workout Transactions for workout: {}", workoutId);
		try {
			List<WorkoutTransaction> txns = txnService.getWorkoutTransactions(workoutId);
			logger.info("Returning {} Workout Transactions to App.", 
					(txns != null ? txns.size() : 0));
			return ResponseEntity.ok(txns);
		} catch (Exception e) {
			logger.error("Error while fetching Workout Transactions: {}", e);
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PostMapping("/report")
	public ResponseEntity<Object> getTransactionsReport(@RequestBody WorkoutTransaction txn) {
		logger.info("Get Transactions Report");
		try {
			List<WorkoutTransaction> txns = txnService.getWorkoutTransactionReport(txn);
			logger.info("Returning Workout Transactions fetched from database for the given period");
			return ResponseEntity.ok(txns);
		} catch (Exception e) {
			logger.error("Error while fetching Workout Transaction report: {}", e);
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

}

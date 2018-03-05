package com.fsd.workoutportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
			logger.error("Error while adding Workout Transaction: ", e);
			return new ApiResponse(Constants.API_STATUS_ERROR, e.getMessage());
		}
		
	}

}

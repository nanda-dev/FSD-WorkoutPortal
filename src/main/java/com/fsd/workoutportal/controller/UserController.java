package com.fsd.workoutportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.workoutportal.dto.ApiResponse;
import com.fsd.workoutportal.model.User;
import com.fsd.workoutportal.service.UserService;
import com.fsd.workoutportal.util.Constants;

@RestController
@RequestMapping("/api/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ApiResponse login(@RequestBody User user) {
		return new ApiResponse(Constants.API_STATUS_SUCCESS, null);
	}
	
	@PostMapping
	public ApiResponse register(@RequestBody User user) {
		logger.info("Register User");
		try {
			userService.addUser(user);
			logger.info("User added to database.");
			//return ResponseEntity.created(null).body(new ApiResponse(Constants.API_STATUS_SUCCESS, null));
			return new ApiResponse(Constants.API_STATUS_SUCCESS, null);
		} catch(Exception e) {
			logger.error("Error while adding User to database:", e);
			return new ApiResponse(Constants.API_STATUS_ERROR, e.getMessage());
			//return ResponseEntity.badRequest().body(new ApiResponse(Constants.API_STATUS_ERROR, e.getMessage()));
		}
		
	}
	
	@PutMapping
	public ApiResponse changePassword(@RequestBody User user) {
		return new ApiResponse(Constants.API_STATUS_SUCCESS, null);
	}

}

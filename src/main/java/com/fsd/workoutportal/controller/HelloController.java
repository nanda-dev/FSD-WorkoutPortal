package com.fsd.workoutportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.workoutportal.dto.ApiResponse;
import com.fsd.workoutportal.util.Constants;

@RestController
public class HelloController {
	@GetMapping("/")
	public ApiResponse index() {
		return new ApiResponse(Constants.API_STATUS_SUCCESS, "Hello there!");
	}

}

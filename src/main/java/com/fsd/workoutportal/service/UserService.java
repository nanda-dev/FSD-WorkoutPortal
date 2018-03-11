package com.fsd.workoutportal.service;

import com.fsd.workoutportal.model.User;

public interface UserService {
	
	public User addUser(User user);
	public User login(User user);
	public void updatePassword(String password, Long userId);

}

package com.fsd.workoutportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.workoutportal.dao.UserDAO;
import com.fsd.workoutportal.model.User;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDAO userDao;

	@Override
	public User addUser(User user) {
		return userDao.save(user);
	}

	@Override
	public void updatePassword(String password, Long userId) {
		User user = userDao.findOne(userId);
		if(user != null) {
			user.setPassword(password);
			userDao.save(user);
		}
	}

}

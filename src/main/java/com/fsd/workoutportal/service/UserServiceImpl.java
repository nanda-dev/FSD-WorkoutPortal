package com.fsd.workoutportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsd.workoutportal.dao.UserDAO;
import com.fsd.workoutportal.model.User;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDAO userDao;

	@Override
	@Transactional
	public User addUser(User user) {
		logger.info("Sending User Object to DAO: {}", user.toString());
		
		return userDao.save(user);
	}
	
	@Override
	public User login(User user) {
		if(user != null) {
			logger.info("Fetching user with username: {}", user.getName());
			User u = userDao.findByName(user.getName());
			if(u.getPassword().equals(user.getPassword())) {
				logger.info("Passwords match, login successful");
				user.setId(u.getId());
				return user;
			}			
		}
		return null;
	}

	@Override
	@Transactional
	public void updatePassword(String password, Long userId) {
		User user = this.getUserById(userId);
		if(user != null) {
			user.setPassword(password);
			userDao.save(user);
		}
	}
	
	@Cacheable("userCache")
	private User getUserById(Long userId) {
		return userDao.findOne(userId);
	}

}

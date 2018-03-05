package com.fsd.workoutportal.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fsd.workoutportal.model.User;

@Repository
public interface UserDAO extends CrudRepository<User, Long>{

	User findByName(String userName);
}

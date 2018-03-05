package com.fsd.workoutportal.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fsd.workoutportal.model.Workout;

@Repository
public interface WorkoutDAO extends CrudRepository<Workout, Long>{

}

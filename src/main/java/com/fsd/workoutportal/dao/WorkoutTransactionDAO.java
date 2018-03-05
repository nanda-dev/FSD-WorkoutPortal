package com.fsd.workoutportal.dao;

import org.springframework.data.repository.CrudRepository;

import com.fsd.workoutportal.model.WorkoutTransaction;

public interface WorkoutTransactionDAO extends CrudRepository<WorkoutTransaction, Long> {

}

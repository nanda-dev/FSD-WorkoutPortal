package com.fsd.workoutportal.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fsd.workoutportal.model.WorkoutTransaction;

public interface WorkoutTransactionDAO extends CrudRepository<WorkoutTransaction, Long> {
	List<WorkoutTransaction> findByWorkoutId(Long workoutId);
	List<WorkoutTransaction> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}

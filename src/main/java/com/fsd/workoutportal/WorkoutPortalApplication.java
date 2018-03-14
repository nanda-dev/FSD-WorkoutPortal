package com.fsd.workoutportal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class WorkoutPortalApplication {
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(WorkoutPortalApplication.class, args);
	}
	
	//Implementing a basic CacheEvit mechanism, 
	//which will clear the cache after a specified period of time.
	@Scheduled(fixedDelay = 300000)
	public void clearCache() {
		logger.info("Evicting User, Workout and Transaction Caches");
		this.clearUserCache();
		this.clearWorkoutCache();
		this.clearWorkoutTransactionCache();
		this.clearUserWorkoutsCache();
	}
	
	@CacheEvict(value = "userCache", allEntries = true)
	private void clearUserCache() {}
	
	@CacheEvict(value = "workoutCache", allEntries = true)
	private void clearWorkoutCache() {}
	
	@CacheEvict(value = "txnCache", allEntries = true)
	private void clearWorkoutTransactionCache() {}
	
	@CacheEvict(value = "userWorkoutsCache", allEntries = true)
	private void clearUserWorkoutsCache() {}
	
}

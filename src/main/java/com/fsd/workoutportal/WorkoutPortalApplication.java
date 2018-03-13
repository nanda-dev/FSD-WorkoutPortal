package com.fsd.workoutportal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class WorkoutPortalApplication {
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(WorkoutPortalApplication.class, args);
	}
	
	@Scheduled(fixedDelay = 300000)
	public void clearCache() {
		logger.info("Evicting User and Workout Caches");
		this.clearUserCache();
		this.clearWorkoutCache();
	}
	
	@CacheEvict(value = "userCache", allEntries = true)
	private void clearUserCache() {}
	
	@CacheEvict(value = "workoutCache", allEntries = true)
	private void clearWorkoutCache() {}
	
}

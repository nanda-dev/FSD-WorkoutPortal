package com.fsd.workoutportal.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DurationConverter 
	implements AttributeConverter<Duration, Long> {

	@Override
	public Long convertToDatabaseColumn(Duration duration) {
		return (duration == null ? null : TimeUnit.MILLISECONDS.toSeconds(duration.toMillis()));
	}

	@Override
	public Duration convertToEntityAttribute(Long durationInSeconds) {
		return (durationInSeconds == null ? null : Duration.ofSeconds(durationInSeconds));
	}	

}

package com.fsd.workoutportal.util;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DurationConverter 
	implements AttributeConverter<Duration, Long> {

	@Override
	public Long convertToDatabaseColumn(Duration duration) {
		return (duration == null ? null : duration.toMillis());
	}

	@Override
	public Duration convertToEntityAttribute(Long durationInMillis) {
		return (durationInMillis == null ? null : Duration.ofMillis(durationInMillis));
	}	

}

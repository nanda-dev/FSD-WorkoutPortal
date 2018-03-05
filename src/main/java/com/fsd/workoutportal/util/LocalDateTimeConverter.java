package com.fsd.workoutportal.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter 
	implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDt) {
		return (localDt == null ? null : Timestamp.valueOf(localDt));
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp sqlDt) {
		return (sqlDt == null ? null : sqlDt.toLocalDateTime());
	}	

}

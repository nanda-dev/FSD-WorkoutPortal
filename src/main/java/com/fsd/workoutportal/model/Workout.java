package com.fsd.workoutportal.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fsd.workoutportal.util.UnitTime;

@Entity
public class Workout {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private Double calsBurnt;
	
	@Enumerated(EnumType.STRING)
	private UnitTime unit;
	
	private Long userId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getCalsBurnt() {
		return calsBurnt;
	}
	public void setCalsBurnt(Double calsBurnt) {
		this.calsBurnt = calsBurnt;
	}
	public UnitTime getUnit() {
		return unit;
	}
	public void setUnit(UnitTime unit) {
		this.unit = unit;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}	

}

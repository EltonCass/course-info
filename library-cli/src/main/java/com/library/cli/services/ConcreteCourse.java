package com.library.cli.services;

import java.time.Duration;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ConcreteCourse(String id, String title, String duration, String contentUrlm, boolean isRetired) {
	
	public long durationInMinutes() {
		return Duration.between(
				LocalTime.MIN ,
				LocalTime.parse (duration())
		).toMinutes();
	}
}

package com.library.cli.services;

import java.util.List;

import com.library.domain.Course;
import com.library.repository.CourseRepository;

public class CourseStorageService {
	private final CourseRepository courseRepository;
	
	private static final String PS_BASE_URL = "https://app.pluralsight.com"; 
	
	public CourseStorageService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	public void storeCourses(List<ConcreteCourse> course) {
		for (ConcreteCourse c : course) {
			Course newCourse = new Course(c.id(), c.title(), c.durationInMinutes(), PS_BASE_URL + c.contentUrlm());
			courseRepository.saveCourse(newCourse);
		}
	}
	
	public List<Course> getAllCourses() {
		return courseRepository.getAllCourses();
	}
}

package com.library.repository;

import java.util.List;

import com.library.domain.Course;

public interface CourseRepository {
	void saveCourse(Course course);
	Course findCourse(String id);
	List<Course> getAllCourses();
	
	static CourseRepository openCourseRepository(String databaseFile) {
		return new CourseJdbcRepository(databaseFile);
	}
	
}

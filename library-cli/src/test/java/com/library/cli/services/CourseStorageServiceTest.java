package com.library.cli.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.library.domain.Course;
import com.library.repository.CourseRepository;

class CourseStorageServiceTest {
	@Test
	void storeCourses() {
		CourseStorageService service = new CourseStorageService(new InnMemoryCourseRepository());
		ConcreteCourse course = new ConcreteCourse("1", "Test Course", "00:05:37", "/test", false);
		
		service.storeCourses(List.of(course));
		
		assertEquals(1, service.getAllCourses().size());
	}
	
	static class InnMemoryCourseRepository implements CourseRepository{
		
		private final List<Course> courses = new ArrayList<>();

		@Override
		public void saveCourse(Course course) {
			courses.add(course);
			
		}

		@Override
		public Course findCourse(String id) {
			Course course = courses.stream()
				.filter(c -> c.id().equals(id))
				.findFirst().orElse(null);

            return course;
		}

		@Override
		public List<Course> getAllCourses() {
			return courses;
		}

	}
}

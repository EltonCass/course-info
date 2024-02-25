package com.library.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.library.domain.Course;
import com.library.repository.CourseRepository;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/courses")
public class CourseResource {
	private static final Logger LOG = LoggerFactory.getLogger(CourseResource.class);
	
	private final CourseRepository repository;
	
	public CourseResource(CourseRepository repository) {
		this.repository = repository;
	}
	
	@GET
	public String getCourses() {
		LOG.info("Getting courses");
		String courses = repository.getAllCourses()
				.stream()
				.map(Course::toString)
				.collect(java.util.stream.Collectors.joining(", "));
		return courses;
	}

}

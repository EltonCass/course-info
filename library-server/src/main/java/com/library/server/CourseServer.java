package com.library.server;

import java.net.URI;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.library.repository.CourseRepository;

public class CourseServer {
	private static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";
	public static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
	
	public static void main(String[] args) {
		LOG.info("CourseServer starting");

		CourseRepository repository = CourseRepository.openCourseRepository("./courses.db");
		ResourceConfig config = new ResourceConfig().register(new CourseResource(repository));
		
		GrizzlyHttpServerFactory.createHttpServer(URI.create(HTTP_LOCALHOST_8080), config);
	}
}

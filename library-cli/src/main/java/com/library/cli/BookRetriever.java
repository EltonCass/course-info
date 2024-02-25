package com.library.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.library.cli.services.BookRetrievalService;
import com.library.cli.services.ConcreteCourse;
import com.library.cli.services.CourseStorageService;
import com.library.repository.CourseRepository;

public class BookRetriever {
	private static final Logger LOG = LoggerFactory.getLogger(BookRetriever.class);
	
	public static void main(String[] args) {
		LOG.info("BookRetriever starting");
		//System.out.println("BookRetriever");
		if (args.length == 0) {
			LOG.warn("No arguments provided");
			return;
		}
		try {
			retrieveBook(args[0]);
		}
		catch (Exception e) {
			LOG.error("Error: {Error Message} ", e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void retrieveBook(String bookId) {
		LOG.info("Retrieving book: '{}'", bookId);
		BookRetrievalService service = new BookRetrievalService();
		CourseRepository repository = CourseRepository.openCourseRepository("./courses.db");
		CourseStorageService storageService = new CourseStorageService(repository);
		
		List<ConcreteCourse> nonRetiredCourses = service.getCourseFor(bookId)
				.stream()
				.filter(c -> !c.isRetired())
				//.filter(java.util.function.Predicate.not(Course::isRetired))
				.toList();
		if (nonRetiredCourses.isEmpty()) {
			LOG.info("No course found for book: {}", bookId);
			return;
		}
		storageService.storeCourses(nonRetiredCourses);
		LOG.info("Courses successfully stored");
        // nonRetiredCourses.forEach(c -> LOG.info("Retrieved course: {}", c));
	}
}

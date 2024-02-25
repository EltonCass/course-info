package com.library.cli.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.library.cli.services.ConcreteCourse;

class CourseTest {
	
//	@Test
//	void testDurationInMinutes() {
//		Course course = new Course("1", "Test Course", "00:05:37", "http://test.com", false);
//		assertEquals(5, course.durationInMinutes());
//	}
//	
//	@Test
//	void testDurationInMinutesOverHour() {
//		Course course = new Course("1", "Test Course", "01:08:37.995348", "http://test.com", false);
//		assertEquals(68, course.durationInMinutes());
//	}
	
	@ParameterizedTest
	@CsvSource({
        "00:05:37, 5",
        "01:08:37.995348, 68"
    })
	void testDurationInMinutes(String duration, long expected) {
		ConcreteCourse course = new ConcreteCourse("1", "Test Course", duration, "http://test.com", false);
		assertEquals(expected, course.durationInMinutes());
	}
}


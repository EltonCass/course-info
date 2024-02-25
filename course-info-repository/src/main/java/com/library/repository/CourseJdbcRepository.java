package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

import com.library.domain.Course;

class CourseJdbcRepository implements CourseRepository {

	private static final String H2_DATABASE_URL = "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql'";
	private final DataSource dataSource;
	
	public CourseJdbcRepository(String databaseFile) {
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setURL(H2_DATABASE_URL.formatted(databaseFile));
		dataSource = jdbcDataSource;
	}
	
	@Override
	public void saveCourse(Course course) {	
		// this is a try-with-resources block that automatically closes the connection
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement statement = connection.prepareStatement(
					"MERGE INTO COURSES (id, name, length, url) VALUES (?, ?, ?, ?)");
			statement.setString(1, course.id());
			statement.setString(2, course.name());
			statement.setLong(3, course.length());
			statement.setString(4, course.url());
		} catch (SQLException e) {
			throw new RepositoryException("Failed to save " + course, e);
		}
	}

	@Override
	public Course findCourse(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getAllCourses() {
		try (Connection connection = dataSource.getConnection()){
//			PreparedStatement statement = connection.prepareStatement(""
//					+ "SELECT * FROM COURSES");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSES");
			
			List<Course> courses = new ArrayList<Course>();
			while (resultSet.next()) {
				courses.add(new Course(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getString(4)));
			}
			
			// return an unmodifiable list to prevent modification of the list by the caller (immutable)
			return Collections.unmodifiableList(courses);
		} catch (SQLException e) {
			throw new RepositoryException("Failed to retrieve courses", e);
		}
	}

}

package com.library.cli.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class BookRetrievalService {
	private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";
	private static final HttpClient CLIENT = HttpClient
			.newBuilder()
			.followRedirects(HttpClient.Redirect.ALWAYS)
			.build();
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public List<ConcreteCourse> getCourseFor(String bookId) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(PS_URI.formatted(bookId)))
				.GET()
				.build();
		
		try {
			HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			//return response.body();
			return switch (response.statusCode()) {
				case 200 -> toCourses(response.body());
				case 404 -> List.of();
				default -> throw new RuntimeException("Unexpected response code: " + response.statusCode());
			};
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Could not retrieve course for book: {}" + bookId, e);
		}
	}

	private List<ConcreteCourse> toCourses(String body) throws JsonProcessingException,JsonMappingException{
		CollectionType type = OBJECT_MAPPER
			.getTypeFactory()
			.constructCollectionType(List.class, ConcreteCourse.class);
		return OBJECT_MAPPER.readValue(body, type);}
}

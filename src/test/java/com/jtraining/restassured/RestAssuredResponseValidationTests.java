package com.jtraining.restassured;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.Before;
import org.junit.Test;

import com.jtraining.restassured.model2.Student;
import com.jtraining.restassured.model2.Students;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredResponseValidationTests {

	RequestSpecification request;

	@Before
	public void setup() {
		request = RestAssured.given().auth().basic("user", "password");
	}
	
	@Test
	public void validateStudents() {

		System.out.println("Starting validateStudents");

		Response response = request.get("http://localhost:8080/api/v1/students");

		System.out.println(response.asString());
		try {
			response.then().assertThat().body("students[0].name", equalTo("user1"));
			response.then().assertThat().body("students[0].hobbies", hasItem("Coding"));
			System.out.println("User1 validation successful");
			
			response.then().assertThat().body("students[1].name", equalTo("user2"));
			response.then().assertThat().body("students[1].hobbies", hasItem("Coding"));
			System.out.println("User2 validation successful");
		} catch (Exception e) {
			System.out.println("Failure");
		}
	}
	
	@Test
	public void validateStudentsUsingPojo() {

		System.out.println("Starting validateStudents");

		Response response = request.get("http://localhost:8080/api/v1/students");
		
		Students students = response.as(Students.class);

		for(Student student : students.getStudents()) {
			System.out.println(student.getName());
		}
	}

	@Test
	public void validateStudent() {

		System.out.println("Starting validateStudents");

		Response response = request.get("http://localhost:8080/api/v1/student/user1");

		System.out.println(response.asString());
		try {
			response.then().assertThat().body("name", equalTo("user1"));
			response.then().assertThat().body("hobbies", hasItem("Coding"));
			System.out.println("Success");
		} catch (Exception e) {
			System.out.println("Failure");
		}
	}

}

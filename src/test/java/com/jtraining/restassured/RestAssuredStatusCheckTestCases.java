package com.jtraining.restassured;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.jtraining.restassured.model.Student;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredStatusCheckTestCases {

	@Test
	public void testWithoutAuthorization() {

		System.out.println("Starting testWithoutAuthorization");

		Response response = RestAssured
				.given()
				.get("http://localhost:8080/api/v1/students")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue("Expected : 401, but Found : ", response.getStatusCode() == 401);
	}
	
	@Test
	public void testWithAuthorizationButInvalidCredentials() {

		System.out.println("Starting testWithAuthorizationButInvalidCredentials");

		Response response = RestAssured
				.given()
				.auth()
				.basic("userName", "password")
				.get("http://localhost:8080/api/v1/students")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 401, but Found : " + response.getStatusCode()), response.getStatusCode() == 401);
	}
	
	@Test
	public void testOnEmptyApplication() {

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.get("http://localhost:8080/api/v1/students")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 401, but Found : " + response.getStatusCode()), response.getStatusCode() == 401);
	}
	
//	EXAMPLE-1
	@Test
	public void postToApplication() {
		
		System.out.println("Starting postToApplication");
		
		//Map containers Key-value pairs "name" = "John doe".
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", "Mark1");
		requestBody.put("age", 45);
		requestBody.put("emailId", "mark@abc.com");
		requestBody.put("mobileNo", 9876543210L);
		requestBody.put("semester", 8);
		requestBody.put("department", "cse");

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(requestBody)
				.post("http://localhost:8080/api/v1/student")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 200, but Found : " + response.getStatusCode()), response.getStatusCode() == 200);
	}
	
//	EXAMPLE-2
	@Test
	public void postToApplicationUsingGson() {
		
		System.out.println("Starting postToApplicationUsingGson");
		
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("name", "Mark2");
		requestBody.addProperty("age", 50);
		requestBody.addProperty("emailId", "mark2@abc.com");
		requestBody.addProperty("mobileNo", 9876543210L);
		requestBody.addProperty("semester", 8);
		requestBody.addProperty("department", "cse");
		
		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(requestBody)
				.post("http://localhost:8080/api/v1/student")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 200, but Found : " + response.getStatusCode()), response.getStatusCode() == 200);
	}
	
	@Test
	public void postToApplicationUsingPojo() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting postToApplicationUsingPojo");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("Mark3");
		newStudent.setAge(34);
		newStudent.setEmailId("mark3@abc.com");
		newStudent.setMobileNo(87656786567L);
		newStudent.setSemester(4);
		newStudent.setDepartment("computers");

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.post("http://localhost:8080/api/v1/student")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 200, but Found : " + response.getStatusCode()), response.getStatusCode() == 200);
		
//		ErrorDetails error = response.getBody().as(ErrorDetails.class);
//		System.out.println(error.getStatus());
//		System.out.println(response.asString());
	}
	
//	@Test
//	public void postExistingToApplicationUsingPojoAndParseErrorMessage() {
//		
//		System.out.println("Starting postToApplicationUsingPojo");
//		
//		Student student = new Student();
//		student.setName("Mark5");
//		student.setAge(32);
//		student.setEmailId("mark4@abc.com");
//		student.setMobileNo(8765478987L);
//		student.setDepartment("cse");
//		student.setSemester(6);
//
//		Response response = RestAssured
//				.given()
//				.auth()
//				.basic("user", "password")
//				.contentType("application/json")
//				.accept("application/json")
//				.body(student)
//				.post("http://localhost:8080/api/v1/student")
//				.andReturn();
//		Assert.assertTrue(("Expected : 404, but Found : " + response.getStatusCode()), response.getStatusCode() == 404);
//		
//		ErrorDetails error = response.getBody().as(ErrorDetails.class);
//		System.out.println(error.getStatus());
//	}
//	
//	@Test
//	public void postExistingToApplicationUsingPojoAndParseErrorMessageU() {
//		
//		System.out.println("Starting postToApplicationUsingPojo");
//		
//		Student student = new Student();
//		student.setName("Mark5");
//		student.setAge(32);
//		student.setEmailId("mark4@abc.com");
//		student.setMobileNo(8765478987L);
//		student.setDepartment("cse");
//		student.setSemester(6);
//
//		Response response = RestAssured
//				.given()
//				.auth()
//				.basic("user", "password")
//				.contentType("application/json")
//				.accept("application/json")
//				.body(student)
//				.post("http://localhost:8080/api/v1/student")
//				.andReturn();
//		Assert.assertTrue(("Expected : 404, but Found : " + response.getStatusCode()), response.getStatusCode() == 404);
//		
//		ErrorDetails error = response.getBody().as(ErrorDetails.class);
//		System.out.println(error.getStatus());
//	}
	
	@Test
	public void postToApplicationUsingPojoAndCheck405() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting postToApplicationUsingPojoAndCheck405");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("Mark4");
		newStudent.setAge(34);
		newStudent.setEmailId("mark3@abc.com");
		newStudent.setMobileNo(87656786567L);
		newStudent.setSemester(4);
		newStudent.setDepartment("computers");

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.post("http://localhost:8080/api/v1/students")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 405, but Found : " + response.getStatusCode()), response.getStatusCode() == 405);
		
//		ErrorDetails error = response.getBody().as(ErrorDetails.class);
//		System.out.println(error.getStatus());
//		System.out.println(response.asString());
	}
	
	@Test
	public void postToApplicationUsingPojoAndCheck415() { // POJO -> Plain Old Java Objects
		
		boolean isTestPassed = false;
		try {
			System.out.println("Starting postToApplicationUsingPojoAndCheck405");
			
			//Let's use the student pojo we created -
			Student newStudent = new Student();
			newStudent.setName("Mark5");
			newStudent.setAge(34);
			newStudent.setEmailId("mark3@abc.com");
			newStudent.setMobileNo(87656786567L);
			newStudent.setSemester(4);
			newStudent.setDepartment("computers");

			Response response = RestAssured
					.given()
					.auth()
					.basic("user", "password")
					.contentType("text/plain")
					.accept("application/json")
					.body(newStudent)
					.post("http://localhost:8080/api/v1/student")
					.andReturn();
			System.out.println("The Response message is : " + response.asString());
			System.out.println("The Response status code is : " + response.getStatusCode());
			Assert.assertTrue(("Expected : 200, but Found : " + response.getStatusCode()), response.getStatusCode() == 200);
//			System.out.println("Test cases : postToApplicationUsingPojoAndCheck415 is successful");
			
		} catch(Exception e) {
//			e.printStackTrace();
			String error = e.getMessage();
			Assert.assertTrue(error.contains("text/plain"));
			isTestPassed = true;
		}
		Assert.assertTrue("Test failed", isTestPassed);
	}
	
	@Test
	public void putToApplicationUsingPojo() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting putToApplicationUsingPojo");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("Mark4");
		newStudent.setAge(34);
		newStudent.setEmailId("mark4_updated@abc.com");
		newStudent.setMobileNo(87656786567L);
		newStudent.setSemester(4);
		newStudent.setDepartment("computers_updated");

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.put("https://e702f69f1ebb.ngrok.io/api/v1/student")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 200, but Found : " + response.getStatusCode()), response.getStatusCode() == 200);
		
//		ErrorDetails error = response.getBody().as(ErrorDetails.class);
//		System.out.println(error.getStatus());
//		System.out.println(response.asString());
	}
	
	@Test
	public void deleteAStudentFromTheApplication() {

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.delete("http://localhost:8080/api/v1/student/Mark3")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
	}
	
	@Test
	public void getAllStudents() {

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.get("http://localhost:8080/api/v1/students")
				.andReturn();
		System.out.println("The Response message is : " + response.asString());
	}
}

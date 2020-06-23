package com.jtraining.restassured;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jtraining.restassured.model2.CustomResponse;
import com.jtraining.restassured.model2.Student;
import com.jtraining.restassured.model2.Students;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredValidationCheck {
	
	@Test
	public void postToApplicationUsingPojoAndHamcrestValidation() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting postToApplicationUsingPojo");
		
		List<String> hobbiesList = new ArrayList<String>();
		hobbiesList.add("Coding");
		hobbiesList.add("Gaming");
		hobbiesList.add("Playing Cricket");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("User3");
		newStudent.setAge(34);
		newStudent.setEmailId("user3@abc.com");
		newStudent.setMobileNo(87656786567L);
		newStudent.setSemester(4);
		newStudent.setDepartment("cse");
		newStudent.setHobbies(hobbiesList);

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.post("http://localhost:8080/api/v1/student")
				.andReturn();
		
		
//		response.then().assertThat().body("", hasItems("status","message"));
		if(true) {
			response.then().assertThat().body("status", equalTo("pass"));
			System.out.println("hamcrest validation success");
		}
		
//		response.then().assertThat().body("message", containsString("Successfully"));
		
		System.out.println("The Response message is : " + response.asString());
		System.out.println("The Response status code is : " + response.getStatusCode());
		Assert.assertTrue(("Expected : 200, but Found : " + response.getStatusCode()), response.getStatusCode() == 200);
		
//		ErrorDetails error = response.getBody().as(ErrorDetails.class);
//		System.out.println(error.getStatus());
//		System.out.println(response.asString());
	}
	
	@Test
	public void postToApplicationAndValidateUsingPojoForFailureCase() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting postToApplicationUsingPojo");
		
		List<String> hobbiesList = new ArrayList<String>();
		hobbiesList.add("Coding");
		hobbiesList.add("Gaming");
		hobbiesList.add("Playing Cricket");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("User3");
		newStudent.setAge(34);
		newStudent.setEmailId("user3@abc.com");
		newStudent.setMobileNo(87656786567L);
		newStudent.setSemester(4);
		newStudent.setDepartment("cse");
		newStudent.setHobbies(hobbiesList);

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.post("http://localhost:8080/api/v1/student")
				.andReturn();
		
		CustomResponse customResponse = response.as(CustomResponse.class);
		
		String actualStatus = customResponse.getStatus();
		String actualMessage = customResponse.getMessage();
		
		String expectedStatus = "fail";
		String expectedMessage = "already available";
		
		System.out.println("Status from API is : " + actualStatus);
		System.out.println("Message from API is : " + actualMessage);
		
		Assert.assertTrue("Status validation failed", actualStatus.equalsIgnoreCase(expectedStatus));//Not recommended, try using assertEquals
		Assert.assertEquals("Status validation failed", expectedStatus, actualStatus);
		Assert.assertTrue("Message validation failed", actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void postToApplicationAndValidateUsingPojoForPositiveCase() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting postToApplicationUsingPojo");
		
		List<String> hobbiesList = new ArrayList<String>();
		hobbiesList.add("Coding");
		hobbiesList.add("Gaming");
		hobbiesList.add("Playing Cricket");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("User2");
		newStudent.setAge(36);
		newStudent.setEmailId("user2@abc.com");
		newStudent.setMobileNo(8765678656L);
		newStudent.setSemester(8);
		newStudent.setDepartment("cse");
		newStudent.setHobbies(hobbiesList);

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.post("http://localhost:8080/api/v1/student")
				.andReturn();
		
		CustomResponse customResponse = response.as(CustomResponse.class);
		
		String actualStatus = customResponse.getStatus();
		String actualMessage = customResponse.getMessage();
		
		String expectedStatus = "pass";
		String expectedMessage = "Successfully";
		
		System.out.println("Status from API is : " + actualStatus);
		System.out.println("Message from API is : " + actualMessage);
		
		Assert.assertTrue("Status validation failed", actualStatus.equalsIgnoreCase(expectedStatus));//Not recommended, try using assertEquals
		Assert.assertEquals("Status validation failed", expectedStatus, actualStatus);
		Assert.assertTrue("Message validation failed", actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void searchAStudentAndValidate() {
		
		List<String> hobbiesList = new ArrayList<String>();
		hobbiesList.add("Coding");
		hobbiesList.add("Gaming");
		hobbiesList.add("Playing Cricket");
		
		Student expectedStudent = new Student();
		expectedStudent.setName("User4");
		expectedStudent.setAge(36);
		expectedStudent.setDepartment("cse");
		expectedStudent.setEmailId("user4@abc.com");
		expectedStudent.setSemester(8);
		expectedStudent.setMobileNo(8765678656L);
		expectedStudent.setHobbies(hobbiesList);

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.get("http://localhost:8080/api/v1/student/user4")
				.andReturn();
		
		Student actualStudent = response.as(Student.class);
		System.out.println(actualStudent.getName());
		System.out.println(actualStudent.getAge());
		System.out.println(actualStudent.getDepartment());
		System.out.println(actualStudent.getEmailId());
		System.out.println(actualStudent.getSemester());
		System.out.println(actualStudent.getMobileNo());
		System.out.println(actualStudent.getHobbies());
		
		Assert.assertEquals(String.format("Name validation failed. Expected '%s', but found '%s'", expectedStudent.getName(), actualStudent.getName()), expectedStudent.getName(), actualStudent.getName());
		Assert.assertEquals(String.format("Age validation failed. Expected '%s', but found '%s'", expectedStudent.getAge(), actualStudent.getAge()), expectedStudent.getAge(), actualStudent.getAge());
		Assert.assertEquals(String.format("Department validation failed. Expected '%s', but found '%s'", expectedStudent.getDepartment(), actualStudent.getDepartment()), expectedStudent.getDepartment(), actualStudent.getDepartment());
		Assert.assertEquals(String.format("EmailId validation failed. Expected '%s', but found '%s'", expectedStudent.getEmailId(), actualStudent.getEmailId()), expectedStudent.getEmailId(), actualStudent.getEmailId());
		Assert.assertEquals(String.format("Semester validation failed. Expected '%s', but found '%s'", expectedStudent.getSemester(), actualStudent.getSemester()), expectedStudent.getSemester(), actualStudent.getSemester());
		Assert.assertEquals(String.format("Mobile Number validation failed. Expected '%s', but found '%s'", expectedStudent.getMobileNo(), actualStudent.getMobileNo()), expectedStudent.getMobileNo(), actualStudent.getMobileNo());
		Assert.assertEquals(String.format("Hobbies validation failed. Expected '%s', but found '%s'", expectedStudent.getHobbies(), actualStudent.getHobbies()), expectedStudent.getHobbies(), actualStudent.getHobbies());
	}
	
	@Test
	public void putToApplicationAndValidateUsingPojoForPositiveCase() { // POJO -> Plain Old Java Objects
		
		System.out.println("Starting postToApplicationUsingPojo");
		
		List<String> hobbiesList = new ArrayList<String>();
		hobbiesList.add("Coding");
		hobbiesList.add("Gaming");
		hobbiesList.add("Playing Cricket");
		hobbiesList.add("Music");
		
		//Let's use the student pojo we created -
		Student newStudent = new Student();
		newStudent.setName("User4");
		newStudent.setAge(36);
		newStudent.setEmailId("user4_updated@abc.com");
		newStudent.setMobileNo(8765678656L);
		newStudent.setSemester(8);
		newStudent.setDepartment("cse");
		newStudent.setHobbies(hobbiesList);

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.contentType("application/json")
				.accept("application/json")
				.body(newStudent)
				.put("http://localhost:8080/api/v1/student")
				.andReturn();
		
		CustomResponse customResponse = response.as(CustomResponse.class);
		
		String actualStatus = customResponse.getStatus();
		String actualMessage = customResponse.getMessage();
		
		String expectedStatus = "pass";
		String expectedMessage = "Successfully";
		
		System.out.println("Status from API is : " + actualStatus);
		System.out.println("Message from API is : " + actualMessage);
		
		Assert.assertTrue("Status validation failed", actualStatus.equalsIgnoreCase(expectedStatus));//Not recommended, try using assertEquals
		Assert.assertEquals("Status validation failed", expectedStatus, actualStatus);
		Assert.assertTrue("Message validation failed", actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void deleteStudentAndValidate() {

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.delete("http://localhost:8080/api/v1/student/user4")
				.andReturn();
		
		CustomResponse customResponse = response.as(CustomResponse.class);
		
		String actualStatus = customResponse.getStatus();
		String actualMessage = customResponse.getMessage();
		
		String expectedStatus = "pass";
		String expectedMessage = "Successfully";
		
		System.out.println("Status from API is : " + actualStatus);
		System.out.println("Message from API is : " + actualMessage);
		
		Assert.assertTrue("Status validation failed", actualStatus.equalsIgnoreCase(expectedStatus));//Not recommended, try using assertEquals
		Assert.assertEquals("Status validation failed", expectedStatus, actualStatus);
		Assert.assertTrue("Message validation failed", actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void deleteAllStudentAndValidate() {

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.delete("http://localhost:8080/api/v1/students")
				.andReturn();
		
		System.out.println(response.asString());
		
		CustomResponse customResponse = response.as(CustomResponse.class);
		
		String actualStatus = customResponse.getStatus();
		String actualMessage = customResponse.getMessage();
		
		String expectedStatus = "pass";
		String expectedMessage = "Successfully";
		
		System.out.println("Status from API is : " + actualStatus);
		System.out.println("Message from API is : " + actualMessage);
		
		Assert.assertTrue("Status validation failed", actualStatus.equalsIgnoreCase(expectedStatus));//Not recommended, try using assertEquals
		Assert.assertEquals("Status validation failed", expectedStatus, actualStatus);
		Assert.assertTrue("Message validation failed", actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void SearchAllStudentsAndValidateAll() {
		
		List<String> hobbiesList1 = new ArrayList<String>();
		hobbiesList1.add("Coding");
		hobbiesList1.add("Gaming");
		
		Student expectedStudent1 = new Student();
		expectedStudent1.setName("User1");
		expectedStudent1.setAge(36);
		expectedStudent1.setDepartment("cse");
		expectedStudent1.setEmailId("user1@abc.com");
		expectedStudent1.setSemester(8);
		expectedStudent1.setMobileNo(8765678656L);
		expectedStudent1.setHobbies(hobbiesList1);
		
		List<String> hobbiesList2 = new ArrayList<String>();
		hobbiesList2.add("Coding");
		hobbiesList2.add("Gaming");
		hobbiesList2.add("Playing Cricket");
		
		Student expectedStudent2 = new Student();
		expectedStudent2.setName("User2");
		expectedStudent2.setAge(36);
		expectedStudent2.setDepartment("cse");
		expectedStudent2.setEmailId("user2@abc.com");
		expectedStudent2.setSemester(8);
		expectedStudent2.setMobileNo(8765678656L);
		expectedStudent2.setHobbies(hobbiesList2);
		
		List<Student> expectedStudentsList = new ArrayList<Student>();
		expectedStudentsList.add(expectedStudent1);
		expectedStudentsList.add(expectedStudent2);

		Response response = RestAssured
				.given()
				.auth()
				.basic("user", "password")
				.get("http://localhost:8080/api/v1/students")
				.andReturn();
		
		System.out.println(response.asString());
		
		Students students = response.as(Students.class);
		
		List<Student> actualStudentsList = students.getStudents();
		
		for(int i = 0; i < actualStudentsList.size(); i++) {
			System.out.println(String.format("Currently validating user : '%s' across the expected value of '%s'", actualStudentsList.get(i).getName(), expectedStudentsList.get(i).getName()));
			Assert.assertEquals(String.format("Name validation failed. Expected '%s', but found '%s'", expectedStudentsList.get(i).getName(), actualStudentsList.get(i).getName()), expectedStudentsList.get(i).getName(), actualStudentsList.get(i).getName());
//			Assert.assertEquals(String.format("Name validation failed. Expected '%s', but found '%s'", expectedStudent2.getName(), actualStudent.getName()), expectedStudent2.getName(), actualStudent.getName());
//			Assert.assertEquals(String.format("Age validation failed. Expected '%s', but found '%s'", expectedStudent2.getAge(), actualStudent.getAge()), expectedStudent2.getAge(), actualStudent.getAge());
//			Assert.assertEquals(String.format("Department validation failed. Expected '%s', but found '%s'", expectedStudent2.getDepartment(), actualStudent.getDepartment()), expectedStudent2.getDepartment(), actualStudent.getDepartment());
//			Assert.assertEquals(String.format("EmailId validation failed. Expected '%s', but found '%s'", expectedStudent2.getEmailId(), actualStudent.getEmailId()), expectedStudent2.getEmailId(), actualStudent.getEmailId());
//			Assert.assertEquals(String.format("Semester validation failed. Expected '%s', but found '%s'", expectedStudent2.getSemester(), actualStudent.getSemester()), expectedStudent2.getSemester(), actualStudent.getSemester());
//			Assert.assertEquals(String.format("Mobile Number validation failed. Expected '%s', but found '%s'", expectedStudent2.getMobileNo(), actualStudent.getMobileNo()), expectedStudent2.getMobileNo(), actualStudent.getMobileNo());
//			Assert.assertEquals(String.format("Hobbies validation failed. Expected '%s', but found '%s'", expectedStudent2.getHobbies(), actualStudent.getHobbies()), expectedStudent2.getHobbies(), actualStudent.getHobbies());
		}
		}
		



}

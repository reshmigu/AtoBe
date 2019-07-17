package com.test;
import static org.testng.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import  org.hamcrest.Matchers;

public class SampleProject extends Matchers{
//GET HTTP Protocol which is used to request data from a specific resource
	
//@Test
 public void retrieveSpecificEmployee() {
/**
 * Given Accept the content in JSON Format
 * AND name as ABC
 * And salary as 2000
 * When i performed the POST Request
 * Then the status code 200 ok should be returned
 * And the response content should have name as ABC
 */
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		HashMap<String, String> postContent = new HashMap<String, String>();
		{
		
			postContent.put("name","ABC");
			postContent.put("salary","2000");
			postContent.put("age","20");
	
			ValidatableResponse response = null;

			try {
				response = RestAssured.given()
						.contentType(ContentType.JSON).with()
						.body(postContent).when()
						.post("/create")
						.then().body("name", hasItem("ABC")).statusCode(200);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Response :" + response);

		}
}
// POST methods---is used to send data to a server to create the resource. 
 
 @Test
	public void createEmployee() {

		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		String requestBody = "{\n" +
				"  \"name\": \"ABC\",\n" +
				"  \"salary\": \"5000\",\n" +
				"  \"age\": \"20\"\n" +
				"}";


		Response response = null;

		try {
			response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestBody)
					.post("/create");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Response :" + response.asString());
		System.out.println("Status Code :" + response.getStatusCode());
		System.out.println("Does Reponse contains 'ABC'? :" + response.asString().contains("ABC"));


		assertEquals(200, response.getStatusCode());
	}
	
//	An update operation will happen if the Request-URI;PUT is idempotent means if you try to make a request multiple times, 
//	it would result in the same output as it would have no effect.
 @Test
	public void updateEmployee() {

		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		String requestBody = "{\r\n" + 
				" \"name\":\"put_test_employee\",\r\n" + 
				" \"salary\":\"1123\",\r\n" + 
				" \"age\":\"23\"\r\n" + 
				"}";


		Response response = null;

		try {
			response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestBody)
					.put("/update/4710");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Response :" + response.asString());
		System.out.println("Status Code :" + response.getStatusCode());
		System.out.println("Does Reponse contains 'put_test_employee'? :" + response.asString().contains("put_test_employee"));
		System.out.println("Does Reponse contains 'ABC'? :" + response.asString().contains("ABC"));
		


		assertEquals(200, response.getStatusCode());
	}
 
//  it is used to ‘Delete’ any resource specified
 @Test
	public void deleteEmployee() {

		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		Response response = null;

		try {
			response = RestAssured.given()
					.contentType(ContentType.JSON)
					.delete("/delete/11400");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Response :" + response.asString());
		System.out.println("Status Code :" + response.getStatusCode());
		System.out.println("Does Reponse contains 'put_test_employee'? :" + response.asString().contains("put_test_employee"));

	}
	
//	POJO (Plain Old Java Object) and we need to send it to the API call
 @Test
	public void testSerialization() {
		
		Response response = null;
		
		Student student = new Student();
		student.setAge(10);
		student.setWeight(100);
		student.setHome("India");
		
		
		response = RestAssured.given()
				              .contentType("application/json")
				              .body(student)
				              .when()
				              .post("http://www.thomas-bayer.com/restnames/countries.groovy");
		System.out.println("Response :" + response.asString());
		System.out.println("Status Code :" + response.getStatusCode());
		assertEquals(200, response.getStatusCode());
		System.out.println("Does Reponse contains 'Country-Name'? :" + response.asString().contains("Belgium"));
				            
	}

	//we have the API response and you would need to de-serialize it into a POJO
 	@Test
 	public void testDeSerialization() {
 		
 		Student student = RestAssured.get("http://www.thomas-bayer.com/restnames/countries.groovy").as(Student.class);
 		
 		System.out.println(student.toString());	
 		System.out.println("student :" + student.toString());
 		System.out.println("Does Reponse contains 'Country-Name'? :" + student.toString().contains("Belgium"));
 	}
 	
}
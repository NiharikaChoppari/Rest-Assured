package com.claraanalytics.main;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	Response response;
	JsonPath jsonpath;
	RequestSpecification request = RestAssured.given();

	public Response login(String username, String password, String redirectToURL, String reqURL) {

		response = request.header("content-type", "application/x-www-form-urlencoded").param("username", username)
				.param("password", password).param("redirectTo", redirectToURL).post(reqURL);
		return response;
	}

	public Response post(String reqBody, String reqURL, String contentType, String JWTTOKEN) {
		response = request.header("content-type", contentType).header("cookie", JWTTOKEN).body(reqBody).post(reqURL);
		return response;
	}

	public Response put(String reqBody, String reqURL, String contentType, String JWTTOKEN) {
		response = request.header("content-type", contentType).header("cookie", JWTTOKEN).body(reqBody).put(reqURL);
		return response;
	}

	public Response get(String reqURL, String contentType, String JWTTOKEN) {
		response = request.header("content-type", contentType).header("cookie", JWTTOKEN).get(reqURL);
		return response;
	}

	public Response delete(String reqURL, String contentType, String JWTTOKEN) {
		response = request.header("content-type", contentType).header("cookie", JWTTOKEN).delete(reqURL);
		return response;
	}

	public JsonPath parseJSON(Response response) {
		jsonpath = response.jsonPath();
		return jsonpath;
	}
}

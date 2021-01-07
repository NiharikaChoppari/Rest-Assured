package com.claraanalytics.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class RoleTest {

	Response response;
	String role_ID;
	JsonPath jsonpath;
	String URL = "";
	String username = "";
	String password = "";
	String JWTTOKEN;
	String permissionId1 = "";
	String permissionId2 = "";
	String serviceId = "";

	@Test(priority = 1)
	void POST_Login() {

		response = given().header("Content-Type", "application/x-www-form-urlencoded").param("username", username)
				.param("password", password).param("redirectTo", URL).post(URL + "/login");

		JWTTOKEN = "JWTTOKEN=" + response.getCookie("JWTTOKEN");
		Assert.assertEquals(response.getStatusCode(), 302, "Status code is not 302");
	}

	@Test(priority = 2)
	void POST_CreateRole() {

		response = given().header("Content-Type", "application/json").header("cookie", JWTTOKEN)
				.body("{\"name\":\"Test_Role\",\"permissionIds\":[\"" + permissionId1 + "\"]}")
				.post(URL + "/api/v1/service/" + serviceId + "/role");

		System.out.println(response.getBody().asString());
		jsonpath = response.jsonPath();
		role_ID = jsonpath.get("data");
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
	}

	@Test(priority = 3)
	void PUT_UpdateRole() {

		response = given().header("Content-Type", "application/json").header("cookie", JWTTOKEN)
				.body("{\"permissionIds\":[\"" + permissionId1 + "\",\"" + permissionId2 + "\"]}")
				.put(URL + "/api/v1/service/" + serviceId + "/role/" + role_ID);

		System.out.println(response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
	}

	@Test(priority = 4)
	void GET_Role() {
		response = given().header("Content-Type", "application/json").header("cookie", JWTTOKEN)
				.get(URL + "/api/v1/service/" + serviceId + "/role/" + role_ID);

		System.out.println(response.getBody().asString());
		jsonpath = response.jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
	}

	@Test(priority = 5)
	void DELETE_Role() {

		response = given().contentType(ContentType.JSON).header("Content-Type", "application/json")
				.header("cookie", JWTTOKEN).delete(URL + "/api/v1/service/" + serviceId + "/role/" + role_ID);

		System.out.println(response.getBody().asString());
		jsonpath = response.jsonPath();
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		Assert.assertEquals(jsonpath.get("data"), "Test_Role deleted successfully from service " + serviceId,
				"Role couldnot be deleted");
	}
}

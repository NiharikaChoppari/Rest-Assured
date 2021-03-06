package com.claraanalytics.test;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.claraanalytics.main.Utils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RoleTest extends BaseTest{

	Response JSONresponse;
	String baseURI;
	String role_ID;
	JsonPath jsonpath;
	String reqURL;
	String JWTTOKEN;
	String permissionId1;
	String permissionId2;
	String serviceId;
	String requestPayload;
	String contentType = "application/json";

	Utils obj = new Utils();

	@BeforeTest()
	void token() {
		baseURI = BaseTest.baseURI;
		JWTTOKEN = BaseTest.JWTTOKEN;
		serviceId = BaseTest.serviceId;
		permissionId1 = BaseTest.permissionId1;
		permissionId2 = BaseTest.permissionId2;
	}

	@Test(priority = 1)
	void createRole() {

		requestPayload = "{\"name\":\"Test_Role\",\"permissionIds\":[\"" + permissionId1 + "\"]}";
		reqURL = baseURI + "/api/v1/service/" + serviceId + "/role";

		JSONresponse = obj.post(requestPayload, reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		role_ID = jsonpath.get("data");
		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		System.out.println("role creation");
	}

	@Test(priority = 2)
	void updateRole() {

		requestPayload = "{\"permissionIds\":[\"" + permissionId1 + "\",\"" + permissionId2 + "\"]}";
		reqURL = baseURI + "/api/v1/service/" + serviceId + "/role/" + role_ID;

		JSONresponse = obj.put(requestPayload, reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		System.out.println("role updation");

	}

	@Test(priority = 3)
	void getRole() {

		reqURL = baseURI + "/api/v1/service/" + serviceId + "/role/" + role_ID;

		JSONresponse = obj.get(reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		System.out.println("role details");

	}

	@Test(priority = 4)
	void deleteRole() {

		reqURL = baseURI + "/api/v1/service/" + serviceId + "/role/" + role_ID;

		JSONresponse = obj.delete(reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		Assert.assertEquals(jsonpath.get("data"), "Test_Role deleted successfully from service " + serviceId,
				"Role couldnot be deleted");
		System.out.println("role deletion");

	}
}

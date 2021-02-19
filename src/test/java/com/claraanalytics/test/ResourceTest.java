package com.claraanalytics.test;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.claraanalytics.main.Utils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResourceTest extends BaseTest {

	Response JSONresponse;
	String baseURI;
	String resource_ID;
	JsonPath jsonpath;
	String reqURL;
	String JWTTOKEN;
	String serviceId;
	String requestPayload;
	String contentType = "application/json";

	Utils obj = new Utils();

	@BeforeTest()
	void token() {
		baseURI = BaseTest.baseURI;
		JWTTOKEN = BaseTest.JWTTOKEN;
		serviceId = BaseTest.serviceId;
	}

	@Test(priority = 1)
	void createResource() {

		requestPayload = "{\"name\":\"ResourceTest\",\"permissionInfo\":[{\"name\":\"default\",\"bitMask\":15}]}";
		reqURL = baseURI + "/api/v1/service/" + serviceId + "/resource";

		JSONresponse = obj.post(requestPayload, reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		resource_ID = jsonpath.get("data");
		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		System.out.println("Resource creation");
	}

	@Test(priority = 2)
	void updateResource() {

		requestPayload = "{\"name\":\"Test\",\"permissionInfo\":[{\"id\":\"" + resource_ID
				+ "\",\"createdBy\":\"niharika.c_abc\",\"createdOn\":1611309709000,\"updatedBy\":\"niharika.c_abc\",\"updatedOn\":1611309709000,\"bitMask\":15,\"name\":\"default\"}]}";
		reqURL = baseURI + "/api/v1/service/" + serviceId + "/resource/" + resource_ID;

		JSONresponse = obj.put(requestPayload, reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		resource_ID = jsonpath.get("data");
		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		System.out.println("Resource updation");
	}

	@Test(priority = 3)
	void getResource() {

		reqURL = baseURI + "/api/v1/service/" + serviceId + "/resource/" + resource_ID;

		JSONresponse = obj.get(reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		System.out.println("Resource details");
	}

	@Test(priority = 4)
	void deleteResource() {

		reqURL = baseURI + "/api/v1/service/" + serviceId + "/resource/" + resource_ID;

		JSONresponse = obj.delete(reqURL, contentType, JWTTOKEN);
		jsonpath = obj.parseJSON(JSONresponse);

		System.out.println(JSONresponse.asString());
		Assert.assertEquals(JSONresponse.getStatusCode(), 200, "Status code is not 200");
		Assert.assertEquals(jsonpath.get("status"), "SUCCESS", "Response status is not SUCCESS");
		Assert.assertEquals(jsonpath.get("data"), "Test deleted successfully", "Resource couldnot be deleted");
		System.out.println("Resource deletion");
	}

}

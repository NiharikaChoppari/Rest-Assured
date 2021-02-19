package com.claraanalytics.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import com.claraanalytics.main.Utils;
import io.restassured.response.Response;

public class BaseTest {

	Response JSONresponse;
	String reqURL;
	static String baseURI;
	static String username;
	static String password;
	static String serviceId;
	static String permissionId1;
	static String permissionId2;
	public static String JWTTOKEN;

	static Properties prop;

	@BeforeSuite
	void login() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./config/config.properties");
			prop.load(ip);
			baseURI = prop.getProperty("baseURI");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			serviceId = prop.getProperty("serviceId");
			permissionId1 = prop.getProperty("permissionId1");
			permissionId2 = prop.getProperty("permissionId2");

		} catch (IOException e) {
			e.printStackTrace();
		}
		Utils obj = new Utils();
		reqURL = baseURI + "/login";

		JSONresponse = obj.login(username, password, baseURI, reqURL);
		JWTTOKEN = "JWTTOKEN=" + JSONresponse.getCookie("JWTTOKEN");
		Assert.assertEquals(JSONresponse.getStatusCode(), 302, "Status code is not 302");
	}
}

package files;


import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI="http://localhost:8080";
		
		// to fetch session id details, there are two methods
		//1. sesssion filter
		
		SessionFilter session= new SessionFilter();
		// logging into JIRA
		
		String response=given().header("Content-Type","application/json").body("{ \"username\": \"christinaprem88\", \"password\": \"Isai4110@\" }")
		.filter(session).when().post("/rest/auth/1/session")
		.then().extract().response().asString();
		
		//2. JSONPath
	/*	JsonPath js= ReuseableMethods.rawToJason(response);
		String session=js.getString("session.value");
		String sesId=js.getString("session.name");
		
		String sessionId=sesId+"="+session;
		
		*/
		
		// adding comment
		
		given().pathParam("id", "10006").log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \"Hei I have commented from REST API Eclipse\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session)
		.when().post("/rest/api/2/issue/{id}/comment")
		.then().assertThat().statusCode(201);
		
		//add attachment
		
		given().header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").filter(session).pathParam("id", "10006")
		.multiPart("file",new File("jira.txt"))
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		

	}

}

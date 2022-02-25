import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReuseableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) {
		
		//given, when, then
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//Add Place
		String response=given().log().all().queryParams("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.addPlace()).when().log().all().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js= new JsonPath(response);
		String placeID=js.getString("place_id");
		System.out.println("Place ID is :"+placeID);
		
		//Update place
		
		given().log().all().queryParams("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\"116 Keelavaikolkara\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get place
		
		
		
		String response1= given().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1= ReuseableMethods.rawToJason(response1);
		String address_new=js1.getString("address");
		Assert.assertEquals("116 Keelavaikolkara", address_new);

	}

}

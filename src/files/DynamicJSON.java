package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJSON {
	
	@Test(dataProvider = "BooksData")
	public void AddBook(String isbn, String aisle) {
	
		
		RestAssured.baseURI="http://216.10.245.166";
		String response= given().header("Content-Type","application/json")
		.body(payload.AddBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		
		System.out.println("Response is "+response);
		
		JsonPath js= ReuseableMethods.rawToJason(response);
		String id=js.getString("ID");
		
	//	JsonPath js= ReuseableMethods.rawToJason(response);
		//String id=js.get("id");
		
	}
	
	
	
	@Test(dataProvider = "DelData")
	public void DeleteBook(String id) {
		String response= given().header("Content-Type","application/json")
				.body(payload.DeleteBook(id))
				.when().post("/Library/DeleteBook.php")
				.then().log().all().statusCode(200).extract().response().asString();
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		//array - collection of elements
		//mulit dimensional arrays - collection of arrays
		return new Object[][] {
			{"qr","117"},{"arq","147"}, {"gsq","187"}
			};
	}
/*	
	@DataProvider(name=DelData)
	public String[] getDat() {
		
		return new String[] {}
	}
*/
}

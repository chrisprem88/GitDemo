package files;

import io.restassured.path.json.JsonPath;

public class ReuseableMethods {
	
	public static JsonPath rawToJason(String response) {
		
		JsonPath js= new JsonPath(response);
		return js;
		
	}

}

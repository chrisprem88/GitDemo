package files;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuth2 {

	public static void main(String[] args) throws InterruptedException {
		
		// get access code
	/*	
		System.setProperty("webdriver.chrome.driver","D:\\Eclipse\\chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("christinaprem88");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Joel2252@");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		String accessCodeURL=driver.getCurrentUrl();
		String code[]= accessCodeURL.split("=");
		System.out.println(code);
		*/
		
		
	//	String accessCode= given().
		
		String[] expectedCourses= {"Selenium Webdriver Java","Cypress","Protractor"};
		
		
		//get access token'
		
		String CodeResponse =given().urlEncodingEnabled(false).queryParams("code","4%2F0AX4XfWhFTn0OZgCbbikBfadSjV3Jo6xRGet8r2BZevOqZE1x7YDzepjhjjj-4C-oAIdRmQ")
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		
		JsonPath js= new JsonPath(CodeResponse);
		String accessToken=js.getString("access_token");
		
		//Get course details
		
		GetCourse gc= given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<API> apiCourses= gc.getCourses().getApi();
		
		for(int i=0;i<apiCourses.size();i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
			
		}
		
		//Dynamic array list Hence not using array and using arraylist
		
		ArrayList<String> a= new ArrayList<>();
		
		List<WebAutomation> wa= gc.getCourses().getWebAutomation();
		for(int j=0;j<wa.size();j++) {
			a.add(wa.get(j).getCourseTitle());
		}
		
		List<String> expectedList= Arrays.asList(expectedCourses);
		Assert.assertTrue(a.equals(expectedList));
		
		//System.out.println(response);
		
	}

}

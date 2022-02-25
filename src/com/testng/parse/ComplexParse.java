package com.testng.parse;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexParse {

	
	@Test
	public void sumOfCourse() {
		
		JsonPath js= new JsonPath(payload.coursePrice());
		int count= js.getInt("courses.size()");
		
		int sum=0;
		for(int i=0;i<count;i++) {
			int RPACopies1= js.getInt("courses["+i+"].copies");
			int price= js.getInt("courses["+i+"].price");
			int amount= RPACopies1*price;
			System.out.println(amount );
			sum=sum+amount;
			
		}
		System.out.println(sum);
		
		int purchaseAmt= js.getInt("dashboard.purchaseAmount");
		if(sum==purchaseAmt) 
			System.out.println("same amount");
		else
			System.out.println("wrong amount");
		
	}
}

import files.ReuseableMethods;
import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJSONParse {
	
	public static void main(String args[]) {
		
		JsonPath js= new JsonPath(payload.coursePrice());
		int count= js.getInt("courses.size()");
		System.out.println(count);
		
		int totalAmt=js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Amount "+totalAmt);
		
		String titleOfFirstIndex= js.getString("courses[0].title");
		System.out.println("title Of First Index "+titleOfFirstIndex);
		
		for(int i=0;i<count;i++) {
			String course= js.getString("courses["+i+"].title");
			int price= js.getInt("courses["+i+"].price");
			System.out.println("Course : " +course+" price : "+price);
			
		}
		
		int RPACopies= js.getInt("courses[2].copies");
		System.out.println(RPACopies);
		
		int sum=0;
		for(int i=0;i<count;i++) {
			int RPACopies1= js.getInt("courses["+i+"].copies");
			int price= js.getInt("courses["+i+"].price");
			int amount= RPACopies1*price;
		}
		
	}

}

import io.restassured.path.json.JsonPath;

public class JsonPayloadOps {

	public static void main(String[] args)
	{
		JsonPath js = new JsonPath(Payload.courseApi());
		// 1. Print No of courses returned by API
		int course_size=js.getInt("courses.size()");
		System.out.println(course_size);
		//2.Print Purchase Amount
		int course_purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println(course_purchaseAmt);
		//3. Print Title of the first course
		String textcourse = js.getString("courses[0].title");
		System.out.println(textcourse);
		//4. Print All course titles and their respective prices
		for(int i =0;i<course_size;i++)
		{
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		//5. Print no of copies sold by RPA Course
		for(int i =0;i<course_size;i++)
		{
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA"))
					{
			String no_of_copies = js.getString("courses["+i+"].copies");
			System.out.println("number of copies of RPA = "+no_of_copies);
					}
		}
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for(int i =0;i<course_size;i++)
		{
			int no_of_copies = js.getInt("courses["+i+"].copies");
			int prices = js.getInt("courses["+i+"].price");
			sum= sum+(no_of_copies*prices);
					}
		System.out.println("sum of copies = "+sum);
		if(sum==course_purchaseAmt)
		{
			System.out.println("sum matches -> thanks");
		}
		}
	

}

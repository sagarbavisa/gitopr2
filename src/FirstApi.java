import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.testng.Assert;
public class FirstApi 
{
public static void main(String[] args) {
		// TODO Auto-generated method stub	
	RestAssured.baseURI = "https://rahulshettyacademy.com";
	
	String response_value = given().queryParam("key","qaclick123").headers("Content-Type","application/json")
	 .body(Payload.payloadApi())
	 .when().post("/maps/api/place/add/json")
	  .then().log().all().body("scope", Matchers.equalTo("APP")).extract().response().asString();
	 System.out.println(response_value);
	 
	 JsonPath js = new JsonPath(response_value);
	 String placeid=js.getString("place_id");
	 System.out.println("place_id = "+js.getString("place_id"));
//---------------------------------------------------------------------------------------------//
	 String put_new_address = "70 Summer walk, USA";
	 given().queryParam("key", "qaclick123")
	 .body("{\r\n"
	 		+ "\"place_id\":\""+placeid+"\",\r\n"
	 		+ "\"address\":\""+put_new_address+"\",\r\n"
	 		+ "\"key\":\"qaclick123\"\r\n"
	 		+ "}\r\n"
	 		+ "")
	 .when().put("maps/api/place/update/json")
	 .then().log().all().assertThat().statusCode(200);
//----------------------------------------------------------------------------------------------//	 
	 String get_response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).body("accuracy",Matchers.equalTo("50")).extract().asString();
	 JsonPath js1 = new JsonPath(get_response);
	 String get_new_address = js1.getString("address");
	 Assert.assertEquals(put_new_address, get_new_address);
	 System.out.println("new address is "+get_new_address);
//----------------------------------------------------------------------------------------------//
		  given().log().all().queryParam("key", "qaclick123")
		  .body("{\r\n" +
		  "    \"place_id\":\""+placeid+"\"\r\n" + "}\r\n" + "")
		  .when().delete("maps/api/place/delete/json")
		  .then().log().all().assertThat().statusCode(200);
		 
	
	}

}

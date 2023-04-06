import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.hamcrest.*;

public class LibraryApiTest 
{
@Test
 public void postapi()
 {
	RestAssured.baseURI = "http://216.10.245.166";
	String postlibresponse = given().body(Payload.postlibload())
	.when().post("/Library/Addbook.php")
	.then().log().all().assertThat().statusCode(200).extract().asString();
	JsonPath js = new JsonPath(postlibresponse);
	String id1 = js.get("ID");
	String Msg = js.get("Msg");
 }
}

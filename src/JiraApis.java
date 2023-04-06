import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.hamcrest.*;
import org.testng.Assert;
public class JiraApis {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
//---------------------------------------login session -----------------------------------------------------------------------
		SessionFilter sessionfilt = new SessionFilter();
	String response =	given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"sagarbavisa\", \"password\": \"sagarbavisa\" }")
		.filter(sessionfilt)
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	JsonPath js = new JsonPath(response);
	String session_name = js.getString("session.name");
	String session_value = js.getString("session.value");
//--------------------------------------add comment --------------------------------------------------------------------------
	String comment = "this is a new restassured generated new comment by Sagar";
	String comment_response = given().pathParam("id", "10100").header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(sessionfilt)
		.when().post("/rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js1 = new JsonPath(comment_response);
		String id = js1.getString("id");
//--------------------------------------add attachments---------------------------------------------------------------------------
		given().header("Content-Type","multipart/form-data").header("X-Atlassian-Token","no-check").pathParam("id", "10100")
		.filter(sessionfilt).multiPart("file",new File("C://Users//sbavisa//eclipse-workspace//RestAssuredPrac//attach.txt"))
		.when().post("rest/api/2/issue/{id}/attachments")
		.then().assertThat().statusCode(200);
//---------------------------------------get issue----------------------------------------------------------------------------
	String response_issue = given().filter(sessionfilt).pathParam("id", "10101").queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{id}")
		.then().assertThat().statusCode(200).extract().response().asString();
	JsonPath js2 = new JsonPath(response_issue);
	int total_comments = js2.getInt("fields.comment.comments.size()");
	System.out.println(id);
	System.out.println(js2.get("fields.comment.comments[1].body").toString());
	for(int i=0;i<total_comments;i++)
	{
		String values = js2.get("fields.comment.comments["+i+"].id").toString();
		System.out.println(values);
		if(values.equals(id))
		{
			Assert.assertEquals(comment, js2.getString("fields.comment.comments["+i+"].body"));
			System.out.println(js2.getString("fields.comment.comments["+i+"].body"));
			
		}
	}
	}

}

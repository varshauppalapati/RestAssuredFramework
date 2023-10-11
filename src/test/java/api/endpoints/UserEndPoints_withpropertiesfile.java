package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//To perform CRUD operations

public class UserEndPoints_withpropertiesfile {

	//method created to get URL from properties file
	static ResourceBundle getURL()
	{
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}
	
	
	public static Response createUser(User payload) {
		
		String post_url = getURL().getString("post_url");
			
		Response response = given()
									.contentType(ContentType.JSON)
									.accept(ContentType.JSON)
									.body(payload)
							.when()
									.post(post_url);
		return response;
	}
	
	public static Response GetUser(String username) {
		
		Response response = given()
								.pathParam("username", username)
							.when()
								.get(Routes.get_url);
		return response;
	}
	
	public static Response UpdateUser(String username, User payload) {
		
		Response response = given()
									.contentType(ContentType.JSON)
									.accept(ContentType.JSON)
									.pathParam("username", username)
									.body(payload)
							.when()
									.put(Routes.update_url);
		return response;
	}
	
	public static Response DeleteUser(String username) {
		
		Response response = given()
								.pathParam("username", username)
							.when()
								.delete(Routes.delete_url);
		return response;
	}
}

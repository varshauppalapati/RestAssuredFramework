package api.test;

import com.github.javafaker.*;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import org.json.*;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		
		//data
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		
		//logs
		logger = LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void test_PostUser(){
		
		logger.info("*****************************creating a user**********************************");
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*****************************user is created**********************************");
	}
	
	@Test(priority=2)
	public void test_GetUserByName(){
		
		Response response = UserEndPoints.GetUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void test_UpdateUserByName(){
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		
		Response response = UserEndPoints.UpdateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after updation
		Response responseAfterUpdation = UserEndPoints.GetUser(this.userPayload.getUsername());
		responseAfterUpdation.then().log().body();
		
		Assert.assertEquals(responseAfterUpdation.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void test_DeleteUserByName(){
		
		Response response = UserEndPoints.DeleteUser(this.userPayload.getUsername());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after updation
		Response responseAfterUpdation = UserEndPoints.GetUser(this.userPayload.getUsername());
		responseAfterUpdation.then().log().all();
		Assert.assertEquals(responseAfterUpdation.jsonPath().get("type"), "error");
		Assert.assertEquals(responseAfterUpdation.jsonPath().get("message"), "User not found");
		
		
	}
	
}

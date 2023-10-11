package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.utilities.DataProviders;
import api.payload.User;
import io.restassured.response.Response;

public class DataDrivenTests {

	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class) //If data provider is in same class no need to give 3rd value
	public void Test_PostUser(String userID, String userName, String fName, String lName, String useremail, String pwd, String ph) // parameters hsould be in same order that of excel	
	{  
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority=2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void Test_DeleteUserByName(String userName) 
	{
		Response response = UserEndPoints.DeleteUser(userName);
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}

package demo;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

import files.Payload;
import files.ReUsableMethods;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		// Add place -> update place with new address -> get place to validate if new address is same as old

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();	
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);
		
		
		String newAddress = "70 winter walk, USA";
		//Update place	
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\n"
				+ "\"place_id\":\""+place_id+"\",\n"
				+ "\"address\":\""+newAddress+"\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//get place	
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(newAddress, actualAddress);
	}

}

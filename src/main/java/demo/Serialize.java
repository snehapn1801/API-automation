package demo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.*;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

public class Serialize {

	@Test

	public void serialization() {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setWebsite("http://google.com");

		List<String> myList = new ArrayList<String>();
		myList.add("abc");
		myList.add("pqr");
		p.setTypes(myList);

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);

		String response = given().queryParam("key", "qaclick123").body(p).when().post("/maps/api/place/add/json").then()
				.assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);
	}
}

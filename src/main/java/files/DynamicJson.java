package files;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String isle){
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().header("Content-Type","application/json")
		.body(Payload.AddBook(isbn, isle))
		.when().post("/Library/Addbook.php")
		.then().statusCode(200).extract().response().asString();
		
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		
		return new Object [][]{{"fhdjfh","1234"},{"fhdh","1237"}};
	}
}

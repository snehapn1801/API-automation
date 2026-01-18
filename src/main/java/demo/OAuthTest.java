package demo;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.*;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTest {

	@Test
	public void OAuth() {

		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String response = given()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");

		GetCourse gc = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
//		gc.getCourses().getApi().get(1).getCourseTitle();

		List<Api> apiCourses = gc.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {

			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}

		ArrayList<String> a = new ArrayList<String>();
		
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		for (int i = 0; i < w.size(); i++) {
			a.add(w.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expectedList));
		
	}
}

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

//	@Test
	public static void main(String[] args) {
//	public void sumOfCourses() {

		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");

		// Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for (int i = 0; i < count; i++) {
			int coursePrice = js.get("courses[" + i + "].price");
			int courseCopies = js.get("courses[" + i + "].copies");
			int product = coursePrice * courseCopies;// sum = sum + product;
			System.out.println(product);
			sum = sum + product;
		}
		System.out.println(sum);
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, totalAmount);
	}
}

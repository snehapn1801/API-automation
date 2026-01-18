package demo;
import files.Payload;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.CoursePrice());

		// Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// Print Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		// Print All course titles and their respective Prices
		for (int i = 0; i < count; i++) {
			String courseTile = js.get("courses[" + i + "].title");
			System.out.println(courseTile);
			System.out.println(js.get("courses[" + i + "].price").toString());
		}

		// Print no of copies sold by RPA Course

		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			if (courseTitle.equalsIgnoreCase("RPA")) {
				int copiesSold = js.get("courses[" + i + "].copies");
				System.out.println(copiesSold);
				break;
			}

		}

	}
}

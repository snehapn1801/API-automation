package demo;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import java.io.File;
import java.util.List;
import java.util.*;
import pojo.Orders;
import pojo.OrderDetail;

public class ECommerceAPITest {

	@Test
	public void ecommerce() {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("snehapn@gmail.com");
		loginRequest.setUserPassword("Sneha@123");

		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);

		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();

		// Add product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Mobile")
				.param("productAddedBy", userId).param("productCategory", "Fashion")
				.param("productSubCategory", "Dress").param("productPrice", "10000")
				.param("productDescription", "Addias Originals").param("productFor", "Women")
				.multiPart("productImage", new File("/Users/snehanatekar/Downloads/newImage.jpg"));

		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();

		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");

		// Create order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);

		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);

		Orders orders = new Orders();
		orders.setOrders(orderDetailList);

		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();
		System.out.println(responseAddOrder);

		// Delete order

		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId",
				productId);
		String deleteProdResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then()
				.log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(deleteProdResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));

	}
}

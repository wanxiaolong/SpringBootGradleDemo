package com.demo.gradle.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.demo.gradle.BaseApiTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class CustomerControllerApiTest extends BaseApiTest {
  @Test
  public void whenCreateCustomer_thenStatus201AndCustomerReturned() {
    String requestBody =
        """
        {
            "firstName": "John",
            "lastName": "Doe"
        }
        """;

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .when()
        .post("/api/customer")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("firstName", equalTo("John"))
        .body("lastName", equalTo("Doe"))
        .body("createdAt", notNullValue());
  }

  @Test
  public void whenGetExistingCustomer_thenStatus200AndCustomerReturned() {
    // 先创建测试数据
    Long customerId = createTestCustomer("Jane", "Smith");

    given()
        .when()
        .get("/api/customer/{id}", customerId)
        .then()
        .statusCode(200)
        .body("id", equalTo(customerId.intValue()))
        .body("firstName", equalTo("Jane"))
        .body("lastName", equalTo("Smith"))
        .body("createdAt", notNullValue());
  }

  @Test
  public void whenGetNonExistingCustomer_thenStatus404() {
    given().when().get("/api/customer/9999").then().statusCode(404);
  }

  @Test
  public void whenGetAllCustomers_thenStatus200AndCustomersReturned() {
    // 创建多个测试数据
    createTestCustomer("Alice", "Johnson");
    createTestCustomer("Bob", "Williams");

    given()
        .when()
        .get("/api/customer")
        .then()
        .statusCode(200)
        .body("size()", greaterThanOrEqualTo(2))
        .body("firstName", hasItems("Alice", "Bob"));
  }

  @Test
  public void whenUpdateCustomer_thenStatus200AndUpdatedCustomerReturned() {
    Long customerId = createTestCustomer("Old", "Name");

    String updateBody =
        """
        {
            "firstName": "New",
            "lastName": "Name"
        }
        """;

    given()
        .contentType(ContentType.JSON)
        .body(updateBody)
        .when()
        .put("/api/customer/{id}", customerId)
        .then()
        .statusCode(200)
        .body("id", equalTo(customerId.intValue()))
        .body("firstName", equalTo("New"))
        .body("lastName", equalTo("Name"));
  }

  @Test
  public void whenDeleteCustomer_thenStatus204AndCustomerRemoved() {
    Long customerId = createTestCustomer("ToDelete", "User");

    given().when().delete("/api/customer/{id}", customerId).then().statusCode(204);

    // 验证是否真的删除
    given().when().get("/api/customer/{id}", customerId).then().statusCode(404);
  }

  @Test
  public void whenSearchByFirstName_thenStatus200AndMatchingCustomersReturned() {
    createTestCustomer("User", "Smith");
    createTestCustomer("User", "Johnson");
    createTestCustomer("User3", "Smith2");

    given()
        .queryParam("firstName", "User")
        .when()
        .get("/api/customer/search")
        .then()
        .statusCode(200)
        .body("size()", equalTo(2))
        .body("firstName", everyItem(equalTo("User")));
  }

  private Long createTestCustomer(String firstName, String lastName) {
    String requestBody =
        String.format(
            """
        {
            "firstName": "%s",
            "lastName": "%s"
        }
        """,
            firstName, lastName);

    Integer id =
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/customer")
            .then()
            .extract()
            .path("id");
    return id.longValue();
  }
}

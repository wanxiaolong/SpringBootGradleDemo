package com.demo.gradle;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("api-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContainerConfig.class)
@ExtendWith(SpringExtension.class)
public class BaseApiTest {
  @LocalServerPort private int port;

  @BeforeAll
  public void setup() {
    RequestSpecBuilder builder =
        new RequestSpecBuilder()
            .setPort(port)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .setBasePath("/");
    RestAssured.requestSpecification = builder.build();
  }
}

package com.cbt.day3_path_guery_params;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.*;

public class QueryParamsEx {

  @BeforeAll
  public static void setup() {
    baseURI = "https://api.exchangeratesapi.io";
  }

  @Test
  public void testPHP() {

    Response response = given().queryParam("base", "PHP").
         when().get("/latest");
    System.out.println(response.asString());
    System.out.println(response.statusCode());

    assertThat(response.statusCode(), equalTo(200));


  }

}

package com.Practice;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

public class QueryEx {

  @BeforeAll
  public static void setup(){

    baseURI = "https://api.exchangeratesapi.io";
  }

  @Test
  public void practice(){

    Response response = given().queryParam("base", "BRL").when().get("latest/");

    assertThat(response.statusCode(),equalTo(200));
    System.out.println(response.getBody().prettyPrint());
    System.out.println(response.getHeader("Content-Type"));
    assertThat(response.getContentType(),equalTo("application/json"));
  }
}

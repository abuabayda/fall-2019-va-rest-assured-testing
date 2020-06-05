package com.cbt.day2_endpoints_response;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EndpointsDem {
  /*
  baseUri -- >
  endPoint -->
   */
  @BeforeAll
  public static void setup() {
    baseURI = "http://api.openrates.io";

  }

  @Test
  public void getLatest() {
    when().get("/latest")
         .prettyPeek().then()
         .statusCode(200);
  }

  @Test
  public void getHistoricRate() {
    when().get("/2010-01-01").prettyPeek().
         then().statusCode(200);
  }
}

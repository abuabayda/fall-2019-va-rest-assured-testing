package com.cbt.day2_endpoints_response;
import io.restassured.RestAssured.*;
import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginRestAssured {
  @BeforeAll
  public static void setup(){
    baseURI = "http://api.openrates.io";
  }

  @Test
  public void test1(){
    // request logging
         given().log().everything().
         when().get("latest").
         then().statusCode(200);
  }


  @Test
  public void test2(){
    when().get("latest").
         then().log().all().
         statusCode(200);
  }

}

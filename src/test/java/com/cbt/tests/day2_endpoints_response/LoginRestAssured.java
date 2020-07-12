package com.cbt.tests.day2_endpoints_response;
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
    // log everything

         given().
              log().everything().
         when().
              get("latest").
         then().
              statusCode(200);
    // log only the request url

         given().
              log().uri().
         when().
              get("latest").
         then().
              statusCode(200);

    // log only the request url

         given().
              log().method().
         when().
              get("latest").
         then().
              statusCode(200);
  }

  @Test
  public void logReqifFails(){
    // log if the test fails

         given().
              log().ifValidationFails().
         when().
              get("latest").
         then().
              statusCode(200);
  }

  @Test
  public void test2(){
    // print everything in response
//        RestAssured.
//                when().get("latest").
//                then().log().all().
//                    statusCode(200);
    // print only status
//        RestAssured.
//                when().get("latest").
//                then().log().status().
//                statusCode(200);
    // print only body

         when().
              get("latest").
         then().
              log().body().
              statusCode(200);

  }

  @Test
  public void printFailedResp(){

         when().
                get("latest").
         then().
                log().ifValidationFails().
                statusCode(200);
  }

  @Test
  public void printReqAndRes() {

         given().
                log().ifValidationFails().
         when().
                 get("latest").
         then().
                log().ifValidationFails().
         statusCode(200);

  }
}

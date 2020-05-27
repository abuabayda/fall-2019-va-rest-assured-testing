package com.Practice;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.*;


public class CustomAndResponse {

  @BeforeAll
  public static void setup(){
    baseURI = "http://api.openrates.io";
  }

  @Test
  public void getStatus(){
    when().get("/latest")
         .prettyPeek().then()
         .statusCode(200);
  }
}

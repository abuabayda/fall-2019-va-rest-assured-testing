package com.cbt.tests.day3_path_guery_params;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PathParamsExs {

  @BeforeAll
  public static void setup(){

    baseURI = "http://api.cybertektraining.com/";
  }


  @Test
  public void testVasylFromIndia(){

          given().
               log().all().
               pathParam("id","10521").
          when().
               get("/student/{id}").
               prettyPeek().
          then().statusCode(200);
  }



  @Test
  public void testVeraFromIndia(){

    given().
         log().all().
         pathParam("id","10662").
    when().
         get("/student/{id}").
         prettyPeek().
    then().statusCode(200);

  }


}

package com.cbt.day3_path_guery_params;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NotFound404Test {

  @BeforeAll
  public static void setup() {
    baseURI = "http://api.cybertektraining.com";
  }

  @Test
  public void getStudentNames() {
    /*
    make a request to student endpoint
    provide no existing id as a parameter
    verify status 404
    verify response contains student with id = not found
     */
    Response response = given().pathParams("id", 1).
         when().get("/student/{id}");
    System.out.println(response.statusCode());
    System.out.println(response.asString());

    response.then().statusCode(404);
    assertThat(response.asString(),containsString("NOT FOUND!"));
  }

  @Test
  public void test404(){
    given().
         pathParams("id","1").
    when().
         get("/student/{id}").
     then().
         statusCode(404);
  }
}

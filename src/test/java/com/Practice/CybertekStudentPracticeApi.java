package com.Practice;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CybertekStudentPracticeApi {

  @BeforeAll
  public static void setup(){
   baseURI = "http://api.cybertektraining.com";
  }


  @Test
  public void firstTest(){
    /*

     */
    given().pathParams("id","4585").
         when().get("teacher/{id}").
         prettyPeek().then().statusCode(200);
  }

  @Test
  public void secondTest(){
    /*

     */
    Response response = given().pathParams("name", "Orlando").
         when().get("teacher/name/{name}");
    assertThat(response.statusCode(),is(200));

    Map<String ,String> params = new HashMap<>();
    params.put("name","Sean");

    response = given().pathParams(params).when().get("teacher/name/{name}");
    assertThat(response.statusCode(),is(200));

    System.out.println(response.prettyPeek());

  }
}

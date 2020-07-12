package com.cbt.tests.day3_path_guery_params;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GithubTest {

  /*
    get user from github url
    get user from github from /users/:username
    base url https://api.github.com
   */

  @BeforeAll
  public static void setup() {
    baseURI = "https://api.github.com";
  }

  @Test
  public void getName() {
    given().
         pathParam("username", "marufjont").
         when().
         get("/users/{username}").
         prettyPeek().
         then().statusCode(200);
  }

  @Test
  public void getName2() {
   //using map for entering key and value

    Map<String,String> params = new HashMap<>();
    params.put("username","Scarlet");

    given().
        pathParams(params).
    when().
         get("/users/{username}").
         prettyPeek().
    then().statusCode(200);
  }

  @Test//first param
  public void pramEx() {

//    //2 or more path parameters with endpoints / value
//    given().
//         pathParam("endpoint", "users").
//         pathParam("username", "marufjont").
//         when().
//         get("{endpoint}/{username}").
//         prettyPeek().
//         then().statusCode(200);

    //2 params
    Map<String,String> pathParams = new HashMap<>();

    pathParams.put("username","abuabayda");
    pathParams.put("endpoint","users");

     given().log().all().
         pathParams(pathParams).
     when().get("{endpoint}/{username}").
         prettyPeek().
     then().statusCode(200);


  }

}

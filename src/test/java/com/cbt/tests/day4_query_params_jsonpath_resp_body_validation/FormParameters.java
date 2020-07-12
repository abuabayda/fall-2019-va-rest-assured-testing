package com.cbt.tests.day4_query_params_jsonpath_resp_body_validation;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class FormParameters {
      /*
    login to library 2 using librarian information
    username: librarian16@library
    password: 8BzUhhaU
     */

  @Test
  public void testLogin(){
     baseURI="http://library2.cybertekschool.com/rest/v1";
    given().
         log().all().
         formParam("email", "librarian16@library").
         formParam("password", "8BzUhhaU").
         when().
         post("/login").
         prettyPeek().
         then().statusCode(200);
  }

}

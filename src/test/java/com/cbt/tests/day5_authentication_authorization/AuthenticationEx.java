package com.cbt.day5_authentication_authorization;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.Test;

public class AuthenticationEx {
  @Test
  public void basicPreemptive(){
    given().
         auth().preemptive().basic("admin","admin").
         when().get("http://54.146.89.247:8000/api/spartans").
         then().statusCode(200);
  }
  @Test
  public void basicAuth(){
    given().
         auth().basic("admin","admin").
         when().get("http://54.146.89.247:8000/api/spartans").
         then().statusCode(200);
  }
  @Test
  public void apiKeyEx(){
    given().
         queryParam("apiKey","e0484f01").
         queryParam("t","romcom").
         when().get("http://omdbapi.com/").
         prettyPeek().
         then().statusCode(200);
  }

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

package com.cbt.day1_intro;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class RestAssured {

  /**
   * make a get request to api.zippopotam.us/us/90210
   * verify that status code is 200
   */


  @Test
  public void testStatusCode() {
    /*
    in rest assured we use gherkin style given when then
    given --> used to prepare the request with custom parameters and headers
    when  used to send
     */

    given().log().all().
         when().get("http://api.zippopotam.us/us/90210").
         then().statusCode(200);
  }

  /**
   * make a get request to http://api.openrates.io/latest
   * verify that status code is 200
   * <p>
   * positive   * 1---> information
   * positive   * 2----> success
   * positive   * 3---> redirect
   * 4---> client error
   * 5---> server error
   */
  public void testStatusCode2() {

    when().get("http://api.openrates.io/latest").
         then().statusCode(200);
  }

  @Test
  public void printResponse() {
    when().get("http://api.openrates.io/latest").
         prettyPeek().
         then().statusCode(200);
  }

  @Test
  public void verifyContentType(){
    /**
     * make a get request to api.zippopotam.us/us/90210
     * verify that status code is 200
     * verify header response type application /json
     */
    when().get("http://api.openrates.io/latest").
         prettyPeek().
         then().statusCode(200).
         contentType(ContentType.JSON);

    //same test with some syntactic sugar
    when().get("http://api.openrates.io/latest").
         prettyPeek().
         then().assertThat().statusCode(200).and().contentType(ContentType.JSON);

    //same test but verify header differently
    when().get("http://api.openrates.io/latest").
           prettyPeek().
         then().statusCode(200).contentType("application/json");

    //same test but verify header differently and differently

    when().get("http://api.openrates.io/latest").
         prettyPeek().
         then().statusCode(200).header("Content-Type","application/json");
  }

  @Test
  public void verifyHeader(){
    /**
     * make a get request to api.zippopotam.us/us/22041
     * verify that status code is 200
     * verify header response type application /json
     * verify header chaser UTF-8
     *
     */
    when().get("http://api.zippopotam.us/us/22041").
         prettyPeek().
         then().statusCode(200).
         and().contentType(ContentType.JSON).
         and().header("Charset",equalTo("UTF-8"));
  }


}

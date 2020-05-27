package com.OfficeHrs;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

public class StatusCodeAndContentTypeTests {

  /**
   * make a get request to https://api.github.com/users/cybertekschool
   * verify status 200
   * verify content type application /json
   * verify header server github.com
   */

  @Test
  public void getHubTest() {
    when().get("http://api.github.com/users/cybertekschool").
         prettyPeek().
         then().statusCode(200).
         and().contentType(ContentType.JSON).assertThat().
         header("server", "GitHub.com");
  }


  @Test
  public void getGoogle() {
/**
 * TC 02
 * make a get request to https://google.com
 * verify status code 200
 * verify content type application/html
 */
    when().get("https://google.com").
         prettyPeek().
         then().statusCode(200).
         contentType(ContentType.HTML);
  }

}

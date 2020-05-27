package com.Homeworks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UINames {

  @BeforeAll
  public static void setup(){
    baseURI = "https://uinames.com/";
  }


  /*
No params test
1.Send a get request without providing any parameters
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify that name, surname, gender, region fields have valueGender test

 */
  @Test
  public void getRequest(){
    Response response = given().log().all().
         get("https://www.getpostman.com/collections/e1338b73a8be7a5500e6");

        response.statusCode();
        assertThat(response.statusCode(),is(200));
    System.out.println(response.contentType());
    assertThat(response.contentType(),equalTo("application/json"));
    assertThat(response.header("name"),equalTo(null));
    assertThat(response.header("gender"),equalTo(null));
    assertThat(response.header("region"),equalTo(null));
    assertThat(response.header("surname"),equalTo(null));
  }

  /*
   1.Create a request by providing query parameter: gender, male or female
  2.Verify status code 200, content type application/json; charset=utf-8
  3.Verify that value of gender field is same from step 1
   */
}

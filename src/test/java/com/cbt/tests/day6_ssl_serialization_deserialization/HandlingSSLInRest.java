package com.cbt.day6_ssl_serialization_deserialization;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class HandlingSSLInRest {

  @Test
  public void setup(){
   given().
         relaxedHTTPSValidation().
    when().
          get("http://untrusted-root.dadssl.com/").
         prettyPeek().
    then().
         statusCode(200);
  }


  @Test
  public void trustedStore(){

    given().
         trustStore("/path/to/file","password").
    when().
          get("/ay/api").
    then().
         statusCode(200);
  }

  @Test
  public void keyStore(){

    given().
         keyStore("/path/to/file","password").
         when().
         get("/ay/api").
         then().
         statusCode(200);
  }

}

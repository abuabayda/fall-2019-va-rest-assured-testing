package com.cbt.day4_query_params_jsonpath_resp_body_validation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;


public class MoreQueryParameters {

  @BeforeAll
  public static void setup(){
    baseURI = "https://api.exchangeratesapi.io";
  }


  /*
    get the rates against USD only
     */
  @Test
  public void symbolsTest(){
    given().
         queryParam("symbols","USD").
         log().all().
         when().
         get("/latest").
         prettyPeek().
         then().
         statusCode(200);
  }
  /*
    base  PHP
    symbols USD
     */
  @Test
  public void baseAndSymbolsTest(){
    given().
         log().all().
         queryParam("symbols", "USD").
         queryParam("base", "PHP").
         when().
         get("/latest").
         prettyPeek().
         then().
         statusCode(200);
  }
  /*
   https://api.exchangeratesapi.io/latest?symbols=USD,GBP
 base  PHP
 symbols USD
  */
  @Test
  public void baseANDSymbolsTestWithMap(){

    Map<String,String> params = new HashMap<>();

    params.put("base","PHP");
    params.put("symbols","USD,GBP");

    given().
         log().all().
     when().
         get("/latest").
         prettyPeek().
     then().statusCode(200);
  }

  /*
  //   https://www.google.com/search?
//   q=selenium&
//   oq=selenium&
//   aqs=chrome..69i57j69i60l2j69i65l3j69i60l2.2095j0j4&
//   sourceid=chrome&
//   ie=UTF-8
   */
  @Test
  public void longParameters(){

  }
}

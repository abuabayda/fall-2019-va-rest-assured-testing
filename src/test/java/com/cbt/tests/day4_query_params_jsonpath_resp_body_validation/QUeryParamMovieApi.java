package com.cbt.day4_query_params_jsonpath_resp_body_validation;

import static io.restassured.RestAssured.given;

public class QUeryParamMovieApi {




  public void test(){

    given().pathParams("apikey","e0484f01").
         queryParam("i","Star Wars").
         when().get("http://www.omdbapi.com/").
         prettyPeek().
         then().statusCode(200);
  }
}



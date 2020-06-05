package com.cbt.tests.More_Serial;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UINames {



    @Test
    public void firstTest(){
        given().
             queryParam("region", "Mexico").
             when().
             get("https://cybertek-ui-names.herokuapp.com/api/").
             prettyPeek().
             then().
             statusCode(200).
             body("region", is("Mexico")).
             body("name", not(emptyOrNullString()));

    }
}

package com.Homeworks.HarryPotterApi;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.Homeworks.HarryPotterApi.Pojo.Character;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;



public class HarryPotterApi {
        String key = "$2a$10$I3hu9b.nwxgrGgyixnZmb.1YeSV5Lr0lojB3KcLmtKWfdZ4uhs4Ky";
    @BeforeAll
    public static void setup() {
        baseURI = "https://www.potterapi.com/v1/";
    }

    @Test
    public void verifySortingHat() {
        Response response = when().
                                    get("sortingHat").prettyPeek();

        response.then().
                        assertThat().
                        statusCode(200).
                        contentType("application/json; charset=utf-8").
                        body(anyOf(containsString("Gryffindor"),
                                    containsString("Slytherin"),
                                    containsString("Hufflepuff"),
                                    containsString("Ravenclaw")));

//        String s = response.print();
//        switch (s) {
//            case "Gryffindor":
//                assertThat(s, equalTo("Gryffindor"));
//                break;
//            case "Slytherin":
//                assertThat(s, equalTo("Slytherin"));
//                break;
//            case "Hufflepuff":
//                assertThat(s, equalTo("Hufflepuff"));
//                break;
//            case "Ravenclaw":
//                assertThat(s, equalTo("Ravenclaw"));
//        }
    }

    @Test
    public void verifyBadKey() {
        Response response = given().
                header("Accept", "application/json").
                queryParam("key", "invalid").
         when().
                get("/characters");

        response.
             then().
                    assertThat().
                                statusCode(401).
                                contentType("application/json; charset=utf-8").
                                statusLine(containsString("Unauthorized")).
                                body(containsString("\"error\":\"API Key Not Found\""));
    }

    @Test
    public void VerifyNoKey(){
        Response response = given().
             header("Accept", "application/json").
             when().
             get("/characters");
        assertThat(response.contentType(), equalTo("application/json; charset=utf-8"));
        assertThat(response.statusCode(), is(409));
        assertTrue(response.getStatusLine().contains("Conflict"));
        assertTrue(response.body().asString().contains("\"error\":\"Must pass API key for request\""));
    }

    @Test
    public void verifyNumberOfCharacter(){
        Response response = given().
                                     header("Accept", "application/json").
                                     queryParam("key", key).
                            when().
                                     get("/characters");
            response.then().
                            assertThat().
                                        statusCode(200).
                                        contentType("application/json; charset=utf-8");
            response.prettyPeek();
//
        List<String> chars = response.jsonPath().getList("name");

       assertTrue(chars.size() >= 194);

    }

    @Test
    public void verifyNumOfCharsIdAndHouse(){
        Response response = given().
             header("Accept", "application/json").
             queryParam("key", "$2a$10$I3hu9b.nwxgrGgyixnZmb.1YeSV5Lr0lojB3KcLmtKWfdZ4uhs4Ky").
             when().
             get("/characters");
        assertThat(response.statusCode(),is(200));
        assertThat(response.contentType(),equalTo("application/json; charset=utf-8"));


        List<Character> list = response.jsonPath().getList("", Character.class);
//        System.out.println(jsonPath.getList("_id"));
//        assertFalse(Character);
//        assertTrue(jsonPath.getList("dumbledoresArmy") instanceof Boolean);
//        System.out.println(jsonPath.getList("dumbledoresArmy"));

    }
}

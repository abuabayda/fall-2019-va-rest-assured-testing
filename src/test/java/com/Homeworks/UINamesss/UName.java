package com.Homeworks.UINamesss;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.nashorn.internal.ir.CallNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UName {

//    UI names API testing
//    In this assignment, you will test api uinames. This is a free api used to create test users. Documentation
//    for this api is available at https://github.com/CybertekSchool/uinames. You can import Postman
//    collection for this API using link: https://www.getpostman.com/collections/c878c63023e6d788da7d
//    Automate the given test cases. You can use any existing project. You can automate all test cases in
//    same class or different classes.

    @BeforeAll
    public static void setup() {
        baseURI = "https://cybertek-ui-names.herokuapp.com/api/";
    }

    //    TEST CASES
//    No params test
//1. Send a get request without providing any parameters
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that name, surname, gender, region fields have value
    @Test
    @DisplayName("No Prams")
    public void noPrams() {
        Response response = when().get().prettyPeek();
        response.then().
             assertThat().statusCode(200).
             and().
             assertThat().contentType("application/json; charset=utf-8").
             and().
             body("name", notNullValue()).
             body("surname", notNullValue()).
             body("gender", notNullValue()).
             body("region", notNullValue());
    }

    //    Gender test
//1. Create a request by providing query parameter: gender, male or female
//2. Verify status code 200, content type application/json; charset=utf-8
//            3. Verify that value of gender field is same from step 1
    @Test
    @DisplayName("Gender Test")
    public void genderTest() {
        Response response = given().
             queryParam("gender", "female").
             when().
             get().prettyPeek();
        response.
             then().
             assertThat().
             statusCode(200).
             and().
             contentType("application/json; charset=utf-8").
             and().
             body("gender", is("female"));
    }

    //     2 params test
//1. Create a request by providing query parameters: a valid region and gender
//    NOTE: Available region values are given in the documentation
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that value of gender field is same from step 1
//4. Verify that value of region field is same from step 1
    @Test
    @DisplayName("2 prams")
    public void twoPrams() {

        Response response = given().
             queryParam("region", "Belgium").
             queryParam("gender", "female").
             when().
             get().prettyPeek();
        response.
             then().
             assertThat().
             statusCode(200).
             and().
             contentType("application/json; charset=utf-8").
             and().
             body("gender", is("female")).
             body("region", is("Belgium"));
    }

    //    Invalid gender test
//1. Create a request by providing query parameter: invalid gender
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Invalid gender
    @Test
    @DisplayName("Invalid Gender Test")
    public void genderTest2() {

        Response response = given().
             queryParam("gender", "dog").
             when().
             get().prettyPeek();
        response.
             then().
             assertThat().
             statusCode(400).
             and().
             statusLine(containsString("Bad Request")).
             and().
             body("error", is("Invalid gender"));
    }

    //    Invalid region test
//1. Create a request by providing query parameter: invalid region
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Region or language not found
    @Test
    @DisplayName("Invalid region test")
    public void InvalidRegionTest() {

        Response response = given().
             queryParam("region", "sudan").
             when().
             get().prettyPeek();
        response.
             then().
             assertThat().
             statusCode(400).
             and().
             statusLine(containsString("Bad Request")).
             and().
             body("error", is("Region or language not found"));
    }

    //    Amount and regions test
//1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects have different name+surname combination
    @Test
    @DisplayName("Amount and regions test")
    public void Amount_and_regions_test() {

        Response response = given().
             queryParam("amount", 100).
             queryParam("region", "Germany").
             when().
             get().prettyPeek();
        response.
             then().
             assertThat().
             statusCode(200).
             and().
             header("Content-Type", "application/json; charset=utf-8");

        List<UserPOJO> list = response.jsonPath().getList("", UserPOJO.class);
//    System.out.println(list);
        //this remove duplicated
        Set<String> userSet = new HashSet<>();

        for (UserPOJO user : list) {
            String fullName = user.getName() + " " + user.getSurname();
            userSet.add(fullName);
        }
        response.
             then().
             assertThat().
             statusCode(200).
             and().
             header("Content-Type", "application/json; charset=utf-8").
             and().
             body("size()", is(userSet.size()));


    }

//      3 params test
//1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger
//            than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects the response have the same region and gender passed in step 1

    List<String> genders = Arrays.asList("male", "female"); // we will pick random gender for each execution
    int randomAmount = new Random().nextInt(500) + 1; //0 - 499

    public String generateRandomGender() {
        Collections.shuffle(genders);
        return genders.get(0);
    }

    @Test
    @DisplayName("3 params test")
    public void threePrams() {

        String randomGender = generateRandomGender();
        Response response = given().
             queryParam("amount", randomAmount).
             queryParam("region", "France").
             queryParam("gender", randomGender).
             when().
             get().prettyPeek();

        response.
             then().
             assertThat().
             statusCode(200).
             and().
             header("Content-Type", "application/json; charset=utf-8").
             body("size()", is(randomAmount)).
             body("gender", everyItem(is(randomGender))).
             body("region", everyItem(is("France")));
    }

    //    Amount count test
//1. Create a request by providing query parameter: amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that number of objects returned in the response is same as the amount passed in step 1
    @Test
    @DisplayName("Amount count test")
    public void Amount_count_test() {

        System.out.println(randomAmount);
        Response response = given().
             queryParam("amount", randomAmount).
             when().
             get().prettyPeek();
        response.
             then().
                statusCode(200).
                contentType("application/json; charset=utf-8").
             and().
                body("size()",is(randomAmount));


    }
}
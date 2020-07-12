package com.cbt.tests.day9_schema_validation_morem_api;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.DBUtil.AuthenticationUtility;
import com.DBUtil.ConfigReader;
import com.DBUtil.LibraryUserUtility;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class LibraryCreateUserTests {
    /*
     1. Student should not be able to create users
     2. Librarian should not be able create admin users
     3. Librarian should be able create librarian users
     4. Librarian should be able create student users
     5. Librarian should not be able create users with wrong group id
     6. Librarian should not be able create users with status
     7. Librarian should not be able create users with missing fields
      */
   static RequestSpecification request;
    @BeforeAll
    public static void setup(){
        baseURI = ConfigReader.getProperty("library1_base_url");
        String token = AuthenticationUtility.getLibrarianToken();
         request = given().header("x-library-token", token).log().all();
    }

    @Test
    @DisplayName(("Librarians cannot create admins"))
    public void test(){
       request.
                formParam("user_group_id",1).
        then().
//                statusCode(403).
                contentType(ContentType.JSON).
                body("error",is("You do not add/edit admins."));
    }

    @Test
    @DisplayName("Librarian should not be able create users with status")
    public void wrongGroupId(){
        request.
                formParam("full_name","john doe").
                formParam("email","jon.doe@someemail.com").
                formParam("password","admin").
                formParam("user_group_id",44).
                formParam("status","active").
                formParam("start_date","2020-05-05").
                formParam("end-date","2021-05-05").
                formParam("address","123 main st").
        when().
                post("/add_user").prettyPeek().
        then().
                statusCode(400).
                contentType(ContentType.JSON);
    }
    @Test
    @DisplayName("Librarian should not be able to create users with wrong group id")
    public void wrongStatus(){
        request.
             formParam("full_name","john doe").
             formParam("email","jon.doe@someemail.com").
             formParam("password","admin").
             formParam("user_group_id",3).
             formParam("status","super-active").
             formParam("start_date","2020-05-05").
             formParam("end-date","2021-05-05").
             formParam("address","123 main st").
         when().
             post("/add_user").prettyPeek().
         then().
             statusCode(not(200)).
             contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Create student user test")
    public void createUser(){
        Map<String ,?> user = LibraryUserUtility.createUser(3);
        request.
             formParams(user).
        when().
                post("add_user").prettyPeek().
        then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("message",is("The user has been created.")).
                body("user_id",not(emptyOrNullString()));
    }
    @Test
    @DisplayName("Create get, update and get again")
    public void endToEnd(){
        Map<String,?> user = LibraryUserUtility.createUser(3);
        Response response = request.
             formParams(user).
             when().
             post("add_user").prettyPeek();
        response.then().statusCode(200);

        String id = response.path("user_id");
        System.out.println("User ID: "+id);

        //get user
        request.pathParams("id",id).
        when().
                get("get_user_by_id/{id}").prettyPeek().
        then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("id", equalTo(id)).
                body("email", equalTo(user.get("email"))).
                body("full_name",equalTo(user.get("full_name"))).
                body("password",not(emptyOrNullString())).
                body("user_group_id",equalTo("3"));

        //update user information

        String newName = new Faker().name().fullName();
       request = given().
            header("x-library-token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImlkIjoiMTY0IiwiZnVsbF9uYW1lIjoiVGVzdCBMaWJyYXJpYW4gMTYiLCJlbWFpbCI6ImxpYnJhcmlhbjE2QGxpYnJhcnkiLCJ1c2VyX2dyb3VwX2lkIjoiMiJ9LCJpYXQiOjE1OTA5NTgwOTcsImV4cCI6MTU5MzU1MDA5N30.RE56pSRGjKNsbcGPbPjkncX-v_k2d6pMVAqV127lxbE");
       request.
                formParam("full_name",newName).
                pathParams("",id).
        when().
                patch("update_user").prettyPeek().
        then().
             statusCode(200).
             contentType(ContentType.JSON);

    }
}

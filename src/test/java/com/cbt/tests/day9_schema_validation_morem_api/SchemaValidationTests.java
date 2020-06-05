package com.cbt.tests.day9_schema_validation_morem_api;

import static io.restassured.RestAssured.*;

import com.DBUtil.ConfigReader;
import com.cbt.pojos.Spartan;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

/*
json schema --> doc that describe a json file, tells how json must be structured, include dat type
xsd file --> doc that describe a XMl file, tells how json must be structured, include dat type
 */
public class SchemaValidationTests {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigReader.getProperty("spartan_url");
        authentication = basic("admin", "admin");
    }
    @BeforeEach
    public void disableWarning(){
        System.err.close();
        System.setErr(System.out);
    }

    @Test
    public void ValidateJson() {
        given().
             pathParams("id", 44).
        when().
         get("/spartans/{id}").prettyPeek().
       then().
             statusCode(200).
             //matchesJasonSchemaInClassPath -->verifies that response body matches the schema in test/resources folder
                  body(JsonSchemaValidator.matchesJsonSchemaInClasspath("spartan-schema.json"));
    }

    @Test
    public void validateJsonNotInClassPath() {
        //create file , where json file locate
        File file = new File("src/test/resources/Spartan-schema.json");
        given().
             pathParams("id", 45).
        when().
             get("/spartans/{id}").prettyPeek().
        then().
             statusCode(200).
             body(JsonSchemaValidator.matchesJsonSchema(file));
    }

    @Test
    public void ValidatePostSpartanResponse() {
        //create spartan object pojo
        //attach pojo in post request/validated

        Faker faker = new Faker();
        String gender = faker.bool().bool() ? "Male" : "Female";
        Spartan spartan = new Spartan(faker.name().firstName(), gender, faker.number().digits(10));

        given().
             contentType(ContentType.JSON).
             body(spartan).
        when().
             post("/spartans").prettyPeek().
        then().
             statusCode(201).
             body(JsonSchemaValidator.matchesJsonSchemaInClasspath("post-spartan-schema.json"));
    }

    @Test
    public void validateXMLResponse(){

        given().
                accept(ContentType.XML).
        when().
                get("/spartans").
        then().
                statusCode(200).
                body(RestAssuredMatchers.matchesXsdInClasspath("allspartans.xsd"));
    }

    @Test
    public void validateXMLResponseNotInClassPath(){
        File file = new File("src/test/resources/AllSpartans.xsd");
        given().
                header("Accept","application/xml").
        when().
                get("/spartans").
        then().
                statusCode(200).
                body(RestAssuredMatchers.matchesXsd(file));
    }
}

package com.cbt.tests.day7_Serialization_deserialization_delete;

import com.DBUtil.ConfigReader;
import com.cbt.pojos.Spartan;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class EndToEndTesting {
    @BeforeAll
    public static void setup() {

        baseURI = ConfigReader.getProperty("spartan_url");
    }

    // end to end testing
// create a new spartan
// get the id if the new spartan
// use the get single spartan endpoint to get that new spartan info using it
// verify 200
// delete that spartan using the id
// use the get single spartan endpoint to get that new spartan info using it
// 201

    @Test
    public void endToEndTesting() {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String gender;
        if (faker.bool().bool()) {
            gender = "Female";
        } else {
        gender = "Male";
    }
    String phone = faker.number().digits(10);
    Spartan spartan = new Spartan(name, gender, phone);

    Response response = given().
         auth().basic("admin", "admin").
         contentType(ContentType.JSON).
         body(spartan).
     when().
         post("/spartans/").prettyPeek();

    response.
         then().
                statusCode(201).
         and().
             body("success", is("A Spartan is Born!"));

    int id = response.path("data.id");
    System.out.println("id = " + id);

    //get spartan info using id

    given().
             auth().basic("admin", "admin").
             pathParam("id", id).
     when().
            get("/spartans/{id}").prettyPeek().
     then().
             statusCode(200).
             body("name", is(name)).
             body("gender", is(gender)).
             body("phone", is(Long.parseLong(phone))).
             body("id", is(id));

    Spartan newBornSpartan = response.as(Spartan.class);
    spartan.setId(id);
    assertThat(newBornSpartan, samePropertyValuesAs((spartan)));

    //delete spartan using id
    given().
             auth().basic("admin", "admin").
             pathParam("id", id).
     when().
            delete("/spartans/{id}").
            prettyPeek().
     then().
            statusCode(204);


    //verify that thing is deleted
     given().
             auth().basic("admin", "admin").
             pathParam("id", id).
     when().
             get("/spartans/{id}").
     then().
            statusCode(404).
     and().
         body("message", is("Spartan Not Fount"));

    }

}

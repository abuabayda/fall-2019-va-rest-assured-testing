package com.cbt.tests.day8_More_Serial;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import com.DBUtil.ConfigReader;
import com.cbt.pojos.Address;
import com.cbt.pojos.Company;
import com.cbt.pojos.Student;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(PER_CLASS)
public class CybertekTrainingPojoTest {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigReader.getProperty("preschool_url");
    }

    @Test
    public void getFirstStudentUsingJasonPath() {

        Response response = when().get("student/all");

        response.then().statusCode(200).contentType(ContentType.JSON);

        Student students = response.jsonPath().getObject("students[0]",Student.class);
        System.out.println(students);

        //verify first name
        assertThat(students.getFirstName(),not(emptyOrNullString()));
        response.then().body("students[0].firstName",not(emptyOrNullString()));

        //verify last name
        assertThat(students.getLastName(),not(emptyOrNullString()));
        response.then().body("students[0].lastName",not(emptyOrNullString()));

        //verify gender
        assertThat(students.getGender(),not(emptyOrNullString()));
        response.then().body("students[0].gender",not(emptyOrNullString()));


    }
}

package com.cbt.tests.day5_authentication_authorization;
import static io.restassured.RestAssured.*;

import com.DBUtil.ConfigReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TokenAuthentication {

  @BeforeAll
  public static void setup(){
    baseURI = ConfigReader.getProperty("library2_base_url");}

  @Test
  public void tokenAuthentication(){
    //get token from login method
    String  token = given().log().ifValidationFails().
         formParam("email", "student27@library").
         formParam("password", "kkMksO2i").
         when().post("/login").jsonPath().getString("token");

    given().
         header("x-library-token",token).
         log().ifValidationFails()
         .when().
   get("/get_book_categories").prettyPeek().then().statusCode(200);
  }

  /*
  get token as student then add a new book
  as student should received 403 forbidden as status
   */
  @Test
  public void getTokenAddBook(){
    String token = given().log().ifValidationFails().
         formParam("email", "student27@library").
         formParam("password", "kkMksO2i").
                  when().
         post("/login").jsonPath().getString("token");
    System.out.println("token = " + token);

    given().
         header("x-library-token",token).
         when().post("/add_book").
         prettyPeek().
         then().statusCode(403);
  }

  @Test
  public void oAuth(){
    given().
         auth().oauth2("06d395e2eabddd0ab67e7573b65fe640c469226f").
    when().
         get("https://api.github.com/repos/marufjont/secret-repository").
         prettyPeek().
    then().
         statusCode(200);
  }
}

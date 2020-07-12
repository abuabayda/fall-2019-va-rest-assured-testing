package com.Homeworks.GitHUbApi;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.junit.jupiter.api.Assertions.*;


public class GithubApiTesting {
  @BeforeAll
  public static void setup() {
    baseURI = "https://api.github.com";
  }

  @Test
  public void VerifyOrganizationInfo() {
/*
1. Send a get request to /orgs/:org. Request includes : • Path param org with value cucumber
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify value of the login field is cucumber
4. Verify value of the name field is cucumber
5. Verify value of the id field is 320565
 */
    Response response = given().pathParams("org", "cucumber").when().
         get("/orgs/{org}");
    //one option to store string value from json file by its reference
//    JsonPath jsonPath = response.jsonPath();
//    String login = jsonPath.getString("login");
//    String name = jsonPath.getString("name");
//    String id = jsonPath.getString("id");
//    System.out.println(response.prettyPeek());
//    assertThat(response.statusCode(), is(200));
//    assertThat(response.getContentType(), equalTo("application/json; charset=utf-8"));
//    assertThat(login, equalTo("cucumber"));
//    assertThat(name, equalTo("Cucumber"));
//    assertThat(id, is("320565"));
    // or using body() and then provide var name
    response.
         then().
         assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                body("login",is("cucumber")).
                body("name",equalToIgnoringCase("cucumber")).
                body("id",is(320565));

  }

  @Test
  public void Verify_error_message() {
/*
1. Send a get request to /orgs/:org. Request includes : 
     • Header Accept with value application/xml
     • Path param org with value cucumber
2. Verify status code 415, content type application/json; charset=utf-8
3. Verify response status line include message Unsupported Media Type
 */
    Response response = given().
         header("Accept", "application/xml").
         pathParams("org", "cucumber").
         when().get("/orgs/{org}");
//    assertThat(response.statusCode(), is(415));
//    assertThat(response.getContentType(), equalTo("application/json; charset=utf-8"));
//    Assertions.assertTrue(response.statusLine().contains("Unsupported Media Type"));
      response.
           then().
                  assertThat().
                      statusCode(415).
                      contentType("application/json; charset=utf-8").
                      statusLine(containsString("Unsupported Media Type"));


  }

  /*
  Number of repositories
  1. Send a get request to /orgs/:org. Request includes : • Path param org with value cucumber
  2. Grab the value of the field public_repos
  3. Send a get request to /orgs/:org/repos. Request includes :
  • Path param org with value cucumber
  4. Verify that number of objects in the response is equal to value from step 2
   */
  @Test
  public void NumberOfRepositories() {
    Response response = given().
         pathParams("org", "cucumber").
         when().get("/orgs/{org}");
    System.out.println("response " + response.prettyPeek());

        response.then().
             assertThat().
                  body("public_repos",is(90));

    int public_repos = response.jsonPath().getInt("public_repos");

    Response response2 = given().
                                 pathParams("org", "cucumber").
                                 queryParam("per_page", "100").
                         when().
                                 get("/orgs/{org}/repos").prettyPeek();

    response2.then().
                assertThat().
                      body("size()",is(public_repos));


  }

  /*
  Repository id information
1. Send a get request to /orgs/:org/repos. Request includes :
    • Path param org with value cucumber
2. Verify that id field is unique in every in every object in the response
3. Verify that node_id field is unique in every in every object in the response
   */
  @Test
  public void numOfId() {
    // first ids
    Response response =
         given().
              queryParams("per_page",100).
              pathParam("org","cucumber").
         when().
              get("/orgs/{org}/repos").prettyPeek();

    List<Integer> idList = response.jsonPath().getList("id");
    List<String> nodeIDList = response.jsonPath().getList("node_id");

    Set<Integer>idSet = new HashSet<>(idList);

    Set<String>nodeIDSet = new HashSet<>(nodeIDList);

    assertEquals(idList.size(),idSet.size());
    assertEquals(nodeIDList.size(),nodeIDSet.size());

  }

  /*
  epository owner information
1. Send a get request to /orgs/:org. Request includes :
 • Path param org with value cucumber
2. Grab the value of the field id
3. Send a get request to /orgs/:org/repos. Request includes :
• Path param org with value cucumber
4. Verify that value of the id inside the owner object in every response is equal to value from step 2
   */
  @Test
  public void repoOwnerInfo() {
    //sending a request
    Response response =
         given().
              pathParam("org","cucumber").
              when().
              get("/orgs/{org}").prettyPeek();
    int id = response.jsonPath().getInt("id");

    Response response2 =
         given().
              queryParams("per_page",100).
              pathParam("org","cucumber").
              when().
              get("/orgs/{org}/repos").prettyPeek();
    response2.then().
         assertThat().
         body("owner.id",everyItem(is(id)));
  }
  /*
  Ascending order by full_name sort
1. Send a get request to /orgs/:org/repos. Request includes :
 • Path param org with value cucumber
• Query param sort with value full_name
2. Verify that all repositories are listed in alphabetical order based on the value of the field name
   */
  @Test
  public void AscOrdFullName(){
    Response response = given().
                                 pathParams("org", "cucumber").
                                 queryParam("sort","full_name").
                        when().
                                 get("/orgs/{org}/repos");


    List<String> listNames = response.jsonPath().getList("full_name");
    List<String> sorted = new ArrayList<>(listNames);
    Collections.sort(sorted);


    assertThat(sorted,equalTo(listNames));
  }

  /*
  Descending order by full_name sort
1. Send a get request to /orgs/:org/repos. Request includes :
 • Path param org with value cucumber
• Query param sort with value full_name
• Query param direction with value desc
2. Verify that all repositories are listed in reverser alphabetical order based on the value of the field
name
   */
  @Test
  @DisplayName("Descending order by full_name sort")
  public void descOrdFullName(){
    Response response = given().
         pathParams("org", "cucumber").
         queryParam("sort", "full_name").
         queryParam("direction", "desc").
         when().
         get("/orgs/{org}/repos");


    List<String> name = response.jsonPath().getList("full_name");
//    System.out.println("name = " + name);
    
    List<String> reversed = new ArrayList<>(name);
    Collections.sort(reversed,Collections.reverseOrder());

    assertThat(reversed,equalTo(name));
  }
/*
Default sort
1. Send a get request to /orgs/:org/repos. Request includes :
 • Path param org with value cucumber
2. Verify that by default all repositories are listed in descending order based on the value of the field
created_at
 */
  
  @Test
  public void defaultSort(){
    Response response = given().pathParams("org", "cucumber").
         when().
         get("/orgs/{org}/repos");

    List<String> dates = response.jsonPath().getList("created_at");

    List<String> desc = new ArrayList<>(dates);
    Collections.sort(desc,Collections.reverseOrder());

    assertThat(desc,equalTo(dates));
  }
}
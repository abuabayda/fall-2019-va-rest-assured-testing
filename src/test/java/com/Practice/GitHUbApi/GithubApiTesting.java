package com.Practice.GitHUbApi;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    JsonPath jsonPath = response.jsonPath();
    String login = jsonPath.getString("login");
    String name = jsonPath.getString("name");
    String id = jsonPath.getString("id");
    System.out.println(response.prettyPeek());
    assertThat(response.statusCode(), is(200));
    assertThat(response.getContentType(), equalTo("application/json; charset=utf-8"));
    assertThat(login, equalTo("cucumber"));
    assertThat(name, equalTo("Cucumber"));
    assertThat(id, is("320565"));
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
    System.out.println(response.prettyPeek());
    assertThat(response.statusCode(), is(415));
    assertThat(response.getContentType(), equalTo("application/json; charset=utf-8"));
    Assertions.assertTrue(response.statusLine().contains("Unsupported Media Type"));
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

    JsonPath jasonPath = response.jsonPath();
    String public_repos = jasonPath.getString("public_repos");
    assertThat(public_repos, equalTo("90"));

    response = given().pathParams("org", "cucumber").
         queryParam("per_page", "100").
         when().get("/orgs/{org}/repos").prettyPeek();

    List<Object> list = response.jsonPath().getList("owner.id");

    assertThat(Integer.parseInt(public_repos), equalTo(list.size()));


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
    Response response = given().pathParams("org", "cucumber")
         .when().get("/orgs/{org}/repos").prettyPeek();
    //getting ids
    JsonPath jasonPath = response.jsonPath();
    List<Integer> ids = jasonPath.getList("id");
    assertThat(ids.size(), is(30));

    //verifying ids are unique
    Set<Integer> uniqueIds = new LinkedHashSet<>();
    uniqueIds.addAll(ids);
    System.out.println(uniqueIds);
    Assertions.assertTrue(uniqueIds.containsAll(ids));

    //second node_id
    response = given().pathParams("org", "cucumber").
         when().get("/orgs/{org}/repos").prettyPeek();
    jasonPath = response.jsonPath();
    //getting node_ids
    List<Integer> node_id = jasonPath.getList("node_id");
    //remove dupicate and checking if they're unique
    Set<Integer> uniqueNodeId = new LinkedHashSet<Integer>();
    uniqueNodeId.addAll(node_id);
    //compare if they are same
    Assertions.assertTrue(node_id.containsAll(uniqueNodeId));

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
    Response response = given().pathParams("org", "cucumber").
         when().get("/orgs/{org}");

    //grab value of ids
    JsonPath jsonPath = response.jsonPath();
    String ids = jsonPath.getString("id");
    System.out.println("ids = " + ids);

    //
    response = given().pathParams("org", "cucumber").
         queryParam("owner", "id").
         when().get("/orgs/{org}/repos");
    jsonPath = response.jsonPath();

    //getting each owner id in all repos owner
    List<Integer> innerIds = jsonPath.getList("owner.id");
    System.out.println("innerIds = " + innerIds);
    //verify all owner ids equal first id
    for (int i = 0; i <= innerIds.size() - 1; i++) {
      assertThat(innerIds.get(i), is(Integer.parseInt(ids)));
    }
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
         when().get("/orgs/{org}/repos");

//    System.out.println(response.prettyPeek());

    JsonPath jsonPath = response.jsonPath();
    List<Map<String,Object>> listNames = jsonPath.getList("full_name");
//    System.out.println("Full Name: " + listNames);

    System.out.println();
    List<Map<String, Object>> sorted = new ArrayList<>();
    sorted.addAll(listNames);

//    System.out.println("sorted = " + sorted);
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
  public void descOrdFullName(){
    Response response = given().
         pathParams("org", "cucumber").
         queryParam("sort", "full_name").
         queryParam("direction", "desc").
         when().
         get("/orgs/{org}/repos");
   JsonPath jsonPath = response.jsonPath();
    List<Map<String,Object>> name = jsonPath.getList("name");
    System.out.println("name = " + name);
    
    List<Map<String,Object>> reversed = new ArrayList<>();
    reversed.addAll(name);

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

    JsonPath jsonPath = response.jsonPath();
    List<Map<String,Object>> created_at = jsonPath.getList("created_at");
    System.out.println("created_at = " + created_at);

    List<Map<String,Object>> desc = new ArrayList<>();
    desc.addAll(created_at);

    assertThat(desc,equalTo(created_at));
  }
}
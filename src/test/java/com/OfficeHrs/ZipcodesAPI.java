package com.OfficeHrs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZipcodesAPI {
  @BeforeAll
  public static void setup() {
    baseURI = "https://www.zipcodeapi.com/API";
  }
  @Test
  public void test1() {
    given().pathParams("api_key", "tzrViKLgmbJFy1UpzX8EFzxXr63WTg5D4BgDUxWaIeswT8FPXv60qpykag6twfmB").
         pathParams("zip_code1", "22041").
         pathParams("zip_code2", "22306").
         pathParams("units", "miles").
         when().
         get("/rest/{api_key}/distance.jason/{zip_code1}/{zip_code2}/{units}").
         prettyPeek().
         then().
         statusCode(200).
         body("distance", not(emptyOrNullString()));
  }
  /*
test zipcode 29577
verify city Myrtle Beach
 */
  @Test
  public void testMB() {
    given().
         pathParam("api_key", "tzrViKLgmbJFy1UpzX8EFzxXr63WTg5D4BgDUxWaIeswT8FPXv60qpykag6twfmB").
         pathParam("zip_code", "29577").
         pathParam("units", "degrees").
         when().
         get("/rest/{api_key}/info.json/{zip_code}/{units}").
         prettyPeek().
         then().
         assertThat().statusCode(is(200)).
         and().assertThat().body("city",is("Myrtle Beach"));
  }
}
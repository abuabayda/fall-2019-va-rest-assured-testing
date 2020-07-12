package com.cbt.tests.day11_cookie_and_extend_report;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.DBUtil.ExtentConfig;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExtentConfig.class)

public class CookieTest {
    /*
    login to zero bank by providing username , password
    user_login: username, user_password: password
     */
    @DisplayName("Get a cookie and access the reports using cookie")
    @Test
    public void getCookie() {
        ExtentConfig.test.info("Send request a cookie");
        Response postResponse = given().
                formParam("user_login", "username").
                formParam("user_password", "password").
                log().ifValidationFails().
            when().
                post("http://zero.webappsecurity.com/signin.html");
        //cookie is returned as part of response
        //getDetailedCookie -> returns cookie with given name
        Cookie cookie = postResponse.getDetailedCookie("JSESSIONID");
        ExtentConfig.test.info("Cookie = " + cookie.toString());
        System.out.println(cookie.getName());

        // accessing app using cookie
        //send request with cookie attached
        given().
                cookie(cookie).
        when().
                get("http://zero.webappsecurity.com/bank/online-statements.html").
                prettyPeek().
        then().
                statusCode(200).
                body(not(containsString("login")));
        ExtentConfig.test.info("Test Done");
    }

    @DisplayName("Make your own a cookie and access the reports using cookies")
    @Test
    public void makeCookie() {
        //make a cookie
        ExtentConfig.test.info("Make a cookie");
        Cookie cookie = new Cookie.Builder("JSESSIONID", "7AD69F38").build();
        ExtentConfig.test.info("Cookie = " + cookie.toString());
        given().
                cookie(cookie).
        when().
                get("http://zero.webappsecurity.com/bank/online-statements.html").
                prettyPeek().
        then().
                statusCode(200).
                body(not(containsString("login")));
        ExtentConfig.test.info("Done");
    }
}

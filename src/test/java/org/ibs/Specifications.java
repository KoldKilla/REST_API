package org.ibs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class Specifications {
    static String sessionID;

    public static RequestSpecification requestSpecification(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .build();
    }

    public static ResponseSpecification responseSpecification(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public static void installSpecification(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    public static void getInfo (String foodName) {
        given()
                .header ("Accept", "application/json")
                .header ("Content-Type", "application/json")
                .cookie ("JSESSIONID", sessionID)
                .when ()
                .get ("/food")
                .then ()
                .log ().all ()
                .assertThat ()
                .body ("name", hasItem(foodName));
    }

    public static Response getFood () {
        Response response = given ()
                .header ("Accept", "application/json")
                .header ("Content-Type", "application/json")
                .when ()
                .get ("/food");
        sessionID = response.getCookie ("JSESSIONID");
        return response;
    }
}

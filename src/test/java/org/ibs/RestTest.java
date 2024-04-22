package org.ibs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pojos.UserPojo;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.ibs.Specifications.sessionID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTest {

    @Test
    void addExoticVegetable() {

      Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
         Specifications.responseSpecification(200));

      Specifications.getFood();

        Response response = given()
                .header ("Accept", "application/json")
                .header ("Content-Type", "application/json")
                .cookie ("JSESSIONID", sessionID)
                .contentType(ContentType.JSON)
                 .body("{\n" +
                         "  \"name\": \"test1\",\n" +
                         "  \"type\": \"VEGETABLE\",\n" +
                         "  \"exotic\": true\n" +
                         "}")
                .when()
                .log().all()
                .post("/food")
                .then()
                .log().all()
                .extract().response();

        Specifications.getInfo("test1");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void addNonExoticVegetable() {

        Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));

        Specifications.getFood();

        Response response = given()
                .header ("Accept", "application/json")
                .header ("Content-Type", "application/json")
                .cookie ("JSESSIONID", sessionID)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"name\": \"test2\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\": false\n" +
                        "}")
                .when()
                .log().all()
                .post("/food")
                .then()
                .log().all()
                .extract().response();

        Specifications.getInfo("test2");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void addNonExoticFruit() {
        Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));

        Specifications.getFood();

        Response response = given()
                .header ("Accept", "application/json")
                .header ("Content-Type", "application/json")
                .cookie ("JSESSIONID", sessionID)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"name\": \"test3\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": false\n" +
                        "}")
                .when()
                .log().all()
                .post("/food")
                .then()
                .log().all()
                .extract().response();

        Specifications.getInfo("test3");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void addExoticFruit() {
        Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));

        Specifications.getFood();

        Response response = given()
                .header ("Accept", "application/json")
                .header ("Content-Type", "application/json")
                .cookie ("JSESSIONID", sessionID)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"name\": \"test4\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .when()
                .log().all()
                .post("/food")
                .then()
                .log().all()
                .extract().response();

        Specifications.getInfo("test4");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getAllItems() {
        Response response = given()
                .baseUri("http://localhost:8080/api")
                .when()
                .log().all()
                .get("/food")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .extract().response();
        sessionID = response.getCookie ("JSESSIONID");
        assertEquals(200, response.getStatusCode());

        System.out.println("\nОТВЕТ");
        System.out.println(response.getBody().prettyPrint());
    }
}

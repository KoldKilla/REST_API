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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTest {

    @Test
    void addExoticVegetable() {

      Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
         Specifications.responseSpecification(200));


        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .basePath("/food")
                 .body("{\n" +
                         "  \"name\": \"test1\",\n" +
                         "  \"type\": \"VEGETABLE\",\n" +
                         "  \"exotic\": true\n" +
                         "}")
                .when()
                .log().all()
                .post()
                .then()
                .assertThat()
                .extract().response();

        assertEquals(200, response.getStatusCode());

    }

    @Test
    void addNonExoticVegetable() {

        Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));


        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .basePath("/food")
                .body("{\n" +
                        "  \"name\": \"test2\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\": false\n" +
                        "}")
                .when()
                .log().all()
                .post()
                .then()
                .assertThat()
                .extract().response();

        assertEquals(200, response.getStatusCode());

    }

    @Test
    void addNonExoticFruit() {

        Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));


        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .basePath("/food")
                .body("{\n" +
                        "  \"name\": \"trst3\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": false\n" +
                        "}")
                .when()
                .log().all()
                .post()
                .then()
                .assertThat()
                .extract().response();

        assertEquals(200, response.getStatusCode());

    }

    @Test
    void addExoticFruit() {

        Specifications.installSpecification(Specifications.requestSpecification("http://localhost:8080/api"),
                Specifications.responseSpecification(200));


        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .basePath("/food")
                .body("{\n" +
                        "  \"name\": \"test4\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .when()
                .log().all()
                .post()
                .then()
                .assertThat()
                .extract().response();

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
                .body("createdAt", not(emptyOrNullString()))
                .extract().response();

        assertEquals(200, response.getStatusCode());

        System.out.println("\nОТВЕТ");
        System.out.println(response.getBody().prettyPrint());
    }
}

//        given()
//                .baseUri("https://reqres.in/api")
//                .basePath("/users")
//                .contentType(ContentType.JSON)
//                .when()
//                .log().all()
//                .get("/api/users/2")
//                .then()
//                .log().all()
//                .and()
//                .assertThat()
//                .statusCode(200)
//                .extract().asString()
                //.body("data.id", equalTo(2))


//        Assertions.assertEquals(200, response.getStatusCode());
//        Assertions.assertEquals(2, response.jsonPath().getInt("data.id"));


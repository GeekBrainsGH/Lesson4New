package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetRecipesLesson3Test {

    private final String apiKey = "7cd6183314c94478924f5298a2e54b8f";
    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;



    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("addRecipeInformation", true)
                .build();
    }

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void CuisinePositiveTest() {
        JsonPath response = given().spec(requestSpecification)
                .queryParam("cuisine", "British")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        for (int i = 0; i < 10; i++) {
            assertThat(response.get("results[" + i + "].cuisines"), hasItem("British"));
        }
    }

    @Test
    void NumberPositiveTest() {
        given().spec(requestSpecification)
                .queryParam("number", "1")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .spec(responseSpecification).body("number", equalTo(1));
    }

    @Test
    void SortMaxTimePositiveTest() {
        given().spec(requestSpecification)
                .queryParam("sort", "time")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .spec(responseSpecification).body("results[0].readyInMinutes", equalTo(4320));
    }

    @Test
    void DietPositiveTest() {
        JsonPath response = given().spec(requestSpecification)
                .queryParam("diet","primal")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        for (int i = 0; i < 10; i++) {
            assertThat(response.get("results[" + i + "].diets"), hasItem("primal"));
        }
    }

    @Test
    void WrongTypeNegativeTest() {
        given().spec(requestSpecification)
                .queryParam("maxReadyTime", "vyjuj")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(404);
    }
    }




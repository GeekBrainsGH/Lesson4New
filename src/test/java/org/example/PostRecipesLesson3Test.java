package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostRecipesLesson3Test {

    private final String apiKey = "7cd6183314c94478924f5298a2e54b8f";
    RequestSpecification requestSpecification = null;
    ResponseSpecification responseSpecification = null;

    @BeforeEach
    void beforeTest() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .setContentType(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .build();
    }

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void CuisineEuropeanPositiveTest() {
        ResponseCuisine response = given().spec(requestSpecification)
                .when()
                .formParam("title", "Strawberries and Cream Scones")
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisine(), equalTo("European"));
        assertThat(response.getCuisines(), hasItems("European", "Scottish", "British", "English"));
    }


    @Test
    void CuisineAfricanPositiveTest() {
        ResponseCuisine response = given().spec(requestSpecification)
                .when()
                .formParam("title", "African Chicken Peanut Stew")
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisines(), hasItem("African"));
        assertThat(response.getCuisine(), equalTo("African"));}

    @Test
    void CuisineScandinavianPositiveTest() {
        ResponseCuisine response = given().spec(requestSpecification)
                .when()
                .formParam("title", "Baked Swedish Pancake")
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisines(), hasItems("Scandinavian", "European", "Nordic"));
        assertThat(response.getCuisine(), equalTo("Scandinavian"));}

    @Test
    void CuisineJapanesePositiveTest() {
        ResponseCuisine response = given().spec(requestSpecification)
                .when()
                .formParam("title", "Miso Soup With Thin Noodles")
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisines(), hasItems("Japanese", "Asian"));
        assertThat(response.getCuisine(), equalTo("Japanese"));}

    @Test
    void CuisineCreolePositiveTest() {
        ResponseCuisine response = given().spec(requestSpecification)
                .when()
                .formParam("title", "Red Kidney Bean Jambalaya")
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseCuisine.class);
        assertThat(response.getCuisines(), hasItems("Creole","Cajun"));
        assertThat(response.getCuisine(), equalTo("Creole"));}


}



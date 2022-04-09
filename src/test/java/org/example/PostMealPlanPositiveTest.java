package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class PostMealPlanPositiveTest {


    private final String hash = "d2a99c96d86d52173b095fbe8ef15cf7cd675646";
    private final String username = "your-users-name310";
    private final String apiKey = "7cd6183314c94478924f5298a2e54b8f";
    private String id;
    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;


    @BeforeEach
    void beforeTest() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("hash", hash)
                .addQueryParam("username", username)
                .addQueryParam("apiKey", apiKey)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .build();
    }

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    void tearDown() {
        given()
                .queryParam("hash", hash)
                .queryParam("apiKey", apiKey)
                .when()
                .delete("https://api.spoonacular.com/mealplanner/" + username + "/shopping-list/items/" + id)
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    void postAddToShoppingListTest() {
        RequestAddProduct requestAddProduct = new RequestAddProduct();
        requestAddProduct.setItem("17 Carrots");
        requestAddProduct.setAisle("vegetables");
        requestAddProduct.setParse(true);

        ResponseAddProduct responseAddProduct = given().spec(requestSpecification)
                .body(requestAddProduct)
                .when()
                .post("https://api.spoonacular.com/mealplanner/"+ username + "/shopping-list/items")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseAddProduct.class);
        assertThat(responseAddProduct.getAisle(), equalTo("vegetables"));
        assertThat(responseAddProduct.getName(), equalTo("carrots"));
        id = responseAddProduct.getId().toString();
    }

    @Test
    void postAddWithParseFalseTest() {
        RequestAddProduct requestAddProduct = new RequestAddProduct();
        requestAddProduct.setItem("5 packs of shampoo");
        requestAddProduct.setParse(false);

        ResponseAddProduct responseAddProduct = given().spec(requestSpecification)
                .body(requestAddProduct)
                .when()
                .post("https://api.spoonacular.com/mealplanner/"+ username + "/shopping-list/items")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(ResponseAddProduct.class);
        assertThat(responseAddProduct.getAisle(), equalTo("Non-Food Items"));
        assertThat(responseAddProduct.getName(), equalTo("5 packs of shampoo"));
        assertThat(responseAddProduct.getPantryItem(), equalTo(false));
        id = responseAddProduct.getId().toString();
    }
}

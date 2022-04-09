package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class GetMealPlanTest {

    private final String hash = "d2a99c96d86d52173b095fbe8ef15cf7cd675646";
    private final String username = "your-users-name310";
    private final String apiKey = "7cd6183314c94478924f5298a2e54b8f";

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void getShoppingListTest() {
        given()
                .queryParam("hash", hash)
                .queryParam("username", username)
                .queryParam("apiKey", apiKey)
                .expect()
                .body(containsString("aisles"))
                .when()
                .get("https://api.spoonacular.com/mealplanner/" + username + "/shopping-list")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}

package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.Steps;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Tag("API")
@DisplayName("/register tests")

public class RegresTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    Steps steps = new Steps();

    @Test
    void successfulUpdateTest() {
        String authData = steps.createAuthData("morpheus", "zion resident");

        steps.sendPutRequest("/api/users/2", authData)
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", notNullValue());
    }

    @Test
    void successfulRegisterTest() {
        String authData = steps.createAuthData("eve.holt@reqres.in", "pistol");

        String token = steps.sendPostRequest("/api/register", authData)
                .statusCode(200)
                .body("id", is(4))
                .extract().path("token");

        steps.checkToken(token);
    }

    @Test
    void unsuccessfulRegisterMissingPasswordTest() {
        String authData = steps.createAuthDataWithoutPassword("sydney@fife");

        steps.sendPostRequest("/api/register", authData)
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulRegisterBadRequestTest() {
        String authData = steps.createAuthData("eve.holt@reqres.in3435262", "pistol");

        steps.sendPostRequest("/api/register", authData)
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void unsuccessfulRegisterMissingCredentialsTest() {
        String authData = "{}";

        steps.sendPostRequest("/api/register", authData)
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
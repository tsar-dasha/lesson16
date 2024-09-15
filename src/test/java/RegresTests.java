import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import specs.Steps;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegresTests {

    Steps steps = new Steps();

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void successfulUpdateTest() {
        String authData = steps.createAuthData("morpheus", "zion resident");

        steps.sendPutRequest("/users/2", authData)
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", notNullValue());
    }

    @Test
    void successfulRegisterTest() {
        String authData = steps.createAuthData("eve.holt@reqres.in", "pistol");

        String token = steps.sendPostRequest("/register", authData)
                .statusCode(200)
                .body("id", is(4))
                .extract().path("token");

        steps.checkToken(token);
    }

    @Test
    void unsuccessfulRegisterMissingPasswordTest() {
        String authData = steps.createAuthDataWithoutPassword("sydney@fife");

        steps.sendPostRequest("/register", authData)
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulRegisterBadRequestTest() {
        String authData = steps.createAuthData("eve.holt@reqres.in3435262", "pistol");

        steps.sendPostRequest("/register", authData)
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void unsuccessfulRegisterMissingCredentialsTest() {
        String authData = "{}";

        steps.sendPostRequest("/register", authData)
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
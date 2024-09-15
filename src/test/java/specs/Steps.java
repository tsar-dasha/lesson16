package specs;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class Steps {

    @Step("Создаем данные для авторизации")
    public String createAuthData(String name, String job) {
        return String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
    }

    @Step("Создаем данные для авторизации без пароля")
    public String createAuthDataWithoutPassword(String email) {
        return String.format("{\"email\": \"%s\"}", email);
    }

    @Step("Отправляем PUT запрос на {endpoint}")
    public io.restassured.response.ValidatableResponse sendPutRequest(String endpoint, String body) {
        return given()
                .body(body)
                .contentType(JSON)
                .log().uri()
                .when()
                .put(endpoint)
                .then()
                .log().status()
                .log().body();
    }

    @Step("Отправляем POST запрос на {endpoint}")
    public io.restassured.response.ValidatableResponse sendPostRequest(String endpoint, String body) {
        return given()
                .body(body)
                .contentType(JSON)
                .log().uri()
                .when()
                .post(endpoint)
                .then()
                .log().status()
                .log().body();
    }

    @Step("Проверяем валидность токена")
    public void checkToken(String token) {
        assertThat(token)
                .isNotNull()
                .hasSizeGreaterThan(10)
                .matches("[a-zA-Z0-9]+");
    }
}
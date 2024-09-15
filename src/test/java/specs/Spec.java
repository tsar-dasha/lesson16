package specs;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class Spec {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType(JSON)
            .build();
}

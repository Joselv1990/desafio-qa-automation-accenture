package br.com.joselv.desafioqa.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Client responsável pelos endpoints da BookStore da DemoQA.
 * Swagger: https://demoqa.com/swagger/
 */
public class BookStoreClient {

    private static final String BASE_URI = "https://demoqa.com";

    public Response listarLivros() {
        return given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Books");
    }

    public Response alugarLivros(String userId, List<String> isbns, String token) {
        List<Map<String, String>> collectionOfIsbns = isbns.stream()
                .map(isbn -> Map.of("isbn", isbn))
                .toList();

        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(Map.of("userId", userId, "collectionOfIsbns", collectionOfIsbns))
                .when()
                .post("/BookStore/v1/Books");
    }
}

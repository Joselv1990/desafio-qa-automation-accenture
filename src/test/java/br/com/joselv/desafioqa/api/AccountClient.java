package br.com.joselv.desafioqa.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Client responsável pelos endpoints de conta (Account) da DemoQA.
 * Swagger: https://demoqa.com/swagger/
 */
public class AccountClient {

    private static final String BASE_URI = "https://demoqa.com";

    public Response criarUsuario(String userName, String password) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(Map.of("userName", userName, "password", password))
                .when()
                .post("/Account/v1/User");
    }

    public Response gerarToken(String userName, String password) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(Map.of("userName", userName, "password", password))
                .when()
                .post("/Account/v1/GenerateToken");
    }

    public Response verificarAutorizacao(String userName, String password) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(Map.of("userName", userName, "password", password))
                .when()
                .post("/Account/v1/Authorized");
    }

    public Response buscarDetalhesUsuario(String userId, String token) {
        return given()
                .baseUri(BASE_URI)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId);
    }
}

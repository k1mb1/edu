package me.k1mb.edu.controller.config;

import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;

@Component
public class TokenUtility {
    static String adminAccessToken;
    static String userAccessToken;

    @Value("${server.keycloak.admin.username}")
    String adminUsername;
    @Value("${server.keycloak.admin.password}")
    String adminPassword;
    @Value("${server.keycloak.user.username}")
    String userUsername;
    @Value("${server.keycloak.user.password}")
    String userPassword;
    @Value("${server.keycloak.grant_type}")
    String grantType;
    @Value("${server.keycloak.client_id}")
    String clientId;
    @Value("${server.keycloak.client_secret}")
    String clientSecret;
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String baseKeycloakUrl;

    public String getAdminAccessToken() {
        if (adminAccessToken == null) {
            adminAccessToken = fetchAccessToken(adminUsername, adminPassword);
        }
        return adminAccessToken;
    }

    public String getUserAccessToken() {
        if (userAccessToken == null) {
            userAccessToken = fetchAccessToken(userUsername, userPassword);
        }
        return userAccessToken;
    }

    String fetchAccessToken(String username, String password) {
        Response response = given()
            .param("grant_type", grantType)
            .param("client_id", clientId)
            .param("client_secret", clientSecret)
            .param("username", username)
            .param("password", password)
            .when()
            .post(baseKeycloakUrl + "/protocol/openid-connect/token")
            .then()
            .statusCode(OK.value())
            .contentType(JSON)
            .extract()
            .response();

        return response.jsonPath().getString("access_token");
    }
}
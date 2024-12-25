package me.k1mb.edu.controller;

import io.restassured.RestAssured;
import me.k1mb.edu.controller.config.TestDatabaseConfig;
import me.k1mb.edu.controller.config.TestPostgreSQLContainer;
import me.k1mb.edu.controller.config.TokenUtility;
import me.k1mb.edu.controller.model.CourseRequest;
import me.k1mb.edu.controller.model.LessonRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(TestDatabaseConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class CourseControllerAssuredTest {

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer = TestPostgreSQLContainer.getInstance();
    static String createdCourseId;
    static String createdLessonId;
    final CourseRequest courseRequest = new CourseRequest(
        "Основы программирования",
        "Курс по основам программирования на языке Java",
        UUID.fromString("00a8998c-271d-4f1a-968f-821135c11a88"));// TODO UUID переделывать по тестовому пользователю
    @Autowired
    TokenUtility tokenUtility;
    @LocalServerPort
    int port;
    String adminToken;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        adminToken = tokenUtility.getAdminAccessToken();
    }

    @Test
    @Order(1)
    void testCreateCourse() {
        createdCourseId = given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .body(courseRequest)
            .when()
            .post("/api/v1/courses")
            .then()
            .statusCode(CREATED.value())
            .body("title", equalTo(courseRequest.title()))
            .body("description", equalTo(courseRequest.description()))
            .body("authorId", equalTo(courseRequest.authorId().toString()))
            .body("id", notNullValue())
            .contentType(JSON)
            .extract()
            .path("id");
    }

    @Test
    @Order(2)
    void testGetCourse() {
        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/api/v1/courses/{id}", createdCourseId)
            .then()
            .statusCode(OK.value())
            .body("id", equalTo(createdCourseId))
            .body("title", equalTo("Основы программирования"))
            .body("description", equalTo("Курс по основам программирования на языке Java"));
    }

    @Test
    @Order(3)
    void testUpdateCourse() {
        CourseRequest updatedCourseRequest = new CourseRequest(
            "Обновленный курс",
            "Обновленное описание курса",
            UUID.fromString("00a8998c-271d-4f1a-968f-821135c11a88")); // TODO UUID переделывать по тестовому пользователю

        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .body(updatedCourseRequest)
            .when()
            .put("/api/v1/courses/{courseId}", createdCourseId)
            .then()
            .statusCode(OK.value())
            .body("title", equalTo(updatedCourseRequest.title()))
            .body("description", equalTo(updatedCourseRequest.description()));
    }

    @Test
    @Order(4)
    void testGetAllCourses() {
        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/api/v1/courses")
            .then()
            .statusCode(OK.value())
            .contentType(JSON)
            .body("size()", greaterThan(0));
    }


    @Test
    @Order(5)
    void testCreateLesson() {
        LessonRequest lessonRequest = new LessonRequest(
            UUID.fromString(createdCourseId),
            "Введение в Java",
            "Основные концепции языка Java",
            10);

        createdLessonId = given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .body(lessonRequest)
            .when()
            .post("/api/v1/courses/{courseId}/lessons", createdCourseId)
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("title", equalTo(lessonRequest.title()))
            .body("description", equalTo(lessonRequest.description()))
            .body("id", notNullValue())
            .extract()
            .path("id");
    }

    @Test
    @Order(6)
    void testGetAllLessonsByCourseId() {
        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/api/v1/courses/{courseId}/lessons", createdCourseId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", greaterThan(0));
    }

    @Test
    @Order(7)
    void testDeleteLesson() {
        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .delete("/api/v1/courses/{courseId}/lessons/{lessonId}", createdCourseId, createdLessonId)
            .then()
            .statusCode(NO_CONTENT.value());
    }

    @Test
    @Order(8)
    void testDeleteCourse() {
        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .delete("/api/v1/courses/{courseId}", createdCourseId)
            .then()
            .statusCode(NO_CONTENT.value());
    }

    @Test
    @Order(9)
    void testGetCourseException() {
        given()
            .contentType(JSON)
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/api/v1/courses/{courseId}", createdCourseId)
            .then()
            .statusCode(NOT_FOUND.value());
    }

}
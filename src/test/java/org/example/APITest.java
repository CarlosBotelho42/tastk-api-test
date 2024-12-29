package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8081/tasks-backend";
    }

    @Test
    public void shouldReturnTasks() {
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldAddTaskWithSuccess(){
        RestAssured.given()
                .body(" { \"task\": \"Teste via API\", \"dueDate\": \"2024-12-29\" }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201);
    }

    @Test
    public void shouldNotAddInvalidTask(){
        RestAssured.given()
                .body(" { \"task\": \"Teste via API\", \"dueDate\": \"2014-12-29\" }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}

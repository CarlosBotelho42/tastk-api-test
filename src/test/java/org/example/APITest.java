package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.client.utils.DateUtils;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

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

        LocalDate localDate = LocalDate.now().plusDays(1);
        String tomorrowDate = String.valueOf(localDate);

        RestAssured.given()
                .body(" { \"task\": \"Teste via API\", \"dueDate\": \""+ tomorrowDate + "\" }")
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

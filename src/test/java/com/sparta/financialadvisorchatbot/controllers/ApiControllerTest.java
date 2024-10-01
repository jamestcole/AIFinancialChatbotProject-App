package com.sparta.financialadvisorchatbot.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApiControllerTest {

    @BeforeEach
    public void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080/api/sg-financial-chatbot/v1.0";
    }

    @Test
    public void testGetIndividualRequestResponse() {
        int conversationId = 1;
        String dateTime = "2023-09-25T15:30:00";

        given()
                .pathParam("id", conversationId)
                .queryParam("createdAt", dateTime)
                .when()
                .get("/conversations/{id}/messages")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id.conversationId", equalTo(conversationId))
                .body("_links.'Entire conversation link:'.href", containsString("/conversations/" + conversationId));
    }

    @Test
    public void testGetEntireSingleConversation() {
        int conversationId = 1;

        given()
                .pathParam("id", conversationId)
                .when()
                .get("/conversations/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].id.conversationId", equalTo(conversationId));
    }

    @Test
    public void testGetAllConversations() {
        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/conversations")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetAllConversationsBetweenDates() {
        String fromDate = "2023-09-20";
        String toDate = "2023-09-30";

        given()
                .queryParam("from", fromDate)
                .queryParam("to", toDate)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/conversations/dates")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].createdAt", allOf(greaterThanOrEqualTo(fromDate), lessThanOrEqualTo(toDate)));
    }

    @Test
    public void testGetAllConversationsContainingKeyword() {
        String keyword = "finance";

        given()
                .queryParam("keyword", keyword)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/conversations/containing")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].conversationId", notNullValue());
    }
}

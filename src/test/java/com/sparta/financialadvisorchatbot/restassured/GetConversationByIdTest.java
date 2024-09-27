package com.sparta.financialadvisorchatbot.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetConversationByIdTest {

    private static Response response;


    @BeforeAll
    public static void setup() {
        response = RestAssured.given().baseUri().basePath().headers(Map.of(

        )).pathParams(Map.of()).when().log().all().get().thenReturn();
    }

    @Test
    void getConversationByIdCheckStatusCode(){
        assertEquals(200, response.getStatusCode());
    }
}

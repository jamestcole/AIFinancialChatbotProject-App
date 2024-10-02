package com.sparta.financialadvisorchatbot.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OpenAiRequestTest {

    private OpenAiRequest openAiRequest;

    @BeforeEach
    void setUp() {
        openAiRequest = new OpenAiRequest();

        OpenAiRequest.Message message = new OpenAiRequest.Message("test role", "test content");
        message.setRole("Test Role");
        message.setContent("Test Content");

        openAiRequest.setMessages(List.of(message));
    }

    @Test
    void testGetMessagesContent(){
        String expected = "Test Content";
        String actual = openAiRequest.getMessages().getFirst().getContent();
        assertEquals(expected, actual);
    }

    @Test
    void testGetMessagesRole(){
        String expected = "Test Role";
        String actual = openAiRequest.getMessages().getFirst().getRole();
        assertEquals(expected, actual);
    }
}

package com.sparta.financialadvisorchatbot.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OpenAiResponseTest {

    private OpenAiResponse openAiResponse;

    @BeforeEach
    void setUp() {
        openAiResponse = new OpenAiResponse();
        OpenAiResponse.Choice choice = new OpenAiResponse.Choice();
        OpenAiResponse.Message message = new OpenAiResponse.Message();
        message.setContent("Test Content");
        message.setRole("Test Role");
        choice.setMessage(message);
        openAiResponse.setChoices(List.of(choice));
    }
    @Test
    void testGetChoicesRole() {
        String expected = "Test Role";
        String actual = openAiResponse.getChoices().getFirst().getMessage().getRole();
        assertEquals(expected,actual);
    }
    @Test
    void testGetChoicesContent() {
        String expected = "Test Content";
        String actual = openAiResponse.getChoices().getFirst().getMessage().getContent();
        assertEquals(expected,actual);
    }
}

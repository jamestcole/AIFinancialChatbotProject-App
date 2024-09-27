package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.service.ConversationService;
import com.sparta.financialadvisorchatbot.service.FaqService;
import com.sparta.financialadvisorchatbot.service.OpenAiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {

    @Mock
    OpenAiService openAiService;

    @Mock
    FaqService faqService;

    @InjectMocks
    ConversationService conversationService;

    @Test //for old code may need rewriting
    void testProcessUserInputReturnsGptResponseIfStateIsAwaitingClarification() throws IOException {
        String expected = "Test GPT response" + "\nCan I help you with anything else";
        when(openAiService.getChatResponse(anyString(), anyString())).thenReturn("Test GPT response");
        String actual = conversationService.processUserInput("1","What is a dividend");
        Assertions.assertEquals(expected,actual);
    }
    @Test //for old code may need rewriting
    void testProcessUserInputReturnsFaQResponseIfFAQResponseExists() throws IOException {

        String expected = "Test FAQ Response" + "\nCan I help you with anything else";
        when(faqService.findClosestFaq("How can I save money?")).thenReturn("Test FAQ Response");
        String actual = conversationService.processUserInput("1","How can I save money?");
        Assertions.assertEquals(expected,actual);

    }
    @Test //for old code may need rewriting
    void testProcessUserInputReturnsGptResponseIfFAQResponseDoesNotExist() throws IOException {
        String expected = "Test GPT response" + "\nCan I help you with anything else";
        when(openAiService.getChatResponse(anyString(), anyString())).thenReturn("Test GPT response");
        String actual = conversationService.processUserInput("1","What is a dividend");
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void testGetLatestConversationIdReturnsLatestConversationId(){

    }
    @Test
    void testGetConversationReturnsCurrentConversation(){

    }
    @Test
    void testGetFaqResponseReturnsFaqResponseIfPresent(){

    }
    @Test
    void testGetFaqResponseReturnsGptResponseIfNoMatchToFaq(){

    }
    @Test
    void testGetStartMessageReturnsStartMessage(){

    }
}

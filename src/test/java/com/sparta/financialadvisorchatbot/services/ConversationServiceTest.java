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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {

    //May also need to mock ConversationId

    @Mock
    OpenAiService openAiService;

    @Mock
    ConversationHistoryRepository conversationHistoryRepository;

    @Mock
    FaqService faqService;

    @InjectMocks
    ConversationService conversationService;

    /** Methods to test
     String processUserInput()
     String handleUserResponse()
     ConversationHistory getLatestConversation()
     Integer getLatestConversationId()
     ConversationHistory getConversationById()
     String getStartMessage()
     ConversationHistory startNewConversation()
     void addMessageToConversation(Integer convId, String message)
     void updateConversation(int latestId, ArrayList<String> conversation)
     **/

    @Test
    void testGetStartMessageReturnsStartMessage(){
        String expected = "Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?";
        String actual = conversationService.getStartMessage();
        assertEquals(expected, actual);
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
    void testGetFaqResponseReturnsGptResponseIfNoMatchToFaq() {

    }
}

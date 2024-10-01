package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.FinancialAdvisorChatbotApplication;
import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.exceptions.ResponseParsingError;
import com.sparta.financialadvisorchatbot.models.OpenAiResponse;
import com.sparta.financialadvisorchatbot.service.OpenAiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = FinancialAdvisorChatbotApplication.class)
@TestPropertySource(properties = {
        "openai.api.key=test-api-key",
        "openai.api.endpoint=test-endpoint",
        "openai.api.prompt=test-prompt"
})
@ExtendWith(MockitoExtension.class)
public class OpenAiServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private OpenAiService openAiService;

    OpenAiResponse openAiResponse;
    OpenAiResponse openAiResponse2;

    @BeforeEach
    void setUp() {

        OpenAiResponse.Message message = new OpenAiResponse.Message();
        message.setRole("Financial Advisor Chatbot");
        message.setContent("Test Bot Response");

        OpenAiResponse.Choice choice = new OpenAiResponse.Choice();
        choice.setMessage(message);

        openAiResponse = new OpenAiResponse();
        openAiResponse.setChoices(List.of(choice));
        openAiResponse2 = new OpenAiResponse();
        openAiResponse2.setChoices(List.of());

    }

    @Test
    public void testGetChatResponseIsSuccessful(){
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Open AI Test Response\"}}]}";
        String expected = "Test Bot Response";

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(openAiResponse, HttpStatus.OK));

        String userMessage = "This is a test message";
        ArrayList<ConversationHistory> conversationHistory = new ArrayList<>();
        int conversationId = 1;
        String actual = openAiService.getResponse(userMessage, conversationHistory, conversationId);
        Assertions.assertEquals(expected,actual);
    }
    @Test
    public void testGetChatResponseThrowsResponseParsingErrorIfNoChoiceFound(){
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Open AI Test Response\"}}}]}";

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(openAiResponse2, HttpStatus.OK));

        String userMessage = "This is a test message";
        ArrayList<ConversationHistory> conversationHistory = new ArrayList<>();
        int conversationId = 1;
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage, conversationHistory, conversationId);
        });
    }
    @Test
    public void testGetChatResponseThrowsErrorIfRequestNotSuccessful(){

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        String userMessage = "This is a test message";
        ArrayList<ConversationHistory> conversationHistory = new ArrayList<>();
        int conversationId = 1;
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage, conversationHistory, conversationId);
        });
    }
    @Test
    public void testGetChatResponseThrowsErrorIfResponseBodyIsNull(){

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(null , HttpStatus.OK));
        String userMessage = "This is a test message";
        ArrayList<ConversationHistory> conversationHistory = new ArrayList<>();
        int conversationId = 1;
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage, conversationHistory, conversationId);
        });
    }
    @Test
    public void testGetChatResponseThrowsErrorIfResponseBodyIsNull(){

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(null , HttpStatus.OK));
        String userMessage = "This is a test message";
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage);
        });
    }
}

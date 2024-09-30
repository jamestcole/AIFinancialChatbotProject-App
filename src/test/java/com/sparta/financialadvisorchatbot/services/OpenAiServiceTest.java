package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.exceptions.ResponseParsingError;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@EnableConfigurationProperties(value = {OpenAiService.class})
@TestPropertySource(properties = {
        "openai.api.key=test-api-key",
        "openai.api.endpoint=test-endpoint",
        "openai.api.prompt=test-prompt"
})
@ExtendWith(MockitoExtension.class)
public class OpenAiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenAiService openAiService;

    @Test
    public void testGetChatResponseIsSuccessful(){
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Open AI Test Response\"}}]}";
        String expected = "Open AI Test Response";

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String userMessage = "This is a test message";
        String actual = openAiService.getResponse(userMessage);
        Assertions.assertEquals(expected,actual);
    }
    @Test
    public void testGetChatResponseThrowsUnableToParseExceptionIfUnableToParse(){
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Open AI Test Response\"}}}]}";

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String userMessage = "This is a test message";
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage);
        });
    }
    @Test
    public void testGetChatResponseThrowsErrorIfRequestNotSuccessful(){

        Mockito.when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        String userMessage = "This is a test message";
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage);
        });
    }
}

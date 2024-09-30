package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.exceptions.ResponseParsingError;
import com.sparta.financialadvisorchatbot.service.OpenAiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = {
        "openai.api.key=test-api-key"
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

        Mockito.when(restTemplate.postForEntity(Mockito.any(String.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String userMessage = "This is a test message";
        String actual = openAiService.getResponse(userMessage).getResponse();
        Assertions.assertEquals(expected,actual);
    }
    @Test
    public void testGetChatResponseThrowsUnableToParseExceptionIfUnableToParse(){
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Open AI Test Response\"}}}]}";

        Mockito.when(restTemplate.postForEntity(Mockito.any(String.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String userMessage = "This is a test message";
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage);
        });
    }
    @Test
    public void testGetChatResponseThrowsErrorIfRequestNotSuccessful(){
        Mockito.when(restTemplate.postForEntity(Mockito.any(String.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        String userMessage = "This is a test message";
        Assertions.assertThrows(ResponseParsingError.class, () -> {
            openAiService.getResponse(userMessage);
        });
    }
}

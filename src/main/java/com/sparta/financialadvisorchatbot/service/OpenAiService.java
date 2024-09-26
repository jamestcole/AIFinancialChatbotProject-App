package com.sparta.financialadvisorchatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String getChatResponse(String userInput){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("model", "gpt-4o");
        requestBody.put("messages", List.of(
                Map.of("role","system","content",""),
                Map.of("role","user","content",userInput)
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            //parse Json object to get content
        }
        return ""; // return response
    }
}

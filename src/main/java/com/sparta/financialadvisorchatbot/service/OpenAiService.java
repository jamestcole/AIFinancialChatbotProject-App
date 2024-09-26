package com.sparta.financialadvisorchatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.financialadvisorchatbot.exceptions.ResponseParsingError;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RestTemplate restTemplate;

    @Autowired
    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getChatResponse(String userInput, String chatPrompt){

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        userInput = InputValidation.validateInput(userInput);

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("model", "gpt-4o");
        requestBody.put("messages", List.of(
                Map.of("role","system","content",chatPrompt),
                Map.of("role","user","content",userInput)
        ));
        requestBody.put("max_tokens", 100);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);
        if(response.getStatusCode().is2xxSuccessful()){

            //todo change to create new GptResponseModel ie. GptResponseModel chatResponse = new GptResponseModel(params from response)

            try{
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getBody());
                return jsonNode.path("choices").get(0).path("message").path("content").asText();
            }
            catch (JsonProcessingException e){
                throw new ResponseParsingError("ERR: Unable to read chatbot response");
            }
        }
        return "ERR: Error while generating chat response"; // return response
    }
}

package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.exceptions.ResponseParsingError;
import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.models.OpenAiRequest;
import com.sparta.financialadvisorchatbot.models.OpenAiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiService {
    private final RestTemplate restTemplate;
    private final ConversationFormatter conversationFormatter;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.endpoint}")
    private String endpoint;

    @Value("${openai.api.prompt}")
    private String prompt;

    @Autowired
    public OpenAiService(RestTemplate restTemplate, ConversationFormatter conversationFormatter) {
        this.restTemplate = restTemplate;
        this.conversationFormatter = conversationFormatter;
    }

    public String getResponse(String userInput, List<ConversationHistory> messageHistory, Integer conversationId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("api-key", apiKey);

        String input = conversationFormatter.formatConversation(messageHistory) + "\nUser: " + userInput;

        OpenAiRequest body = new OpenAiRequest();
        //todo: send the conversation history with the prompt
        List<OpenAiRequest.Message> messages = new ArrayList<>();
        messages.add(new OpenAiRequest.Message("system", prompt));
        messages.add(new OpenAiRequest.Message("user", input));

        body.setMessages(messages);

        HttpEntity<OpenAiRequest> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<OpenAiResponse> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, OpenAiResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            List<OpenAiResponse.Choice> choices = responseEntity.getBody().getChoices();
            if (!choices.isEmpty()) {
                return choices.get(0).getMessage().getContent();
            } else {
                throw new ResponseParsingError("No choices found in response from OpenAI API.");
            }
        } else {
            throw new ResponseParsingError("Failed to get response from OpenAI API: " + responseEntity.getStatusCode());
        }
    }
}

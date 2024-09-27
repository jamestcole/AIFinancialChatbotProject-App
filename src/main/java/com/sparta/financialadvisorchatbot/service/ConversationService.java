package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.models.ConversationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConversationService {

    private final FaqService faqService;
    private final OpenAiService openAIService;

    @Autowired
    public ConversationService(FaqService faqService, OpenAiService openAIService) {
        this.faqService = faqService;
        this.openAIService = openAIService;
    }

    //Hello, how can I help you today?
    //2. First user input (first prompt)
    //->FaqService find closest FAQ match -> send to controller
    //3. Does this answer your question?
    //Yes = Can I help you with anything else? Return to 2.
    //No = Could you please be a little more specific?
    //User input
    //Get GPT response, return to 3.

    public String processUserInput(String userInput) {
        String faqAnswer = faqService.findClosestFaq(userInput);

        if (faqAnswer != null) {
            return faqAnswer + "\n\nDoes this answer your question? (Yes/No)";
        } else {
            return "Could you please be a little more specific?";
        }
    }

    public String handleUserResponse(String userResponse, String originalInput) {
        if ("Yes".equalsIgnoreCase(userResponse)) {
            return "Can I help you with anything else?";
        } else if ("No".equalsIgnoreCase(userResponse)) {
            String gptResponse = openAIService.getChatResponse(originalInput, "temp chat prompt");
        return gptResponse + "\n\nDoes this answer your question? (Yes/No)";
        } else {
            return "Sorry, I didn't understand that. Please respond with Yes or No.";
        }
    }
}

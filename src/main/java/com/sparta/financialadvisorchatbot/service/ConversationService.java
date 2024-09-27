package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.models.ConversationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConversationService {
    /*

    private final FaqService faqService;
    private final OpenAiService openAIService;

    @Autowired
    public ConversationService(FaqService faqService, OpenAiService openAIService) {
        this.faqService = faqService;
        this.openAIService = openAIService;
    }

    //Temporary hashmap (replace with post to SQL database)
    private Map<String, ConversationState> userSessions = new HashMap<>();

    //Process user input and manage conversation state
    public String processUserInput(String userId, String userInput) throws IOException {
        //Retrieve conversation state -> create new one if it does not exist
        ConversationState state = userSessions.getOrDefault(userId, new ConversationState());

        if (state.isAwaitingClarification()) {
            //If chatbot is awaiting clarification - use next response with GPT model
            String gptResponse = openAIService.getChatResponse(userInput,"You are a financial advisor");
            state.setAwaitingClarification(false);
            state.setLastResponse(gptResponse);
            userSessions.put(userId, state);
            return gptResponse + "\nCan I help you with anything else?";
        }

        //Try finding the closest FAQ match
        String faqAnswer = faqService.findClosestFaq(userInput);

        if (faqAnswer != null) {
            //Save the response (replace with DB) and update state
            state.setLastResponse(faqAnswer);
            userSessions.put(userId, state);
            return faqAnswer + "\nCan I help you with anything else?";
        } else {
            //Ask for clarification if no FAQ match is found
            state.setLastQuestion(userInput);
            state.setAwaitingClarification(true);
            userSessions.put(userId, state);
            return "I couldn't find an exact match. Could you please be more specific about your question?";
        }
    }

    public String processClarification(String userId, String userInput) throws IOException {
        ConversationState state = userSessions.get(userId);
        if (state == null || !state.isAwaitingClarification()) {
            return "I need more context for your query. Could you please start over?";
        }

        String gptResponse = openAIService.getChatResponse(userInput, "You are a financial advisor");
        state.setAwaitingClarification(false);
        state.setLastResponse(gptResponse);
        userSessions.put(userId, state);
        return gptResponse + "\nCan I help you with anything else?";
    }
     */
}

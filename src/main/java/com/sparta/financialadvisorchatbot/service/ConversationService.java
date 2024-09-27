package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.Conversation;
import com.sparta.financialadvisorchatbot.entities.Question;
import com.sparta.financialadvisorchatbot.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConversationService {

    private final FaqService faqService;
    private final OpenAiService openAIService;
    private final ConversationRepository conversationRepository;

    @Autowired
    public ConversationService(FaqService faqService, OpenAiService openAIService, ConversationRepository conversationRepository) {
        this.faqService = faqService;
        this.openAIService = openAIService;
        this.conversationRepository = conversationRepository;
    }

    //Hello, how can I help you today?
    //2. First user input (first prompt)
    //->FaqService find closest FAQ match -> send to controller
    //3. Does this answer your question?
    //Yes = Can I help you with anything else? Return to 2.
    //No = Could you please be a little more specific?
    //User input
    //Get GPT response, return to 3.


    //1. hi how can i help
    //2 user inputs
    public String processUserInput(String userInput) {
        //3. check faq
        ArrayList<Question> faqAnswer = faqService.getFAQs(userInput);

        //4. if match, give
        if (faqAnswer != null) {
            return faqAnswer + "\n\nDoes this answer your question? (Yes/No)";
        } else {
            //if not, ask for clarification
            return "Could you please be a little more specific?";
            //pass it to the next tier
        }
    }

    public String handleUserResponse(String userResponse, String originalInput) {
        if ("Yes".equalsIgnoreCase(userResponse)) {
            return "Can I help you with anything else?"; //return to 2.
        } else if ("No".equalsIgnoreCase(userResponse)) {
            //Could you please clarify your question please
            //user input? -> Gpt-> return response
            String gptResponse = openAIService.getMockGptResponse(originalInput);
        return gptResponse + "\n\nDoes this answer your question? (Yes/No)";


        } else {
            return "Sorry, I didn't understand that. Please respond with Yes or No.";
        }
    }

    public Conversation getLatestConversation() {
        return conversationRepository.findFirstByOrderByIdDesc();
    }

    public Integer getLatestConversationId() {
        Conversation latestConversation = getLatestConversation();
        return latestConversation !=  null ? latestConversation.getId() : null;
    }

    public Conversation getConversationById(Integer conversationId) {
        return conversationRepository.findById(conversationId).orElse(null);
    }

    public String getStartMessage() {
        return "Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?";
    }
}

package com.sparta.financialadvisorchatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConversationService {
    private final FaqService faqService;
    private final OpenAiService openAiService;

    public ConversationService(FaqService faqService, OpenAiService openAiService) {
        this.faqService = faqService;
        this.openAiService = openAiService;
    }

    public String handleUserInput(String userInput) {
        String faqAnswer = faqService.findClosestFaq(userInput);
        return Objects.requireNonNullElse(faqAnswer, "Please could you be a little more specific?");
    }

    public String handleGptInput(String userInput) {
        return OpenAiService.getChatCompletion(userInput);
    }
}
